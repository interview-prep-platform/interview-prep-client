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
import edu.cnm.deepdive.interviewprep.databinding.FragmentDetailBinding;
import edu.cnm.deepdive.interviewprep.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

  private DetailViewModel detailViewModel;
  private FragmentDetailBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    detailViewModel =
        new ViewModelProvider(this).get(DetailViewModel.class);

    binding = FragmentDetailBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    final TextView textView = binding.textGallery;
    detailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        textView.setText(s);
      }
    });
    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}