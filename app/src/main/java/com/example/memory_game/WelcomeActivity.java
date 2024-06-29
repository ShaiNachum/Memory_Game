package com.example.memory_game;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class WelcomeActivity extends AppCompatActivity {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private MaterialTextView welcome_LBL_welcome;
    private MaterialTextView welcome_LBL_enter_name;
    private AppCompatEditText welcome_EDT_name;
    private MaterialTextView welcome_LBL_enter_email;
    private AppCompatEditText welcome_EDT_email;
    private ShapeableImageView welcome_BTN_start;
    private boolean isNameEntered;
    private boolean isEmailEnteredAndValid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViews();
        initViews();
    }

    private void initViews() {
        welcome_BTN_start.setOnClickListener(View -> startClicked());
    }

    private void startClicked() {
        String playerName = welcome_EDT_name.getText().toString();
        if(isNotNullOrEmpty(playerName))
            this.isNameEntered = true;

        String email = welcome_EDT_email.getText().toString();
        if(isValidEmail(email))
            this.isEmailEnteredAndValid = true;

        Intent intent = new Intent(WelcomeActivity.this, GameActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("playerEmail", email);

        if(isNameEntered && isEmailEnteredAndValid){
            startActivity(intent);
            this.finish();
        }
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isNotNullOrEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    private void findViews() {
        welcome_LBL_welcome = findViewById(R.id.welcome_LBL_welcome);
        welcome_LBL_enter_name = findViewById(R.id.welcome_LBL_enter_name);
        welcome_EDT_name = findViewById(R.id.welcome_EDT_name);
        welcome_BTN_start = findViewById(R.id.welcome_BTN_start);
        welcome_LBL_enter_email = findViewById(R.id.welcome_LBL_enter_email);
        welcome_EDT_email = findViewById(R.id.welcome_EDT_email);
    }
}