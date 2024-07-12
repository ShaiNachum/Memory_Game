package com.example.memory_game.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.memory_game.Model.NewUser;
import com.example.memory_game.R;
import com.example.memory_game.Model.User;
import com.example.memory_game.Utilities.UserApi;
import com.example.memory_game.Utilities.UserController;
import com.example.memory_game.Model.UserRoleEnum;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WelcomeActivity extends AppCompatActivity {
    private MaterialTextView welcome_LBL_welcome;
    private MaterialTextView welcome_LBL_enter_email;
    private AppCompatEditText welcome_EDT_email;
    private MaterialButton welcome_BTN_signup;
    private MaterialButton welcome_BTN_login;
    private MaterialTextView welcome_MTV_signUp;
    UserApi apiService = UserController.getRetrofitInstance().create(UserApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViews();
        initViews();
    }

    private void initViews() {
        welcome_BTN_login.setOnClickListener(View -> logInClicked());
        welcome_MTV_signUp.setOnClickListener(View -> SwitchToSignUpActivity());
    }

    private void logInClicked() {
        String email = welcome_EDT_email.getText().toString();
        Call<User> call = apiService.findUser("MiniHeros", email);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if (response.isSuccessful()  && user != null) {
                    Toast.makeText(WelcomeActivity.this, "Hello " + user.getUsername().toString(), Toast.LENGTH_LONG).show();
                    SwitchToGameIntent(user.getUserId().getSuperapp(),user.getUserId().getEmail());

                } else {
                    //Log.e("Retrofit", "Failed to retrieve user. Error code: " + response.code());
                    Toast.makeText(WelcomeActivity.this, "Failed to log in ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Retrofit", "Network error or failure: " + t.getMessage());
            }
        });
    }

    private void SwitchToSignUpActivity(){
        Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void findViews() {
        welcome_LBL_welcome = findViewById(R.id.welcome_LBL_welcome);
        welcome_BTN_login = findViewById(R.id.welcome_BTN_login);
        welcome_LBL_enter_email = findViewById(R.id.welcome_LBL_enter_email);
        welcome_EDT_email = findViewById(R.id.welcome_EDT_email);
        welcome_MTV_signUp = findViewById(R.id.welcome_MTV_signUp);
    }


    private void SwitchToGameIntent(String superapp, String email) {
        Intent intent = new Intent(WelcomeActivity.this, GameActivity.class);
        intent.putExtra("superapp", superapp);
        intent.putExtra("email", email);
        startActivity(intent);
        this.finish();
    }
}