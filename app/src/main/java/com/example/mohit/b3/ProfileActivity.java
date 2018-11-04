package com.example.mohit.b3;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohit.b3.POJOS.GetUserInfo;
import com.example.mohit.b3.POJOS.UserSignUp;
import com.example.mohit.b3.Retrofit.RetrofitClient;
import com.example.mohit.b3.extra.UserInfo;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextView icon_user;
    private ProgressDialog progressDialog;

    TextView tv_total_coin, tv_book_buyed, tv_book_sell;
    TextView tv_user_name;
    TextView tv_user_name_edit;
    TextView tv_user_email;
    TextView tv_user_mobile;
    TextView tv_user_address;
    TextView tv_user_language;

    CircleImageView user_profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressDialog=new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Loading");

        tv_total_coin = findViewById(R.id.tv_total_coin);
        tv_book_buyed = findViewById(R.id.tv_book_buyed);
        tv_book_sell = findViewById(R.id.tv_book_sell);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_name_edit = findViewById(R.id.tv_user_name_edit);
        tv_user_email = findViewById(R.id.tv_user_email);
        tv_user_mobile = findViewById(R.id.tv_user_mobile);
        tv_user_address = findViewById(R.id.tv_user_address);
        tv_user_language = findViewById(R.id.tv_user_language);

        user_profile_image = findViewById(R.id.user_profile_image);

        getUserInfo(UserInfo.userAuthId);


    }

    public void getUserInfo(String userAuthId){
        progressDialog.show();
        Call<GetUserInfo> call = RetrofitClient.getApi().GetUserInfo(userAuthId);
        call.enqueue(new Callback<GetUserInfo>() {
            @Override
            public void onResponse(Call<GetUserInfo> call, Response<GetUserInfo> response) {

                tv_user_name.setText(response.body().getUserName());
                tv_user_name_edit.setText(response.body().getUserName());
                tv_user_address.setText(response.body().getAddress()+" "+response.body().getCity());
                tv_user_language.setText(response.body().getLanguage());
                tv_user_mobile.setText(response.body().getUserMobile());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetUserInfo> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }
}
