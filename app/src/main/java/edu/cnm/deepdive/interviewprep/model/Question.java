package edu.cnm.deepdive.interviewprep.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class Question implements Comparable<Question> {

  @Expose
  private UUID id;

  @Expose
  private String question;

  @Expose
  private String answer;

  @Expose
  private String source;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public int compareTo(Question other) {
    return this.question.compareToIgnoreCase(other.question);
  }
}
