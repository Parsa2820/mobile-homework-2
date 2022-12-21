package ir.parsa2820.kingdomquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import ir.parsa2820.kingdomquiz.model.Question;
import ir.parsa2820.kingdomquiz.model.QuizResult;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
    private final Question[] localDataSet;
    private final QuizResult result;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getQuestionTextView().setText(localDataSet[position].getQuestion());
        ArrayList<String> answers = new ArrayList<>();
        answers.add(localDataSet[position].getCorrectAnswer());
        answers.addAll(Arrays.asList(localDataSet[position].getIncorrectAnswers()));
        Collections.shuffle(answers);
        RadioGroup radioGroup = holder.getAnswersRadioGroup();
        radioGroup.removeAllViews();
        for (String answer : answers) {
            RadioButton radioButton = new RadioButton(radioGroup.getContext());
            radioButton.setText(answer);
            radioGroup.addView(radioButton);
        }
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            group.setEnabled(false);
            for (int i = 0; i < group.getChildCount(); i++) {
                group.getChildAt(i).setEnabled(false);
            }
            RadioButton radioButton = group.findViewById(checkedId);
            int difficulty = localDataSet[position].getDifficultyDegree();
            if (radioButton.getText().equals(localDataSet[position].getCorrectAnswer())) {
                holder.getCorrectAnswerTextView().setText("Correct");
                result.addScore(3*difficulty);
            } else {
                holder.getCorrectAnswerTextView().setText("The correct answer is: " + localDataSet[position].getCorrectAnswer());
                result.addScore(-difficulty);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionTextView;
        private final RadioGroup answersRadioGroup;
        private final TextView correctAnswerTextView;

        public ViewHolder(View view) {
            super(view);
            answersRadioGroup = view.findViewById(R.id.radioGroupAnswers);
            questionTextView = view.findViewById(R.id.textViewQuestion);
            correctAnswerTextView = view.findViewById(R.id.textViewCorrrectAnswer);
        }

        public TextView getQuestionTextView() {
            return questionTextView;
        }

        public RadioGroup getAnswersRadioGroup() {
            return answersRadioGroup;
        }

        public TextView getCorrectAnswerTextView() {
            return correctAnswerTextView;
        }
    }

    public QuestionsAdapter(Question[] questions, QuizResult result) {
        this.localDataSet = questions;
        this.result = result;
    }
}
