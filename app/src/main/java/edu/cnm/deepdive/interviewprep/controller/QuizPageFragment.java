package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizPageBinding;
import java.util.UUID;

public class QuizPageFragment extends Fragment {

  private UUID questionId;
  private FragmentQuizPageBinding binding;


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
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}
