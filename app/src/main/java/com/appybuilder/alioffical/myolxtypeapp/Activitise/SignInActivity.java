package com.appybuilder.alioffical.myolxtypeapp.Activitise;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.MainActivity;
import com.appybuilder.alioffical.myolxtypeapp.Models.MyPrefrence;
import com.appybuilder.alioffical.myolxtypeapp.Models.ShowAdsModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.User;
import com.appybuilder.alioffical.myolxtypeapp.Models.UserModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView btnSignup, gotosignup;
    private Button btnSignin;
    public static EditText et_email, et_passwod;
    private boolean chekforsignin = false;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    private ImageView btnshow_hide_pass;
    private ProgressDialog pDialog;
    FirebaseAuth auth;
    TextView btnforgotpass;
    private String strToken;
    MyPrefrence myPrefrence;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String username, email, phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        InitViews();//intilization of views .......
    }

    public void InitViews() {
        myPrefrence = new MyPrefrence(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        btnSignup = findViewById(R.id.gotosignup);
        btnSignup.setOnClickListener(this::onClick);
        btnSignin = findViewById(R.id.signinbtnid);
        btnSignin.setOnClickListener(this::onClick);
        et_email = findViewById(R.id.email_signi_id);
        et_email.requestFocus();
        et_passwod = findViewById(R.id.password_signin_id);

        pDialog = new ProgressDialog(SignInActivity.this, R.style.AppCompatAlertDialogStyle);
        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gotosignup = findViewById(R.id.gotosignup);
        gotosignup.setOnClickListener(this);
        btnforgotpass = findViewById(R.id.btnforgotpass);
        btnforgotpass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signinbtnid) {
            signInfun();//sign in via......
        }
        if (view.getId() == R.id.gotosignup) {
            startActivity(new Intent(SignInActivity.this, UserRegisterActivity.class));
        }
        if (view.getId() == R.id.btnforgotpass) {
            startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
        }

    }

    private void signInfun() {
        if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
            et_email.setError("Please Enter Email");
            et_email.setFocusable(true);
            et_email.requestFocus();
        } else if (!et_email.getText().toString().trim().matches(emailPattern)) {
            et_email.setError("Please Enter Valid Email");
            et_email.setFocusable(true);
            et_email.requestFocus();
        } else if (TextUtils.isEmpty(et_passwod.getText().toString().trim())) {
            et_passwod.setError("Enter Password");
            et_passwod.setFocusable(true);
            et_passwod.requestFocus();
        } else {
            pDialog.show();
            signInfunApis();
        }
    }

    public void signInfunApis() {
//        Intent newActivity1 = new Intent(SignInActivity.this, MainActivity.class);
//        newActivity1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(newActivity1);
        //  Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
        Call<ShowAdsModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        call = service.Login(et_email.getText().toString().trim(), et_passwod.getText().toString().trim());
        call.enqueue(new Callback<ShowAdsModel>() {
            @Override
            public void onResponse(Call<ShowAdsModel> call, Response<ShowAdsModel> response) {
                try {
                    if (response.isSuccessful()) {
                        pDialog.dismiss();
                        ShowAdsModel model = response.body();
                        User model1 = response.body().getUser();
                        strToken = model.getToken();
                        //storing Token Inside Prefrence
                        username = model1.getName().toString();
                        try {
                            phoneno = model1.getPhoneNo().toString();
                            email = model1.getEmail();
                        } catch (NullPointerException e) {
                            Log.d("Exception", e.getMessage());
                        }
                        MyPrefrence.setStr("token", strToken);
                        MyPrefrence.setUserDetails(username, email, phoneno);
                        MyPrefrence.setStr("phone", phoneno);
                        MyPrefrence.setStr("name", username);
                        MyPrefrence.setStr("email", model1.getEmail());
                        MyPrefrence.setStr("session",model1.getEmail());
                        Toast.makeText(getApplicationContext(), "SignInSuceFully", Toast.LENGTH_LONG).show();
                        Intent newActivity1 = new Intent(SignInActivity.this, MainActivity.class);
                        newActivity1.putExtra("token", strToken);
                        newActivity1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(newActivity1);
                    }
                } catch (NullPointerException e) {
                    Log.d("TAG", e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<ShowAdsModel> call, Throwable t) {
                call.cancel();
                pDialog.dismiss();
                Toast.makeText(SignInActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        customeExist();

    }
    private void customeExist() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custome_dialog_2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button btnContinue = (Button) dialog.findViewById(R.id.btnContinue);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        LinearLayout uperlayout = dialog.findViewById(R.id.uperlayout);
        RelativeLayout confirm_layout = dialog.findViewById(R.id.confirm_layout);
        TextView txt_ok = dialog.findViewById(R.id.ok);
        ImageView img = dialog.findViewById(R.id.foodImg);
        //hare simple displaying the imge of specific items....

        btnCancel.setEnabled(true);
        btnContinue.setEnabled(true);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                finish();
                onDestroy();
            }
        });
        confirm_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   uperlayout.setVisibility(View.VISIBLE);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        dialog.show();
    }
}