package com.example.quizmaster.util;

import android.content.Context;
import android.widget.Toast;

public class AppUtils {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}