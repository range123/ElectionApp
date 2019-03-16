package com.range.electionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VoteridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voterid);
        Button log = findViewById(R.id.login);
        final EditText v1 = findViewById(R.id.vid);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vid = v1.getText().toString();
                if(vid.equals("123") || vid.equals("456")) {
                    startActivity(new Intent(getApplicationContext(), FacedetectActivity.class).putExtra("vid",vid));
                    finish();
                }
                else
                    Toast.makeText(VoteridActivity.this, "Invalid Voter ID", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
