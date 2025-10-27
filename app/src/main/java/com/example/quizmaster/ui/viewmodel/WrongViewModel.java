package com.example.quizmaster.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.quizmaster.data.model.Wrong;
import com.example.quizmaster.data.repository.RecordRepository;
import java.util.List;

public class WrongViewModel extends AndroidViewModel {
    private RecordRepository repository;
    private LiveData<List<Wrong>> wrongs;

    public WrongViewModel(Application application) {
        super(application);
        repository = new RecordRepository(application);
        wrongs = repository.getAllWrongs();
    }

    public LiveData<List<Wrong>> getAllWrongs() { return wrongs; }
    public void insert(Wrong wrong) { repository.insertWrong(wrong); }
    public void deleteByQuestionId(int questionId) { repository.deleteWrongByQuestionId(questionId); }
}