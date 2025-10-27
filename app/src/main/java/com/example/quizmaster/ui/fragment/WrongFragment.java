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
import com.example.quizmaster.data.model.Wrong;
import com.example.quizmaster.ui.adapter.WrongAdapter;
import com.example.quizmaster.ui.viewmodel.QuestionViewModel;
import com.example.quizmaster.ui.viewmodel.WrongViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrongFragment extends Fragment {

    private RecyclerView recyclerView;
    private WrongAdapter adapter;
    private List<Question> questionList = new ArrayList<>();
    private List<String> userAnswers = new ArrayList<>();
    private Map<Integer, Question> questionMap = new HashMap<>();
    private WrongViewModel wrongViewModel;
    private QuestionViewModel questionViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wrong, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new WrongAdapter(questionList, userAnswers, this::retryQuestion);
        recyclerView.setAdapter(adapter);

        wrongViewModel = new ViewModelProvider(this).get(WrongViewModel.class);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        loadWrongs();

        return view;
    }

    private void loadWrongs() {
        wrongViewModel.getAllWrongs().observe(getViewLifecycleOwner(), wrongs -> {
            if (wrongs == null || wrongs.isEmpty()) {
                questionList.clear();
                userAnswers.clear();
                adapter.notifyDataSetChanged();
                return;
            }

            questionViewModel.getAllQuestions().observe(getViewLifecycleOwner(), allQuestions -> {
                questionMap.clear();
                for (Question q : allQuestions) questionMap.put(q.id, q);

                questionList.clear();
                userAnswers.clear();
                for (Wrong w : wrongs) {
                    Question q = questionMap.get(w.questionId);
                    if (q != null) {
                        questionList.add(q);
                        userAnswers.add(w.userAnswer);
                    }
                }
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void retryQuestion(Question question) {
        wrongViewModel.deleteByQuestionId(question.id);
        // 可选：跳转到单题练习
    }
}