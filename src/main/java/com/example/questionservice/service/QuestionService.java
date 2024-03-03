package com.example.questionservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.questionservice.dto.QuestionWrapper;
import com.example.questionservice.dto.ResponseDTO;
import com.example.questionservice.entity.QuestionEntity;

public interface QuestionService {
	
	List<QuestionEntity> getAllQuestions();
	
	List<QuestionEntity> getAllQuestionsByCategory(String category);

	ResponseEntity<String> addQuestion(QuestionEntity question);

	ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions);

	ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> questionIds);

	ResponseEntity<Integer> getScores(List<ResponseDTO> responses);

}
