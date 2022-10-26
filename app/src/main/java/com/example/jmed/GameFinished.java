package com.example.jmed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameFinished extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_finished);
        TextView statutText = (TextView) findViewById(R.id.statut);
        boolean status = getIntent().getExtras().getBoolean("status");
        if (status) {
            statutText.setText("You win !");
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.win);
            mp.start();
        }
        else {
            statutText.setText("You lose !");
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.lose);
            mp.start();
        }
        System.gc();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(GameFinished.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}