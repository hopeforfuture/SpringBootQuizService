package com.telusko.quiz_service.service;

import com.telusko.quiz_service.dao.QuizDao;
import com.telusko.quiz_service.dto.QuestionWrapper;
import com.telusko.quiz_service.dto.QuizResultResponse;
import com.telusko.quiz_service.feign.QuizInterface;
import com.telusko.quiz_service.model.Quiz;
import com.telusko.quiz_service.model.QuizAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
          Quiz quiz = quizDao.findById(id).get();
          List<Integer> questionIds = quiz.getQuestionIds();
          List<QuestionWrapper> questions = quizInterface.getQuestionsFromId(questionIds).getBody();
          return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<QuizResultResponse> calculateResult(
            Integer id,
            List<QuizAnswer> quizAnswers) {

        int marks = Objects.requireNonNull(quizInterface.getScore(quizAnswers).getBody()).getMarks();
        QuizResultResponse response =
                new QuizResultResponse(
                        "Quiz submitted successfully",
                        marks
                );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
