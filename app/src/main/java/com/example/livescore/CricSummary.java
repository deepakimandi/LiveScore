package com.example.livescore;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class CricSummary extends AppCompatActivity {

    TextView inn1hdr, inn2hdr;
    TextView extra1Tv, total1Tv, didnot1Tv, fallof1Tv;
    TextView extra2Tv, total2Tv, didnot2Tv, fallof2Tv;
    RecyclerView inn1batRv, inn2batRv, inn1bowlRv, inn2bowlRv;
    FullScorecardInn1BatAdapter fullScorecardInn1BatAdapter, liveBatAdapter;
    FullScorecardInn1BatAdapter fullScorecardInn2BatAdapter;
    FullWicketsAdapter fullWickets1Adapter, fullWickets2Adapter, liveWicketAdapter;
    View layoutInflater;
    Button liveBtn, msBtn;
    String url, t1s, t2s, status, resortoss;
    TextView t1sTv, t2sTv, statusTv, resortossTv;
    RecyclerView livebatRv, livebowlRv;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cric_summary);
        final String url = getIntent().getExtras().getString("url");
        final String t1s = getIntent().getExtras().getString("t1s");
        final String t2s = getIntent().getExtras().getString("t2s");
        final String status = getIntent().getExtras().getString("statustxt");
        final String resortoss = getIntent().getExtras().getString("resortoss");

        this.url = url;
        this.t1s = t1s;
        this.t2s = t2s;
        this.status = status;
        this.resortoss = resortoss;
//        this.layoutInflater = findViewById(R.id.cslayoutId);
        liveBtn = findViewById(R.id.cslive_button);
        msBtn = findViewById(R.id.ms_button);
        setLivecard(null);

//        swipeRefreshLayout = findViewById(R.id.refreshDetailsId);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                ColorDrawable viewColor = (ColorDrawable) liveBtn.getBackground();
//                int lc = viewColor.getColor();
//                if(lc == Color.parseColor("#FF8000"))
//                    setLivecard(null);
//                else
//                    setMS(null);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    public void setLivecard(View view)
    {
        liveBtn.setBackgroundColor(Color.parseColor("#FF8000"));
        msBtn.setBackgroundColor(Color.parseColor("#ffffff"));
        liveBtn.setTextColor(Color.parseColor("#ffffff"));
        msBtn.setTextColor(Color.parseColor("#000000"));
        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.live_scorecard, null);


        swipeRefreshLayout = view1.findViewById(R.id.refreshDetailsId);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ColorDrawable viewColor = (ColorDrawable) liveBtn.getBackground();
                int lc = viewColor.getColor();
                if(lc == Color.parseColor("#FF8000"))
                    setLivecard(null);
                else
                    setMS(null);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rlId);
        relativeLayout.removeAllViews();

        t1sTv = view1.findViewById(R.id.t1sId);
        t2sTv = view1.findViewById(R.id.t2sId);
        statusTv = view1.findViewById(R.id.statustxtId);
        resortossTv = view1.findViewById(R.id.resortossId);
        if(t1s.charAt(t1s.length() - 2) == '-')
            t1s = t1s + "Not yet";
        if(t2s.charAt(t2s.length() - 2) == '-')
            t2s = t2s + "Not yet";

        SpannableStringBuilder ssb = new SpannableStringBuilder(t1s);
//        ForegroundColorSpan grey = new ForegroundColorSpan(Color.parseColor("#686868"));
        int idx = t1s.lastIndexOf('-');
        ssb.setSpan(new StyleSpan(Typeface.BOLD), idx, t1s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder ssb1 = new SpannableStringBuilder(t2s);
//        ForegroundColorSpan grey = new ForegroundColorSpan(Color.parseColor("#686868"));
        int idx1 = t2s.lastIndexOf('-');
        ssb1.setSpan(new StyleSpan(Typeface.BOLD), idx1, t2s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        t1sTv.setText(ssb);
        t2sTv.setText(ssb1);
        statusTv.setText(status);
        resortossTv.setText(resortoss);
//        liveBtn = view1.findViewById(R.id.cslive_button);
//        msBtn = view1.findViewById(R.id.ms_button);

//        inn1hdr = view1.findViewById(R.id.inn1hId);
//        inn2hdr = view1.findViewById(R.id.inn2hId);
//        extra1Tv = view1.findViewById(R.id.extra1Id);
//        total1Tv = view1.findViewById(R.id.total1Id);
//        didnot1Tv = view1.findViewById(R.id.didnot1Id);
//        fallof1Tv = view1.findViewById(R.id.fallof1Id);
//
//        extra2Tv = view1.findViewById(R.id.extra2Id);
//        total2Tv = view1.findViewById(R.id.total2Id);
//        didnot2Tv = view1.findViewById(R.id.didnot2Id);
//        fallof2Tv = view1.findViewById(R.id.fallof2Id);

        if(!Python.isStarted())
        {
            Python.start(new AndroidPlatform(getApplicationContext()));
        }
        Python py1 = Python.getInstance();
        final PyObject pyobj1 = py1.getModule("temp");
        int len = url.length();
        String url1 = url.substring(0, len - 14);
        url1 = url1 + "live-cricket-score";
        System.out.println(url1);
        PyObject pyObject = pyobj1.callAttr("livecard", url1);
        JSONObject jsonObject = null;
        JSONArray jsonArray1= null, jsonArray2 = null, jsonArray3 = null, jsonArray4 = null;
        ArrayList<ScoreModel> bat_list = new ArrayList<>(), bowl_list = new ArrayList<>();
        System.out.println(pyObject.toString());

        try {
            jsonObject = new JSONObject(pyObject.toString());
            jsonArray1 = jsonObject.getJSONArray("bat1");
            jsonArray2 = jsonObject.getJSONArray("bat2");
            jsonArray3 = jsonObject.getJSONArray("bowl1");
            jsonArray4 = jsonObject.getJSONArray("bowl2");

//            inn1hdr.setText(jsonArray.get(0).toString());
            if(jsonArray1.length() != 0) {
                bat_list.add(new ScoreModel((String) jsonArray1.get(0), (String) jsonArray1.get(1), (String) jsonArray1.get(2), (String) jsonArray1.get(3), jsonArray1.get(4).toString(), jsonArray1.get(5).toString()));

            }
            if(jsonArray2.length() != 0) {
                bat_list.add(new ScoreModel((String) jsonArray2.get(0), (String) jsonArray2.get(1), (String) jsonArray2.get(2), (String) jsonArray2.get(3), jsonArray2.get(4).toString(), jsonArray2.get(5).toString()));
            }

            if(jsonArray3.length() != 0) {
                bowl_list.add(new ScoreModel((String) jsonArray3.get(0), (String) jsonArray3.get(1), (String) jsonArray3.get(2), (String) jsonArray3.get(3), jsonArray3.get(4).toString(), jsonArray3.get(5).toString()));

            }

            if(jsonArray4.length() != 0) {
                bowl_list.add(new ScoreModel((String) jsonArray4.get(0), (String) jsonArray4.get(1), (String) jsonArray4.get(2), (String) jsonArray4.get(3), jsonArray4.get(4).toString(), jsonArray4.get(5).toString()));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        livebatRv = view1.findViewById(R.id.livebat_rvId);
        livebatRv.setHasFixedSize(true);
        livebatRv.setLayoutManager(new LinearLayoutManager(this));
        liveBatAdapter = new FullScorecardInn1BatAdapter(this, bat_list);
        livebatRv.setAdapter(liveBatAdapter);

        livebowlRv = view1.findViewById(R.id.livebowl_rvId);
        livebowlRv.setHasFixedSize(true);
        livebowlRv.setLayoutManager(new LinearLayoutManager(this));
        liveWicketAdapter = new FullWicketsAdapter(this, bowl_list);
        livebowlRv.setAdapter(liveWicketAdapter);

//        this.layoutInflater.add

        relativeLayout.addView(view1);
    }

    public void setMS(View view) {
        msBtn.setBackgroundColor(Color.parseColor("#FF8000"));
        liveBtn.setBackgroundColor(Color.parseColor("#ffffff"));
        msBtn.setTextColor(Color.parseColor("#ffffff"));
        liveBtn.setTextColor(Color.parseColor("#000000"));
        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.full_scorecard, null);


        swipeRefreshLayout = view1.findViewById(R.id.refreshDetailsId);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ColorDrawable viewColor = (ColorDrawable) liveBtn.getBackground();
                int lc = viewColor.getColor();
                if(lc == Color.parseColor("#FF8000"))
                    setLivecard(null);
                else
                    setMS(null);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rlId);
        relativeLayout.removeAllViews();
//        liveBtn = view1.findViewById(R.id.cslive_button);
//        msBtn = view1.findViewById(R.id.ms_button);

        t1sTv = view1.findViewById(R.id.t1sId);
        t2sTv = view1.findViewById(R.id.t2sId);
        statusTv = view1.findViewById(R.id.statustxtId);
        resortossTv = view1.findViewById(R.id.resortossId);
        if(t1s.charAt(t1s.length() - 2) == '-')
            t1s = t1s + "Not yet";
        if(t2s.charAt(t2s.length() - 2) == '-')
            t2s = t2s + "Not yet";
        SpannableStringBuilder ssb = new SpannableStringBuilder(t1s);
//        ForegroundColorSpan grey = new ForegroundColorSpan(Color.parseColor("#686868"));
        int idx = t1s.lastIndexOf('-');
        ssb.setSpan(new StyleSpan(Typeface.BOLD), idx, t1s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder ssb1 = new SpannableStringBuilder(t2s);
//        ForegroundColorSpan grey = new ForegroundColorSpan(Color.parseColor("#686868"));
        int idx1 = t2s.lastIndexOf('-');
        ssb1.setSpan(new StyleSpan(Typeface.BOLD), idx1, t2s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        t1sTv.setText(ssb);
        t2sTv.setText(ssb1);
        statusTv.setText(status);
        resortossTv.setText(resortoss);
        inn1hdr = view1.findViewById(R.id.inn1hId);
        inn2hdr = view1.findViewById(R.id.inn2hId);
        extra1Tv = view1.findViewById(R.id.extra1Id);
        total1Tv = view1.findViewById(R.id.total1Id);
        didnot1Tv = view1.findViewById(R.id.didnot1Id);
        fallof1Tv = view1.findViewById(R.id.fallof1Id);

        extra2Tv = view1.findViewById(R.id.extra2Id);
        total2Tv = view1.findViewById(R.id.total2Id);
        didnot2Tv = view1.findViewById(R.id.didnot2Id);
        fallof2Tv = view1.findViewById(R.id.fallof2Id);

        if(!Python.isStarted())
        {
            Python.start(new AndroidPlatform(getApplicationContext()));
        }
        Python py1 = Python.getInstance();
        final PyObject pyobj1 = py1.getModule("temp");
        PyObject pyObject = pyobj1.callAttr("scorecard", url);
        JSONObject jsonObject = null;
        JSONArray jsonArray= null;
        try {
            jsonObject = new JSONObject(pyObject.toString());
            jsonArray = jsonObject.getJSONArray("headers");
            inn1hdr.setText(jsonArray.get(0).toString());
            if(jsonArray.length() != 1) {
                inn2hdr.setText(jsonArray.get(1).toString());
                total2Tv.setText(jsonObject.get("t21").toString());
                didnot2Tv.setText(jsonObject.get("t22").toString());
                if(jsonObject.has("t23"))
                    fallof2Tv.setText(jsonObject.get("t23").toString());
                else
                    fallof2Tv.setText("Fall of Wickets: Not yet" );
            }
            else {
                inn2hdr.setText("Second Innings yet to happen");
            }
            total1Tv.setText(jsonObject.get("t11").toString());
            didnot1Tv.setText(jsonObject.get("t12").toString());
            fallof1Tv.setText(jsonObject.get("t13").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        inn1batRv = view1.findViewById(R.id.inn1bat_rvId);
        inn1batRv.setHasFixedSize(false);
//        inn1batRv.setNestedScrollingEnabled(false);

        inn1batRv.setLayoutManager(new LinearLayoutManager(this));
        fullScorecardInn1BatAdapter = new FullScorecardInn1BatAdapter(this, getList1(jsonObject));
        inn1batRv.setAdapter(fullScorecardInn1BatAdapter);

        inn2batRv = view1.findViewById(R.id.inn2bat_rvId);
        inn2batRv.setHasFixedSize(false);
        inn2batRv.setLayoutManager(new LinearLayoutManager(this));
        fullScorecardInn2BatAdapter = new FullScorecardInn1BatAdapter(this, getList2(jsonObject));
        inn2batRv.setAdapter(fullScorecardInn2BatAdapter);

        inn1bowlRv = view1.findViewById(R.id.inn1bowl_rvId);
        inn1bowlRv.setHasFixedSize(true);
        inn1bowlRv.setLayoutManager(new LinearLayoutManager(this));
        fullWickets1Adapter = new FullWicketsAdapter(this, getList3(jsonObject));
        inn1bowlRv.setAdapter(fullWickets1Adapter);

        inn2bowlRv = view1.findViewById(R.id.inn2bowl_rvId);
        inn2bowlRv.setHasFixedSize(true);
        inn2bowlRv.setLayoutManager(new LinearLayoutManager(this));
        fullWickets2Adapter = new FullWicketsAdapter(this, getList4(jsonObject));
        inn2bowlRv.setAdapter(fullWickets2Adapter);

//        this.layoutInflater.add

        relativeLayout.addView(view1);
    }

    private ArrayList<ScoreModel> getList1(JSONObject jsonObject){
        ArrayList<ScoreModel> score_list = new ArrayList<>();


//        JSONObject jsonObject = null;
        try {
//            jsonObject = new JSONObject(jsonString);

            JSONObject jsonObject1 = jsonObject.getJSONObject("team1batting");
            Iterator<String> it = jsonObject1.keys();
            for (; it.hasNext(); ) {
                String key = it.next();
                JSONArray jsonArray = jsonObject1.getJSONArray(key);
                if (jsonArray.length() != 4) {

                    score_list.add(new ScoreModel((String) jsonArray.get(0), (String) jsonArray.get(1), (String) jsonArray.get(2), (String) jsonArray.get(3), jsonArray.get(5).toString(), jsonArray.get(6).toString(), jsonArray.get(7).toString()));
                }
                else{
                    extra1Tv.setText(jsonArray.get(0).toString() + ' ' + jsonArray.get(1).toString() + ' ' + jsonArray.get(2).toString());
                }
            }
//
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return score_list;
    }


    private ArrayList<ScoreModel> getList2(JSONObject jsonObject){
        ArrayList<ScoreModel> score_list = new ArrayList<>();


//        JSONObject jsonObject = null;
        try {
//            jsonObject = new JSONObject(jsonString);

            JSONObject jsonObject1 = jsonObject.getJSONObject("team2batting");
            Iterator<String> it = jsonObject1.keys();
            for (; it.hasNext(); ) {
                String key = it.next();
                JSONArray jsonArray = jsonObject1.getJSONArray(key);
                if (jsonArray.length() != 4) {

                    score_list.add(new ScoreModel((String) jsonArray.get(0), (String) jsonArray.get(1), (String) jsonArray.get(2), (String) jsonArray.get(3), jsonArray.get(5).toString(), jsonArray.get(6).toString(), jsonArray.get(7).toString()));
                }
                else{
                    extra2Tv.setText(jsonArray.get(0).toString() + ' ' + jsonArray.get(1).toString() + ' ' + jsonArray.get(2).toString());
                }

            }
//
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return score_list;
    }

    private ArrayList<ScoreModel> getList3(JSONObject jsonObject){
        ArrayList<ScoreModel> score_list = new ArrayList<>();


//        JSONObject jsonObject = null;
        try {
//            jsonObject = new JSONObject(jsonString);

            JSONObject jsonObject1 = jsonObject.getJSONObject("team1bowling");
            Iterator<String> it = jsonObject1.keys();
            for (; it.hasNext(); ) {
                String key = it.next();
                JSONArray jsonArray = jsonObject1.getJSONArray(key);
//                if (jsonArray.length() != 4) {

                score_list.add(new ScoreModel((String) jsonArray.get(0), (String) jsonArray.get(1), (String) jsonArray.get(2), jsonArray.get(3).toString(), jsonArray.get(4).toString(), jsonArray.get(5).toString()));
//                }
            }
//
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return score_list;
    }

    private ArrayList<ScoreModel> getList4(JSONObject jsonObject){
        ArrayList<ScoreModel> score_list = new ArrayList<>();


//        JSONObject jsonObject = null;
        try {
//            jsonObject = new JSONObject(jsonString);

            JSONObject jsonObject1 = jsonObject.getJSONObject("team2bowling");
            Iterator<String> it = jsonObject1.keys();
            for (; it.hasNext(); ) {
                String key = it.next();
                JSONArray jsonArray = jsonObject1.getJSONArray(key);
//                if (jsonArray.length() != 4) {

                score_list.add(new ScoreModel((String) jsonArray.get(0), (String) jsonArray.get(1), (String) jsonArray.get(2), jsonArray.get(3).toString(), jsonArray.get(4).toString(), jsonArray.get(5).toString()));
//                }
            }
//
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return score_list;
    }
}
