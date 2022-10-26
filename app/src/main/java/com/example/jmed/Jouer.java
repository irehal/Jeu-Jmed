package com.example.jmed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Jouer extends AppCompatActivity {

    private Partie partie;
    public Timer timer;
    private ImageView[] joueurs = new ImageView[3];
    private ImageView[] joueursRun = new ImageView[3];
    private ImageView ligneDepart;
    private ImageView ligneDarrive;
    private View background;
    public ImageView light;
    public TextView timerView;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_jouer);
        background = findViewById(R.id.backgroundH);
        joueurs[0] = (ImageView) findViewById(R.id.joueur0H);
        joueursRun[0] = (ImageView) findViewById(R.id.joueurRun0H);
        ligneDepart = (ImageView) findViewById(R.id.ligneDepartH);
        ligneDarrive = (ImageView) findViewById(R.id.ligneDarriveH);
        light = (ImageView) findViewById(R.id.imageViewH);
        timerView = (TextView) findViewById(R.id.timerH);

        level = getIntent().getExtras().getInt("niveau");


        Glide.with(this).load(R.drawable.runner).into(joueursRun[0]);

        background.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                partie.avancer();
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {

            Position joueurPos0 = new Position(joueurs[0].getX(),joueurs[0].getY(),joueurs[0].getWidth(),joueurs[0].getHeight());
            Position ligneDepartPos = new Position(ligneDepart.getX(),ligneDepart.getY(),ligneDepart.getWidth(),ligneDepart.getHeight());
            Position ligneDarrivePos = new Position(ligneDarrive.getX(),ligneDarrive.getY(),ligneDarrive.getWidth(),ligneDarrive.getHeight());

            Joueur[] players = new Joueur[] {new Joueur(joueurPos0),null,null};

            float pas = 30;
            int time = 50;
            switch(level) {
                case 1 :
                    pas = 30;
                    time = 50;
                    break;
                case 2 :
                    pas = 20;
                    time = 40;
                    break;
                case 3 :
                    pas = 10;
                    time = 30;
                    break;
                default :
                    break;
            }

            partie = new Partie(players,0,ligneDepartPos,ligneDarrivePos,this,pas);
            timer = new Timer(time,this,partie);
            timer.start();
        }
    }

    public Partie getPartie() { return partie; }
    public ImageView[] getJoueurs() { return joueurs; }
    public ImageView[] getJoueursRun() { return joueursRun; }
}