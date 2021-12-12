package edu.cnm.deepdive.interviewprep.service;

import edu.cnm.deepdive.interviewprep.model.Question;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class QuizRepository {

  private final WebServiceProxy proxy;
  private final GoogleSignInRepository signInRepository;

  public QuizRepository() {
    proxy = WebServiceProxy.getInstance();
//    CodebreakerDatabase database = CodebreakerDatabase.getInstance();
    signInRepository = GoogleSignInRepository.getInstance();

  }

  public Single<Question> getRandomQuestion() {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.getRandomQuestion(token))
        .subscribeOn(Schedulers.io());
  }

}