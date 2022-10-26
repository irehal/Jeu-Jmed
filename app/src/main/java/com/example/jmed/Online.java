package com.example.jmed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.Socket;

public class Online extends AppCompatActivity {

    private Partie2 partie;
    public Timer2 timer;
    private ImageView[] joueurs = new ImageView[3];
    private ImageView[] joueursRun = new ImageView[3];
    private ImageView ligneDepart;
    private ImageView ligneDarrive;
    private View background;
    public ImageView light;
    public TextView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_online);

        background = findViewById(R.id.background);
        joueurs[0] = (ImageView) findViewById(R.id.joueur0);
        joueursRun[0] = (ImageView) findViewById(R.id.joueurRun0);
        joueurs[1] = (ImageView) findViewById(R.id.joueur1);
        joueursRun[1] = (ImageView) findViewById(R.id.joueurRun1);
        joueurs[2] = (ImageView) findViewById(R.id.joueur2);
        joueursRun[2] = (ImageView) findViewById(R.id.joueurRun2);
        ligneDepart = (ImageView) findViewById(R.id.ligneDepart);
        ligneDarrive = (ImageView) findViewById(R.id.ligneDarrive);
        light = (ImageView) findViewById(R.id.imageView);
        timerView = (TextView) findViewById(R.id.timer);

        int width  = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        int i;
        int id = IpThread.id;

        for (i=0;i<3;i++) {
            if (id==i) {
                joueurs[i].setImageResource(R.drawable.joueur);
                joueursRun[i].setImageResource(R.drawable.runner);
            }
            joueurs[i].getLayoutParams().width = Math.round(0.3F * width);
            joueursRun[i].getLayoutParams().width = Math.round(0.3F * width);
            joueurs[i].getLayoutParams().height = Math.round(0.2F * height);
            joueursRun[i].getLayoutParams().height = Math.round(0.2F * height);
        }

        for (i=0;i<3;i++) {
            if (id==i)
                Glide.with(this).load(R.drawable.runner).into(joueursRun[i]);
            else
                Glide.with(this).load(R.drawable.runnero).into(joueursRun[i]);

        }

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
            Position joueurPos1 = new Position(joueurs[1].getX(),joueurs[1].getY(),joueurs[1].getWidth(),joueurs[1].getHeight());
            Position joueurPos2 = new Position(joueurs[2].getX(),joueurs[2].getY(),joueurs[2].getWidth(),joueurs[2].getHeight());
            Position ligneDepartPos = new Position(ligneDepart.getX(),ligneDepart.getY(),ligneDepart.getWidth(),ligneDepart.getHeight());
            Position ligneDarrivePos = new Position(ligneDarrive.getX(),ligneDarrive.getY(),ligneDarrive.getWidth(),ligneDarrive.getHeight());

            float pas = getWindowManager().getDefaultDisplay().getHeight() * 0.02F;
            Joueur[] players = new Joueur[] {new Joueur(joueurPos0),new Joueur(joueurPos1),new Joueur(joueurPos2)};

            int id = IpThread.id;
            Socket S1 = IpThread.S1;
            Socket S2 = IpThread.S2;
            Socket C1 = IpThread.C1;
            Socket C2 = IpThread.C2;

            System.out.println(id);
            System.out.println(S1.getPort());
            System.out.println(S2.getPort());
            System.out.println(C1.getPort());
            System.out.println(C2.getPort());
            //finish();

            Server[] S = new Server[2];
            Client[] C = new Client[2];
            C[0] = new Client(this,C1);
            C[1] = new Client(this,C2);

            switch(id) {
                case 0:
                    S[0] = new Server(this,1,S1);
                    S[1] = new Server(this,2,S2);
                    //C[0] = new Client(this,player1.getIp(),player1.getPort1());
                    //C[1] = new Client(this,player2.getIp(),player2.getPort1());
                    break;
                case 1:
                    S[0] = new Server(this,0,S1);
                    S[1] = new Server(this,2,S2);
                    //C[0] = new Client(this,player1.getIp(),player1.getPort1());
                    //C[1] = new Client(this,player2.getIp(),player2.getPort2());
                    break;
                case 2:
                    S[0] = new Server(this,0,S1);
                    S[1] = new Server(this,1,S2);
                    //C[0] = new Client(this,player1.getIp(),player1.getPort2());
                    //C[1] = new Client(this,player2.getIp(),player2.getPort2());
                    break;
                default:
                    break;

            }
            for(int i=0;i<2;i++) {
                S[i].start();
                C[i].start();
            }


            partie = new Partie2(players,id,ligneDepartPos,ligneDarrivePos,this,pas,S,C);
            timer = new Timer2(50,this,partie);
            timer.start();
        }
    }

    public Partie2 getPartie() { return partie; }
    public ImageView[] getJoueurs() { return joueurs; }
    public ImageView[] getJoueursRun() { return joueursRun; }
}