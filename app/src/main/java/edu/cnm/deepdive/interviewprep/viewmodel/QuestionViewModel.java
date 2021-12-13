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
import java.util.UUID;

/**
 * Implements the business logic behind the application.  Interacts with the QuestionRepository to
 * perform CRUD operations on the server.
 */
public class QuestionViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final QuestionRepository repository;
  private final MutableLiveData<List<Question>> questions;
  private final MutableLiveData<Question> question;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;


  /**
   * Class constructor.  Instantiates local class variables. Additionally, gets all the questions
   * from the server.
   *
   * @param application an application.
   */
  public QuestionViewModel(@NonNull Application application) {
    super(application);
    repository = new QuestionRepository();
    questions = new MutableLiveData<>();
    question = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    refreshQuestions();
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

  private LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * gets a list of all questions from the question repository. Additionally, subscribes to the
   * result and puts the result in the local variable questions.
   */
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

  /**
   * updates a question and sends a question to the questionRepository for updating on the server.
   * Additionally, subscribes to the result and puts the result in the local variable question. Then
   * reinitializes the questions field so that it will refresh on the screen.
   *
   * @param question An updated {@link Question} object.
   */
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


  /**
   * creates a question and sends a question to the questionRepository for creation on the server.
   * Additionally, subscribes to the result and puts the result in the local variable question. Then
   * reinitializes the questions field so that it will refresh on the screen.
   *
   * @param question A new {@link Question} object.
   */
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


  /**
   * gets a question from the questionRepository which subsequently gets a question from the server.
   * Additionally, subscribes to the result and puts the result in the local variable question. Then
   * reinitializes the questions field so that it will refresh on the screen.
   *
   * @param questionId A questionId in the form of a universally unique identifier.
   */
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

  /**
   * deletes a question from the questionRepository which subsequently deletes a question from the
   * server.
   *
   * @param questionId A questionId in the form of a universally unique identifier.
   */
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
    for (Question question : questions) {
      if (question.getId().equals(questionId)) {
        return question;
      }
    }
    return null;
  }

}
