package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import edu.cnm.deepdive.interviewprep.databinding.FragmentEditQuestionBinding;
import edu.cnm.deepdive.interviewprep.model.Question;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;
import java.util.UUID;

public class EditQuestionFragment extends BottomSheetDialogFragment implements TextWatcher {

  private FragmentEditQuestionBinding binding;
  private QuestionViewModel viewModel;
  private UUID questionId;
  private Question question;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EditQuestionFragmentArgs args = EditQuestionFragmentArgs.fromBundle(getArguments());
    questionId = args.getQuestionId();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentEditQuestionBinding.inflate(inflater, container, false);
    binding.question.addTextChangedListener(this);
    binding.answer.addTextChangedListener(this);
    binding.source.addTextChangedListener(this);
    binding.cancel.setOnClickListener((v) -> dismiss());
    binding.save.setOnClickListener((v) -> {
      question = new Question();
      question.setId(questionId);
      question.setQuestion(binding.question.getText().toString().trim());
      question.setAnswer(binding.answer.getText().toString().trim());
      question.setSource(binding.source.getText().toString().trim());
      viewModel.updateQuestion(question);
      dismiss();
    });
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(QuestionViewModel.class);
    if (questionId != null) {
      viewModel.refreshQuestion(questionId);
      viewModel.getQuestion().observe(getViewLifecycleOwner(), (question) -> {
        binding.question.setText(question.getQuestion());
        binding.answer.setText(question.getAnswer());
        binding.source.setText(question.getSource());
      });
    } else {
      question = new Question();
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    // Do nothing.
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    // Do nothing.
  }

  @Override
  public void afterTextChanged(Editable s) {
    checkSubmitConditions();
  }

  private void checkSubmitConditions() {
    String question = binding.question
        .getText()
        .toString()
        .trim();
    String answer = binding.answer
        .getText()
        .toString()
        .trim();
    String source = binding.source
        .getText()
        .toString()
        .trim();
    binding.save.setEnabled(!question.isEmpty() && !answer.isEmpty());
  }

}