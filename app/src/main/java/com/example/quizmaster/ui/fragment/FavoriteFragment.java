package com.example.quizmaster.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.R;
import com.example.quizmaster.data.model.Favorite;
import com.example.quizmaster.data.model.Question;
import com.example.quizmaster.ui.adapter.FavoriteAdapter;
import com.example.quizmaster.ui.viewmodel.FavoriteViewModel;
import com.example.quizmaster.ui.viewmodel.QuestionViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private List<Question> questionList = new ArrayList<>();
    private Map<Integer, Question> questionMap = new HashMap<>();
    private FavoriteViewModel favoriteViewModel;
    private QuestionViewModel questionViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new FavoriteAdapter(questionList, this::removeFavorite);
        recyclerView.setAdapter(adapter);

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        favoriteViewModel.getAllFavorites().observe(getViewLifecycleOwner(), favorites -> {
            if (favorites == null || favorites.isEmpty()) {
                questionList.clear();
                adapter.notifyDataSetChanged();
                return;
            }

            questionViewModel.getAllQuestions().observe(getViewLifecycleOwner(), allQuestions -> {
                questionMap.clear();
                for (Question q : allQuestions) {
                    questionMap.put(q.id, q);
                }

                questionList.clear();
                for (Favorite f : favorites) {
                    Question q = questionMap.get(f.questionId);
                    if (q != null) questionList.add(q);
                }
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void removeFavorite(Question question) {
        favoriteViewModel.delete(new Favorite(question.id));
        Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
    }
}