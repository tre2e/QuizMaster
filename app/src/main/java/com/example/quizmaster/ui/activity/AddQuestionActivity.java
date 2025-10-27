package com.example.quizmaster.ui.activity;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.quizmaster.R;
import com.example.quizmaster.data.model.Question;
import com.example.quizmaster.ui.viewmodel.QuestionViewModel;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText etQuestion, etOptionA, etOptionB, etOptionC, etOptionD;
    private Spinner spinnerCorrect;
    private QuestionViewModel viewModel;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        initViews();
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        findViewById(R.id.btnAdd).setOnClickListener(v -> addSingleQuestion());
        findViewById(R.id.btnImportJson).setOnClickListener(v -> importFromJson());
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void initViews() {
        etQuestion = findViewById(R.id.etQuestion);
        etOptionA = findViewById(R.id.etOptionA);
        etOptionB = findViewById(R.id.etOptionB);
        etOptionC = findViewById(R.id.etOptionC);
        etOptionD = findViewById(R.id.etOptionD);
        spinnerCorrect = findViewById(R.id.spinnerCorrect);
    }

    private void addSingleQuestion() {
        String q = etQuestion.getText().toString().trim();
        String a = etOptionA.getText().toString().trim();
        String b = etOptionB.getText().toString().trim();
        String c = etOptionC.getText().toString().trim();
        String d = etOptionD.getText().toString().trim();
        String correct = spinnerCorrect.getSelectedItem().toString();

        if (q.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int newId = getNextId();
        Question question = new Question(newId, q, a, b, c, d, correct);
        viewModel.insertQuestions(Collections.singletonList(question));

        Toast.makeText(this, "Question added!", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void importFromJson() {
        try {
            InputStream is = getAssets().open("questions.json");
            InputStreamReader reader = new InputStreamReader(is);
            Type listType = new TypeToken<List<Question>>(){}.getType();
            List<Question> imported = gson.fromJson(reader, listType);
            reader.close();

            if (imported == null || imported.isEmpty()) {
                Toast.makeText(this, "No questions in JSON", Toast.LENGTH_SHORT).show();
                return;
            }

            // 去重 + 补 ID
            List<Question> valid = new ArrayList<>();
            Set<Integer> existingIds = getExistingIds();
            int maxId = getMaxId();

            for (Question q : imported) {
                if (q.question == null || q.question.trim().isEmpty()) continue;
                if (!existingIds.contains(q.id)) {
                    valid.add(q);
                } else {
                    // ID 冲突，重新分配
                    maxId++;
                    q.id = maxId;
                    valid.add(q);
                }
            }

            if (!valid.isEmpty()) {
                viewModel.insertQuestions(valid);
                Toast.makeText(this, "Imported " + valid.size() + " questions", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No new questions to import", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Import failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private int getNextId() {
        return getMaxId() + 1;
    }

    private int getMaxId() {
        // 简单实现：从数据库取最大 ID
        // 实际项目可用 Room 查询
        return 1000; // 临时
    }

    private Set<Integer> getExistingIds() {
        // 实际应查询数据库
        return new HashSet<>();
    }

    private void clearFields() {
        etQuestion.setText("");
        etOptionA.setText("");
        etOptionB.setText("");
        etOptionC.setText("");
        etOptionD.setText("");
        spinnerCorrect.setSelection(0);
    }
}