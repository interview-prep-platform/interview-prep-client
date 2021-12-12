package edu.cnm.deepdive.interviewprep.controller;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.interviewprep.adapter.QuestionItemAdapter;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuestionBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;
import java.util.UUID;

/**
 * Home screen for application. Implements logic to add/edit/delete questions.
 */
public class QuestionFragment extends Fragment {

  private QuestionViewModel questionViewModel;
  private FragmentQuestionBinding binding;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * Overrides the onCreateView method in Fragment.  Instantiates local variables. Inflates (sets up
   * and displays) the layout as specified in fragment_question.xml.
   *
   * @param savedInstanceState a {@link Bundle}.
   * @param container          a {@link ViewGroup}.
   * @param inflater           a {@link LayoutInflater}.
   */
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentQuestionBinding.inflate(inflater, container, false);
    binding.addQuestion.setOnClickListener((v) ->
    {
      Navigation.findNavController(binding.getRoot())
          .navigate(QuestionFragmentDirections.openQuestion());
    });
    return binding.getRoot();
  }

  /**
   * Overrides the onViewCreated method in Fragment.  Specifically, interacts with the {@link
   * QuestionViewModel} to get a list of questions from the database. Additionally implements logic
   * for editing & deleting a question in coordination with the questionViewModel.
   *
   * @param view               a {@link View}.
   * @param savedInstanceState a {@link Bundle}.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    questionViewModel = new ViewModelProvider(getActivity()).get(QuestionViewModel.class);
    questionViewModel
        .getQuestions()
        .observe(getViewLifecycleOwner(), (questions) -> {
          QuestionItemAdapter adapter = new QuestionItemAdapter(getContext(), questions,
              this::editQuestion,
              (id, v) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete this?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                        questionViewModel.deleteQuestion(id);
                      }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                      }
                    });
                AlertDialog alert = builder.create();
                alert.show();
              }, this::showQuestionDetail);
          binding.history.setAdapter(adapter);
        });
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private void showQuestionDetail(UUID id, View view) {
    QuestionFragmentDirections.OpenQuestionDetail toQuestionDetail
        = QuestionFragmentDirections.openQuestionDetail();
    toQuestionDetail.setQuestionId(id);
    Navigation.findNavController(view).navigate(toQuestionDetail);
  }

  private void editQuestion(UUID id, View view) {
    Navigation.findNavController(binding.getRoot())
        .navigate(QuestionFragmentDirections.openQuestion().setQuestionId(id));
  }

}