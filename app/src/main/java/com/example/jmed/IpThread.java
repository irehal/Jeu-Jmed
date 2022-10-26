package com.example.jmed;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IpThread extends Thread {

    private ModeDuJeu activity;
    private ServerSocket SS1,SS2;
    public static Socket S1,S2,C1,C2;
    public static int id = 3;
    private boolean clientConnected = false;
    private ClientIp CI1,CI2;
    private ServerIp SI1,SI2;

    public IpThread(ModeDuJeu activity) {
        this.activity = activity;
    }

    public void run() {

        try {
            SS1 = new ServerSocket(0);
            SS2 = new ServerSocket(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Ecoute en port 1 : " + SS1.getLocalPort());
        System.out.println("Ecoute en port 2 : " + SS2.getLocalPort());

        addIp();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://jmed-ef4e9-default-rtdb.firebaseio.com/").getReference().child("Partie");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                PartieDB partieDB = snapshot.getValue(PartieDB.class);

                if( clientConnected && partieDB.getPlayer1().getConnected1() && partieDB.getPlayer1().getConnected2() && partieDB.getPlayer2().getConnected1() && partieDB.getPlayer2().getConnected2() && partieDB.getPlayer3().getConnected1() && partieDB.getPlayer3().getConnected2() ) {
                    S1 = SI1.S;
                    S2 = SI2.S;
                    C1 = CI1.S;
                    C2 = CI2.S;
                    System.out.println("finish");
                    switch (id) {
                        case 0 :
                            reference.child("player1").child("ip").setValue("");
                            reference.child("player1").child("connected1").setValue(false);
                            reference.child("player1").child("connected2").setValue(false);
                            break;
                        case 1 :
                            reference.child("player2").child("ip").setValue("");
                            reference.child("player2").child("connected1").setValue(false);
                            reference.child("player2").child("connected2").setValue(false);
                            break;
                        case 2 :
                            reference.child("player3").child("ip").setValue("");
                            reference.child("player3").child("connected1").setValue(false);
                            reference.child("player3").child("connected2").setValue(false);
                            break;
                        default :
                            break;
                    }
                    Intent intent = new Intent(activity.getBaseContext(), Online.class);
                    activity.startActivity(intent);
                    activity.finish();
                    interrupt();
                }


                else if (!clientConnected) {
                    switch (id) {
                        case 0 :
                            if ( !( partieDB.getPlayer2().getIp().equals("") || partieDB.getPlayer3().getIp().equals("") ) ) {
                                System.out.println("id" + " : " + id);
                                CI1 = new ClientIp(partieDB.getPlayer2().getIp(),partieDB.getPlayer2().getPort1());
                                CI2 = new ClientIp(partieDB.getPlayer3().getIp(),partieDB.getPlayer3().getPort1());
                                CI1.start();
                                CI2.start();
                                clientConnected = true;
                            }
                            break;
                        case 1 :
                            if ( !( partieDB.getPlayer1().getIp().equals("") || partieDB.getPlayer3().getIp().equals("") ) ) {
                                System.out.println("id" + " : " + id);
                                CI1 = new ClientIp(partieDB.getPlayer1().getIp(),partieDB.getPlayer1().getPort1());
                                CI2 = new ClientIp(partieDB.getPlayer3().getIp(),partieDB.getPlayer3().getPort2());
                                CI1.start();
                                CI2.start();
                                clientConnected = true;
                            }
                            break;
                        case 2 :
                            if ( !( partieDB.getPlayer1().getIp().equals("") || partieDB.getPlayer2().getIp().equals("") ) ) {
                                System.out.println("id" + " : " + id);
                                CI1 = new ClientIp(partieDB.getPlayer1().getIp(),partieDB.getPlayer1().getPort2());
                                CI2 = new ClientIp(partieDB.getPlayer2().getIp(),partieDB.getPlayer2().getPort2());
                                CI1.start();
                                CI2.start();
                                clientConnected = true;
                            }
                            break;
                        default :
                            break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addIp() {
        WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(activity.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        int port1 = SS1.getLocalPort();
        int port2 = SS2.getLocalPort();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://jmed-ef4e9-default-rtdb.firebaseio.com/").getReference().child("Partie");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    PartieDB partieDB = task.getResult().getValue(PartieDB.class);
                    PlayerIp player = new PlayerIp(ip,port1,port2);

                    if (partieDB.getPlayer1().getIp().equals("")) {
                        id = 0;
                        reference.child("player1").setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    SI1 = new ServerIp(SS1,0, 1);
                                    SI2 = new ServerIp(SS2,0, 2);
                                    SI1.start();
                                    SI2.start();
                                    Toast.makeText(activity, "Player1 ip has been added successfully", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    Toast.makeText(activity, "Failed to add player1 ip. Try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                    else if (partieDB.getPlayer2().getIp().equals("")) {
                        id = 1;
                        reference.child("player2").setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    SI1 = new ServerIp(SS1,1, 1);
                                    SI2 = new ServerIp(SS2,1, 2);
                                    SI1.start();
                                    SI2.start();
                                    Toast.makeText(activity, "Player2 ip has been added successfully", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    Toast.makeText(activity, "Failed to add player2 ip. Try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                    else if (partieDB.getPlayer3().getIp().equals("")) {
                        id = 2;
                        reference.child("player3").setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    SI1 = new ServerIp(SS1,2, 1);
                                    SI2 = new ServerIp(SS2,2, 2);
                                    SI1.start();
                                    SI2.start();
                                    Toast.makeText(activity, "Player3 ip has been added successfully", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    Toast.makeText(activity, "Failed to add player3 ip. Try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                    else
                        addIp();

                } else {
                    //Toast.makeText(GameStarted.this, "Failed to add player3 ip. Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
