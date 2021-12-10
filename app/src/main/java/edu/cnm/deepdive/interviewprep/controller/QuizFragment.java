package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.interviewprep.R;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizBinding;
import edu.cnm.deepdive.interviewprep.model.Question;
import edu.cnm.deepdive.interviewprep.viewmodel.QuizViewModel;
import java.util.List;

public class QuizFragment extends Fragment {

  private QuizViewModel quizViewModel;
  private FragmentQuizBinding binding;
  private List<Question> quizQuestions;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    quizViewModel =
        new ViewModelProvider(this).get(QuizViewModel.class);

    binding = FragmentQuizBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }


  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled;
    //Todo Check that we dont have duplicate questions!
    if (item.getItemId() == R.id.new_quiz) {
      handled = true;
//      quizQuestions = quizViewModel.startQuiz();
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }


}