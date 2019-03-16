package com.range.electionapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity {

    public static String vid;
    private ArrayList<Candidate> players;
    public static ArrayList<Vote> votes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        vid = getIntent().getStringExtra("vid");
        players = new ArrayList<>();
        votes = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Poll").child("Votes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Vote v = dataSnapshot.getValue(Vote.class);
                votes.add(v);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "Vote activity", Toast.LENGTH_SHORT).show();
        RecyclerView rv = findViewById(R.id.recycler);


        Candidate a=new Candidate("Ehtesham","BJP",50,"I promise to bring electricity",R.drawable.ic_launcher_background);
        Candidate b=new Candidate("Ehtesham","BJP",50,"I promise to bring electricity",R.drawable.ic_launcher_background);
        Candidate c=new Candidate("Ehtesham","BJP",50,"I promise to bring electricity",R.drawable.ic_launcher_background);
        Candidate d=new Candidate("Ehtesham","BJP",50,"I promise to bring electricity",R.drawable.ic_launcher_background);
        players.add(a);
        players.add(b);
        players.add(c);
        players.add(d);


        PlayerAdapter p=new PlayerAdapter(VoteActivity.this,players,vid,votes);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(p);
        p.notifyDataSetChanged();


    }
}
