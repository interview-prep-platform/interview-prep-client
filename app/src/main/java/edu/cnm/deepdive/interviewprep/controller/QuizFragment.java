package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.QuizViewModel;

public class QuizFragment extends Fragment {

  private QuizViewModel quizViewModel;
  private FragmentQuizBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    quizViewModel =
        new ViewModelProvider(this).get(QuizViewModel.class);

    binding = FragmentQuizBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

//    final TextView textView = binding.textSlideshow;
//    quizViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//      @Override
//      public void onChanged(@Nullable String s) {
//        textView.setText(s);
//      }
//    });
    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}