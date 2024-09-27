package com.johurulislam.service;

import com.johurulislam.model.Question;
import com.johurulislam.model.Result;
import com.johurulislam.repository.QuestionRepository;
import com.johurulislam.repository.ResultRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {
    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;
    public QuizService(QuestionRepository questionRepository, ResultRepository resultRepository) {
        this.questionRepository = questionRepository;
        this.resultRepository = resultRepository;
    }
    public List<Question> getQuestions() {
        List<Question> questions=questionRepository.findAll();
        List<Question> selectedQuestions=new ArrayList<>();
        Random random=new Random();
        for(int i=0;i<5;i++){
            int randomnumber=random.nextInt(questions.size());
            selectedQuestions.add(questions.get(randomnumber));
            questions.remove(randomnumber);
        }
        return selectedQuestions;
    }
    public int getResult(List<Question>questionList) {
        int score=0;
        for(Question question: questionList) {
            if(question.getAns()==question.getChosen()) score++;
        }
        return score;
    }
    public void saveResult(Result result) {
        resultRepository.save(result);
    }
    public List<Result> getAllResults() {
        return resultRepository.findAll(Sort.by(Sort.Direction.DESC,"totalCorrect"));
    }
}
