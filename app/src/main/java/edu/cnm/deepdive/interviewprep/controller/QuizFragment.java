package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import edu.cnm.deepdive.interviewprep.adapter.QuizQuestionAdapter;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizBinding;
import edu.cnm.deepdive.interviewprep.model.Question;
import edu.cnm.deepdive.interviewprep.viewmodel.HistoryViewModel;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;
import java.util.List;

/**
 * Fragment for displaying a quiz.
 */
public class QuizFragment extends Fragment {

  private final OnPageChangeCallback callback = new PageChangeCallback();

  private QuestionViewModel questionViewModel;
  private HistoryViewModel historyViewModel;
  private FragmentQuizBinding binding;
  private QuizQuestionAdapter adapter;
  private List<Question> questions;

  /**
   * Overrides the onCreateView method in Fragment.  Instantiates local variables. Inflates (sets up
   * and displays) the layout as specified in fragment_quiz.xml.
   *
   * @param savedInstanceState a {@link Bundle}.
   * @param container          a {@link ViewGroup}.
   * @param inflater           a {@link LayoutInflater}.
   */
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentQuizBinding.inflate(inflater, container, false);
    binding.pager.setOffscreenPageLimit(1);
    binding.pager.registerOnPageChangeCallback(callback);
    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding.pager.unregisterOnPageChangeCallback(callback);
    binding = null;
  }

  /**
   * Overrides the onViewCreated method in Fragment.  Specifically, interacts with the question
   * pager adapter to display a list of questions from the database that the user can use to quiz
   * themselves.
   *
   * @param view               a {@link View}.
   * @param savedInstanceState a {@link Bundle}.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    questionViewModel = new ViewModelProvider(getActivity()).get(QuestionViewModel.class);
    historyViewModel = new ViewModelProvider(getActivity()).get(HistoryViewModel.class);
    questionViewModel.refreshRandomQuestions();
    questionViewModel.getQuestions().observe(getViewLifecycleOwner(), (questions) -> {
      this.questions = questions;
      adapter = new QuizQuestionAdapter(this, questions);
      binding.pager.setAdapter(adapter);
    });
  }

  private class PageChangeCallback extends OnPageChangeCallback {

    @Override
    public void onPageSelected(int position) {
      super.onPageSelected(position);
      Question question = questions.get(position);
      questionViewModel.refreshQuestion(question.getId());
      historyViewModel.refreshHistories(question.getId());
    }

  }

}