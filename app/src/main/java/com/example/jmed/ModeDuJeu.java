package com.example.jmed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class ModeDuJeu extends AppCompatActivity {
    private Button retourne;
    private Button jouerHL,jouerL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mode_du_jeu);

        retourne = (Button) findViewById(R.id.retourne);
        retourne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWelcomeActivity();
            }
        });
        jouerHL = (Button) findViewById(R.id.facile);
        jouerHL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
        jouerL = (Button) findViewById(R.id.jouerL);
        jouerL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();
            }
        });

    }
    public void openWelcomeActivity(){
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
    public void openActivity2(){
        Intent intent = new Intent(this, Niveau.class);
        startActivity(intent);
    }
    public void openActivity3(){
        IpThread ipThread = new IpThread(this);
        ipThread.start();
        Toast.makeText(ModeDuJeu.this, "Please wait !", Toast.LENGTH_LONG).show();
        jouerL.setEnabled(false);
        //Intent intent = new Intent(this, OnLine.class);
        //startActivity(intent);
    }
}