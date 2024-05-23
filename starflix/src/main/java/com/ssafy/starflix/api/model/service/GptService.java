package com.ssafy.starflix.api.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.starflix.api.model.dto.ChatGptMessage;
import com.ssafy.starflix.api.model.dto.GptRequest;
import com.ssafy.starflix.api.model.dto.GptResponse;
import com.ssafy.starflix.api.model.dto.GptReviewDTO;
import com.ssafy.starflix.config.GptConfig;
import com.ssafy.starflix.review.model.dto.ReviewDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GptService {

	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER = "Bearer ";

	public static final String CHAT_MODEL = "gpt-3.5-turbo";
	public static final Integer MAX_TOKEN = 500;
	public static final Boolean STREAM = false;
	public static final Double TEMPERATURE = 0.5;
	public static final String MEDIA_TYPE = "application/json";
	// completions : 질답
	public static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";

	public final RestTemplate restTemplate;

	@Value("${openai.apiKey}")
	private String apiKey;

	public GptReviewDTO generateAnswers(List<ReviewDTO> reviewList) {

		StringBuilder sb = new StringBuilder();

		for (ReviewDTO reviewDTO : reviewList) {
			sb.append(reviewDTO.getContent() + "\n");
		}

		System.out.println(sb.toString());

		List<ChatGptMessage> messages = new ArrayList<>();

		messages.add(ChatGptMessage.builder().role("system").content(GptConfig.SYSTEM_CONTENT).build());
		messages.add(ChatGptMessage.builder().role("user").content(GptConfig.USER_CONTENT).build());
		messages.add(ChatGptMessage.builder().role("assistant").content(GptConfig.ASSISTANT_CONTENT).build());
		messages.add(ChatGptMessage.builder().role("user").content(sb.toString()).build());
		GptRequest request = new GptRequest(CHAT_MODEL, MAX_TOKEN, TEMPERATURE, STREAM, messages);

		// header 설정 및 GPT API 호출
		HttpHeaders headers = new HttpHeaders();
		headers.add(AUTHORIZATION, BEARER + apiKey);
		headers.add("Content-Type", MEDIA_TYPE);

		System.out.println("API Key: " + apiKey);

		HttpEntity<GptRequest> requestHttpEntity = new HttpEntity<>(request, headers);

		GptResponse response = restTemplate.postForObject(CHAT_URL, requestHttpEntity, GptResponse.class);

		System.out.println(response);

		String content = response.getChoices().get(0).getMessage().getContent();

		System.out.println("content : " + content);

		GptReviewDTO gptReviewDTO = new GptReviewDTO();

		String[] lines = content.split("\\n");

		// 각 줄을 반복하면서 필드에 해당하는 값을 추출하여 GptReviewDTO 객체에 설정합니다.
		for (String line : lines) {
			// 각 줄을 ":"를 기준으로 분할하여 필드 이름과 값으로 나눕니다.
			String[] parts = line.split(":");
			if (parts.length == 2) {
				String fieldName = parts[0].trim();
				String value = parts[1].trim();
				// 필드 이름에 따라 적절한 필드에 값을 설정합니다.
				switch (fieldName) {
				case "rate":
					gptReviewDTO.setRate(Double.parseDouble(value));
					break;
				case "summary":
					gptReviewDTO.setSummary(value);
					break;
				case "pros":
					gptReviewDTO.setPros(value);
					break;
				case "cons":
					gptReviewDTO.setCons(value);
					break;
				default:
					// 다른 필드는 무시합니다.
					break;
				}
			}
		}
		// 수정된 메서드는 GptReviewDTO 객체를 반환합니다.
		System.out.println(gptReviewDTO);
		return gptReviewDTO;
//		return content;
	}
}
