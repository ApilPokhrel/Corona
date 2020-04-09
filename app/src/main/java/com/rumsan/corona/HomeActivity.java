package com.rumsan.corona;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.NepalDataModel;
import com.rumsan.corona.entity.WorldDataModel;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";


    LinearLayout symptoms, hospitals, liveData, news, myths, faq, explain, podcast, wError, nError;
    TextView wInfected, wDeath, wDeathPercent, wCured, nTest, nPos, nNeg, wMore, nMore;
    GridLayout worldGrid;
    CardView nepCard;
    Button wTryAgain, nTryAgain;

    private ApiEndpoint apiEndpoint;
    int firstCall = 0;
    private List<WorldDataModel> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout_2);

        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);

        worldGrid = findViewById(R.id.worldGrid);
        nepCard = findViewById(R.id.nepCard);

        wError = findViewById(R.id.w_error);
        nError = findViewById(R.id.n_error);

        wTryAgain = wError.findViewById(R.id.try_again);
        nTryAgain = nError.findViewById(R.id.try_again);

        wMore = findViewById(R.id.d_world_more);
        nMore = findViewById(R.id.d_nep_more);

        wInfected = findViewById(R.id.w_infected);
        wDeath = findViewById(R.id.w_death);
        wCured = findViewById(R.id.w_cured);
        wDeathPercent = findViewById(R.id.w_death_percent);

        nTest = findViewById(R.id.n_test);
        nPos = findViewById(R.id.pos);
        nNeg = findViewById(R.id.neg);

        symptoms = findViewById(R.id.symptoms);
        hospitals = findViewById(R.id.hospitals);
        liveData = findViewById(R.id.live_data);
        news = findViewById(R.id.news);
        myths = findViewById(R.id.myth);
        faq = findViewById(R.id.faq);
        explain = findViewById(R.id.explain);
        podcast = findViewById(R.id.podcast);

       //TODO set world Data and Nep Data
        worldGrid.setVisibility(View.VISIBLE);
        wError.setVisibility(View.GONE);

        nepCard.setVisibility(View.VISIBLE);
        nError.setVisibility(View.GONE);
        fillData();
        fillNepData();
    }


    public void fillData(){
        Call<List<WorldDataModel>> call = apiEndpoint.worldData();
        call.enqueue(new Callback<List<WorldDataModel>>() {
            @Override
            public void onResponse(Call<List<WorldDataModel>> call, Response<List<WorldDataModel>> response) {

                worldGrid.setVisibility(View.VISIBLE);
                wError.setVisibility(View.GONE);
                wMore.setVisibility(View.VISIBLE);
                datas = response.body();

                WorldDataModel wm = null;

                for(WorldDataModel m: datas){
                    if(m.getCountry().trim().length() < 1)
                    {
                        wm = m;
                    }
                }

                if(wm != null) {
                    wInfected.setText(String.valueOf(wm.getTotalCases()));
                    wDeath.setText(String.valueOf(wm.getTotalDeaths()));
                    wCured.setText(String.valueOf(wm.getTotalRecovered()));
                    double dp = (Double.valueOf(wm.getTotalDeaths()) / Double.valueOf(wm.getTotalCases())) * 100.0;
                    DecimalFormat df = new DecimalFormat("0.00");
                    wDeathPercent.setText(String.valueOf(df.format(dp)) + "%");
                    firstCall++;
                } else {
                    fillData();
                }
            }

            @Override
            public void onFailure(Call<List<WorldDataModel>> call, Throwable t) {
                if(firstCall < 1) {
                    worldGrid.setVisibility(View.GONE);
                    wError.setVisibility(View.VISIBLE);
                    wMore.setVisibility(View.GONE);
                    wTryAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            worldGrid.setVisibility(View.VISIBLE);
                            wError.setVisibility(View.GONE);
                            fillData();
                        }
                    });
                }
            }
        });
    }


    public void fillNepData(){
        Call<NepalDataModel> call = apiEndpoint.nepalData();
        call.enqueue(new Callback<NepalDataModel>() {
            @Override
            public void onResponse(Call<NepalDataModel> call, Response<NepalDataModel> response) {
                nepCard.setVisibility(View.VISIBLE);
                nError.setVisibility(View.GONE);
                nMore.setVisibility(View.VISIBLE);

                NepalDataModel nd = response.body();
                nTest.setText(String.valueOf(nd.getTestedTotal()));
                nNeg.setText(String.valueOf(nd.getTestedNegative()));
                nPos.setText(String.valueOf(nd.getTestedPositive()));
            }

            @Override
            public void onFailure(Call<NepalDataModel> call, Throwable t) {
                nepCard.setVisibility(View.GONE);
                nError.setVisibility(View.VISIBLE);
                nMore.setVisibility(View.GONE);
                nTryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nepCard.setVisibility(View.VISIBLE);
                        nError.setVisibility(View.GONE);
                        fillNepData();
                    }
                });
            }
        });
    }
}
