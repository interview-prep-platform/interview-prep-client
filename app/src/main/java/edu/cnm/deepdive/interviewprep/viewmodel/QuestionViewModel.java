package edu.cnm.deepdive.interviewprep.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.interviewprep.model.Question;
import edu.cnm.deepdive.interviewprep.service.QuestionRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class QuestionViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final QuestionRepository repository;
  private final MutableLiveData<List<Question>> questions;
  private final MutableLiveData<Question> question;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public QuestionViewModel(@NonNull Application application) {
    super(application);
    repository = new QuestionRepository();
    questions = new MutableLiveData<>();
    question = new MutableLiveData<>();
//    rankings = new RankingLiveData(trigger);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    refreshQuestions();
  }

  public LiveData<List<Question>> getQuestions() {
    return questions;
  }

  public LiveData<Question> getQuestion() {
    return question;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void refreshQuestions() {
    pending.add(
        repository
            .getQuestions()
            .subscribe(
                questions::postValue,
                this::postThrowable
            )
    );
  }

  public void updateQuestion(Question question) {
    pending.add(
        repository
            .updateQuestion(question)
            .subscribe(
                this.question::postValue,
                this::postThrowable
            )
    );
  }

  public void createQuestion(Question question) {
    pending.add(
        repository
            .createQuestion(question)
            .subscribe(
                this.question::postValue,
                this::postThrowable
            )
    );
  }

  public void refreshQuestion(String questionId) {
    pending.add(
        repository
            .getQuestion(questionId)
            .subscribe(
                question::postValue,
                this::postThrowable
            )
    );
  }

  public void deleteQuestion(String questionId) {
//    pending.add(
//        repository
//            .deleteQuestion(questionId)
//            .subscribe(
//                () -> {},
//                this::postThrowable
//            )
//    );
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }


}
