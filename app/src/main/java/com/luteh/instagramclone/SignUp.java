package com.luteh.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.luteh.instagramclone.common.Common;
import com.luteh.instagramclone.common.base.BaseActivity;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends BaseActivity implements View.OnClickListener {

    private EditText etEmailSignUp, etUserNameSignUp, etPasswordSignUp;
    private Button btnLogIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmailSignUp = findViewById(R.id.etEmailSignUp);
        etUserNameSignUp = findViewById(R.id.etUserNameSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            startActivity(HomeActivity.class);
        }

        //Click Sign Up button when Enter keyboard pressed
        /*etPasswordSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp);
                }
                return false;
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn:
                startActivity(LogInActivity.class);
                break;
            case R.id.btnSignUp:
                userSignUp();
                break;
        }
    }

    public void userSignUp() {
        if (etEmailSignUp.getText().toString().equals("") ||
                etUserNameSignUp.getText().toString().equals("") ||
                etPasswordSignUp.getText().toString().equals("")) {
            Common.showInfoMessage(context, "Email, Username, Password is required");
        } else {
            try {
                Common.showProgressBar(context);
                ParseUser user = new ParseUser();
                user.setEmail(etEmailSignUp.getText().toString());
                user.setUsername(etUserNameSignUp.getText().toString());
                user.setPassword(etPasswordSignUp.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Common.showSuccessMessage(context, "Sign Up Success");
                            etEmailSignUp.getText().clear();
                            etUserNameSignUp.getText().clear();
                            etPasswordSignUp.getText().clear();

                        } else {
                            Common.showErrorMessage(context, e.getMessage());
                        }
                        Common.dismissProgressBar();
                    }
                });

            } catch (Exception e) {
                Common.showErrorMessage(context, e.getMessage());
                Common.dismissProgressBar();
            }
        }

    }
}
