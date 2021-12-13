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
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizPageBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;
import java.util.UUID;

public class QuizPageFragment extends Fragment {

  private UUID questionId;
  private FragmentQuizPageBinding binding;
  private QuestionViewModel questionViewModel;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    QuizPageFragmentArgs args = QuizPageFragmentArgs.fromBundle(getArguments());
    questionId = args.getQuestionId();
    Log.d(getClass().getSimpleName(), questionId.toString());
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentQuizPageBinding.inflate(getLayoutInflater(), container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    questionViewModel = new ViewModelProvider(getActivity()).get(QuestionViewModel.class);
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

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}
