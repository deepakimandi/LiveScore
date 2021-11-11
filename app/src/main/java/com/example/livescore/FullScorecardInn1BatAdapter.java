package com.example.livescore;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class FullScorecardInn1BatAdapter extends RecyclerView.Adapter<FullScorecardInn1BatAdapter.ViewHolder> {

    Context ctx;
    ArrayList<ScoreModel> score_list;

    public FullScorecardInn1BatAdapter(Context ctx, ArrayList<ScoreModel> score_list){
        this.ctx = ctx;
        this.score_list = score_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.scorelist_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(score_list != null && score_list.size() > 0)
        {
            ScoreModel scoreModel = score_list.get(position);
            int idx = scoreModel.getBatter().length();
            String batter = scoreModel.getBatter() + '\n' + scoreModel.getOut();
//            SpannableString ss = new SpannableString(batter);
            SpannableStringBuilder ssb = new SpannableStringBuilder(batter);
            ForegroundColorSpan grey = new ForegroundColorSpan(Color.parseColor("#686868"));
            ssb.setSpan(grey, idx, batter.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.batTv.setText(ssb);
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
            batTv = itemView.findViewById(R.id.batterId);
            rTv = itemView.findViewById(R.id.rId);
            bTv = itemView.findViewById(R.id.bId);
            _4sTv = itemView.findViewById(R.id.fsId);
            _6sTv = itemView.findViewById(R.id.sxsId);
            srTv = itemView.findViewById(R.id.srId);
        }
    }
}
