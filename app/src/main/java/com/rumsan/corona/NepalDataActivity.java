package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rumsan.corona.adapter.NepalDataRecyclerAdapter;
import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.NepWorldModel;
import com.rumsan.corona.entity.NepWorldP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NepalDataActivity extends AppCompatActivity {

    RecyclerView nepData;
    ProgressBar progressBar;
    LinearLayout errorLayout, dataLayout;
    ApiEndpoint apiEndpoint;
    Button tryAgain;
    NepalDataRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nepal_data);
        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);

        nepData = findViewById(R.id.nepData);
        progressBar = findViewById(R.id.progress);
        errorLayout = findViewById(R.id.error);
        dataLayout = findViewById(R.id.data);

        tryAgain = errorLayout.findViewById(R.id.try_again);

        adapter = new NepalDataRecyclerAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        nepData.setLayoutManager(linearLayoutManager);
        nepData.setAdapter(adapter);

        loadData();

    }

    public void loadData(){
        progressBar.setVisibility(View.VISIBLE);
        dataLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);

        Call<NepWorldP> call = apiEndpoint.nepWorld(0, 1000, 1);
        call.enqueue(new Callback<NepWorldP>() {
            @Override
            public void onResponse(Call<NepWorldP> call, Response<NepWorldP> response) {
                List<NepWorldModel> d = response.body().getData();
                adapter.addAll(d);
                progressBar.setVisibility(View.GONE);
                errorLayout.setVisibility(View.GONE);
                dataLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<NepWorldP> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
                dataLayout.setVisibility(View.GONE);
                tryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData();
                    }
                });
            }
        });
    }
}
