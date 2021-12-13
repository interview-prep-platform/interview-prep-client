package edu.cnm.deepdive.interviewprep.service;

import edu.cnm.deepdive.interviewprep.model.Question;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

/**
 * Encapsulates a persistent QuestionRepository object that allows the
 * Question ViewModel to interact with the Web Service Proxy to create, read, insert,
 * and update data to the server.
 */
public class QuestionRepository {

  private final WebServiceProxy proxy;
  private final GoogleSignInRepository signInRepository;

  /**
   * Constructor for class that instantiates local fields.
   */
  public QuestionRepository() {
    proxy = WebServiceProxy.getInstance();
    signInRepository = GoogleSignInRepository.getInstance();
  }

  /**
   * Gets a list of all {@link Question}s from the server.
   * @return A list of {@link Question} objects in the form of a ReactiveX {@link Single}.
   */
  public Single<List<Question>> getQuestions() {
    return signInRepository
        .refreshBearerToken()
        .flatMap(proxy::getQuestions)
        .subscribeOn(Schedulers.io());
  }

  /**
   * Gets a single {@link Question} from the server.
   * @param questionId A question id in the form of a universally unique identifier.
   * @return A single {@link Question} object in the form of a ReactiveX {@link Single}.
   *
   */
  public Single<Question> getQuestion(UUID questionId) {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.getQuestion(questionId, token))
        .subscribeOn(Schedulers.io());
  }

  /**
   * Updates a single {@link Question} from the server.
   * @param question A question object.
   * @return An updated {@link Question} object in the form of a ReactiveX {@link Single}.
   */
  public Single<Question> updateQuestion(Question question) {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.updateQuestion(question.getId(),question, token))
        .subscribeOn(Schedulers.io());

  }

  /**
   * Creates a new {@link Question} in the server.
   * @param question A question object.
   * @return A newly created {@link Question} object in the form of a ReactiveX {@link Single}.
   */
  public Single<Question> createQuestion(Question question) {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.createQuestion(question, token))
        .subscribeOn(Schedulers.io());

  }

  /**
   * Deletes a specified {@link Question} from the server.
   * @param questionId A question id in the form of a universally unique identifier.
   * @return A ReactiveX {@link Completable}.
   */
  public Completable deleteQuestion(UUID questionId) {
    return signInRepository
        .refreshBearerToken()
        .flatMapCompletable((token) ->
            proxy.deleteQuestion(questionId, token))
        .subscribeOn(Schedulers.io());
  }

}
