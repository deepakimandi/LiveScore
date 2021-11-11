package com.example.livescore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.Response;
import com.android.volley.toolbox.HttpResponse;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.logging.type.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
//import java.net.http;
//import okhttp3.OkHttpClient;
import okhttp3.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import static com.example.livescore.R.color.black;
import static com.example.livescore.R.color.white;

public class MainActivity extends AppCompatActivity implements CatAdapter.CatClickInterface{

    private RecyclerView scorecardRv, catRv;
    private ArrayList<Scorecard> scorecardArrayList;
    private ArrayList<FScorecard> fScorecardArrayList;
    private ArrayList<CatBean> catStrings;
    private ScorecardAdapter scorecardAdapter;
    private FScorecardAdapter fScorecardAdapter;
    private CatAdapter catAdapter;
    private ProgressBar progressBar;
    private TextView emptyTv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button todayButton, liveButton;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Rect mBounds;

    private RelativeLayout rl;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizantal_layout);
        rl = findViewById(R.id.mainId);
        scorecardRv = findViewById(R.id.rv_scorecardId);
        catRv = findViewById(R.id.rv_categoryId);
        swipeRefreshLayout = findViewById(R.id.refresh);
        progressBar = findViewById(R.id.pbId);
        emptyTv = findViewById(R.id.empty);
        todayButton = findViewById(R.id.today_button);
        liveButton = findViewById(R.id.live_button);
//        long millis=System.currentTimeMillis();
//        java.sql.Date date=new java.sql.Date(millis);
//        LocalDate currentDate
//                = LocalDate.parse((CharSequence) date);
//
//        // Get day from date
//        int day = currentDate.getDayOfMonth();
//
//        // Get month from date
//        Month month = currentDate.getMonth();
        String date=java.util.Calendar.getInstance().getTime().toString();
        String[] dateArr = date.split(" ", 6);
//        System.out.println(date);
        todayButton.setText("TODAY " + dateArr[2] + " " + dateArr[1]);
        liveButton.setText("Live");
        catStrings = new ArrayList<>();

        catAdapter = new CatAdapter(catStrings, this, this::onCatClick);
//        scorecardRv.setLayoutManager(new LinearLayoutManager(this));

        catRv.setAdapter(catAdapter);
        getCats();

        todayButton.setBackgroundColor(Color.parseColor("#FF8000"));
        liveButton.setBackgroundColor(Color.parseColor("#ffffff"));
        todayButton.setTextColor(Color.parseColor("#ffffff"));
        liveButton.setTextColor(Color.parseColor("#000000"));
        getScores("Cricket", 0);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getScores("Cricket", 0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        liveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                liveButton.getDrawingCacheBackgroundColor().
                liveButton.setBackgroundColor(Color.parseColor("#FF8000"));
                todayButton.setBackgroundColor(Color.parseColor("#ffffff"));
                liveButton.setTextColor(Color.parseColor("#ffffff"));
                todayButton.setTextColor(Color.parseColor("#000000"));
                getScores("Cricket", 1);
            }
        });
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayButton.setBackgroundColor(Color.parseColor("#FF8000"));
                liveButton.setBackgroundColor(Color.parseColor("#ffffff"));
                todayButton.setTextColor(Color.parseColor("#ffffff"));
                liveButton.setTextColor(Color.parseColor("#000000"));
                getScores("Cricket", 0);
            }
        });
    }

    public void getCats() {
        catStrings.add(new CatBean("Cricket", false));
        catStrings.add(new CatBean("Football", false));
//        catStrings.add(new CatBean("Tennis", false));
        catStrings.add(new CatBean("Hockey", false));
        catStrings.add(new CatBean("Basketball", false));
        catAdapter.notifyDataSetChanged();
    }

    public void initIfNeeded() {
        if(mBitmap == null) {
            mBitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mBounds = new Rect();
        }
    }

    public int getBackgroundColor(View view) {
        // The actual color, not the id.
        int color = Color.BLACK;

        if(view.getBackground() instanceof ColorDrawable) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                initIfNeeded();

                // If the ColorDrawable makes use of its bounds in the draw method,
                // we may not be able to get the color we want. This is not the usual
                // case before Ice Cream Sandwich (4.0.1 r1).
                // Yet, we change the bounds temporarily, just to be sure that we are
                // successful.
                ColorDrawable colorDrawable = (ColorDrawable)view.getBackground();

                mBounds.set(colorDrawable.getBounds()); // Save the original bounds.
                colorDrawable.setBounds(0, 0, 1, 1); // Change the bounds.

                colorDrawable.draw(mCanvas);
                color = mBitmap.getPixel(0, 0);

                colorDrawable.setBounds(mBounds); // Restore the original bounds.
            }
            else {
                color = ((ColorDrawable)view.getBackground()).getColor();
            }
        }

        return color;
    }

    private void getScores(String sport, int flag)
    {
//        ColorDrawable viewColor = (ColorDrawable) liveButton.getBackground();
//        int lc = viewColor.getColor();
        int lc = getBackgroundColor(liveButton);
        if(lc == Color.parseColor("#FF8000"))
            flag = 1;
        else
            flag = 0;
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String date = f.format(new Date());
        emptyTv.setVisibility(View.GONE);
        if(sport.equalsIgnoreCase("Cricket"))
        {
            progressBar.setVisibility(View.VISIBLE);
            scorecardArrayList = new ArrayList<>();
            scorecardAdapter = new ScorecardAdapter(scorecardArrayList, this);
            scorecardRv.setAdapter(scorecardAdapter);
            scorecardArrayList.clear();
            if(!Python.isStarted())
            {
                Python.start(new AndroidPlatform(this));
            }
            Python py = Python.getInstance();
            final PyObject pyobj = py.getModule("cktMatches");
            PyObject pyObject = pyobj.callAttr("getCKTMatches", flag);
//            Python py1 = Python.getInstance();
//            final PyObject pyobj1 = py1.getModule("temp");
//            PyObject pyObject1 = pyobj1.callAttr("scorecard");
//            Toast.makeText(getApplicationContext(), pyObject1.toString(), Toast.LENGTH_LONG).show();
//            fScorecardArrayList.add(new FScorecard("title", "team1", "team2", "score1", "pyObject.toString()."));
//            fScorecardAdapter.notifyDataSetChanged();
            String title, score1, score2, team1, team2;
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(pyObject.toString());
//                                JSONArray jsonArray = jsonObject.getJSONArray("events");
                Iterator<String> it = jsonObject.keys();
                if(!it.hasNext())
                {
                    scorecardArrayList.clear();
                    scorecardAdapter.notifyDataSetChanged();
                    emptyTv.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    emptyTv.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
                }
//                if(flag == 1)
//                {
////                    fScorecardArrayList.clear();
////                    fScorecardAdapter.notifyDataSetChanged();
//                    for (; it.hasNext(); ) {
//                        String key = it.next();
//                        JSONArray jsonArray = jsonObject.getJSONArray(key);
//                        if(jsonArray.get(5).toString().charAt(8) == 'N' || jsonArray.get(5).toString().charAt(8) == 'E')
//                            continue;
//                        fScorecardArrayList.add(new FScorecard((String) jsonArray.get(0), (String) jsonArray.get(1), (String) jsonArray.get(2), jsonArray.get(3).toString(), jsonArray.get(4).toString(), jsonArray.get(5).toString(), jsonArray.get(6).toString()));
//                    }
//                }
//                else {
                    for (; it.hasNext(); ) {
                        String key = it.next();
                        JSONArray jsonArray = jsonObject.getJSONArray(key);
                        if(jsonArray.length() == 11) {
                            scorecardArrayList.add(new Scorecard((String) jsonArray.get(0), (String) jsonArray.get(3), (String) jsonArray.get(4), jsonArray.get(5).toString(), jsonArray.get(6).toString(), jsonArray.get(1).toString(), jsonArray.get(2).toString(), jsonArray.get(7).toString(), jsonArray.get(8).toString(), jsonArray.get(9).toString(), Integer.parseInt(jsonArray.get(10).toString())));
                            System.out.println("-------------------------********************************");
                        }
//                    Toast.makeText(getApplicationContext(), (String) jsonArray.get(3).toString(), Toast.LENGTH_LONG).show();
                        else
                            scorecardArrayList.add(new Scorecard((String) jsonArray.get(0), (String) jsonArray.get(3), (String) jsonArray.get(4), jsonArray.get(5).toString(), jsonArray.get(6).toString(), jsonArray.get(1).toString(), jsonArray.get(2).toString(), jsonArray.get(7).toString(), jsonArray.get(8).toString(), jsonArray.get(9).toString()));
                    }
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            scorecardAdapter.notifyDataSetChanged();
//            OkHttpClient client = new OkHttpClient();
//            Request request;
//            if(flag == 0) {
//                request = new Request.Builder()
//                        .url("https://livescore6.p.rapidapi.com/matches/v2/list-by-date?Category=cricket&Date=" + date)
//                        .get()
//                        .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
//                        .addHeader("x-rapidapi-key", "ea081c1a92msh0a28796a705c58cp18734ejsnb39ae96537b4")
//                        .build();
//            }
//            else{
////                fScorecardArrayList.clear();
////                fScorecardAdapter.notifyDataSetChanged();
//                request = new Request.Builder()
//                        .url("https://livescore6.p.rapidapi.com/matches/v2/list-live?Category=cricket")
//                        .get()
//                        .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
//                        .addHeader("x-rapidapi-key", "ea081c1a92msh0a28796a705c58cp18734ejsnb39ae96537b4")
//                        .build();
//            }
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String resp = response.body().string();
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String title = "", score1 = "", score2 = "", team1 = "", team2 = "", status = "";
//
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(resp);
//                                JSONArray jsonArray = jsonObject.getJSONArray("Stages");
////                                int i = 0;
//                                if(jsonArray.length() == 0)
//                                {
//                                    Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_LONG).show();
//                                    fScorecardArrayList.clear();
//                                    fScorecardAdapter.notifyDataSetChanged();
//                                    emptyTv.setVisibility(View.VISIBLE);
//                                    progressBar.setVisibility(View.GONE);
//                                    return;
//                                }
//                                else
//                                {
//                                    Toast.makeText(getApplicationContext(), "Non Empty", Toast.LENGTH_LONG).show();
//                                    emptyTv.setVisibility(View.GONE);
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                                for (int i = 0; i < 1; i++) {
//                                    String jsonArrayString = jsonArray.get(i).toString();
//                                    JSONObject innerJson = new JSONObject(jsonArrayString);
//                                    title = (String) innerJson.get("Snm") + " : " + (String) innerJson.get("Cnm");
//                                    JSONArray eves = (JSONArray) innerJson.get("Events");
//                                    int n = eves.length();
//                                    for (int j = 0; j < n; j++) {
//                                        String jsonArrayString1 = eves.get(j).toString();
//                                        JSONObject innerJson1 = new JSONObject(jsonArrayString1);
//                                        score1 = "Yet to bat";
//                                        score2 = "Yet to bat";
//                                        if(innerJson1.has("Tr1C1"))
//                                            score1 = (String) innerJson1.get("Tr1C1").toString() + "/" + (String) innerJson1.get("Tr1CW1").toString() + "(" + (String) innerJson1.get("Tr1CO1").toString() + ")";
//                                        String subtitle = (String) innerJson1.get("EtTx") + "(" + (String) innerJson1.get("ErnInf") + ")";
//                                        if(innerJson1.has("Tr2C1"))
//                                            score2 = innerJson1.get("Tr2C1").toString() + "/" + innerJson1.get("Tr2CW1").toString() + "(" + innerJson1.get("Tr2CO1").toString() + ")";
//                                        if(score1.equalsIgnoreCase("Yet to bat") && score1.equalsIgnoreCase(score2))
//                                        {
//                                            score1 = "";
//                                            score2 = "";
//                                        }
//
//                                        String time = (String) innerJson1.get("Esd").toString();
//                                        time = time.substring(8);
//                                        int h = Integer.parseInt(time.substring(0, 2));
//                                        String y;
//                                        h--;
//                                        if(time.charAt(2) == '0') {
//                                            h--;
//                                            y = "30";
//                                        }
//                                        else
//                                        {
//                                            y = "00";
//                                        }
//                                        String z = Integer.toString(h) + ":" + y;
//                                        status = z + " - " + (String) innerJson1.get("EpsL");
//                                        JSONArray arrayT1 = (JSONArray) innerJson1.get("T1");
//                                        JSONObject x = ((JSONObject)arrayT1.get(0));
//                                        team1 = (String) x.get("Nm");
//                                        JSONArray arrayT2 = (JSONArray) innerJson1.get("T2");
//                                        x = ((JSONObject)arrayT2.get(0));
//                                        team2 = (String) x.get("Nm");
//                                        scorecardArrayList.add(new Scorecard(title, team1, team2, score1, score2, subtitle, status, (String) innerJson1.get("ECo")));
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            scorecardAdapter.notifyDataSetChanged();
////                                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//            });
            progressBar.setVisibility(View.GONE);
        }
        else if(sport.equalsIgnoreCase("Football"))
        {
            progressBar.setVisibility(View.VISIBLE);
            fScorecardArrayList = new ArrayList<>();
            fScorecardAdapter = new FScorecardAdapter(fScorecardArrayList, this);
            scorecardRv.setAdapter(fScorecardAdapter);
            fScorecardArrayList.clear();
            if(!Python.isStarted())
            {
                Python.start(new AndroidPlatform(this));
            }
            Python py = Python.getInstance();
            final PyObject pyobj = py.getModule("fbMatches");
            PyObject pyObject = pyobj.callAttr("getFBMatches");
//            Toast.makeText(getApplicationContext(), pyObject.toString(), Toast.LENGTH_LONG).show();
//            fScorecardArrayList.add(new FScorecard("title", "team1", "team2", "score1", "pyObject.toString()."));
//            fScorecardAdapter.notifyDataSetChanged();
            String title, score1, score2, team1, team2;
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(pyObject.toString());
//                                JSONArray jsonArray = jsonObject.getJSONArray("events");
                Iterator<String> it = jsonObject.keys();
                if(!it.hasNext())
                {
                    fScorecardArrayList.clear();
                    fScorecardAdapter.notifyDataSetChanged();
                    emptyTv.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                else {
                    emptyTv.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
                }
                if(flag == 1)
                {
//                    fScorecardArrayList.clear();
//                    fScorecardAdapter.notifyDataSetChanged();
                    for (; it.hasNext(); ) {
                        String key = it.next();
                        JSONArray jsonArray = jsonObject.getJSONArray(key);
                        if(jsonArray.get(5).toString().charAt(8) == 'N' || jsonArray.get(5).toString().charAt(8) == 'E')
                            continue;
                        fScorecardArrayList.add(new FScorecard("Football", (String) jsonArray.get(0), (String) jsonArray.get(1), (String) jsonArray.get(2), jsonArray.get(3).toString(), jsonArray.get(4).toString(), jsonArray.get(5).toString(), jsonArray.get(6).toString()));
                    }
                }
                else {
                    for (; it.hasNext(); ) {
                        String key = it.next();
                        JSONArray jsonArray = jsonObject.getJSONArray(key);
                        for(int i = 0; i < jsonArray.length(); i++)
                            System.out.println(jsonArray.get(i).toString());
                        System.out.println("\n");
//                    Toast.makeText(getApplicationContext(), (String) jsonArray.get(3).toString(), Toast.LENGTH_LONG).show();
                        fScorecardArrayList.add(new FScorecard("Football", (String) jsonArray.get(0), (String) jsonArray.get(1), (String) jsonArray.get(2), jsonArray.get(3).toString(), jsonArray.get(4).toString(), jsonArray.get(5).toString(), jsonArray.get(6).toString()));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            fScorecardAdapter.notifyDataSetChanged();
//            fScorecardArrayList.add(new FScorecard("title", "team1", "team2", "score1", "pyObject.toString()."));
//            Toast.makeText(getApplicationContext(), pyObject.toString(), Toast.LENGTH_LONG).show();
//            OkHttpClient client = new OkHttpClient();
//            Request request;
//            if(flag == 0) {
//                request = new Request.Builder()
//                        .url("https://livescore6.p.rapidapi.com/matches/v2/list-by-date?Category=soccer&Date=" + date)
//                        .get()
//                        .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
//                        .addHeader("x-rapidapi-key", "ea081c1a92msh0a28796a705c58cp18734ejsnb39ae96537b4")
//                        .build();
//            }
//            else{
//                request = new Request.Builder()
//                        .url("https://livescore6.p.rapidapi.com/matches/v2/list-live?Category=soccer")
//                        .get()
//                        .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
//                        .addHeader("x-rapidapi-key", "ea081c1a92msh0a28796a705c58cp18734ejsnb39ae96537b4")
//                        .build();
//            }
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String resp = response.body().string();
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String title, score1, score2, team1, team2;
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(resp);
//                                JSONArray jsonArray = jsonObject.getJSONArray("Stages");
//                                if(jsonArray.length() == 0)
//                                {
//                                    emptyTv.setVisibility(View.VISIBLE);
//                                    progressBar.setVisibility(View.GONE);
//                                    return;
//                                }
//                                else
//                                {
//                                    emptyTv.setVisibility(View.GONE);
//                                    progressBar.setVisibility(View.GONE);
//                                }
////                                int i = 0;
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    String jsonArrayString = jsonArray.get(i).toString();
//                                    JSONObject innerJson = new JSONObject(jsonArrayString);
//                                    title = (String) innerJson.get("Snm") + " : " + (String) innerJson.get("Cnm");
//                                    JSONArray eves = (JSONArray) innerJson.get("Events");
////                                    int j = 0;
//                                    for (int j = 0; j < eves.length(); j++) {
//                                        String jsonArrayString1 = eves.get(j).toString();
//                                        JSONObject innerJson1 = new JSONObject(jsonArrayString1);
//                                        score1 = (String) innerJson1.get("Tr1");
//                                        score2 = (String) innerJson1.get("Tr2");
//                                        JSONArray arrayT1 = (JSONArray) innerJson1.get("T1");
//                                        JSONObject x = ((JSONObject)arrayT1.get(0));
//                                        team1 = (String) x.get("Nm");
//                                        JSONArray arrayT2 = (JSONArray) innerJson1.get("T2");
//                                        x = ((JSONObject)arrayT2.get(0));
//                                        team2 = (String) x.get("Nm");
//                                        fScorecardArrayList.add(new FScorecard(title, team1, team2, score1, score2));
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            fScorecardAdapter.notifyDataSetChanged();
////                                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//            });
            progressBar.setVisibility(View.GONE);
        }
        else if(sport.equalsIgnoreCase("Tennis"))
        {
            progressBar.setVisibility(View.VISIBLE);
            fScorecardArrayList = new ArrayList<>();
            fScorecardAdapter = new FScorecardAdapter(fScorecardArrayList, this);
            scorecardRv.setAdapter(fScorecardAdapter);
            fScorecardArrayList.clear();
            emptyTv.setVisibility(View.VISIBLE);
//            fScorecardArrayList.add(new FScorecard("INDIA v ENGLAND - T20(LIVE)", "India", "England", "97/1(12.1)", "Yet to bat", "Time"));
            fScorecardAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
        else if(sport.equalsIgnoreCase("Hockey"))
        {
            progressBar.setVisibility(View.VISIBLE);
            fScorecardArrayList = new ArrayList<>();
            fScorecardAdapter = new FScorecardAdapter(fScorecardArrayList, this);
            scorecardRv.setAdapter(fScorecardAdapter);
            fScorecardArrayList.clear();
            if(!Python.isStarted())
            {
                Python.start(new AndroidPlatform(this));
            }
            Python py = Python.getInstance();
            final PyObject pyobj = py.getModule("hyMatches");
            PyObject pyObject = pyobj.callAttr("getHYMatches");
            String title, score1, score2, team1, team2;
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(pyObject.toString());
//                                JSONArray jsonArray = jsonObject.getJSONArray("events");
                Iterator<String> it = jsonObject.keys();
                if(!it.hasNext())
                {
                    fScorecardArrayList.clear();
                    fScorecardAdapter.notifyDataSetChanged();
                    emptyTv.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                else {
                    emptyTv.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
//
                for (; it.hasNext(); ) {
                    String key = it.next();
                    JSONArray jsonArray = jsonObject.getJSONArray(key);
                    JSONArray h = (JSONArray) jsonArray.get(7);
                    List<String > h1 = new ArrayList<>();
                    for(int i=0; i< h.length(); i++){
                        h1.add(h.getString(i));
                    }
                    JSONArray a = (JSONArray) jsonArray.get(8);
                    List<String > a1 = new ArrayList<>();
                    for(int i=0; i < a.length(); i++){
                        a1.add(a.getString(i));
                    }
//                    Toast.makeText(getApplicationContext(), (String) jsonArray.get(3).toString(), Toast.LENGTH_LONG).show();
                    fScorecardArrayList.add(new FScorecard("Hockey", (String) jsonArray.get(0), (String) jsonArray.get(1), (String) jsonArray.get(2), jsonArray.get(3).toString(), jsonArray.get(4).toString(), jsonArray.get(5).toString(), "Winner: " + (jsonArray.getString(6).toString().isEmpty() ? "-----" : jsonArray.getString(6).toString()), h1, a1));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            fScorecardAdapter.notifyDataSetChanged();
//            OkHttpClient client = new OkHttpClient();
//            Request request;
//            if(flag == 0) {
//                request = new Request.Builder()
//                        .url("https://livescore6.p.rapidapi.com/matches/v2/list-by-date?Category=hockey&Date=" + date)
//                        .get()
//                        .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
//                        .addHeader("x-rapidapi-key", "ea081c1a92msh0a28796a705c58cp18734ejsnb39ae96537b4")
//                        .build();
//            }
//            else{
//                request = new Request.Builder()
//                        .url("https://livescore6.p.rapidapi.com/matches/v2/list-live?Category=hockey")
//                        .get()
//                        .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
//                        .addHeader("x-rapidapi-key", "ea081c1a92msh0a28796a705c58cp18734ejsnb39ae96537b4")
//                        .build();
//            }
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String resp = response.body().string();
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String title, score1, score2, team1, team2;
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(resp);
//                                JSONArray jsonArray = jsonObject.getJSONArray("Stages");
//                                if(jsonArray.length() == 0)
//                                {
//                                    emptyTv.setVisibility(View.VISIBLE);
//                                    progressBar.setVisibility(View.GONE);
//                                    return;
//                                }
//                                else
//                                {
//                                    emptyTv.setVisibility(View.GONE);
//                                    progressBar.setVisibility(View.GONE);
//                                }
////                                int i = 0;
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    String jsonArrayString = jsonArray.get(i).toString();
//                                    JSONObject innerJson = new JSONObject(jsonArrayString);
//                                    title = (String) innerJson.get("Snm") + " : " + (String) innerJson.get("Cnm");
//                                    JSONArray eves = (JSONArray) innerJson.get("Events");
////                                    int j = 0;
//                                    for (int j = 0; j < eves.length(); j++) {
//                                        String jsonArrayString1 = eves.get(j).toString();
//                                        JSONObject innerJson1 = new JSONObject(jsonArrayString1);
//                                        score1 = (String) innerJson1.get("Tr1");
//                                        score2 = (String) innerJson1.get("Tr2");
//                                        JSONArray arrayT1 = (JSONArray) innerJson1.get("T1");
//                                        JSONObject x = ((JSONObject)arrayT1.get(0));
//                                        team1 = (String) x.get("Nm");
//                                        JSONArray arrayT2 = (JSONArray) innerJson1.get("T2");
//                                        x = ((JSONObject)arrayT2.get(0));
//                                        team2 = (String) x.get("Nm");
//                                        fScorecardArrayList.add(new FScorecard(title, team1, team2, score1, score2));
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            fScorecardAdapter.notifyDataSetChanged();
////                                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//            });
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            fScorecardArrayList = new ArrayList<>();
            fScorecardAdapter = new FScorecardAdapter(fScorecardArrayList, this);
            scorecardRv.setAdapter(fScorecardAdapter);
            fScorecardArrayList.clear();
            OkHttpClient client = new OkHttpClient();

            Request request;
            if(flag == 0) {
                request = new Request.Builder()
                        .url("https://livescore6.p.rapidapi.com/matches/v2/list-by-date?Category=basketball&Date=" + date)
                        .get()
                        .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "ea081c1a92msh0a28796a705c58cp18734ejsnb39ae96537b4")
                        .build();
            }
            else{
                fScorecardArrayList.clear();
                fScorecardAdapter.notifyDataSetChanged();
                request = new Request.Builder()
                        .url("https://livescore6.p.rapidapi.com/matches/v2/list-live?Category=basketball")
                        .get()
                        .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "ea081c1a92msh0a28796a705c58cp18734ejsnb39ae96537b4")
                        .build();
            }

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resp = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String title, score1, score2, team1, team2;
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(resp);
                                JSONArray jsonArray = jsonObject.getJSONArray("Stages");
                                if(jsonArray.length() == 0)
                                {
                                    fScorecardArrayList.clear();
                                    fScorecardAdapter.notifyDataSetChanged();
                                    emptyTv.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    return;
                                }
                                else
                                {
                                    emptyTv.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                }
//                                int i = 0;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String jsonArrayString = jsonArray.get(i).toString();
                                    JSONObject innerJson = new JSONObject(jsonArrayString);
                                    title = (String) innerJson.get("Snm") + " : " + (String) innerJson.get("Cnm");
                                    JSONArray eves = (JSONArray) innerJson.get("Events");
//                                    int j = 0;
                                    for (int j = 0; j < eves.length(); j++) {
                                        String jsonArrayString1 = eves.get(j).toString();
                                        JSONObject innerJson1 = new JSONObject(jsonArrayString1);
                                        score1 = (String) innerJson1.get("Tr1");
                                        score2 = (String) innerJson1.get("Tr2");
                                        JSONArray arrayT1 = (JSONArray) innerJson1.get("T1");
                                        JSONObject x = ((JSONObject)arrayT1.get(0));
                                        team1 = (String) x.get("Nm");
                                        JSONArray arrayT2 = (JSONArray) innerJson1.get("T2");
                                        x = ((JSONObject)arrayT2.get(0));
                                        team2 = (String) x.get("Nm");
                                        fScorecardArrayList.add(new FScorecard("Basketball", title, team1, team2, score1, score2, "Time"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            fScorecardAdapter.notifyDataSetChanged();
//                                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
//            fScorecardArrayList.add(new FScorecard("INDIA v ENGLAND - T20(LIVE)", "India", "England", "97/1(12.1)", "Yet to bat"));
//            fScorecardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCatClick(int position) {
        progressBar.setVisibility(View.VISIBLE);
//        Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
        todayButton.setBackgroundColor(Color.parseColor("#FF8000"));
        liveButton.setBackgroundColor(Color.parseColor("#ffffff"));
        todayButton.setTextColor(Color.parseColor("#ffffff"));
        liveButton.setTextColor(Color.parseColor("#000000"));
        CatBean sport = catStrings.get(position);
        getScores(sport.getTitle(), 0);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ColorDrawable viewColor = (ColorDrawable) liveButton.getBackground();
                int lc = viewColor.getColor();
                if(lc == Color.parseColor("#FF8000"))
                    getScores(sport.getTitle(), 1);
                else
                    getScores(sport.getTitle(), 0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        liveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liveButton.setBackgroundColor(Color.parseColor("#FF8000"));
                todayButton.setBackgroundColor(Color.parseColor("#ffffff"));
                liveButton.setTextColor(Color.parseColor("#ffffff"));
                todayButton.setTextColor(Color.parseColor("#000000"));
                getScores(sport.getTitle(), 1);
            }
        });
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayButton.setBackgroundColor(Color.parseColor("#FF8000"));
                liveButton.setBackgroundColor(Color.parseColor("#ffffff"));
                todayButton.setTextColor(Color.parseColor("#ffffff"));
                liveButton.setTextColor(Color.parseColor("#000000"));
                getScores(sport.getTitle(), 0);
            }
        });
    }
}