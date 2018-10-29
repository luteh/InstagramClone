package com.luteh.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.luteh.instagramclone.base.BaseActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUserNameSignUp, etPasswordSignUp, etUserNameLogIn, etPasswordLogIn;
    private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        etPasswordLogIn = findViewById(R.id.etPasswordLogIn);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        etUserNameLogIn = findViewById(R.id.etUserNameLogIn);
        etUserNameSignUp = findViewById(R.id.etUserNameSignUp);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn:
                logInUser();
                break;
            case R.id.btnSignUp:
                signUpUser();
                break;
        }
    }

    public void signUpUser() {
        try {
            ParseUser user = new ParseUser();
            user.setUsername(etUserNameSignUp.getText().toString());
            user.setPassword(etPasswordSignUp.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        showSuccessMessage("Sign Up Successed");
                        startActivity(WelcomeActivity.class);
                    } else {
                        showErrorMessage(e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
    }

    public void logInUser() {
        try {
            ParseUser.logInInBackground(etUserNameLogIn.getText().toString(),
                    etPasswordLogIn.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                showSuccessMessage("Log In Success");
                                startActivity(WelcomeActivity.class);
                            } else {
                                showErrorMessage(e.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
    }
}
