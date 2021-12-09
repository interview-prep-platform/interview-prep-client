package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.interviewprep.adapter.QuestionItemAdapter;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuestionBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;

public class QuestionFragment extends Fragment {

  private QuestionViewModel questionViewModel;
  private FragmentQuestionBinding binding;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentQuestionBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
    questionViewModel
        .getQuestions()
        .observe(getViewLifecycleOwner(), (questions) -> {
          QuestionItemAdapter adapter = new QuestionItemAdapter(getContext(), questions,
              this::showQuestionDetail);
          binding.history.setAdapter(adapter);
        });
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private void showQuestionDetail(String id, View view) {
    questionViewModel.refreshQuestion(id);
    QuestionFragmentDirections.OpenQuestionDetail toQuestionDetail
        = QuestionFragmentDirections.openQuestionDetail();
    toQuestionDetail.setQuestionId(id);
    Navigation.findNavController(view).navigate(toQuestionDetail);
  }


}