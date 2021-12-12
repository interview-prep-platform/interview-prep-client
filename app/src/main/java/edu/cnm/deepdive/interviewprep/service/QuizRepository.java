package edu.cnm.deepdive.interviewprep.service;

import edu.cnm.deepdive.interviewprep.model.Question;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Encapsulates a persistent QuizRepository object that allows the
 * Quiz ViewModel to interact with the Web Service Proxy to create, read, insert,
 * and update data to the server.
 */
public class QuizRepository {

  private final WebServiceProxy proxy;
  private final GoogleSignInRepository signInRepository;


  /**
   * Constructor for class that instantiates local fields.
   */
  public QuizRepository() {
    proxy = WebServiceProxy.getInstance();
    signInRepository = GoogleSignInRepository.getInstance();
  }

  /**
   * Gets a random {@link Question} from the server.
   * @return A random {@link Question} object in the form of a ReactiveX {@link Single}.
   *
   */
  public Single<Question> getRandomQuestion() {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.getRandomQuestion(token))
        .subscribeOn(Schedulers.io());
  }

}