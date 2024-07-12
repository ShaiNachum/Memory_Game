package com.example.memory_game.Utilities;

import com.example.memory_game.Model.NewUser;
import com.example.memory_game.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UserApi {

    @POST("users")
    Call<User> createUser(@Body NewUser newUser);

    @GET("users/login/{superapp}/{email}")
    Call<User> findUser(
            @Path("superapp")String superapp,
            @Path("email")String email
    );

    @PUT("users/{superapp}/{userEmail}")
    Call<User> updateUser(
            @Path("superapp") String superapp,
            @Path("userEmail")String userEmail,
            @Body User updatedUser
    );
}
