package com.luteh.instagramclone;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Luthfan Maftuh on 27/10/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("1jCe5Wnclrwi10m0qI3ls6VEXsNnhYPHTqERWYMd")
                // if defined
                .clientKey("fRKdFgYlIag3gjPUly4LtxPdfsipoXjevI5IAppJ")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
