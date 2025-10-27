package com.example.quizmaster.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.quizmaster.data.db.AppDatabase;
import com.example.quizmaster.data.db.FavoriteDao;
import com.example.quizmaster.data.db.WrongDao;
import com.example.quizmaster.data.model.Favorite;
import com.example.quizmaster.data.model.Wrong;
import java.util.List;

public class RecordRepository {
    private FavoriteDao favoriteDao;
    private WrongDao wrongDao;
    private LiveData<List<Favorite>> favorites;
    private LiveData<List<Wrong>> wrongs;

    public RecordRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        favoriteDao = db.favoriteDao();
        wrongDao = db.wrongDao();
        favorites = favoriteDao.getAllFavorites();
        wrongs = wrongDao.getAllWrongs();
    }

    public LiveData<List<Favorite>> getAllFavorites() { return favorites; }
    public LiveData<List<Wrong>> getAllWrongs() { return wrongs; }

    public void insertFavorite(Favorite favorite) {
        AppDatabase.databaseWriteExecutor.execute(() -> favoriteDao.insert(favorite));
    }

    public void deleteFavorite(Favorite favorite) {
        AppDatabase.databaseWriteExecutor.execute(() -> favoriteDao.delete(favorite));
    }

    public void insertWrong(Wrong wrong) {
        AppDatabase.databaseWriteExecutor.execute(() -> wrongDao.insert(wrong));
    }

    public void deleteWrongByQuestionId(int questionId) {
        AppDatabase.databaseWriteExecutor.execute(() -> wrongDao.deleteByQuestionId(questionId));
    }
}