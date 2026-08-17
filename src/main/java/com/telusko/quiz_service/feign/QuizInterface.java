package com.telusko.quiz_service.feign;

import com.telusko.quiz_service.dto.QuestionWrapper;
import com.telusko.quiz_service.dto.QuizResultResponse;
import com.telusko.quiz_service.model.QuizAnswer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("api/v1/quiz")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQ);

    @PostMapping("api/v1/questions/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsIds);

    @PostMapping("api/v1/quiz/getScore")
    public ResponseEntity<QuizResultResponse> getScore(@RequestBody List<QuizAnswer> responses);
}
