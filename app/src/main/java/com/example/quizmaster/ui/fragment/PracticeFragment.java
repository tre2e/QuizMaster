package com.example.quizmaster.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.R;
import com.example.quizmaster.data.model.Question;
import com.example.quizmaster.ui.adapter.QuestionAdapter;
import com.example.quizmaster.ui.viewmodel.QuestionViewModel;
import java.util.ArrayList;
import java.util.List;

public class PracticeFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private List<Question> questionList = new ArrayList<>();
    private QuestionViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_practice, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new QuestionAdapter(questionList);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        viewModel.getRandomQuestions(50).observe(getViewLifecycleOwner(), questions -> {
            questionList.clear();
            if (questions != null) questionList.addAll(questions);
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}