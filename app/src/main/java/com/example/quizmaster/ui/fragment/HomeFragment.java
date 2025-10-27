package com.example.quizmaster.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.quizmaster.R;
import com.example.quizmaster.ui.activity.AboutActivity;
import com.example.quizmaster.ui.activity.QuizActivity;
import com.example.quizmaster.ui.activity.SettingsActivity;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.btnStartQuiz).setOnClickListener(v ->
                startQuiz(10));

        view.findViewById(R.id.btnSettings).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), SettingsActivity.class)));

        view.findViewById(R.id.btnAbout).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), AboutActivity.class)));

        return view;
    }

    private void startQuiz(int count) {
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra("question_count", count);
        startActivity(intent);
    }
}