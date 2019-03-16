package com.range.electionapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private Context context;
    public ArrayList<Candidate> PlayerList;
    ArrayList<Vote> votes;
    String vid;


    public PlayerAdapter(Context context, ArrayList<Candidate> playerList,String v,ArrayList<Vote> votes) {
        this.context = context;
        PlayerList = playerList;
        this.votes = votes;
        this.vid = v;
    }

    boolean notin()
    {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("hasvoted",false);
    }
    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.list_layout,null);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Candidate cnd=PlayerList.get(position);
        holder.cname.setText("Candidate Name: "+cnd.getCname());
        holder.pname.setText("Party Name: "+cnd.getPname());
        holder.age.setText("Age: "+String.valueOf(cnd.getAge()));

        Glide.with(context).load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/electionapp-db3b7.appspot.com/o/test.png?alt=media&token=8ee596f7-020f-435c-b757-e96a82ab7bab")).into(holder.imgV);

    }

    @Override
    public int getItemCount() {
        return PlayerList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder
    {
        private TextView cname,age,pname,desc;
        private ImageView imgV;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.candidate_name);
            age=itemView.findViewById(R.id.age);
            pname= itemView.findViewById(R.id.party_name);
            imgV=itemView.findViewById(R.id.logo);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Confirmation Dialog for DELETION
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setCancelable(true);
                    builder.setTitle("VOTE").setMessage("Are You Sure You Want To Cast Your Vote?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Candidate c  = PlayerList.get(getLayoutPosition());
                            Vote v = new Vote(VoteActivity.vid,c, FirebaseAuth.getInstance().getCurrentUser().getUid());
                            if(!notin()) {
                                FirebaseDatabase.getInstance().getReference("Polls").child("Votes").push().setValue(v).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        SharedPreferences.Editor editor = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit();
                                        editor.putBoolean("hasvoted",true);
                                        editor.apply();
                                    }
                                });
                                context.startActivity(new Intent(context,VoteridActivity.class));

                            }
                            else
                                Toast.makeText(context, "You have already voted", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context,VoteridActivity.class));
                            dialogInterface.cancel();


                        }
                    })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                                    dialogInterface.cancel();

                                }
                            });
                    AlertDialog a = builder.create();
                    a.show();
                    return true;
                }
            });



        }
    }
}
