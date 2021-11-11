package com.example.livescore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class FullWicketsAdapter extends RecyclerView.Adapter<FullWicketsAdapter.ViewHolder> {

    Context ctx;
    ArrayList<ScoreModel> score_list;

    public FullWicketsAdapter(Context ctx, ArrayList<ScoreModel> score_list){
        this.ctx = ctx;
        this.score_list = score_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.wicketlist_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(score_list != null && score_list.size() > 0)
        {
            ScoreModel scoreModel = score_list.get(position);
            holder.batTv.setText(scoreModel.getBatter());
            holder.rTv.setText(scoreModel.getR());
            holder.bTv.setText(scoreModel.getB());
            holder._6sTv.setText(scoreModel.get_6s());
            holder._4sTv.setText(scoreModel.get_4s());
            holder.srTv.setText(scoreModel.getSr());
        }
        else
            return;

    }

    @Override
    public int getItemCount() {
        return score_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView batTv, rTv, bTv, _4sTv, _6sTv, srTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batTv = itemView.findViewById(R.id.bowlerId);
            rTv = itemView.findViewById(R.id.oId);
            bTv = itemView.findViewById(R.id.mId);
            _4sTv = itemView.findViewById(R.id.runsId);
            _6sTv = itemView.findViewById(R.id.wicsId);
            srTv = itemView.findViewById(R.id.ecoId);
        }
    }
}
