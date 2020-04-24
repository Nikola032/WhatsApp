package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmailLogIn,edtPasswordLogIn;
    private Button btnLogInLogin,btnSignUpLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtEmailLogIn = findViewById(R.id.edtEmailLogIn);
        edtPasswordLogIn = findViewById(R.id.edtPassowrdLogIn);

        btnLogInLogin = findViewById(R.id.btnLogInLogIn);
        btnSignUpLogIn = findViewById(R.id.btnSignUpLogIn);

        btnLogInLogin.setOnClickListener(this);
        btnSignUpLogIn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogInLogIn:{

                final ProgressDialog dialog  = new ProgressDialog(this);
                dialog.setMessage("Loading...");
                dialog.show();
                if (edtEmailLogIn.getText().toString().equals("") || edtPasswordLogIn.getText().toString().equals("")){
                    FancyToast.makeText
                            (LogIn.this,"Email, Password is requierd!",
                                    FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();
                }
                ParseUser.logInInBackground(edtEmailLogIn.getText().toString(),
                        edtPasswordLogIn.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null) {
                                    FancyToast.makeText(LogIn.this, user.getUsername()
                                            + " is loged in", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                    Intent intent = new Intent(LogIn.this,WhatsAppUsers.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    FancyToast.makeText(LogIn.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();

                                }
                                dialog.dismiss();

                            }
                        });
                    break;
                }
            case R.id.btnSignUpLogIn:{
                Intent intent = new Intent(LogIn.this,MainActivity.class);
                startActivity(intent);
                finish();
        }

    }
}}
