package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import edu.cnm.deepdive.interviewprep.R;
import edu.cnm.deepdive.interviewprep.adapter.QuizQuestionAdapter;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizBinding;
import edu.cnm.deepdive.interviewprep.model.Question;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;
import edu.cnm.deepdive.interviewprep.viewmodel.QuizViewModel;
import java.util.List;

/**
 * Fragment for displaying a quiz.
 */
public class QuizFragment extends Fragment {

  private final OnPageChangeCallback callback = new PageChangeCallback();

  private QuestionViewModel viewModel;
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
    viewModel = new ViewModelProvider(getActivity()).get(QuestionViewModel.class);
    viewModel.getQuestions().observe(getViewLifecycleOwner(), (questions) -> {
      this.questions = questions;
      adapter = new QuizQuestionAdapter(this, questions);
      binding.pager.setAdapter(adapter);
    });
  }

  private class PageChangeCallback extends OnPageChangeCallback {

    @Override
    public void onPageSelected(int position) {
      super.onPageSelected(position);
      viewModel.refreshQuestion(questions.get(position).getId());
    }

  }

}