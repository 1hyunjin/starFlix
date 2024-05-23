package com.ssafy.starflix.api.model.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GptRequest implements Serializable {

	// chatGPT에 요청할 DTO format
	private String model;

	@JsonProperty("max_tokens")
	private Integer maxTokens;

	private Double temperature;

	private Boolean stream;

	private List<ChatGptMessage> messages;

	@Builder
	public GptRequest(String model, Integer maxTokens, Double temperature, Boolean stream,
			List<ChatGptMessage> messages) {
		this.model = model;
		this.maxTokens = maxTokens;
		this.temperature = temperature;
		this.stream = stream;
		this.messages = messages;
	}
}
