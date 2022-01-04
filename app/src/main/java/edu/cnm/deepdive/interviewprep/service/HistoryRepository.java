package edu.cnm.deepdive.interviewprep.service;

import edu.cnm.deepdive.interviewprep.model.History;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.UUID;

/**
 * Encapsulates a persistent HistoryRepository object that allows the
 * History ViewModel to interact with the Web Service Proxy to create, read, insert,
 * and update data to the server.
 */
public class HistoryRepository {

  private final WebServiceProxy proxy;
  private final GoogleSignInRepository signInRepository;

  /**
   * Constructor for class that instantiates local fields.
   */
  public HistoryRepository() {
    proxy = WebServiceProxy.getInstance();
    signInRepository = GoogleSignInRepository.getInstance();
  }

  /**
   * Gets a list of all {@link History}s from the server.
   * @return A list of {@link History} objects in the form of a ReactiveX {@link Single}.
   */
  public Single<List<History>> getHistories(UUID questionId) {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) -> proxy.getHistories(questionId, token))
        .subscribeOn(Schedulers.io());
  }


  /**
   * Creates a new {@link History} in the server.
   * @param history A history object.
   * @return A newly created {@link History} object in the form of a ReactiveX {@link Single}.
   */
  public Single<History> createHistory(History history) {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.createHistory(history, history.getQuestion().getId(), token))
        .subscribeOn(Schedulers.io());

  }

}
