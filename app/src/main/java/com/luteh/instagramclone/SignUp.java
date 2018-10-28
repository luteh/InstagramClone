package com.luteh.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave, btnGetAllData;
    private EditText etName, etPunchSpeed, etPunchPower, etKickSpeed, etKickPower;
    private TextView tvKickBoxerData;
    private String allKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.etName);
        etPunchSpeed = findViewById(R.id.etPunchSpeed);
        etPunchPower = findViewById(R.id.etPunchPower);
        etKickSpeed = findViewById(R.id.etKickSpeed);
        etKickPower = findViewById(R.id.etKickPower);

        tvKickBoxerData = findViewById(R.id.tvKickBoxerData);
        tvKickBoxerData.setOnClickListener(SignUp.this);

        btnSave = findViewById(R.id.btnSave);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnSave.setOnClickListener(SignUp.this);
        btnGetAllData.setOnClickListener(SignUp.this);
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
            case R.id.btnGetAllData:
                getAllDataFromServer();
                break;
            case R.id.tvKickBoxerData:
                getOneDataFromServer();
                break;
        }
    }

    public void getOneDataFromServer() {
        try {
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
            parseQuery.getInBackground("Id2ogNoFPZ", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object != null && e == null) {
                        tvKickBoxerData.setText("Name : " + object.getString("name") +
                                " - Punch Speed : " + object.getString("punch_speed"));
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }

    public void getAllDataFromServer() {
        allKickBoxers = "";
        ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
        queryAll.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject kickBoxer : objects) {
                            allKickBoxers += kickBoxer.get("name") + "\n";
                        }
                        FancyToast.makeText(SignUp.this, allKickBoxers, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                }
            }
        });
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
        } catch (Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }
}
