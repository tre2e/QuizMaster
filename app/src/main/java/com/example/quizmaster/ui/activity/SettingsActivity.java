package com.example.quizmaster.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.quizmaster.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREF_NAME = "quiz_settings";
    private static final String KEY_QUESTION_COUNT = "question_count";
    private static final String KEY_DARK_MODE = "dark_mode";

    private SharedPreferences prefs;
    private Spinner spinner;
    private SwitchMaterial switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        spinner = findViewById(R.id.spinnerQuestionCount);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        Button btnBack = findViewById(R.id.btnBack);

        //添加题库
        findViewById(R.id.btnAddQuestions).setOnClickListener(v ->
                startActivity(new Intent(this, AddQuestionActivity.class)));

        // 初始化 Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.question_counts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int savedCount = prefs.getInt(KEY_QUESTION_COUNT, 10);
        setSpinnerSelection(savedCount);

        // 夜间模式
        boolean darkMode = prefs.getBoolean(KEY_DARK_MODE,
                (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES));
        switchDarkMode.setChecked(darkMode);
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO);
        });

        btnBack.setOnClickListener(v -> {
            int count = Integer.parseInt(spinner.getSelectedItem().toString());
            prefs.edit().putInt(KEY_QUESTION_COUNT, count).apply();
            finish();
        });
    }

    private void setSpinnerSelection(int value) {
        String[] values = getResources().getStringArray(R.array.question_counts);
        for (int i = 0; i < values.length; i++) {
            if (Integer.parseInt(values[i]) == value) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}