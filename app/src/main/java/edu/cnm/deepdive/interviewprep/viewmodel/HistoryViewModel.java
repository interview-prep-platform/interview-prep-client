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
import edu.cnm.deepdive.interviewprep.service.HistoryRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import java.util.UUID;

/**
 * Implements the business logic behind the application.  Interacts with the QuestionRepository to
 * perform CRUD operations on the server.
 */
public class HistoryViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final HistoryRepository repository;
  private final MutableLiveData<List<History>> histories;
  private final MutableLiveData<History> history;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;


  /**
   * Class constructor.  Instantiates local class variables. Additionally, gets all the questions
   * from the server.
   *
   * @param application an application.
   */
  public HistoryViewModel(@NonNull Application application) {
    super(application);
    repository = new HistoryRepository();
    histories = new MutableLiveData<>();
    history = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  /**
   * Returns the local questions variable.
   *
   * @return a reactivex {@link LiveData} {@link List} object of type {@link Question}.
   */
  public LiveData<List<History>> getHistories() {
    return histories;
  }

  /**
   * Returns the local question variable.
   *
   * @return a reactivex {@link LiveData} object of type {@link Question}.
   */
  public LiveData<History> getHistory() {
    return history;
  }

  private LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * gets a list of all questions from the question repository. Additionally, subscribes to the
   * result and puts the result in the local variable questions.
   */
  public void refreshHistories(UUID questionId) {
    pending.add(
        repository
            .getHistories(questionId)
            .subscribe(
                histories::postValue,
                this::postThrowable
            )
    );
  }

  /**
   * updates a question and sends a question to the questionRepository for updating on the server.
   * Additionally, subscribes to the result and puts the result in the local variable question. Then
   * reinitializes the questions field so that it will refresh on the screen.
   *
   * @param question An updated {@link Question} object.
   */


  /**
   * creates a question and sends a question to the questionRepository for creation on the server.
   * Additionally, subscribes to the result and puts the result in the local variable question. Then
   * reinitializes the questions field so that it will refresh on the screen.
   *
   * @param question A new {@link Question} object.
   */
  public void createHistory(History history, Question question) {
    history.setQuestion(question);
    pending.add(
        repository
            .createHistory(history)
            .subscribe(
                (postedHistory) -> {
                  this.history.postValue(postedHistory);
                  List<History> histories = this.histories.getValue();
                  histories.add(postedHistory);
                  this.histories.postValue(histories);
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
    for (Question question : questions) {
      if (question.getId().equals(questionId)) {
        return question;
      }
    }
    return null;
  }

}
