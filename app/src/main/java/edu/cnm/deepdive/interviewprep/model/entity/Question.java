package edu.cnm.deepdive.interviewprep.model.entity;

import com.google.gson.annotations.Expose;

public class Question {

  private long id;
  @Expose
  private String question;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getQuestionText() {
    return question;
  }

  public void setQuestionText(String questionText) {
    this.question = question;
  }

  public String getQuestionAnswer() {
    return questionAnswer;
  }

  public void setQuestionAnswer(String questionAnswer) {
    this.questionAnswer = questionAnswer;
  }

  private String questionAnswer;

}
