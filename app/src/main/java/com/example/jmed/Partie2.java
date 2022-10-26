package com.example.jmed;

import android.content.Intent;
import android.view.View;

import java.io.IOException;

public class Partie2 {
    private Joueur[] joueurs;
    private Position ligneDepart;
    private Position ligneDarrive;
    private boolean light;
    private float pas;
    private Online activity;
    private int joueurId;
    private boolean[] isPlaying = new boolean[] {true,true,true} ;
    public Client[] client ;
    public Server[] server;

    public Partie2(Joueur[] joueurs, int id, Position ligneDepart, Position ligneDarrive, Online activity, float pas,Server[] server, Client[] client) {
        this.ligneDepart = ligneDepart;
        this.ligneDarrive = ligneDarrive;
        this.joueurs = joueurs;
        light = true;
        this.activity = activity;
        joueurId = id;
        this.pas = pas;
        this.server = server;
        this.client = client;
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
                    for(int i=0;i<3;i++) {
                        System.out.println(i + " : " + isPlaying[i]);
                        if(isPlaying[i]) {
                            activity.getJoueurs()[i].setVisibility(View.INVISIBLE);
                            activity.getJoueursRun()[i].setVisibility(View.VISIBLE);
                        }
                    }
                }
                else {
                    activity.light.setImageResource(R.drawable.red);
                    for(int i=0;i<3;i++) {
                        activity.getJoueurs()[i].setVisibility(View.VISIBLE);
                        //if(isPlaying[i])
                        activity.getJoueursRun()[i].setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

    }

    //methode called remotely
    public void avancer(int id,boolean isArrived) {
        if (id!=joueurId) {
            if (isArrived) {
                joueurs[id].getPosition().setY(ligneDarrive.getY() - joueurs[0].getPosition().getHeight());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.getJoueurs()[id].setY(joueurs[id].getPosition().getY());
                        activity.getJoueursRun()[id].setY(joueurs[id].getPosition().getY());
                    }
                });

                isPlaying[id] = false;
                int i = 0;
                for (i=0;i<3;i++) {
                    if(isPlaying[i]) break;
                }
                if (i==3) {
                    activity.timer.interrupt();
                    for (i=0;i<2;i++) {
                        try{
                            server[i].S.close();
                        }catch(IOException e) {

                        }
                        server[i].interrupt();
                        client[i].interrupt();
                    }
                    Intent intent = new Intent(activity.getBaseContext(), GameFinished.class);
                    intent.putExtra("status", joueurs[joueurId].getStatus());
                    activity.startActivity(intent);
                    activity.finish();
                }

            } else {
                joueurs[id].getPosition().setY(joueurs[id].getPosition().getY() + pas);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.getJoueurs()[id].setY(joueurs[id].getPosition().getY());
                        activity.getJoueursRun()[id].setY(joueurs[id].getPosition().getY());
                    }
                });
            }
        }
    }

    //methode called remotely
    public void eliminer(int id) {
        if (joueurId!=id) {
            isPlaying[id] = false;
            int i=0;
            for (i=0;i<3;i++) {
                if(isPlaying[i]) break;
            }
            if (i==3) {
                activity.timer.interrupt();
                for (i=0;i<2;i++) {
                    try{
                        server[i].S.close();
                    }catch(IOException e) {

                    }
                    server[i].interrupt();
                    client[i].interrupt();
                }
                Intent intent = new Intent(activity.getBaseContext(), GameFinished.class);
                intent.putExtra("status", joueurs[joueurId].getStatus());
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }

    public void avancer() {
        int i;
        if(isPlaying[joueurId]) {
            if(light) {
                if(joueurs[joueurId].getPosition().getY() + pas >= ligneDarrive.getY() - joueurs[0].getPosition().getHeight()) {
                    joueurs[joueurId].getPosition().setY(ligneDarrive.getY() - joueurs[0].getPosition().getHeight());
                    activity.getJoueurs()[joueurId].setY(joueurs[joueurId].getPosition().getY());
                    activity.getJoueursRun()[joueurId].setY(joueurs[joueurId].getPosition().getY());

                    // avancer(joueurId,true) methode distant ;
                    for(i=0;i<2;i++) {
                        while(client[i].n!=0) {}
                        client[i].n = 4;
                    }

                    isPlaying[joueurId] = false;
                    joueurs[joueurId].setStatus(true);
                    for (i=0;i<3;i++) {
                        if(isPlaying[i]) break;
                    }
                    if (i==3) {
                        activity.timer.interrupt();
                        for (i=0;i<2;i++) {
                            try{
                                server[i].S.close();
                            }catch(IOException e) {

                            }
                            server[i].interrupt();
                            client[i].interrupt();
                        }
                        Intent intent = new Intent(activity.getBaseContext(), GameFinished.class);
                        intent.putExtra("status", joueurs[joueurId].getStatus());
                        activity.startActivity(intent);
                        activity.finish();
                    }

                } else {
                    joueurs[joueurId].getPosition().setY(joueurs[joueurId].getPosition().getY() + pas);
                    activity.getJoueurs()[joueurId].setY(joueurs[joueurId].getPosition().getY());
                    activity.getJoueursRun()[joueurId].setY(joueurs[joueurId].getPosition().getY());

                    // avancer(joueurId,false) methode distant ;
                    for(i=0;i<2;i++) {
                        while(client[i].n!=0) {}
                        client[i].n = 1;
                    }

                }
            } else {

                //eliminer(joueurId) appel distant;
                for(i=0;i<2;i++) {
                    while(client[i].n!=0) {}
                    client[i].n = 2;
                }

                //
                isPlaying[joueurId] = false;
                joueurs[joueurId].setStatus(false);
                for (i=0;i<3;i++) {
                    if(isPlaying[i]) break;
                }
                if (i==3) {
                    activity.timer.interrupt();
                    for (i=0;i<2;i++) {
                        try{
                            server[i].S.close();
                        }catch(IOException e) {

                        }
                        server[i].interrupt();
                        client[i].interrupt();
                    }
                    Intent intent = new Intent(activity.getBaseContext(), GameFinished.class);
                    intent.putExtra("status", joueurs[joueurId].getStatus());
                    activity.startActivity(intent);
                    activity.finish();
                }

            }

        }
    }

}
