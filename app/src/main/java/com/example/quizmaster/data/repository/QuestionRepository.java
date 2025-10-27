package com.example.quizmaster.data.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import com.example.quizmaster.data.db.AppDatabase;
import com.example.quizmaster.data.db.QuestionDao;
import com.example.quizmaster.data.model.Question;
import java.util.List;
import java.util.function.Consumer;

public class QuestionRepository {
    private QuestionDao questionDao;
    private LiveData<List<Question>> allQuestions;

    public QuestionRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        questionDao = db.questionDao();
        allQuestions = questionDao.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return allQuestions;
    }

    public void getMaxId(Consumer<Integer> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Integer max = questionDao.getMaxId();
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(max != null ? max : 0));
        });
    }

    public LiveData<List<Question>> getRandomQuestions(int limit) {
        return questionDao.getRandomQuestions(limit);
    }

    public void insertQuestions(List<Question> questions) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            questionDao.insertAll(questions);
        });
    }
}