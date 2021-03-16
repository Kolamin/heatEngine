package ru.anton.data.entity;

import ru.anton.data.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CorrectAnswer extends AbstractEntity {

    @Column(length = 2048)
    private String correctAnswer;

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String answer) {
        this.correctAnswer = answer;
    }

    protected CorrectAnswer() {
    }

    public CorrectAnswer( String answer) {
        this.correctAnswer = answer;
    }

}
