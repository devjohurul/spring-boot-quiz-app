package com.johurulislam.model;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class QuesFrom {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
