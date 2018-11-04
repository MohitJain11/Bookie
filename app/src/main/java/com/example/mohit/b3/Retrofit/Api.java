package com.example.mohit.b3.Retrofit;

import com.example.mohit.b3.POJOS.GetUserInfo;
import com.example.mohit.b3.POJOS.UserSignUp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("userSignUp")
    Call<UserSignUp> RegisterUser(@Field("userAuthId") String userAuthId,
                                  @Field("userName") String userName,
                                  @Field("userMobile") String userMobile,
                                  @Field("address") String address,
                                  @Field("city") String city,
                                  @Field("userPic") String userPic,
                                  @Field("language") String language

    );

    @FormUrlEncoded
    @POST("getUserInfo")
    Call<GetUserInfo> GetUserInfo(@Field("userAuthId") String userAuthId
    );

}
