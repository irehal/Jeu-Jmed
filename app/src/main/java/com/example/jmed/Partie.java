package com.example.jmed;

import android.content.Intent;
import android.view.View;

public class Partie {
    private Joueur[] joueurs;
    private Position ligneDepart;
    private Position ligneDarrive;
    private boolean light;
    private float pas;
    private Jouer activity;
    private int joueurId;
    private boolean[] isPlaying = new boolean[] {true,false,false} ;

    public Partie(Joueur[] joueurs, int id, Position ligneDepart, Position ligneDarrive, Jouer activity, float pas) {
        this.ligneDepart = ligneDepart;
        this.ligneDarrive = ligneDarrive;
        this.joueurs = joueurs;
        light = true;
        this.activity = activity;
        joueurId = id;
        this.pas = pas;
    }

    public Joueur[] getJoueurs() { return joueurs; }
    public boolean getLight() { return light; }
    public void setLight(boolean light) { this.light = light; }
    public int getJoueurId() { return joueurId; }
    public boolean[] getIsPlaying() { return isPlaying; }

    public void switchLight() {
        light = !light;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(light) {
                    activity.light.setImageResource(R.drawable.green);
                    activity.getJoueurs()[0].setVisibility(View.INVISIBLE);
                    activity.getJoueursRun()[0].setVisibility(View.VISIBLE);
                }
                else {
                    activity.light.setImageResource(R.drawable.red);
                    activity.getJoueurs()[0].setVisibility(View.VISIBLE);
                    activity.getJoueursRun()[0].setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public void avancer() {
        int i;
        if(isPlaying[joueurId]) {
            if(light) {
                if(joueurs[joueurId].getPosition().getY() + pas >= ligneDarrive.getY() - joueurs[0].getPosition().getHeight()) {
                    joueurs[joueurId].getPosition().setY(ligneDarrive.getY() - joueurs[0].getPosition().getHeight());
                    activity.getJoueurs()[joueurId].setY(joueurs[joueurId].getPosition().getY());
                    activity.getJoueursRun()[joueurId].setY(joueurs[joueurId].getPosition().getY());


                    isPlaying[joueurId] = false;
                    joueurs[joueurId].setStatus(true);
                    for (i=0;i<3;i++) {
                        if(isPlaying[i]) break;
                    }
                    if (i==3) {
                        activity.timer.interrupt();
                        Intent intent = new Intent(activity.getBaseContext(), GameFinished.class);
                        intent.putExtra("status", joueurs[joueurId].getStatus());
                        activity.startActivity(intent);
                        activity.finish();
                    }

                } else {
                    joueurs[joueurId].getPosition().setY(joueurs[joueurId].getPosition().getY() + pas);
                    activity.getJoueurs()[joueurId].setY(joueurs[joueurId].getPosition().getY());
                    activity.getJoueursRun()[joueurId].setY(joueurs[joueurId].getPosition().getY());

                }
            } else {

                isPlaying[joueurId] = false;
                joueurs[joueurId].setStatus(false);
                for (i=0;i<3;i++) {
                    if(isPlaying[i]) break;
                }
                if (i==3) {
                    activity.timer.interrupt();
                    Intent intent = new Intent(activity.getBaseContext(), GameFinished.class);
                    intent.putExtra("status", joueurs[joueurId].getStatus());
                    activity.startActivity(intent);
                    activity.finish();
                }

            }

        }
    }

}
