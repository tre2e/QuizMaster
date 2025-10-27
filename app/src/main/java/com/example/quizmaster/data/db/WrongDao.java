package com.example.quizmaster.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.quizmaster.data.model.Wrong;
import java.util.List;

@Dao
public interface WrongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Wrong wrong);

    @Query("DELETE FROM wrong WHERE questionId = :questionId")
    void deleteByQuestionId(int questionId);

    @Query("SELECT * FROM wrong")
    LiveData<List<Wrong>> getAllWrongs();
}