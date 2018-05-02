package com.avrilhb.quinielaandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.airbnb.lottie.LottieAnimationView;
import com.avrilhb.quinielaandroid.login.view.LoginActivity;

/**
 * Created by avrilhb on 23/04/18.
 */

public class SplashActivity extends Activity {

    private static boolean splashLoaded = false;
    private LottieAnimationView animationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!splashLoaded) {
            setContentView(R.layout.activity_splash);
            int secondsDelayed = 5;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, secondsDelayed * 1500);

            animationView = findViewById(R.id.lottieAnimationView);
            animationView.loop(true);
            animationView.playAnimation();

            splashLoaded = true;
        }
        else {
            Intent goToMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }
    }
}
