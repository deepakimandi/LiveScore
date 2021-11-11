package com.example.livescore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HockeySummary extends AppCompatActivity {

    TextView inn1hdr, inn2hdr;
    TextView extra1Tv, total1Tv, didnot1Tv, fallof1Tv;
    TextView extra2Tv, total2Tv, didnot2Tv, fallof2Tv;
    RecyclerView inn1batRv, inn2batRv, inn1bowlRv, inn2bowlRv;
    FullScorecardInn1BatAdapter fullScorecardInn1BatAdapter, liveBatAdapter;
    FullScorecardInn1BatAdapter fullScorecardInn2BatAdapter;
    FullWicketsAdapter fullWickets1Adapter, fullWickets2Adapter, liveWicketAdapter;
    View layoutInflater;
    Button liveBtn, msBtn;
//    String team1 = ;
//    ArrayList<String> h = new ArrayList<>(), a = new ArrayList<>();
    RecyclerView livebatRv, livebowlRv;
    TextView hcyhscoreTv, htTv, atTv, hcyascoreTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hockey_scorecard);
        final ArrayList<String> h = getIntent().getStringArrayListExtra("h");
        final ArrayList<String> a = getIntent().getStringArrayListExtra("a");
        final String team1 = getIntent().getExtras().getString("team1");
        final String team2 = getIntent().getExtras().getString("team2");
        String hs = "", as = "";
        htTv = findViewById(R.id.hteamId);
        atTv = findViewById(R.id.ateamId);
        hcyhscoreTv = findViewById(R.id.hcyhscoreId);
        hcyascoreTv = findViewById(R.id.hcyascoreId);
        htTv.setText(team1);
        atTv.setText(team2);
        for(int i = 0; i < h.size(); i++)
        {
            hs += (h.get(i) + "\n");
        }
        for(int i = 0; i < a.size(); i++)
        {
            as += (a.get(i) + "\n");
        }
        if(h.size() == 0)
            hcyhscoreTv.setText("No goals");
        else
            hcyhscoreTv.setText(hs);
        if(a.size() == 0)
            hcyascoreTv.setText("No goals");
        else
            hcyascoreTv.setText(as);
//        this.layoutInflater = findViewById(R.id.cslayoutId);
    }
}