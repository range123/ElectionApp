package com.range.electionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity {

    private ArrayList<Candidate> players;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);



        Candidate a=new Candidate("Ehtesham","BJP",50,"I promise to bring electricity",R.drawable.ic_launcher_background);
        Candidate b=new Candidate("Ehtesham","BJP",50,"I promise to bring electricity",R.drawable.ic_launcher_background);
        Candidate c=new Candidate("Ehtesham","BJP",50,"I promise to bring electricity",R.drawable.ic_launcher_background);
        Candidate d=new Candidate("Ehtesham","BJP",50,"I promise to bring electricity",R.drawable.ic_launcher_background);
        players.add(a);
        players.add(b);
        players.add(c);
        players.add(d);

        PlayerAdapter p=new PlayerAdapter(getApplicationContext(),players);


    }
}