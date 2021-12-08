package edu.cnm.deepdive.interviewprep.service;

import edu.cnm.deepdive.interviewprep.model.entity.Question;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class QuestionRepository {

  private final WebServiceProxy proxy;
  private final GoogleSignInRepository signInRepository;

  public QuestionRepository() {
    proxy = WebServiceProxy.getInstance();
//    CodebreakerDatabase database = CodebreakerDatabase.getInstance();
    signInRepository = GoogleSignInRepository.getInstance();

  }

  public Single<List<Question>> getQuestions() {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.getQuestions(token))
        .subscribeOn(Schedulers.io());
  }

}
