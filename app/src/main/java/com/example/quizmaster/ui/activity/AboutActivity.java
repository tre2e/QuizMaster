package com.example.quizmaster.ui.activity;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizmaster.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // 显示版本号
        TextView tvVersion = findViewById(R.id.tvVersion);
        try {
            PackageInfo pInfo = getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            tvVersion.setText("Version " + pInfo.versionName);
        } catch (Exception e) {
            tvVersion.setText("Version unknown");
        }

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}