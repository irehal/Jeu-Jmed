package com.example.jmed;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;

public class Timer extends Thread {
    private int time;
    private int timeForLight = 3;
    private Jouer activity;
    private Partie partie;

    public Timer(int time, Jouer activity, Partie partie) {
        this.activity = activity;
        this.time = time;
        this.partie = partie;
    }
    public int getTime() { return time; }

    public void run() {
        int minGreen = 3, maxGreen = 6, minRed = 2, maxRed = 5;
        while (!interrupted()) {
            if (--time == 0) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.timerView.setText("0");
                        //partie.getJoueurs()[0].setStatus(false);
                        int i;
                        Intent intent = new Intent(activity.getBaseContext(), GameFinished.class);
                        intent.putExtra("status", false);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
                interrupt();
            } else {
                try {
                    if(partie.getJoueurId()==0) {
                        if(--timeForLight==0) {
                            partie.switchLight();

                            if (partie.getLight())
                                timeForLight = (int) Math.floor(Math.random() * (maxGreen-minGreen+1) + minGreen ) ;
                            else
                                timeForLight = (int) Math.floor(Math.random() * (maxRed-minRed+1) + minRed ) ;
                        } else if ( timeForLight==1 && partie.getLight() ) {
                            MediaPlayer mp = MediaPlayer.create(activity.getApplicationContext(), R.raw.alert);
                            mp.start();
                        }
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.timerView.setText(String.valueOf(time));
                        }
                    });
                    sleep(1000);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }

        }
    }
}
