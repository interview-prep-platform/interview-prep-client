package edu.cnm.deepdive.interviewprep.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import edu.cnm.deepdive.interviewprep.R;
import edu.cnm.deepdive.interviewprep.databinding.FragmentQuizBinding;
import edu.cnm.deepdive.interviewprep.model.Question;
import edu.cnm.deepdive.interviewprep.viewmodel.QuizViewModel;
import java.util.List;

public class QuizFragment extends Fragment {

  private QuizViewModel quizViewModel;
  private FragmentQuizBinding binding;
  private List<Question> quizQuestions;
  QuestionPagerAdapter questionPagerAdapter;
  ViewPager viewPager;
  public static final String ARG_OBJECT = "object";

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
    questionPagerAdapter = new QuestionPagerAdapter(getChildFragmentManager());
    viewPager = view.findViewById(R.id.pager);
    viewPager.setAdapter(questionPagerAdapter);

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

  public class QuestionPagerAdapter extends FragmentStatePagerAdapter {

    public QuestionPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      Fragment fragment = new QuizFragment();
      Bundle args = new Bundle();
      // Our object is just an integer :-P
      args.putInt(QuizFragment.ARG_OBJECT, position + 1);
      fragment.setArguments(args);
      return fragment;
//      return quizQuestions.get(position);
    }

    @Override
    public int getCount() {
      //Todo Get from shared Preferences.
      return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return "OBJECT " + (position + 1);
    }
  }


}