package com.example.livescore;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    private ArrayList<CatBean> cats;
    private Context ctx;
//    private ProgressBar progressBar;
//    private ArrayList<Scorecard> scorecardArrayList;
//    private ScorecardAdapter scorecardAdapter;
    private CatClickInterface catClickInterface;
    private int checkedPosition = 0;

    public CatAdapter(ArrayList<CatBean> cats, Context ctx, CatClickInterface catClickInterface) {
        this.cats = cats;
        this.ctx = ctx;
        this.catClickInterface = catClickInterface;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rv, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {

//        Toast.makeText(ctx, Integer.toString(position), Toast.LENGTH_SHORT).show();
        holder.bind(cats.get(position));
//        CatBean cat = cats.get(position);
//        holder.catTv.setText(cat.getTitle());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(ctx, position, Toast.LENGTH_SHORT).show();
//                holder.catCardRl.setBackgroundResource(R.drawable.custom_button);
//                catClickInterface.onCatClick(position);
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    class CatViewHolder extends RecyclerView.ViewHolder{
        private TextView catTv;
        private RelativeLayout catCardRl;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            catTv = itemView.findViewById(R.id.cattvId);
            catCardRl = itemView.findViewById(R.id.catCardId);
        }
        void bind(final CatBean catBean)
        {
            if(checkedPosition == -1)
            {
                catCardRl.setBackgroundColor(Color.parseColor("#000000"));
                catTv.setTextColor(Color.parseColor("#E6E6E6"));
            }
            else {
                if(checkedPosition == getAdapterPosition())
                {
                    catCardRl.setBackgroundColor(Color.parseColor("#ffffff"));
                    catTv.setTextColor(Color.parseColor("#FF8000"));
                }
                else {
                    catCardRl.setBackgroundColor(Color.parseColor("#000000"));
                    catTv.setTextColor(Color.parseColor("#E6E6E6"));
                }
            }
            catTv.setText(catBean.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    catCardRl.setBackgroundColor(Color.parseColor("#000000"));
                    if(checkedPosition != getAdapterPosition())
                    {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        catClickInterface.onCatClick(checkedPosition);
                        notifyDataSetChanged();
//                        getScores();
                    }
                }
            });
        }
    }

//    private void getScores()
//    {
//        progressBar.setVisibility(View.VISIBLE);
////        scorecardArrayList.clear();
//        scorecardArrayList.add(new Scorecard("T20", "IND", "ENG", "97/1", "Yet to bat", "India won the toss and chose to bat"));
//        scorecardAdapter.notifyDataSetChanged();
//        progressBar.setVisibility(View.GONE);
//    }

    public CatBean getSelected(){
        if(checkedPosition != -1)
            return cats.get(checkedPosition);
        return null;
    }



    public interface CatClickInterface{
        void onCatClick(int position);
    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        private TextView catTv;
//        private RelativeLayout catCardRl;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            catTv = itemView.findViewById(R.id.cattvId);
//            catCardRl = itemView.findViewById(R.id.catCardId);
//        }
//    }
}
