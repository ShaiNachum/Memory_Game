package com.example.memory_game.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.memory_game.Model.NewUser;
import com.example.memory_game.Model.User;
import com.example.memory_game.Model.UserRoleEnum;
import com.example.memory_game.R;
import com.example.memory_game.Utilities.UserApi;
import com.example.memory_game.Utilities.UserController;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private MaterialTextView signup_LBL_signUp;
    private MaterialTextView signup_LBL_enter_familyName;
    private AppCompatEditText signup_EDT_familyName;
    private MaterialTextView signup_LBL_enter_email;
    private AppCompatEditText signup_EDT_email;
    private MaterialButton signup_BTN_signup;
    UserApi apiService = UserController.getRetrofitInstance().create(UserApi.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        initViews();
    }

    private void initViews() {
        signup_BTN_signup.setOnClickListener(View -> signUpClicked());
    }

    private void signUpClicked() {
        String playerName = signup_EDT_familyName.getText().toString();

        String email = signup_EDT_email.getText().toString();

        NewUser newUser = new NewUser();
        newUser.setEmail(email);
        newUser.setAvatar("child#0");
        newUser.setUsername(playerName);
        newUser.setRole(UserRoleEnum.MINIAPP_USER);

        Call<User> call = apiService.createUser(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User createdNewUser = response.body();
                if (response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                    SwitchToGameActivity(createdNewUser.getUserId().getSuperapp(),createdNewUser.getUserId().getEmail());
                } else {
                    Toast.makeText(SignUpActivity.this, "Failed to sign up user", Toast.LENGTH_LONG).show();
                    Log.e("Request failed: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public boolean isNotNullOrEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    private void SwitchToGameActivity(String superapp, String email) {
        Intent intent = new Intent(SignUpActivity.this, GameActivity.class);
        intent.putExtra("superapp", superapp);
        intent.putExtra("email", email);
        startActivity(intent);
        this.finish();
    }

    private void findViews() {
        signup_LBL_signUp = findViewById(R.id.signup_LBL_signUp);
        signup_LBL_enter_familyName = findViewById(R.id.signup_LBL_enter_familyName);
        signup_EDT_familyName = findViewById(R.id.signup_EDT_familyName);
        signup_LBL_enter_email = findViewById(R.id.signup_LBL_enter_email);
        signup_EDT_email = findViewById(R.id.signup_EDT_email);
        signup_BTN_signup = findViewById(R.id.signup_BTN_signup);
    }
}