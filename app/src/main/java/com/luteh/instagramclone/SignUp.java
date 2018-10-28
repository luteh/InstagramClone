package com.luteh.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private EditText etName, etPunchSpeed, etPunchPower, etKickSpeed, etKickPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.etName);
        etPunchSpeed = findViewById(R.id.etPunchSpeed);
        etPunchPower = findViewById(R.id.etPunchPower);
        etKickSpeed = findViewById(R.id.etKickSpeed);
        etKickPower = findViewById(R.id.etKickPower);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(SignUp.this);
    }

    /*public void helloWorldTapped(View view) {
        ParseObject boxer = new ParseObject("Boxer");
        boxer.put("punch_speed", 200);
        boxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUp.this, "Boxer object is saved successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                saveKickBoxerDatatoServer();
                break;
        }
    }

    public void saveKickBoxerDatatoServer() {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", etName.getText().toString());
            kickBoxer.put("punch_speed", etPunchSpeed.getText().toString());
            kickBoxer.put("punch_power", etPunchPower.getText().toString());
            kickBoxer.put("kick_speed", etKickSpeed.getText().toString());
            kickBoxer.put("kick_power", etKickPower.getText().toString());
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " data has been saved", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                }
            });
        }catch (Exception e){
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }
}
