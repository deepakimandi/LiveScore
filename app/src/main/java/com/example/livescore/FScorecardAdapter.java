package com.example.livescore;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FScorecardAdapter extends RecyclerView.Adapter<FScorecardAdapter.ViewHolder> {
    private ArrayList<FScorecard> scorecardArrayList;
    private Context ctx;

    public FScorecardAdapter(ArrayList<FScorecard> scorecardArrayList, Context ctx) {
        this.scorecardArrayList = scorecardArrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scorecard1_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FScorecard scorecard = scorecardArrayList.get(position);
        System.out.println(scorecard.getTitle() + " " + scorecard.getTeam1() + " " + scorecard.getTeam2());
        holder.titleTv.setText(scorecard.getTitle());
        holder.team1Tv.setText(scorecard.getTeam1());
        holder.team2Tv.setText(scorecard.getTeam2());
        holder.score1Tv.setText(scorecard.getScore1());
        holder.score2Tv.setText(scorecard.getScore2());
        holder.timeTv.setText(scorecard.getTime());
        holder.endTitleTv.setText(scorecard.getFendId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scorecard.getSport().equalsIgnoreCase("Hockey"))
                {
                    Intent intent = new Intent(ctx.getApplicationContext(), HockeySummary.class);
                    List<String> h = scorecard.getHomeTeamGoals(), a = scorecard.getAwayTeamGoals();
//                    List<List<String >> ha = new ArrayList<>();
//                    ha.add(h);
//                    ha.add(a);
                    intent.putStringArrayListExtra("h", (ArrayList<String>) h);
                    intent.putStringArrayListExtra("a", (ArrayList<String>) a);
                    intent.putExtra("team1", scorecard.getTeam1());
                    intent.putExtra("team2", scorecard.getTeam2());
                    ctx.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return scorecardArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTv, team1Tv, team2Tv, score1Tv, score2Tv, endTitleTv, timeTv;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.ftitleId);
            team1Tv = itemView.findViewById(R.id.fteam1nameId);
            team2Tv = itemView.findViewById(R.id.fteam2nameId);
            score1Tv = itemView.findViewById(R.id.fscore1Id);
            score2Tv = itemView.findViewById(R.id.fscore2Id);
            timeTv = itemView.findViewById(R.id.ftimeId);
            endTitleTv = itemView.findViewById(R.id.fendId);
            cardView = itemView.findViewById(R.id.fscId);
        }
    }
}
