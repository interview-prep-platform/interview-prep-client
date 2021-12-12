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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
                (postedQuestion) -> {
                  this.question.postValue(postedQuestion);
                  List<Question> questions = this.questions.getValue();
                  Question question2 = findQuestion(postedQuestion.getId(), questions);
                  int indexOfQuestion = questions.indexOf(question2);
                  questions.set(indexOfQuestion, postedQuestion);
                  this.questions.postValue(questions);
                },
                this::postThrowable
            )
    );
  }

  public void createQuestion(Question question) {
    pending.add(
        repository
            .createQuestion(question)
            .subscribe(
                (postedQuestion) -> {
                  this.question.postValue(postedQuestion);
                  List<Question> questions = this.questions.getValue();
                  questions.add(postedQuestion);
                  this.questions.postValue(questions);
                },
                this::postThrowable
            )
    );
  }

  public void refreshQuestion(UUID questionId) {
    pending.add(
        repository
            .getQuestion(questionId)
            .subscribe(
                question::postValue,
                this::postThrowable
            )
    );
  }

  public void deleteQuestion(UUID questionId) {
    pending.add(
        repository
            .deleteQuestion(questionId)
            .subscribe(
                () -> {
                  List<Question> questions = this.questions.getValue();
                  questions.removeIf((question) -> question.getId().equals(questionId));
                  this.questions.postValue(questions);
                },
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

  private Question findQuestion(UUID questionId, List<Question> questions) {
    for(Question question : questions) {
      if(question.getId().equals(questionId)) {
        return question;
      }
    }
    return null;
  }

}
