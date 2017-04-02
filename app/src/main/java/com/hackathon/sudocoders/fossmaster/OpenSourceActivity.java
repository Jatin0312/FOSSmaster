package com.hackathon.sudocoders.fossmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hackathon.sudocoders.fossmaster.Adapter.MyreposAdapter;
import com.hackathon.sudocoders.fossmaster.Adapter.OpenSourceAdapter;
import com.hackathon.sudocoders.fossmaster.Model.MyRepo;
import com.hackathon.sudocoders.fossmaster.Model.OpenSourceModel;
import com.hackathon.sudocoders.fossmaster.Model.OpenSourceModel2;
import com.hackathon.sudocoders.fossmaster.Model.OpenSourceModel2;
import com.hackathon.sudocoders.fossmaster.Utils.ApiInterface;
import com.hackathon.sudocoders.fossmaster.Utils.SharedPref;
import com.hackathon.sudocoders.fossmaster.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenSourceActivity extends AppCompatActivity {
    private ArrayList<OpenSourceModel> users;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        SharedPref sharedPref = new SharedPref(getApplicationContext());
        progressBar = (ProgressBar) findViewById(R.id.leader_progress);


        recyclerView = (RecyclerView) findViewById(R.id.repo_recycler);
      /*  NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        toolbar = (Toolbar) findViewById(R.id.myrepo_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Open Source Activity");

        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new OpenSourceAdapter(users, getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        progressBar.setVisibility(View.VISIBLE);
        int level=1;
        if(sharedPref.getLevel()=="Beginner Level")
            level = 1;
        if(sharedPref.getLevel()=="Moderate Level")
            level = 2;
        if(sharedPref.getLevel()=="Advanced Level")
            level = 3;
        getOpenSource(sharedPref.getLanguage(),level);
        

    }
    
    void getOpenSource(String language,int l){
        ApiInterface mApi = Util.getRetrofitService();
        Call<OpenSourceModel2> mservice = mApi.getOpenSource(language,l);
        System.out.println("help me");
        mservice.enqueue(new Callback<OpenSourceModel2>() {

            @Override
            public void onResponse(Call<OpenSourceModel2> call, Response<OpenSourceModel2> response) {
                if(response!=null && response.isSuccess()) {
                    users = response.body().getUsers();

                    progressBar.setVisibility(View.GONE);
                    adapter = new OpenSourceAdapter(users, getApplicationContext());

                    recyclerView.setAdapter(adapter);

                }
                else{
                    Toast.makeText(OpenSourceActivity.this, "Some Problem is there", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OpenSourceModel2> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    
    
    
}
