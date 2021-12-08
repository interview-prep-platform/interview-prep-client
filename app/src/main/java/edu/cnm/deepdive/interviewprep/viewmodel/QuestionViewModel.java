package edu.cnm.deepdive.interviewprep.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.interviewprep.model.entity.Question;
import edu.cnm.deepdive.interviewprep.service.QuestionRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class QuestionViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final QuestionRepository repository;
  private final MutableLiveData<List<Question>> questions;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public QuestionViewModel(@NonNull Application application) {
    super(application);
    repository = new QuestionRepository();
    questions = new MutableLiveData<>();
//    rankings = new RankingLiveData(trigger);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    refreshQuestions();
  }

  public LiveData<List<Question>> getQuestions() {
    return questions;
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
