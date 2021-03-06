package com.example.mohit.b3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText input_email, input_password;
    Button btn_login;

    public static String validEmail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

            "\\@" +

            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

            "(" +

            "\\." +

            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

            ")+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        btn_login = findViewById(R.id.btn_login);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_email.setBackgroundResource(R.drawable.edit_text_round_shape_normal);
                input_email.setBackgroundResource(R.drawable.edit_text_round_shape_normal);
                if (TextUtils.isEmpty(input_email.getText().toString())) {
                    input_email.setBackgroundResource(R.drawable.edit_text_error);
                    return;
                }
                if (TextUtils.isEmpty(input_password.getText().toString())) {
                    input_password.setBackgroundResource(R.drawable.edit_text_error);
                    return;
                }
                Matcher matcher = Pattern.compile(validEmail).matcher(input_email.getText());
                if (!matcher.matches()) {
                    input_email.setBackgroundResource(R.drawable.edit_text_error);
                    return;
                }
                if (input_password.getText().toString().length() < 6) {
                    input_password.setBackgroundResource(R.drawable.edit_text_error);
                    return;
                }
                userSignIn(input_email.getText().toString(), input_password.getText().toString());
            }
        });

    }

    public void userSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("sign in success", "signInWithEmail:success");
                            Toast.makeText(SignInActivity.this, "Loged In", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(SignInActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(SignInActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                            com.example.mohit.b3.extra.UserInfo.userAuthId = user.getUid();
                            Intent intent = new Intent(SignInActivity.this,ProfileActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("sign in failure", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
