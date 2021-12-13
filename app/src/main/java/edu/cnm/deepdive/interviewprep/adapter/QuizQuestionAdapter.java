package edu.cnm.deepdive.interviewprep.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import edu.cnm.deepdive.interviewprep.controller.QuizPageFragment;
import edu.cnm.deepdive.interviewprep.model.Question;
import java.util.List;

public class QuizQuestionAdapter extends FragmentStateAdapter {

  private final List<Question> questions;
  private final LayoutInflater inflater;


  public QuizQuestionAdapter(@NonNull Fragment fragment,
      List<Question> questions) {
    super(fragment);
    this.questions = questions;
    inflater = LayoutInflater.from(fragment.getContext());
  }


  @NonNull
  @Override
  public Fragment createFragment(int position) {
    Fragment fragment = new QuizPageFragment();
    Bundle args = new Bundle();
    args.putSerializable("question_id", questions.get(position).getId());
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public int getItemCount() {
    return questions.size();
  }
}
