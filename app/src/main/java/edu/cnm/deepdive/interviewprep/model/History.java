package edu.cnm.deepdive.interviewprep.model;

import com.google.gson.annotations.Expose;
import java.util.UUID;

/**
 * Encapsulates a {@link History} object
 */

public class History {

  @Expose
  private UUID id;

  @Expose
  private String answer;

  @Expose
  private Question question;

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

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
