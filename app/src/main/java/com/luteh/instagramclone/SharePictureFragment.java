package com.luteh.instagramclone;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.luteh.instagramclone.common.Common;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureFragment extends Fragment implements View.OnClickListener {

    private ImageView btnSelectImage;
    private Button btnShareImage;
    private EditText etImageDescription;
    private Bitmap receivedImageBitmap;
    private final String TAG = "Share Picture Fragment";

    public SharePictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_picture, container, false);

        etImageDescription = view.findViewById(R.id.etImageDescription);

        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        btnShareImage = view.findViewById(R.id.btnShareImage);
        btnSelectImage.setOnClickListener(this);
        btnShareImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSelectImage:
                getReadExternalStoragePermission();
                break;
            case R.id.btnShareImage:
                saveUserUpdateIntoDatabase();
                break;
        }

    }

    private void saveUserUpdateIntoDatabase() {
        if (receivedImageBitmap != null) {
            if (!etImageDescription.getText().toString().equals("")) {
                try {
                    Common.showProgressBar(getContext());

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile("img.png", bytes);
                    ParseObject parseObject = new ParseObject("Photo");
                    parseObject.put("picture", parseFile);
                    parseObject.put("image_des", etImageDescription.getText().toString());
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Common.showSuccessMessage(getContext(), "Done!");

                            } else {
                                Common.showErrorMessage(getContext(), e.getMessage());
                                Log.e(TAG, e.getMessage());
                            }
                            Common.dismissProgressBar();
                        }
                    });
                } catch (Exception e) {
                    Common.dismissProgressBar();
                    e.printStackTrace();
                }
            } else {
                Common.showInfoMessage(getContext(), "Fill out a description");
            }
        }else{
            Common.showInfoMessage(getContext(),"Select an image first");
        }

    }

    public void getReadExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},
                    1000);
        } else {
            getChosenImage();
        }
    }

    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {

                //Do something with your captured image.
                try {
                    btnSelectImage.setImageBitmap(convertImageToBitmap(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bitmap convertImageToBitmap(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver()
                .query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        receivedImageBitmap = BitmapFactory.decodeFile(picturePath);

        return receivedImageBitmap;
    }


}
