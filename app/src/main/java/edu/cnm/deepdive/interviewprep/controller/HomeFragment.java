package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.interviewprep.databinding.FragmentHomeBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.QuestionViewModel;

public class HomeFragment extends Fragment {

  private QuestionViewModel questionViewModel;
  private FragmentHomeBinding binding;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    questionViewModel = new ViewModelProvider(getActivity()).get(QuestionViewModel.class);
    getLifecycle().addObserver(questionViewModel);
    questionViewModel.getThrowable().observe(getViewLifecycleOwner(), this::displayError);
    questionViewModel.getGame().observe(getViewLifecycleOwner(), this::update);
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    questionViewModel = new ViewModelProvider(this).get(ScoresViewModel.class);
    questionViewModel
        .getScoreboard()
        .observe(getViewLifecycleOwner(), (games) -> {
          GameSummaryAdapter adapter = new GameSummaryAdapter(getContext(), games);
          binding.games.setAdapter(adapter);
        });
    questionViewModel
        .getCodeLength()
        .observe(getViewLifecycleOwner(),
            (codeLength) -> binding.codeLength.setProgress(codeLength));
    questionViewModel
        .getPoolSize()
        .observe(getViewLifecycleOwner(), (poolSize) -> binding.poolSize.setProgress(poolSize));
    questionViewModel
        .getSortedByTime()
        .observe(getViewLifecycleOwner(),
            (sortedByTime) -> handleSortedByTimeChange(sortedByTime, false));
  }


  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    questionViewModel =
        new ViewModelProvider(this).get(QuestionViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}