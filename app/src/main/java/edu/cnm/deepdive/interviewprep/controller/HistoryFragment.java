package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.QuizViewModel;

/**
 * Implements logic to display a history to the user.
 */
public class HistoryFragment extends Fragment {

  private QuizViewModel quizViewModel;
  private FragmentQuizBinding binding;

  /**
   * Overrides the onCreateView method in Fragment. Inflates (sets up and displays) the layout as
   * specified in fragment_history.xml.
   *
   * @param savedInstanceState a {@link Bundle}.
   * @param container          a {@link ViewGroup}.
   * @param inflater           a {@link LayoutInflater}.
   */

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
}