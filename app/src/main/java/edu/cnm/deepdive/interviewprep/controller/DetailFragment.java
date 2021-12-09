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

public class DetailFragment extends Fragment {

  private String questionId;
  private QuestionViewModel questionViewModel;
  private FragmentDetailBinding binding;

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    QuestionFragmentArgs args = QuestionFragmentArgs.fromBundle(getArguments());
    questionId = args.getQuestionId();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = FragmentDetailBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
    questionViewModel.refreshQuestion(questionId);
    questionViewModel//can only observe on live data
        .getQuestion()
        .observe(getViewLifecycleOwner(), (question) -> {
          Log.d(getClass().getSimpleName(), "question is: "+question.getQuestion().toString());
          binding.questionText.setText(question.getQuestion());
          binding.answerText.setText(question.getAnswer());
          binding.sourceText.setText(question.getSource());
        });
  }

}