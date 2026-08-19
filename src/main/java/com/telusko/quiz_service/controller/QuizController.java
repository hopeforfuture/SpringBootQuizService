package com.telusko.quiz_service.controller;

import com.telusko.quiz_service.dto.QuestionWrapper;
import com.telusko.quiz_service.dto.QuizDto;
import com.telusko.quiz_service.dto.QuizResultResponse;
import com.telusko.quiz_service.model.QuizAnswer;
import com.telusko.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping()
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("id") Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<QuizResultResponse> submitQuiz(
            @PathVariable Integer id,
            @RequestBody List<QuizAnswer> quizAnswers) {

        return quizService.calculateResult(id, quizAnswers);
    }

}
