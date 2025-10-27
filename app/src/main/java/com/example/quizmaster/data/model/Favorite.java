package com.example.quizmaster.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class Favorite {
    @PrimaryKey
    public int questionId;

    public Favorite(int questionId) {
        this.questionId = questionId;
    }
}