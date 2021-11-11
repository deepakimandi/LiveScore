package com.example.livescore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;

public class ScorecardAdapter extends RecyclerView.Adapter<ScorecardAdapter.ViewHolder> {
    private ArrayList<Scorecard> scorecardArrayList;
    private Context ctx;

    public ScorecardAdapter(ArrayList<Scorecard> scorecardArrayList, Context ctx) {
        this.scorecardArrayList = scorecardArrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scorecard_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scorecard scorecard = scorecardArrayList.get(position);
        String s1, s2, s3;
        holder.titleTv.setText(scorecard.getTitle());
        holder.team1Tv.setText(scorecard.getTeam1().trim() + " ");
        holder.team2Tv.setText(scorecard.getTeam2().trim() + " ");
        holder.score1Tv.setText(scorecard.getScore1());
        holder.score2Tv.setText(scorecard.getScore2());
        holder.endTitleTv.setText(s1 = scorecard.getEndTitle());
        holder.statusTv.setText(s2 = scorecard.getStatus());
        holder.tossTv.setText(s3 = scorecard.getToss());
        int i;
        if(scorecard.getWin() == 1)
        {
//            String winner = s3.substring(0, i).trim();
//            System.out.println(winner + " --- " + scorecard.getTeam1().trim());
//            if(winner.equalsIgnoreCase(scorecard.getTeam1().trim()))
                holder.team1Tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.trophy, 0);
            holder.team2Tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

//            else if (winner.equalsIgnoreCase(scorecard.getTeam2().trim()))
//                holder.team2Tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.trophy, 0);

        }
        else if (scorecard.getWin() == 2) {
            holder.team2Tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.trophy, 0);
            holder.team1Tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        }
        else
        {
            holder.team2Tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.team1Tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
//        System.out.println(s1 + " ----- " + s2 + " ----- " + s3);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx.getApplicationContext(), CricSummary.class);
                intent.putExtra("url", scorecard.getUrl());
                intent.putExtra("t1s", scorecard.getTeam1() + " - " + scorecard.getScore1());
                intent.putExtra("t2s", scorecard.getTeam2() + " - " + scorecard.getScore2());
                intent.putExtra("statustxt", s2.substring(s2.lastIndexOf(',') + 2));
                intent.putExtra("resortoss", s3);
                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return scorecardArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTv, team1Tv, team2Tv, score1Tv, score2Tv, endTitleTv, statusTv, tossTv;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.titleId);
            team1Tv = itemView.findViewById(R.id.team1nameId);
            team2Tv = itemView.findViewById(R.id.team2nameId);
            score1Tv = itemView.findViewById(R.id.score1Id);
            score2Tv = itemView.findViewById(R.id.score2Id);
            endTitleTv = itemView.findViewById(R.id.endId);
            statusTv = itemView.findViewById(R.id.statusId);
            tossTv = itemView.findViewById(R.id.tossId);
            cardView = itemView.findViewById(R.id.scId);

        }
    }
}
