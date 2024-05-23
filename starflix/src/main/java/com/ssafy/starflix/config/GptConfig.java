package com.ssafy.starflix.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GptConfig {
	
	public static final String SYSTEM_CONTENT = "입력으로 어떤 장소에 대한 리뷰 리스트가 주어집니다. 리스트는 '제목'과 '내용'으로 이루어져 있습니다. 리뷰 리스트를 종합하여 이 장소에 대한 요약을 정리합니다.\n"
			+ "요약에 포함되는 데이터로는 [1]1~5점 사이의 소수점 한자리 까지의 평점, [2]요약, [3]장점, [4]단점 네 가지가 있습니다." 
			+ "[1] 평점 : 1~5사이의 소수점 한 자리까지의 점수입니다. 부정적인 의견과 긍정적인 의견을 종합하여 평점을 정하세요.\n"
			+ "[2] 요약 : 짧고 간략하게 생성합니다. 최대 5문장을 넘지 않아야하며 각 문장은 20자 이내로 작성합니다.\n"
			+ "[3] 장점 : 리뷰에서 긍정적으로 나타내고 있는 부분들을 키워드와 같이 추출하여 생성합니다.\n"
			+ "[4] 단점 : 리뷰에서 부정적으로 나타내고 있는 부분들을 키워드와 같이 추출하여 생성합니다.\n" 
			+ "rate는 평점, summary는 요약, pros는 장점, cons는 단점입니다.";
	
	public static final String USER_CONTENT = "전망이 너무 멋졌지만, 서비스가 상당히 느렸어요. 전반적으로는 즐거운 경험이었습니다.\n"
			+ "<p>시설은 최고 수준이었고 직원들은 정말 친절했습니다. 하지만 위치가 조금 외진 편이었습니다.</p>\n"
			+ "음식은 맛있었지만 청결도는 많이 부족했습니다. 그래도 분위기는 아늑했습니다.\n"
			+ "접근성은 큰 문제였어요, 심지어 차가 있어도요. 하지만 주변은 아름다웠습니다.";
	
	public static final String ASSISTANT_CONTENT = "rate: 3.5\n"
												+ "summary: 전망 멋짐, 시설 최고, 분위기 아늑, 주변 아름다움\n"
												+ "pros: 전망 멋짐, 시설 최고, 직원 친절, 분위기 아늑\n"
												+"cons:서비스 느림, 위치 외진, 청결도 부족, 접근성 문제";
	
	@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5000))
                .setReadTimeout(Duration.ofSeconds(5000))
                .additionalInterceptors(clientHttpRequestInterceptor())
                .build();
    }
	
	public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
        return ((request, body, execution) -> {
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(2));
            try {
                return retryTemplate.execute(context -> execution.execute(request, body));
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }
}
