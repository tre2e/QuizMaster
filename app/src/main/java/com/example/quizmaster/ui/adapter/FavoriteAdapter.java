package com.example.quizmaster.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.R;
import com.example.quizmaster.data.model.Question;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Question> questions;
    private OnRemoveClickListener removeListener;

    public interface OnRemoveClickListener {
        void onRemoveClick(Question question);
    }

    public FavoriteAdapter(List<Question> questions, OnRemoveClickListener listener) {
        this.questions = questions;
        this.removeListener = listener;
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
        holder.bind(q);
        holder.btnViewAnswer.setText("Remove");
        holder.btnViewAnswer.setOnClickListener(v -> removeListener.onRemoveClick(q));
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

        void bind(Question q) {
            tvQuestion.setText(q.question);
            tvOptionA.setText("A. " + q.optionA);
            tvOptionB.setText("B. " + q.optionB);
            tvOptionC.setText("C. " + q.optionC);
            tvOptionD.setText("D. " + q.optionD);
        }
    }
}