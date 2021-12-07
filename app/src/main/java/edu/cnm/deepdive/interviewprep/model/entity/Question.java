package edu.cnm.deepdive.interviewprep.model.entity;

public class Question {
private long id;
private String questionText;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getQuestionText() {
    return questionText;
  }

  public void setQuestionText(String questionText) {
    this.questionText = questionText;
  }

  public String getQuestionAnswer() {
    return questionAnswer;
  }

  public void setQuestionAnswer(String questionAnswer) {
    this.questionAnswer = questionAnswer;
  }

  private String questionAnswer;


}
