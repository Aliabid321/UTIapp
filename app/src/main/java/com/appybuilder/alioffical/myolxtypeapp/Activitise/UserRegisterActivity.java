package com.appybuilder.alioffical.myolxtypeapp.Activitise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.MainActivity;
import com.appybuilder.alioffical.myolxtypeapp.Models.UserModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_fullName, et_phoneNumber, et_Email, et_Pass, et_ConfirmPass;
    private Button btnsignup;
    private boolean chekpermission_to_send_data = false;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    private ImageView btnshow_hide_pass, btnshow_hide_confirmpass;
    private ProgressDialog pDialog;
    FirebaseAuth auth;
    DatabaseReference databaseRefrence;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        IntViews();//intilization of views.......
    }

    private void IntViews() {
        et_fullName = findViewById(R.id.fullname_id);
        et_fullName.requestFocus();
        et_phoneNumber = findViewById(R.id.et_phonenumber_id);
        et_Email = findViewById(R.id.email_id);
        et_Pass = findViewById(R.id.password_signup_id);
        et_ConfirmPass = findViewById(R.id.confirmpass_signup_id);
        btnsignup = findViewById(R.id.btnsignup_id);
        btnsignup.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        databaseRefrence = database.getReference();
        //progress bar dilog,,,,
        pDialog = new ProgressDialog(UserRegisterActivity.this, R.style.AppCompatAlertDialogStyle);
        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

    public void sendData() {
        if (TextUtils.isEmpty(et_fullName.getText().toString().trim())) {
            et_fullName.setError("Please Enter Full Name");
            et_fullName.setFocusable(true);
            et_fullName.requestFocus();
        } else if (TextUtils.isEmpty(et_phoneNumber.getText().toString().trim())) {
            et_phoneNumber.setError("Please Enter Your Phone Number");
            et_phoneNumber.setFocusable(true);
            et_phoneNumber.requestFocus();
        } else if (TextUtils.isEmpty(et_Email.getText().toString().trim())) {
            et_Email.setError("Please Enter Your Email");
            et_Email.setFocusable(true);
            et_Email.requestFocus();
        }
//        else if (!et_Email.getText().toString().trim().matches(emailPattern)) {
//            et_Email.setError("Please Enter Valid Email");
//            et_Email.setFocusable(true);
//            et_Email.requestFocus();
//        }
        else if (TextUtils.isEmpty(et_Pass.getText().toString().trim())) {
            et_Pass.setError("Please Enter Password");
            et_Pass.setFocusable(true);
            et_Pass.requestFocus();
        } else if (TextUtils.isEmpty(et_ConfirmPass.getText().toString().trim())) {
            et_ConfirmPass.setError("Please Confirm Password");
            et_ConfirmPass.setFocusable(true);
            et_ConfirmPass.requestFocus();
        } else if (!et_Pass.getText().toString().trim().equals(et_ConfirmPass.getText().toString().trim())) {
            et_ConfirmPass.setError("Password didnt Match");
            et_ConfirmPass.setFocusable(true);
            et_ConfirmPass.requestFocus();
        } else {
            pDialog.show();
            SignUpfun();//signup method is Hare,,,,,,,,,,
        }
    }

    public void SignUpfun(){
        Call<UserModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        call = service.SignUp(et_Email.getText().toString().trim(),et_Pass.getText().toString().trim(),et_ConfirmPass.getText().toString().trim());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                pDialog.dismiss();
                if (response.isSuccessful()){
                    UserModel signInModel=response.body();
                    Toast.makeText(getApplicationContext(),"SignUpSucessfully",Toast.LENGTH_LONG).show();
                    Intent newActivity1 = new Intent(UserRegisterActivity.this, SignInActivity.class);
                    newActivity1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(newActivity1);
//                    finish();
                }
                if (response.code()==401){
                    Toast.makeText(getApplicationContext(),"Email Already Exist",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                call.cancel();
                pDialog.dismiss();
                Toast.makeText(UserRegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnsignup_id) {
            sendData();
        }

    }
}