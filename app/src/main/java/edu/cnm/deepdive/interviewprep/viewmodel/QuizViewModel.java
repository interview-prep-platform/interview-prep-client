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
import edu.cnm.deepdive.interviewprep.service.QuizRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class QuizViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final QuizRepository repository;
  private final MutableLiveData<List<Question>> questions;
  private final MutableLiveData<Question> question;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private List<LiveData<Question>> quizQuestions;

  public QuizViewModel(@NonNull Application application) {
    super(application);
    repository = new QuizRepository();
    questions = new MutableLiveData<>();
    question = new MutableLiveData<>();
//    rankings = new RankingLiveData(trigger);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    refreshQuestion();
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

  public void refreshQuestion() {
    pending.add(
        repository
            .getRandomQuestion()
            .subscribe(
                question::postValue,
                this::postThrowable
            )
    );
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

  public List<LiveData<Question>> startQuiz() {
    for (int i = 0; i < 3; i++) {
      quizQuestions.add(getQuestion());

    }
    return quizQuestions;
  }
}