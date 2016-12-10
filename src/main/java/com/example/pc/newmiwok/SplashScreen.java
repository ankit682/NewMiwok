package com.example.pc.newmiwok;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by PC on 10-12-2016.
 */

public class SplashScreen extends Activity {


    //Spalsh Screen Time out
    private static int SPLASH_SCREEN_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, SPLASH_SCREEN_OUT);
    }
}
