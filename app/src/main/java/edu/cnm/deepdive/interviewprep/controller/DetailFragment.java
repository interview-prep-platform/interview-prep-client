package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.interviewprep.databinding.FragmentDetailBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.DetailViewModel;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;

public class DetailFragment extends Fragment {

  private long questionId;
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


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
//    questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
//    questionViewModel
//        .getQuestion()
//        .observe(getViewLifecycleOwner(), (question) -> {
//          binding.questionText.setText(question.getQuestion());
//          binding.answerText.setText(question.getAnswer());
//          binding.sourceText.setText(question.getSource());
//        });
  }
}