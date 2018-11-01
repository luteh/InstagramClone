package com.luteh.instagramclone.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luteh.instagramclone.R;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * Created by Luthfan Maftuh on 29/10/2018.
 * Email luthfanmaftuh@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public final void showProgressBar() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public final void dismissProgressBar() {
        dialog.dismiss();
    }

    public final void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public final void finizh() {
        finish();
    }

    public final void showSuccessMessage(String message) {
        FancyToast.makeText(this, message, 0, FancyToast.SUCCESS, false).show();
    }

    public final void showInfoMessage(String message) {
        FancyToast.makeText(this, message, 1, FancyToast.INFO, false).show();
    }

    public final void showErrorMessage(String message) {
        FancyToast.makeText(this, message, 1, FancyToast.ERROR, false).show();
    }

    //Hide keyboard if root layout tapped
    public final void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
