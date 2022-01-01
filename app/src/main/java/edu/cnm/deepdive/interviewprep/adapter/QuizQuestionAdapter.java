package edu.cnm.deepdive.interviewprep.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import edu.cnm.deepdive.interviewprep.controller.QuizPageFragment;
import edu.cnm.deepdive.interviewprep.model.Question;
import java.util.List;

public class QuizQuestionAdapter extends FragmentStateAdapter {

  private final List<Question> questions;

  public QuizQuestionAdapter(@NonNull Fragment fragment,
      List<Question> questions) {
    super(fragment);
    this.questions = questions;
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    return new QuizPageFragment();
  }

  @Override
  public int getItemCount() {
    return questions.size();
  }

}
