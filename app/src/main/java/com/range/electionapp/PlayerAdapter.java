package com.range.electionapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private Context context;
    public ArrayList<Candidate> PlayerList;


    public PlayerAdapter(Context context, ArrayList<Candidate> playerList) {
        this.context = context;
        PlayerList = playerList;
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
        holder.desc.setText("Desc : "+cnd.getDesc());

        holder.imgV.setImageResource(R.drawable.ic_menu_camera);

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


        }
    }
}
