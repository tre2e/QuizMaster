package com.example.quizmaster.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.quizmaster.R;
import com.example.quizmaster.data.model.Question;
import com.example.quizmaster.data.model.Wrong;
import com.example.quizmaster.ui.viewmodel.QuestionViewModel;
import com.example.quizmaster.ui.viewmodel.WrongViewModel;
import com.example.quizmaster.util.SharedPrefUtil;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private QuestionViewModel questionViewModel;
    private WrongViewModel wrongViewModel;
    private SharedPrefUtil prefUtil;

    private List<Question> questionList = new ArrayList<>();
    private List<String> userAnswers = new ArrayList<>();
    private int currentIndex = 0;
    private int correctCount = 0;

    private TextView tvQuestionCount, tvQuestion;
    private RadioGroup radioGroup;
    private RadioButton rbA, rbB, rbC, rbD;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initViews();
        prefUtil = new SharedPrefUtil(this);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        wrongViewModel = new ViewModelProvider(this).get(WrongViewModel.class);

        int questionCount = getIntent().getIntExtra("question_count", 10);
        loadQuestions(questionCount);
    }

    private void initViews() {
        tvQuestionCount = findViewById(R.id.tvQuestionCount);
        tvQuestion = findViewById(R.id.tvQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        rbA = findViewById(R.id.rbA);
        rbB = findViewById(R.id.rbB);
        rbC = findViewById(R.id.rbC);
        rbD = findViewById(R.id.rbD);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> nextQuestion());
    }

    private void loadQuestions(int limit) {
        questionViewModel.getRandomQuestions(limit).observe(this, questions -> {
            if (questions != null && !questions.isEmpty()) {
                questionList = questions;
                for (int i = 0; i < questionList.size(); i++) userAnswers.add("");
                showQuestion(0);
            }
        });
    }

    private void showQuestion(int index) {
        if (index >= questionList.size()) {
            finishQuiz();
            return;
        }

        currentIndex = index;
        Question q = questionList.get(index);

        tvQuestionCount.setText(String.format("Question %d/%d", index + 1, questionList.size()));
        tvQuestion.setText(q.question);
        rbA.setText("A. " + q.optionA);
        rbB.setText("B. " + q.optionB);
        rbC.setText("C. " + q.optionC);
        rbD.setText("D. " + q.optionD);

        radioGroup.clearCheck();
        String saved = userAnswers.get(index);
        if (!saved.isEmpty()) {
            ((RadioButton) findViewById(getRadioButtonId(saved))).setChecked(true);
        }

        btnNext.setText(index == questionList.size() - 1 ? "Finish" : "Next");
    }

    private int getRadioButtonId(String option) {
        switch (option) {
            case "A": return R.id.rbA;
            case "B": return R.id.rbB;
            case "C": return R.id.rbC;
            case "D": return R.id.rbD;
            default: return -1;
        }
    }

    private void nextQuestion() {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        String userAnswer = "";
        if (checkedId == R.id.rbA) userAnswer = "A";
        else if (checkedId == R.id.rbB) userAnswer = "B";
        else if (checkedId == R.id.rbC) userAnswer = "C";
        else if (checkedId == R.id.rbD) userAnswer = "D";

        userAnswers.set(currentIndex, userAnswer);

        Question q = questionList.get(currentIndex);
        if (userAnswer.equals(q.correctAnswer)) {
            correctCount++;
        } else if (!userAnswer.isEmpty()) {
            wrongViewModel.insert(new Wrong(q.id, userAnswer));
        }

        showQuestion(currentIndex + 1);
    }

    private void finishQuiz() {
        prefUtil.incrementQuizCount();
        for (int i = 0; i < correctCount; i++) {
            prefUtil.addCorrectAnswer();
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("total", questionList.size());
        startActivity(intent);
        finish();
    }
}