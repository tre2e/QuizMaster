package com.example.quizmaster.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.quizmaster.R;
import com.example.quizmaster.util.SharedPrefUtil;

public class ProfileFragment extends Fragment {

    private TextView tvTotalQuizzes, tvCorrectAnswers, tvAccuracy;
    private SharedPrefUtil prefUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvTotalQuizzes = view.findViewById(R.id.tvTotalQuizzes);
        tvCorrectAnswers = view.findViewById(R.id.tvCorrectAnswers);
        tvAccuracy = view.findViewById(R.id.tvAccuracy);

        prefUtil = new SharedPrefUtil(requireContext());
        updateStats();

        return view;
    }

    private void updateStats() {
        int total = prefUtil.getTotalQuizzes();
        int correct = prefUtil.getCorrectAnswers();
        double accuracy = total > 0 ? (correct * 100.0 / total) : 0;

        tvTotalQuizzes.setText("Total Quizzes: " + total);
        tvCorrectAnswers.setText("Correct Answers: " + correct);
        tvAccuracy.setText(String.format("Accuracy: %.1f%%", accuracy));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStats(); // 每次返回都刷新
    }
}