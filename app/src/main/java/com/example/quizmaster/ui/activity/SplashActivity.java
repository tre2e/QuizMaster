package com.example.quizmaster.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.quizmaster.R;
import com.example.quizmaster.data.model.Question;
import com.example.quizmaster.ui.viewmodel.QuestionViewModel;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 初始化 ViewModel
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        // 预加载题目
        loadSampleQuestions();

        // 延迟 2 秒后跳转
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000);
    }

    private void loadSampleQuestions() {
        List<Question> sampleQuestions = Arrays.asList(
                new Question(1, "2 + 2 = ?", "3", "4", "5", "6", "B"),
                new Question(2, "What is the capital of France?", "London", "Berlin", "Paris", "Madrid", "C"),
                new Question(3, "1 + 1 = ?", "1", "2", "3", "4", "B"),
                new Question(4, "Largest planet?", "Earth", "Mars", "Jupiter", "Saturn", "C"),
                new Question(5, "5 × 5 = ?", "20", "25", "30", "35", "B")
        );

        // 异步插入，避免阻塞 UI
        questionViewModel.insertQuestions(sampleQuestions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // 防止内存泄漏
    }
}