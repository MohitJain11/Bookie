package com.example.mohit.b3;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mohit.b3.POJOS.UserSignUp;
import com.example.mohit.b3.Retrofit.RetrofitClient;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private String Language="English";
    private EditText input_user_email, input_address,input_user_password,input_city,input_name,input_contact_number;
    private RadioButton radio_hindi,radio_english;
    Button btn_signUp;
    private Uri file;
    private ImageView profilePic;
    private Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private Bitmap bitmap = null;
    private String s[] = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        profilePic = findViewById(R.id.profile_image);
        mAuth = FirebaseAuth.getInstance();
        input_user_email = findViewById(R.id.input_user_email);
        input_user_password = findViewById(R.id.input_user_password);
        input_city=findViewById(R.id.input_city);
        input_name=findViewById(R.id.input_name);
        input_contact_number=findViewById(R.id.input_contact_number);
        input_address=findViewById(R.id.input_address);
        radio_hindi=findViewById(R.id.radio_hindi);
        radio_english=findViewById(R.id.radio_english);
        radio_english.setChecked(true);
        progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading");
        btn_signUp = findViewById(R.id.btn_signUp);
        mStorageRef = FirebaseStorage.getInstance().getReference();
//        @Override
//        public void onStart() {
//            super.onStart();
//            // Check if user is signed in (non-null) and update UI accordingly.
//            FirebaseUser currentUser = mAuth.getCurrentUser();
//            updateUI(currentUser);
//        }

        radio_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Language="Hindi";
            }
        });
        radio_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Language="English";
            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else
                    ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, SELECT_FILE);
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validContactNumber = "[0-9]{10,11}";
                String validEmail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                        "\\@" +

                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                        "(" +

                        "\\." +

                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                        ")+";

                if(TextUtils.isEmpty(input_name.getText().toString())) {
                    input_name.setError("Enter Name");
                    return;
                }
                if(TextUtils.isEmpty(input_contact_number.getText().toString())) {
                    input_contact_number.setError("Enter Contact Number");
                    return;
                }
                if(!input_contact_number.getText().toString().matches(validContactNumber))
                {
                    input_contact_number.setError("Enter Valid Contact Number");
                    return;

                }
                if(TextUtils.isEmpty(input_address.getText().toString())) {
                    input_address.setError("Enter Address");
                    return;
                }
                if(TextUtils.isEmpty(input_city.getText().toString())) {
                    input_city.setError("Enter City");
                    return;
                }
                if(TextUtils.isEmpty(input_user_email.getText().toString())) {
                    input_user_email.setError("Enter Email Id");
                    return;
                }
                Matcher matcher= Pattern.compile(validEmail).matcher(input_user_email.getText().toString());
                if(!matcher.matches()) {
                    input_user_email.setError("Enter Valid Email Id");
                    return;
                }
                if(TextUtils.isEmpty(input_user_password.getText().toString())) {
                    input_user_password.setError("Enter Password");
                    return;
                }
                if(input_user_password.getText().toString().length()<6)
                {
                    input_user_password.setError("Password Should Be More Then 6 Characters");
                    return;
                }
                if(bitmap==null)
                {
                    Toast.makeText(SignUpActivity.this, "Select Profile Picture", Toast.LENGTH_SHORT).show();
                    return ;
                }
                signUpNewUser();
            }
        });

    }

    public void signUpNewUser() {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(input_user_email.getText().toString(), input_user_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserData(task.getResult().getUser().getUid());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Fail SignUp", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            progressDialog.dismiss();
                        }

                        // ...
                    }
                });
    }

    private void sendUserData(final String id)
    {

        final StorageReference riversRef = mStorageRef.child("user/profilePic.jpg");
        riversRef.putFile(file).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downUri = task.getResult();
                    //Log.d(TAG, "onComplete: Url: "+ downUri.toString());
                    Call<UserSignUp> call = RetrofitClient.getApi().RegisterUser(id, input_name.getText().toString(),
                            input_contact_number.getText().toString(), input_address.getText().toString(), "Udaipur", downUri.toString(), Language);
                    call.enqueue(new Callback<UserSignUp>() {
                        @Override
                        public void onResponse(Call<UserSignUp> call, Response<UserSignUp> response) {
                            Toast.makeText(SignUpActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<UserSignUp> call, Throwable t) {
                            progressDialog.dismiss();

                        }
                    });

                }
            }
        });
    }
    private void selectImage() {
        final CharSequence[] items = {"CAMERA", "GALLERY", "CANCEL"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Select Profile Picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("CAMERA")) {
                    if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } else {
                        ActivityCompat.requestPermissions(SignUpActivity.this, s, REQUEST_CAMERA);
                    }

                } else if (items[which].equals("GALLERY")) {
                    if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                    } else{
                        ActivityCompat.requestPermissions(SignUpActivity.this, s, REQUEST_CAMERA);
                    }

                } else if (items[which].equals("CANCEL")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA &&grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else
            Toast.makeText(this, "Please grant permission for camera", Toast.LENGTH_SHORT).show();
        if(requestCode == SELECT_FILE && grantResults[0] == PackageManager.PERMISSION_GRANTED )
            selectImage();
        else
            Toast.makeText(this, "Please grant permission for storage", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                file=getImageUri(bitmap);
                profilePic.setImageBitmap(bitmap);

            } else if (requestCode == SELECT_FILE) {
                Uri selectImageUri = data.getData();
                file=selectImageUri;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectImageUri);
                    profilePic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(SignUpActivity.this.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public void signInUser() {
        mAuth.signInWithEmailAndPassword("abc@gmail.com", "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signIn", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            Toast.makeText(SignUpActivity.this, "Sign In successfully!.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Fail signIn", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

//    public void uploadUserPic(){
//        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
//        StorageReference riversRef = mStorageRef.child("images/rivers.jpg");
//
//        riversRef.putFile(file)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        // ...
//                    }
//                });
//    }
}
