package com.example.quizmaster.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wrong")
public class Wrong {
    @PrimaryKey
    public int questionId;
    public String userAnswer;

    public Wrong(int questionId, String userAnswer) {
        this.questionId = questionId;
        this.userAnswer = userAnswer;
    }
}