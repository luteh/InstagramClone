package com.luteh.instagramclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luteh.instagramclone.common.Common;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UsersPostActivity extends AppCompatActivity {

    private String receivedUserName;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);

        Intent receivedIntentObject = getIntent();
        receivedUserName = receivedIntentObject.getStringExtra(Common.KEY_USERNAME);
//        Common.showSuccessMessage(this, receivedUserName);

        setTitle(receivedUserName + "'s Post");

        linearLayout = findViewById(R.id.linearLayout);

        getUserProfile();
    }

    private void getUserProfile() {
        Common.showProgressBar(this);

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo(Common.KEY_USERNAME, receivedUserName);
        parseQuery.orderByDescending(Common.KEY_CREATEDAT);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseObject post : objects) {
                        final TextView textView = new TextView(UsersPostActivity.this);
                        textView.setText(post.get(Common.KEY_IMAGE_DES) + "");

                        ParseFile postPicture = (ParseFile) post.get(Common.KEY_PICTURE);
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null && e == null) {

                                    //Convert byte file to bitmap
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                    //Create layout programmatically for Image View UI Component
                                    LinearLayout.LayoutParams imageViewParams =
                                            new LinearLayout.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                            );
                                    imageViewParams.setMargins(5, 5, 5, 5);

                                    //Set up Image View UI Component programmatically
                                    ImageView imageView = new ImageView(UsersPostActivity.this);
                                    imageView.setLayoutParams(imageViewParams);
                                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    imageView.setImageBitmap(bitmap);

                                    //Create layout programmatically for Text View UI Component
                                    LinearLayout.LayoutParams textViewParams =
                                            new LinearLayout.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                            );
                                    textViewParams.setMargins(5, 5, 5, 5);

                                    textView.setLayoutParams(textViewParams);
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setBackgroundColor(Color.BLUE);
                                    textView.setTextColor(Color.WHITE);
                                    textView.setTextSize(30f);

                                    linearLayout.addView(imageView);
                                    linearLayout.addView(textView);
                                }
                            }
                        });

                    }
                } else {
                    Common.showErrorMessage(UsersPostActivity.this, "Doesn't have any post");
                    finish();
                }
                Common.dismissProgressBar();
            }
        });
    }
}
