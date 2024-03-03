package com.example.questionservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionservice.dto.QuestionWrapper;
import com.example.questionservice.dto.ResponseDTO;
import com.example.questionservice.entity.QuestionEntity;
import com.example.questionservice.service.QuestionService;

@RestController
@RequestMapping("question/")
public class QuestionsController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	Environment environment;

	@GetMapping("allquestion")
	public List<QuestionEntity> getAllQuestions() {
		return questionService.getAllQuestions();
	}

	@GetMapping("{category}")
	public List<QuestionEntity> getAllQuestionsByCategory(@PathVariable("category") String category) {
		return questionService.getAllQuestionsByCategory(category);
	}

	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody QuestionEntity question) {
		return questionService.addQuestion(question);
	}

	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,
			@RequestParam Integer numQuestions) {
		return questionService.getQuestionsForQuiz(categoryName, numQuestions);
	}
	
//	@GetMapping("getQuestions///[questionIds]")
//	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@PathVariable String questionIds) {

	
	
//	[]
//			 ^] with root cause
//
//			java.util.regex.PatternSyntaxException: Unclosed character class near index 1
//			[]
//			 ^
//				at java.base/java.util.regex.Pattern.error(Pattern.java:2028) ~[na:na]
//				at java.base/java.util.regex.Pattern.clazz(Pattern.java:2690) ~[na:na]
//				at java.base/java.util.regex.Pattern.sequence(Pattern.java:2139) ~[na:na]
//
//	@GetMapping("getQuestions///{questionIds}")
//	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@PathVariable String questionIds) {
//		System.out.println(questionIds);
//		String[] questionStringArray = questionIds.replaceAll("[]", "").split(",");

	@GetMapping("getQuestions///{questionIds}")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@PathVariable String questionIds) {
		System.out.println(questionIds);
		String[] questionStringArray = questionIds.replaceAll("[{}]", "").split(",");
		List<Integer> questionIdIs = Arrays.stream(questionStringArray)
				.map(String::trim)
				.map(Integer::parseInt)
				.collect(Collectors.toList());
		
		return questionService.getQuestionsById(questionIdIs);
	}
	
//	@PostMapping("getQuestions///")
//	public ResponseEntity<List<QuestionWrapper>> getPostQuestionsById(@RequestBody List<Integer> questionsIds) {
//		return questionService.getQuestionsById(questionsIds);
//	}
	
	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getPostQuestionsById(@RequestBody List<Integer> questionsIds) {
		System.out.println(environment.getProperty("local.server.port"));
		return questionService.getQuestionsById(questionsIds);
	}
	
	@PostMapping("getScores")
	public ResponseEntity<Integer> getScores(@RequestBody List<ResponseDTO> responses) {
		return questionService.getScores(responses);
	}
	
	

}
