package com.example.questionservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.questionservice.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer>{

	List<QuestionEntity> findByCategory(String category);
	
    @Query(value = "SELECT id FROM question_entity q WHERE q.category = :category ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Integer> findRandomQuestions(@Param("category") String category, @Param("limit") int numQ);
    
}
