package edu.cnm.deepdive.interviewprep.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.interviewprep.model.History;
import edu.cnm.deepdive.interviewprep.model.Question;
import edu.cnm.deepdive.interviewprep.service.QuizRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

/**
 * Implements the business logic behind the application.  Interacts with the QuizRepository to
 * perform CRUD operations on the server.
 */
public class QuizViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final QuizRepository repository;
  private final MutableLiveData<List<Question>> questions;
  private final MutableLiveData<Question> question;
  private final MutableLiveData<History> answer;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  /**
   * Class constructor.  Instantiates local class variables. Additionally, gets a question from the
   * server.
   *
   * @param application an application.
   */
  public QuizViewModel(@NonNull Application application) {
    super(application);
    repository = new QuizRepository();
    questions = new MutableLiveData<>();
    question = new MutableLiveData<>();
    answer = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    refreshQuestion();
  }

  /**
   * Returns the local questions variable.
   *
   * @return a reactivex {@link LiveData} {@link List} object of type {@link Question}.
   */
  public LiveData<List<Question>> getQuestions() {
    return questions;
  }

  /**
   * Returns the local question variable.
   *
   * @return a reactivex {@link LiveData} object of type {@link Question}.
   */
  public LiveData<Question> getQuestion() {
    return question;
  }

  public LiveData<History> getAnswer() {
    return answer;
  }

  private LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * gets a list of all questions from the question repository. Additionally, subscribes to the
   * result and puts the result in the local variable questions.
   */
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

}