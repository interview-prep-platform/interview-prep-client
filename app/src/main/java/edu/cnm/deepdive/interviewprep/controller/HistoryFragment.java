package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import edu.cnm.deepdive.interviewprep.adapter.HistoryAdapter;
import edu.cnm.deepdive.interviewprep.databinding.FragmentHistoryBinding;
import edu.cnm.deepdive.interviewprep.model.Question;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;
import java.util.List;

/**
 * Fragment for displaying a quiz.
 */
public class HistoryFragment extends Fragment {

  private final OnPageChangeCallback callback = new PageChangeCallback();

  private QuestionViewModel viewModel;
  private FragmentHistoryBinding binding;
  private HistoryAdapter adapter;
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
    binding = FragmentHistoryBinding.inflate(inflater, container, false);
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
      adapter = new HistoryAdapter(this, questions);
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