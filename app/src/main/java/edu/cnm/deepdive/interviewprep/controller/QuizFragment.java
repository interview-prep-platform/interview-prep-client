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

/**
 * Fragment for displaying a quiz.
 */
public class QuizFragment extends Fragment {

  private QuizViewModel quizViewModel;
  private FragmentQuizBinding binding;
  private List<Question> quizQuestions;
  QuestionPagerAdapter questionPagerAdapter;
  ViewPager viewPager;
  public static final String ARG_OBJECT = "object";

  /**
   * Overrides the onCreateView method in Fragment.  Instantiates local variables. Inflates (sets up
   * and displays) the layout as specified in fragment_quiz.xml.
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

  /**
   * Overrides the onViewCreated method in Fragment.  Specifically, interacts with the question
   * pager adapter to display a list of questions from the database that the user can use to quiz
   * themselves.
   *
   * @param view               a {@link View}.
   * @param savedInstanceState a {@link Bundle}.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    questionPagerAdapter = new QuestionPagerAdapter(getChildFragmentManager());
    viewPager = view.findViewById(R.id.pager);
    viewPager.setAdapter(questionPagerAdapter);

  }

  /**
   * Overrides the onOptionsItemSelected method in AppCompatActivity.  Specifies what to do if the
   * user clicks on each menu item (Sign out versus Settings).
   *
   * @param item a menu item.
   * @return a boolean representing if the item was handled successfully or not.
   */
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled;
    if (item.getItemId() == R.id.new_quiz) {
      handled = true;
//      quizQuestions = quizViewModel.startQuiz();
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  /**
   * Populates {@link Question}s into a Viewpage Adapter as specified by the accompanying item
   * layout.
   */

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