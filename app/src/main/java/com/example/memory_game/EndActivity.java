package com.example.memory_game;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class EndActivity extends AppCompatActivity {
    private MaterialTextView end_LBL_title;
    private MaterialTextView end_LBL_attempts;
    private MaterialTextView end_LBL_score;
    private MaterialTextView end_LBL_balance;
    private ShapeableImageView end_BTN_backToOpening;
    private int score;
    private int attempts;
    private int currentBalance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        this.attempts = intent.getIntExtra("attempts", 0);
        this.score = intent.getIntExtra("score", 0);

        findViews();
        initViews();
    }

    private void initViews() {
        end_LBL_attempts.setText("Attempts: " + this.attempts);
        end_LBL_score.setText("Score: " + this.score);
        end_BTN_backToOpening.setOnClickListener(View -> homeClicked());
    }

    private void homeClicked() {
        Intent intent = new Intent(EndActivity.this, WelcomeActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void findViews() {
        end_LBL_title = findViewById(R.id.end_LBL_title);
        end_LBL_attempts = findViewById(R.id.end_LBL_attempts);
        end_LBL_score = findViewById(R.id.end_LBL_score);
        end_LBL_balance = findViewById(R.id.end_LBL_balance);
        end_BTN_backToOpening = findViewById(R.id.end_BTN_backToOpening);
    }
}