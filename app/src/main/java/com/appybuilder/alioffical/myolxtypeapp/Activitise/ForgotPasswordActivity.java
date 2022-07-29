package com.appybuilder.alioffical.myolxtypeapp.Activitise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.Models.UserModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnsubmit;
    FirebaseAuth auth;
    EditText et_email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        intit_views();//intilization of views
    }
    public void intit_views(){
        auth=FirebaseAuth.getInstance();
        btnsubmit=findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(this);
        et_email=findViewById(R.id.emailET_reset);
    }
    public void sendEmail(){
        if (et_email.getText().toString().trim().isEmpty()){
            et_email.setError("Please Enter Email Address");
            et_email.requestFocus();
        }
        else if (!et_email.getText().toString().trim().matches(emailPattern)){
            et_email.setError("Please Enter the Valid Email");
            et_email.requestFocus();
        }
        else{
            emailsentcall();
        }

    }
    public void emailsentcall(){
        Call<UserModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        call = service.resetPass(et_email.getText().toString().trim());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
               // pDialog.dismiss();
                if (response.isSuccessful()){
                    UserModel signInModel=response.body();
                    Toast.makeText(getApplicationContext(),"Chek Your Email Address",Toast.LENGTH_LONG).show();
                    Intent newActivity1 = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                    newActivity1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(newActivity1);
//                    finish();
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(ForgotPasswordActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnsubmit){
            sendEmail();
        }
    }
}