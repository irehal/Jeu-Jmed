package com.example.jmed;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerIp extends Thread {

    private ServerSocket SS;
    public Socket S;
    private int playerId,portId;

    public ServerIp(ServerSocket SS, int playerId, int portId) {
        this.SS = SS;
        this.playerId = playerId;
        this.portId = portId;
    }

    public void run() {
        try {
            S = SS.accept();
            System.out.println("connected successfully with " + S.getInetAddress().getHostName());
            DatabaseReference reference = FirebaseDatabase.getInstance("https://jmed-ef4e9-default-rtdb.firebaseio.com/").getReference().child("Partie");
            reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        PartieDB partieDB = task.getResult().getValue(PartieDB.class);
                        switch (playerId) {
                            case 0 :
                                if (portId==1)
                                    reference.child("player1").child("connected1").setValue(true);
                                else if(portId==2)
                                    reference.child("player1").child("connected2").setValue(true);
                                break;
                            case 1 :
                                if (portId==1)
                                    reference.child("player2").child("connected1").setValue(true);
                                else if(portId==2)
                                    reference.child("player2").child("connected2").setValue(true);
                                break;
                            case 2 :
                                if (portId==1)
                                    reference.child("player3").child("connected1").setValue(true);
                                else if(portId==2)
                                    reference.child("player3").child("connected2").setValue(true);
                                break;
                            default :
                                break;

                        }
                    } else {
                        //Toast.makeText(GameStarted.this, "Failed to add player3 ip. Try again", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
