package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.interviewprep.R;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizPageBinding;
import edu.cnm.deepdive.interviewprep.model.History;
import edu.cnm.deepdive.interviewprep.viewmodel.HistoryViewModel;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;

public class QuizPageFragment extends Fragment {

  private FragmentQuizPageBinding binding;
  private QuestionViewModel questionViewModel;
  private HistoryViewModel historyViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentQuizPageBinding.inflate(getLayoutInflater(), container, false);
    binding.questionText.setText("");
    binding.answerText.setText("");
    binding.sourceText.setText("");
    binding.userAnswerText.setText("");
    binding.userEditAnswerText.setText("");
    binding.answerText.setVisibility(View.GONE);
    binding.showAnswer.setText(R.string.show_answer_button);
    binding.showAnswer.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (binding.showAnswer.getText().toString().equals("Show Answer")) {
          binding.answerText.setVisibility(View.VISIBLE);
          binding.showAnswer.setText(R.string.hide_answer_button);
        } else {
          binding.showAnswer.setText(R.string.show_answer_button);
          binding.answerText.setVisibility(View.GONE);
        }
      }
    });
    binding.submit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        binding.userAnswerText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        questionViewModel.getQuestion().observe(getViewLifecycleOwner(), (q) -> {
          binding.userAnswerText.setText(binding.userEditAnswerText.getText().toString().trim());
          //q.setUserAnswer(binding.userEditAnswerText.getText().toString().trim());
          //questionViewModel//can only observe on live data
          //    .updateQuestion(q);
          History history = new History();
          history.setAnswer(binding.userEditAnswerText.getText().toString().trim());
          historyViewModel
              .createHistory(history, q);
          binding.userEditAnswerText.setVisibility(View.GONE);
          binding.submit.setVisibility(View.GONE);
          binding.userAnswerText.setVisibility(View.VISIBLE);
        });
        Toast.makeText(
            getContext(), "Your Answer has been Submitted", Toast.LENGTH_SHORT).show();
      }
    });
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    questionViewModel = new ViewModelProvider(getActivity()).get(QuestionViewModel.class);
    historyViewModel = new ViewModelProvider(getActivity()).get(HistoryViewModel.class);
    questionViewModel//can only observe on live data
        .getQuestion()
        .observe(getViewLifecycleOwner(), (question) -> {
          Log.d(getClass().getSimpleName(), "question is: " + question.getQuestion().toString());
          historyViewModel.refreshHistories(question.getId());
          binding.questionText.setText(question.getQuestion());
          binding.answerText.setText(question.getAnswer());
          binding.sourceText.setText(question.getSource());
          //if (history.getAnswer() != null) {
//            Toast.makeText(
//                getContext(), "question.getAnswer() != null", Toast.LENGTH_SHORT).show();
//            binding.userAnswerText.setText(question.getUserAnswer());
            //binding.userEditAnswerText.setVisibility(View.GONE);
            //binding.submit.setVisibility(View.GONE);
          //} else {
//            Toast.makeText(
//                getContext(), "here", Toast.LENGTH_SHORT).show();


         // }
        });
    historyViewModel.getHistories().observe(getViewLifecycleOwner(), (histories) -> {
      Log.d(getClass().getSimpleName(), histories.toString());
      //historyViewModel.refreshHistories(question.getId());
      if (histories != null && !histories.isEmpty()) {
        History history = histories.get(0);
        binding.userAnswerText.setText(history.getAnswer());
        binding.userAnswerText.setVisibility(View.VISIBLE);
        binding.submit.setVisibility(View.GONE);
        binding.userEditAnswerText.setVisibility(View.GONE);
      } else {
        binding.userEditAnswerText.setVisibility(View.VISIBLE);
        binding.userAnswerText.setVisibility(View.GONE);
        binding.userEditAnswerText.setVisibility(View.VISIBLE);
        binding.submit.setVisibility(View.VISIBLE);
      }
    });

  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

}
