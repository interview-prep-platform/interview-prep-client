package edu.cnm.deepdive.interviewprep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.interviewprep.adapter.QuestionItemAdapter.Holder;
import edu.cnm.deepdive.interviewprep.databinding.ItemQuestionBinding;
import edu.cnm.deepdive.interviewprep.model.Question;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class QuestionItemAdapter extends Adapter<Holder> {


  private final Context context;
  private final List<Question> questions;
  private final LayoutInflater inflater;
  private final OnQuestionClickHelper onQuestionClickHelper;
  private final OnQuestionEditHelper onQuestionEditHelper;
  private final OnQuestionDeleteHelper onQuestionDeleteHelper;


  public QuestionItemAdapter(Context context,
      List<Question> questions,
      OnQuestionEditHelper onQuestionEditHelper,
      OnQuestionDeleteHelper onQuestionDeleteHelper, OnQuestionClickHelper onQuestionClickHelper
  ) {
    this.context = context;
    this.onQuestionEditHelper = onQuestionEditHelper;
    this.onQuestionDeleteHelper = onQuestionDeleteHelper;
    this.questions = questions;
    this.questions.sort(null);
    this.onQuestionClickHelper = onQuestionClickHelper;
    inflater = LayoutInflater.from(context);

  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    return new Holder(ItemQuestionBinding.inflate(inflater, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return questions.size();
  }

  class Holder extends ViewHolder {

    private final ItemQuestionBinding binding;

    private Holder(@NonNull ItemQuestionBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(int position) {
      Question question = questions.get(position);
      binding.question.setText(question.getQuestion());
      UUID id = question.getId();
      binding.questionEdit.setOnClickListener(
          (v) -> onQuestionEditHelper.onQuestionClick(id, v));
      binding.questionDelete.setOnClickListener(
          (v) -> onQuestionDeleteHelper.onQuestionClick(id, v));

      binding.getRoot()
          .setOnClickListener(
              (view) -> onQuestionClickHelper.onQuestionClick(id, view));
    }

  }

  public interface OnQuestionClickHelper {

    void onQuestionClick(UUID id, View view);
  }

  /**
   * A helper class to aid in editing a {@link Question} object.
   */
  public interface OnQuestionEditHelper {

    void onQuestionClick(UUID id, View view);
  }

  /**
   * A helper class to aid in deleting a {@link Question} object.
   */
  public interface OnQuestionDeleteHelper {

    void onQuestionClick(UUID id, View view);
  }

}
