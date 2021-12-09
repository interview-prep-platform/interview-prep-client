package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.interviewprep.databinding.FragmentDetailBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

  private long questionId;
  private DetailViewModel detailViewModel;
  private FragmentDetailBinding binding;

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
//noinspection ConstantConditions
    detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
//    if (getArguments() != null) {
//      QuestionFragmentArgs args = QuestionFragmentArgs.fromBundle(getArguments());
//      questionId = args.questionId();
//    }
//    detailViewModel
//        .getQuestion()
//        .observe(getViewLifecycleOwner(), (question) -> {
//          binding.questionText.setText(question.getQuestion());
//          binding.answerText.setText(question.getAnswer());
//          binding.sourceText.setText(question.getSource());
//        });
  }
}