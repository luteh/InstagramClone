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

public class LogInActivity extends BaseActivity implements View.OnClickListener{

    private EditText etUserNameLogIn, etPasswordLogIn;
    private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etUserNameLogIn = findViewById(R.id.etUserNameLogIn);
        etPasswordLogIn = findViewById(R.id.etPasswordLogIn);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn:
                userLogIn();
                break;
            case R.id.btnSignUp:
                finizh();
                break;
        }
    }

    public void userLogIn() {
        showProgressBar();
        ParseUser.logInInBackground(etUserNameLogIn.getText().toString(),
                etPasswordLogIn.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            showSuccessMessage("Log In Success");
                            startActivity(HomeActivity.class);
                            finizh();
                        } else {
                            showErrorMessage(e.getMessage());
                        }
                        dismissProgressBar();
                    }
                });
    }
}
