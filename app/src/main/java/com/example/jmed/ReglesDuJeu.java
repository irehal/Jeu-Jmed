package com.example.jmed;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ReglesDuJeu extends AppCompatActivity {
    private Button retourne;
    private ImageButton back;
    private ImageButton next;
    private TextView reglesText;
    private ImageView reglesImage;
    int scroll = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_regles_du_jeu);

        retourne = findViewById(R.id.retourne4);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        back.setImageResource(R.drawable.empty);
        reglesText = findViewById(R.id.reglestext);
        reglesImage = findViewById(R.id.imageView4);

        retourne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWelcomeAcitivy();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scroll<=0)back.setImageResource(R.drawable.empty);
                else {
                    if(scroll>=8)next.setImageResource(R.drawable.next);
                    scroll--;
                }
                if(scroll==0)back.setImageResource(R.drawable.empty);
                setReglesText();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scroll>=8) next.setImageResource(R.drawable.empty);
                else {
                    if(scroll<=0)back.setImageResource(R.drawable.back);
                    scroll++;
                }
                if(scroll==8)next.setImageResource(R.drawable.empty);
                setReglesText();
            }
        });
    }
    public void openWelcomeAcitivy(){
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
    public void setReglesText(){
        switch (scroll){
            case 0:
                reglesText.setText("Offline version");
                reglesImage.setImageResource(R.drawable.mode);
                break;
            case 1:
                reglesText.setText("The player plays alone against the machine with levels (easy, medium, hard).");
                reglesImage.setImageResource(R.drawable.rule1);
                break;
            case 2:
                reglesText.setText("The game begins with a green signal from starting point A to finishing point B.");
                reglesImage.setImageResource(R.drawable.capture);
                break;
            case 3:
                reglesText.setText("As long as the signal is green, the player advances by making clicks on the screen.");
                reglesImage.setImageResource(R.drawable.rule2_3);
                break;
            case 4:
                reglesText.setText("He must stop his movement when the signal turns red otherwise he will be eliminated.");
                reglesImage.setImageResource(R.drawable.rule3);
                break;
            case 5:
                reglesText.setText("The movement based on the clicks makes it possible to advance its body towards the destination.");
                reglesImage.setImageResource(R.drawable.rule5);
                break;
            case 6:
                reglesText.setText("He continues to play until he reaches his final destination within a given time interval following these rules, otherwise he is declared the loser.");
                reglesImage.setImageResource(R.drawable.rule5);
                break;
            case 7:
                reglesText.setText("Online version");
                reglesImage.setImageResource(R.drawable.mode);
                break;
            case 8:
                reglesText.setText("the player has a red sign. Same rules as offline version. Who gets to the end wins");
                reglesImage.setImageResource(R.drawable.rule6);
                break;
            default:break;
        }
    }
}