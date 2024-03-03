package com.example.questionservice.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.questionservice.dto.QuestionWrapper;
import com.example.questionservice.dto.ResponseDTO;
import com.example.questionservice.entity.QuestionEntity;
import com.example.questionservice.repo.QuestionRepository;
import com.example.questionservice.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public List<QuestionEntity> getAllQuestions() {
		return questionRepository.findAll();
	}

	@Override
	public List<QuestionEntity> getAllQuestionsByCategory(String category) {
		return questionRepository.findByCategory(category);
	}

	@Override
	public ResponseEntity<String> addQuestion(QuestionEntity question) {
		questionRepository.saveAndFlush(question);
//		return new ResponseEntity<String>("success",HttpStatus.CREATED);
		return ResponseEntity.status(HttpStatus.CREATED).body("success");
	}

	@Override
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
//		return new ResponseEntity<List<Integer>>(questionRepository.findRandomQuestions(categoryName, numQuestions),HttpStatus.OK);
		return ResponseEntity.ok(questionRepository.findRandomQuestions(categoryName, numQuestions));
	}

	@Override
	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> questionIds) {
		List<QuestionWrapper> questionWrappers = questionRepository.findAllById(questionIds).stream()
				.filter(t -> t != null)
				.map(question -> new QuestionWrapper(question.getId(), question.getQuestionTitle(),
						question.getOption1(), question.getOption2(), question.getOption3(), question.getOptions4()))
				.collect(Collectors.toList());
		return ResponseEntity.ok(questionWrappers);
	}

	@Override
	public ResponseEntity<Integer> getScores(List<ResponseDTO> responses) {
		Map<Integer, String> questionIdToRightAnswer = questionRepository
				.findAllById(responses.stream().map(ResponseDTO::getId).collect(Collectors.toList())).stream()
				.collect(Collectors.toMap(QuestionEntity::getId, QuestionEntity::getRightAnswer));

		long right = responses.stream().filter(response -> questionIdToRightAnswer.containsKey(response.getId())
				&& response.getResponse().equals(questionIdToRightAnswer.get(response.getId()))).count();
		return ResponseEntity.ok(Math.toIntExact(right));
	}

}
