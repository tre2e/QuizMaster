package com.example.quizmaster.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {
    private static final String PREF_NAME = Constants.PREF_NAME;
    private SharedPreferences prefs;

    public SharedPrefUtil(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void incrementQuizCount() {
        int count = prefs.getInt(Constants.KEY_TOTAL_QUIZZES, 0);
        prefs.edit().putInt(Constants.KEY_TOTAL_QUIZZES, count + 1).apply();
    }

    public void addCorrectAnswer() {
        int count = prefs.getInt(Constants.KEY_CORRECT_ANSWERS, 0);
        prefs.edit().putInt(Constants.KEY_CORRECT_ANSWERS, count + 1).apply();
    }

    public int getTotalQuizzes() {
        return prefs.getInt(Constants.KEY_TOTAL_QUIZZES, 0);
    }

    public int getCorrectAnswers() {
        return prefs.getInt(Constants.KEY_CORRECT_ANSWERS, 0);
    }
}