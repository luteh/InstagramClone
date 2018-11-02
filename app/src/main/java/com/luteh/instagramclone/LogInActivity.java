package com.luteh.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.luteh.instagramclone.common.Common;
import com.luteh.instagramclone.common.base.BaseActivity;
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
        Common.showProgressBar(context);
        ParseUser.logInInBackground(etUserNameLogIn.getText().toString(),
                etPasswordLogIn.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            Common.showSuccessMessage(context,"Log In Success");
                            startActivity(HomeActivity.class);
                            finizh();
                        } else {
                            Common.showErrorMessage(context,e.getMessage());
                        }
                        Common.dismissProgressBar();
                    }
                });
    }
}
