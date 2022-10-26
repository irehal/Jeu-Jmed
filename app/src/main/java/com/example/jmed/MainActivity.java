package com.example.jmed;

import static com.example.jmed.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    Timer timer;
    ImageView lightImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(layout.activity_main);

        lightImage = findViewById(id.imageView);

        new SplashScreen().start();
    }

    //SplashScreen inner class
    class SplashScreen extends Thread{

        @Override
        public void run() {


            lightImage.setImageResource(drawable.redlightbig2);

            //yello color
            try {
                sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lightImage.setImageResource(drawable.yellowlightbig2);
                }
            });


            //green color
            try {
                sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lightImage.setImageResource(drawable.greenlightbig2);
                }
            });


            //to next activity
            try {
                sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(MainActivity.this,Welcome.class);
            startActivity(intent);
            finish();
        }
    }
}