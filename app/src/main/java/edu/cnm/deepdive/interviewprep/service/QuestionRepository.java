package edu.cnm.deepdive.interviewprep.service;

import edu.cnm.deepdive.interviewprep.model.Question;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

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
        .flatMap(proxy::getQuestions)
        .subscribeOn(Schedulers.io());
  }

  public Single<Question> getQuestion(UUID questionId) {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.getQuestion(questionId, token))
        .subscribeOn(Schedulers.io());
  }

  public Single<Question> updateQuestion(Question question) {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.updateQuestion(question.getId(),question, token))
        .subscribeOn(Schedulers.io());

  }

  public Single<Question> createQuestion(Question question) {
    return signInRepository
        .refreshBearerToken()
        .flatMap((token) ->
            proxy.createQuestion(question, token))
        .subscribeOn(Schedulers.io());

  }

  public Completable deleteQuestion(UUID questionId) {
    return signInRepository
        .refreshBearerToken()
        .flatMapCompletable((token) ->
            proxy.deleteQuestion(questionId, token))
        .subscribeOn(Schedulers.io());
  }

}
