package com.example.whatsapp;

import com.parse.Parse;
import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Ix34mATpMv64S9gNAnXuj6yRQtpdobIyZWvt8gfE")
                // if defined
                .clientKey("LatMpxznZ5XKeP7KiEWxx8tEplZIks2iFCH0ZRUv")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}