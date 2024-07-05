package com.example.memory_game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndActivity extends AppCompatActivity {
    private MaterialTextView end_LBL_title;
    private MaterialTextView end_LBL_attempts;
    private MaterialTextView end_LBL_score;
    private MaterialTextView end_LBL_balance;
    private ShapeableImageView end_BTN_backToOpening;
    private int score;
    private int attempts;
    private int coinsBalance;
    private String email;
    private String superapp;
    UserApi apiService = UserController.getRetrofitInstance().create(UserApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        this.superapp = intent.getStringExtra("superapp");
        this.email = intent.getStringExtra("email");
        this.attempts = intent.getIntExtra("attempts", 0);
        this.score = intent.getIntExtra("score", 0);

        findViews();
        initViews();
        GetUserFromDB();
    }


    private void GetUserFromDB() {
        Call<User> call = apiService.findUser("MiniHeros", email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if (response.isSuccessful()  && user != null) {
                    UpdateUserInDB(user);
                } else {
                    Toast.makeText(EndActivity.this, "Failed to find user ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Retrofit", "Network error or failure: " + t.getMessage());
            }
        });
    }



    private void UpdateUserInDB(User user) {
        String[] userDetails = user.getAvatar().split("#");
        this.coinsBalance = Integer.parseInt(userDetails[1]);
        coinsBalance += score;
        end_LBL_balance.setText("Current balance: " + this.coinsBalance);
        user.setAvatar(userDetails[0] + "#" + coinsBalance);
        String tostring = user.toString();
        Call<User> call = apiService.updateUser(user.getUserId().getSuperapp(),user.getUserId().getEmail(),user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User UpdatedUser = response.body();
                if (response.isSuccessful()  && user != null) {

                } else {
                    Toast.makeText(EndActivity.this, "Failed to find user ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
    }



    private void initViews() {
        end_LBL_attempts.setText("Attempts: " + this.attempts);
        end_LBL_score.setText("Score: " + this.score);
        end_LBL_balance.setText("Current balance: " + this.coinsBalance);
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