package com.example.quizmaster.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.quizmaster.data.model.Favorite;
import com.example.quizmaster.data.repository.RecordRepository;
import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private RecordRepository repository;
    private LiveData<List<Favorite>> favorites;

    public FavoriteViewModel(Application application) {
        super(application);
        repository = new RecordRepository(application);
        favorites = repository.getAllFavorites();
    }

    public LiveData<List<Favorite>> getAllFavorites() { return favorites; }
    public void insert(Favorite favorite) { repository.insertFavorite(favorite); }
    public void delete(Favorite favorite) { repository.deleteFavorite(favorite); }
}