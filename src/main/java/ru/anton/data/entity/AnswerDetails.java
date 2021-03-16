package ru.anton.data.entity;

import ru.anton.data.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AnswerDetails extends AbstractEntity{

    @Column
    private Boolean status;
    @Column
    private String textQuestion;

    public AnswerDetails(Boolean status, String textQuestion) {

        this.status = status;
        this.textQuestion = textQuestion;
    }

    public AnswerDetails() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

}
