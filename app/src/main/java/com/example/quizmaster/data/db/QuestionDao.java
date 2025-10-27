package com.example.quizmaster.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.quizmaster.data.model.Question;
import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Question>> getRandomQuestions(int limit);

    @Query("SELECT * FROM question")
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT MAX(id) FROM question")
    Integer getMaxId();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Question> questions);
}