package com.luteh.instagramclone.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * Created by Luthfan Maftuh on 29/10/2018.
 * Email luthfanmaftuh@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public final void startActivity(Class clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public final void finizh(){
        finish();
    }

    public final void showSuccessMessage(String message){
        FancyToast.makeText(this,message,0,FancyToast.SUCCESS,false).show();
    }

    public final void showErrorMessage(String message){
        FancyToast.makeText(this,message,1,FancyToast.ERROR,false).show();
    }
}
