package com.luteh.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.luteh.instagramclone.base.BaseActivity;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvWelcome;
    private Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tvWelcome = findViewById(R.id.tvWelcome);

        tvWelcome.setText("Welcome " + ParseUser.getCurrentUser().getUsername());

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogOut:
                logOut();
                break;
        }
    }

    public void logOut() {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    showSuccessMessage("Log Out Success");
                    finizh();
                } else {
                    showErrorMessage(e.getMessage());
                }
            }
        });
    }
}
