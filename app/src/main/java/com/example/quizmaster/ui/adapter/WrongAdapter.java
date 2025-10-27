package com.example.quizmaster.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.R;
import com.example.quizmaster.data.model.Question;
import java.util.List;

public class WrongAdapter extends RecyclerView.Adapter<WrongAdapter.ViewHolder> {
    private List<Question> questions;
    private List<String> userAnswers;
    private OnRetryClickListener retryListener;

    public interface OnRetryClickListener {
        void onRetryClick(Question question);
    }

    public WrongAdapter(List<Question> questions, List<String> userAnswers, OnRetryClickListener listener) {
        this.questions = questions;
        this.userAnswers = userAnswers;
        this.retryListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question q = questions.get(position);
        String userAnswer = userAnswers.get(position);
        holder.bind(q, userAnswer);
        holder.btnViewAnswer.setText("Retry");
        holder.btnViewAnswer.setOnClickListener(v -> retryListener.onRetryClick(q));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvOptionA, tvOptionB, tvOptionC, tvOptionD;
        Button btnViewAnswer;

        ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvOptionA = itemView.findViewById(R.id.tvOptionA);
            tvOptionB = itemView.findViewById(R.id.tvOptionB);
            tvOptionC = itemView.findViewById(R.id.tvOptionC);
            tvOptionD = itemView.findViewById(R.id.tvOptionD);
            btnViewAnswer = itemView.findViewById(R.id.btnViewAnswer);
        }

        void bind(Question q, String userAnswer) {
            tvQuestion.setText(q.question + " (Wrong)");
            tvOptionA.setText("A. " + q.optionA);
            tvOptionB.setText("B. " + q.optionB);
            tvOptionC.setText("C. " + q.optionC);
            tvOptionD.setText("D. " + q.optionD);

            // 高亮用户答案（红色）和正确答案（绿色）
            TextView userView = getOptionView(userAnswer);
            if (userView != null) userView.setTextColor(Color.RED);

            TextView correctView = getOptionView(q.correctAnswer);
            if (correctView != null) correctView.setTextColor(Color.GREEN);
        }

        private TextView getOptionView(String option) {
            switch (option) {
                case "A": return tvOptionA;
                case "B": return tvOptionB;
                case "C": return tvOptionC;
                case "D": return tvOptionD;
                default: return null;
            }
        }
    }
}