package edu.cnm.deepdive.interviewprep.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;

/**
 * Encapsulates a {@link Question} object
 */

public class Question implements Comparable<Question> {

  @Expose
  private UUID id;

  @Expose
  private String question;

  @Expose
  private String answer;

  @Expose
  private String source;

  @Expose
  private String userAnswer;

  /**
   * Returns a unique identifier for this instance.
   */
  public UUID getId() {
    return id;
  }

  /**
   * Sets a unique identifier for this instance.
   */
  public void setId(UUID id) {
    this.id = id;
  }


  /**
   * Returns a question in the form of a string.
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Sets a question in the form of a string.
   */
  public void setQuestion(String question) {
    this.question = question;
  }


  /**
   * Returns an answer in the form of a string.
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * Sets an answer in the form of a string.
   */
  public void setAnswer(String answer) {
    this.answer = answer;
  }

  /**
   * Returns a source in the form of a string.
   */
  public String getSource() {
    return source;
  }

  /**
   * Sets a source in the form of a string.
   */
  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public int compareTo(Question other) {
    return this.question.compareToIgnoreCase(other.question);
  }

  public String getUserAnswer() {
    return userAnswer;
  }

  public void setUserAnswer(String userAnswer) {
    this.userAnswer = userAnswer;
  }
}
