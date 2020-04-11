package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rumsan.corona.adapter.PodcastRecyclerAdapter;
import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.PodcastModel;
import com.rumsan.corona.entity.PodcastP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PodcastActivity extends AppCompatActivity {

    RecyclerView rv;
    ProgressBar progressBar;
    LinearLayout error;
    ApiEndpoint apiEndpoint;
    PodcastRecyclerAdapter adapter;
    Button tryAgain;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        rv = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress);
        error = findViewById(R.id.error);
        tryAgain = findViewById(R.id.try_again);

        LinearLayout toolbar = findViewById(R.id.toolbar_layout);

        ImageView back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.bar_title);

        title.setText("Podcasts");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

        adapter  = new PodcastRecyclerAdapter(this);
        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

        load();
    }


    private void load() {
        Call<PodcastP> pCall = apiEndpoint.podcast();
        pCall.enqueue(new Callback<PodcastP>() {
            @Override
            public void onResponse(Call<PodcastP> call, Response<PodcastP> response) {
                progressBar.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                List<PodcastModel> pods = response.body().getPods();
                adapter.addAll(pods);
            }

            @Override
            public void onFailure(Call<PodcastP> call, Throwable t) {
                    rv.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);

                    tryAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rv.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            error.setVisibility(View.GONE);
                            load();
                        }
                    });

            }
        });
    }
}
