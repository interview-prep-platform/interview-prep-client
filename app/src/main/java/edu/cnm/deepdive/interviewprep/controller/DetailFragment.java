package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.interviewprep.databinding.FragmentDetailBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;
import java.util.UUID;

/**
 * Implements logic to display a detail view of Question.
 */
public class DetailFragment extends Fragment {

  private UUID questionId;
  private QuestionViewModel questionViewModel;
  private FragmentDetailBinding binding;

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  /**
   * Overrides the onCreateView method in Fragment.  Initially gets QuestionId argument from the
   * Question Fragment.
   *
   * @param savedInstanceState a {@link Bundle}.
   */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
    questionId = args.getQuestionId();
  }

  /**
   * Overrides the onCreateView method in Fragment. Inflates (sets up and displays) the layout as
   * specified in fragment_detail.xml.
   *
   * @param savedInstanceState a {@link Bundle}.
   * @param container          a {@link ViewGroup}.
   * @param inflater           a {@link LayoutInflater}.
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = FragmentDetailBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  /**
   * Overrides the onViewCreated method in Fragment.  Specifically, interacts with the {@link
   * QuestionViewModel} to get a question from the server as specified by the questionId.
   *
   * @param view               a {@link View}.
   * @param savedInstanceState a {@link Bundle}.
   */

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
    questionViewModel.refreshQuestion(questionId);
    questionViewModel//can only observe on live data
        .getQuestion()
        .observe(getViewLifecycleOwner(), (question) -> {
          Log.d(getClass().getSimpleName(), "question is: " + question.getQuestion().toString());
          binding.questionText.setText(question.getQuestion());
          binding.answerText.setText(question.getAnswer());
          binding.sourceText.setText(question.getSource());
        });
  }

}