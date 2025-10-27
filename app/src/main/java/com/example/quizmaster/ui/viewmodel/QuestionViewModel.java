package com.example.quizmaster.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.quizmaster.data.model.Question;
import com.example.quizmaster.data.repository.QuestionRepository;
import java.util.List;

public class QuestionViewModel extends AndroidViewModel {
    private QuestionRepository repository;

    public QuestionViewModel(Application application) {
        super(application);
        repository = new QuestionRepository(application);
    }

    public LiveData<List<Question>> getRandomQuestions(int limit) {
        return repository.getRandomQuestions(limit);
    }

    public LiveData<List<Question>> getAllQuestions() {
        return repository.getAllQuestions();
    }

    public void insertQuestions(List<Question> questions) {
        repository.insertQuestions(questions);
    }
}