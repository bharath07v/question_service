package com.example.questionservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseDTO {
	
	private Integer id;
	private String response;
	
}
