package com.example.memory_game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WelcomeActivity extends AppCompatActivity {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private MaterialTextView welcome_LBL_welcome;
    private MaterialTextView welcome_LBL_enter_name;
    private AppCompatEditText welcome_EDT_name;
    private MaterialTextView welcome_LBL_enter_email;
    private AppCompatEditText welcome_EDT_email;
    private MaterialButton welcome_BTN_signup;
    private MaterialButton welcome_BTN_login;

    private boolean isNameEntered;
    private boolean isEmailEnteredAndValid;

    UserApi apiService = UserController.getRetrofitInstance().create(UserApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViews();
        initViews();
    }

    private void initViews() {
        welcome_BTN_signup.setOnClickListener(View -> signUpClicked());
        welcome_BTN_login.setOnClickListener(View -> signInClicked());
    }

    private void signInClicked() {
        String email = welcome_EDT_email.getText().toString();
        Call<User> call = apiService.findUser("MiniHeros", email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
            //        Log.d("Retrofit", "Hello " + user.getUserId().toString());
                    Toast.makeText(WelcomeActivity.this, "Hello " + user.getUsername().toString(), Toast.LENGTH_LONG).show();
                    SwitchToGameIntent();

                } else {
            //        Log.e("Retrofit", "Failed to retrieve user. Error code: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Retrofit", "Network error or failure: " + t.getMessage());
            }
        });
    }

    private void signUpClicked() {
        String playerName = welcome_EDT_name.getText().toString();
        if (isNotNullOrEmpty(playerName))
            this.isNameEntered = true;

        String email = welcome_EDT_email.getText().toString();
        if (isValidEmail(email))
            this.isEmailEnteredAndValid = true;


        NewUser newUser = new NewUser();
        newUser.setEmail(email);
        newUser.setAvatar("child#0");
        newUser.setUsername(playerName);
        newUser.setRole(UserRoleEnum.MINIAPP_USER);

        Call<NewUser> call = apiService.createUser(newUser);
        call.enqueue(new Callback<NewUser>() {
            @Override
            public void onResponse(Call<NewUser> call, Response<NewUser> response) {
                if (response.isSuccessful()) {
                    NewUser createdNewUser = response.body();
                    Toast.makeText(WelcomeActivity.this, "User created", Toast.LENGTH_LONG).show();
                    Log.e("Request failed: ", response.message());
                    SwitchToGameIntent();
                } else {
                    Toast.makeText(WelcomeActivity.this, "Failed to sign up user", Toast.LENGTH_LONG).show();
                    Log.e("Request failed: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<NewUser> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
        welcome_BTN_signup = findViewById(R.id.welcome_BTN_signup);
        welcome_BTN_login = findViewById(R.id.welcome_BTN_login);
        welcome_LBL_enter_email = findViewById(R.id.welcome_LBL_enter_email);
        welcome_EDT_email = findViewById(R.id.welcome_EDT_email);
    }

    private void SwitchToGameIntent() {
        Intent intent = new Intent(WelcomeActivity.this, GameActivity.class);
    //    if (isNameEntered && isEmailEnteredAndValid) {
            startActivity(intent);
            this.finish();
    //    }
    }
}