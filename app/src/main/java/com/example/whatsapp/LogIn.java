package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

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
            case R.id.btnLogInLogIn:
                {
                    ParseUser.logInInBackground(edtEmailLogIn.getText().toString(),
                            edtPasswordLogIn.getText().toString(),
                            new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if ( user != null && e == null) {
                                FancyToast.makeText(LogIn.this,
                                        ParseUser.getCurrentUser().getUsername() + " is loged in",
                                        Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();


                            }else {
                                FancyToast.makeText(LogIn.this,
                                        e.getMessage(),Toast.LENGTH_SHORT,
                                        FancyToast.ERROR,true).show();
                            }

                        }
                    });
                    break;

            }
            case R.id.btnSignUpLogIn:
                Intent intent = new Intent(LogIn.this,MainActivity.class);
                startActivity(intent);
        }

    }
}
