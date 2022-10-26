package com.example.jmed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Niveau extends AppCompatActivity {
    private Button retourne2,facile,moyen,difficile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_niveau);

        retourne2 = (Button) findViewById(R.id.retourne2);
        facile = (Button) findViewById(R.id.facile);
        moyen = findViewById(R.id.moyen);
        difficile = findViewById(R.id.difficile);

        facile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Niveau.this, Jouer.class);
                intent.putExtra("niveau",1);
                startActivity(intent);
            }
        });
        moyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Niveau.this, Jouer.class);
                intent.putExtra("niveau",2);
                startActivity(intent);
            }
        });
        difficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Niveau.this, Jouer.class);
                intent.putExtra("niveau",3);
                startActivity(intent);
            }
        });
        retourne2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Niveau.this, ModeDuJeu.class);
                startActivity(intent);
            }
        });
    }
}
