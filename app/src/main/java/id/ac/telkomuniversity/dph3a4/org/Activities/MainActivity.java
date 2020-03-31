package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import id.ac.telkomuniversity.dph3a4.org.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final boolean firstStart = prefs.getBoolean("firstStart", true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firstStart) {
                    startActivity(new Intent(getApplicationContext(), OnboardingActivity.class));
                    finish();
                } else if (!firstStart) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
