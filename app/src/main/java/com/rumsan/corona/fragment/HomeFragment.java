package com.rumsan.corona.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.rumsan.corona.BrowserActivity;
import com.rumsan.corona.DataActivity;
import com.rumsan.corona.NepalDataActivity;
import com.rumsan.corona.R;
import com.rumsan.corona.UiActivity;
import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.NepalDataModel;
import com.rumsan.corona.entity.WorldDataModel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<WorldDataModel> datas;


    Button w_m, s_m, p_m, t_m, d_m, tryAgain, nTryAgain, n_d_m;
    LinearLayout errorLayout, dataLayout, nErrorLayout, nDataLayout;
    TextView totalInfected, totalDeath, totalCured, deathPercent, nTotalTest, nNegTest, nPosTest;
    
    private ApiEndpoint apiEndpoint;
    int firstCall = 0;
   

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestPhoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        VideoView vv = view.findViewById(R.id.video);
        errorLayout = view.findViewById(R.id.error);
        dataLayout = view.findViewById(R.id.l_d_corona);
        nDataLayout = view.findViewById(R.id.l_n_corona);
        nErrorLayout = view.findViewById(R.id.n_error);
        TextView ui = view.findViewById(R.id.ui);
        ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), UiActivity.class);
                getActivity().startActivity(in);
            }
        });
        String uriPath =  "android.resource://"+  getContext().getPackageName() + "/raw/bhuntay";
        Uri uri = Uri.parse(uriPath);
        vv.setVideoURI(uri);
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        vv.start();

        w_m = view.findViewById(R.id.w_more);
        p_m = view.findViewById(R.id.p_more);
        s_m = view.findViewById(R.id.s_more);
        t_m = view.findViewById(R.id.t_more);
        d_m = view.findViewById(R.id.d_more);
        n_d_m = view.findViewById(R.id.n_more);
        tryAgain = errorLayout.findViewById(R.id.try_again);

        d_m.setVisibility(View.GONE);
        n_d_m.setVisibility(View.GONE);

        nTryAgain = nErrorLayout.findViewById(R.id.try_again);


        totalInfected = view.findViewById(R.id.total_infected);
        totalDeath = view.findViewById(R.id.total_death);
        totalCured = view.findViewById(R.id.total_cured);
        deathPercent = view.findViewById(R.id.death_percent);

        nTotalTest = view.findViewById(R.id.n_total_test);
        nNegTest = view.findViewById(R.id.n_neg_test);
        nPosTest = view.findViewById(R.id.n_test_pos);


        w_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BrowserActivity.class);
                intent.putExtra("url", "https://nepalcorona.info/introduction");
                startActivity(intent);
            }
        });

        s_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BrowserActivity.class);
                intent.putExtra("url", "https://nepalcorona.info/symptoms");
                startActivity(intent);
            }
        });

        p_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BrowserActivity.class);
                intent.putExtra("url", "https://nepalcorona.info/prevention");
                startActivity(intent);
            }
        });

        t_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BrowserActivity.class);
                intent.putExtra("url", "https://nepalcorona.info/treatment");
                startActivity(intent);
            }
        });

        d_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DataActivity.class);
                intent.putExtra("datas", (Serializable) datas);
                startActivity(intent);
            }
        });

        n_d_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NepalDataActivity.class);
                startActivity(intent);
            }
        });

        dataLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);

        nDataLayout.setVisibility(View.VISIBLE);
        nErrorLayout.setVisibility(View.GONE);
        fillData();
        fillNepData();
        return view;
    }

   public void fillData(){
       Call<List<WorldDataModel>> call = apiEndpoint.worldData();
       call.enqueue(new Callback<List<WorldDataModel>>() {
           @Override
           public void onResponse(Call<List<WorldDataModel>> call, Response<List<WorldDataModel>> response) {


               dataLayout.setVisibility(View.VISIBLE);
               errorLayout.setVisibility(View.GONE);
               d_m.setVisibility(View.VISIBLE);
               datas = response.body();

               WorldDataModel wm = null;

               for(WorldDataModel m: datas){
                   if(m.getCountry().trim().length() < 1)
                   {
                       wm = m;
                   }
               }

               if(wm != null) {
                   totalInfected.setText(String.valueOf(wm.getTotalCases()));
                   totalDeath.setText(String.valueOf(wm.getTotalDeaths()));
                   totalCured.setText(String.valueOf(wm.getTotalRecovered()));
                   double dp = (Double.valueOf(wm.getTotalDeaths()) / Double.valueOf(wm.getTotalCases())) * 100.0;
                   DecimalFormat df = new DecimalFormat("0.00");
                   deathPercent.setText(String.valueOf(df.format(dp)) + "%");
                   firstCall++;
               } else {
                   fillData();
               }
           }

           @Override
           public void onFailure(Call<List<WorldDataModel>> call, Throwable t) {
               if(firstCall < 1) {
                   dataLayout.setVisibility(View.GONE);
                   errorLayout.setVisibility(View.VISIBLE);
                   d_m.setVisibility(View.GONE);
                   tryAgain.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dataLayout.setVisibility(View.VISIBLE);
                           errorLayout.setVisibility(View.GONE);
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
                nDataLayout.setVisibility(View.VISIBLE);
                nErrorLayout.setVisibility(View.GONE);
                n_d_m.setVisibility(View.VISIBLE);

                NepalDataModel nd = response.body();
                nTotalTest.setText(String.valueOf(nd.getTestedTotal()));
                nNegTest.setText(String.valueOf(nd.getTestedNegative()));
                nPosTest.setText(String.valueOf(nd.getTestedPositive()));
            }

            @Override
            public void onFailure(Call<NepalDataModel> call, Throwable t) {
                nDataLayout.setVisibility(View.GONE);
                nErrorLayout.setVisibility(View.VISIBLE);
                n_d_m.setVisibility(View.GONE);
                nTryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nDataLayout.setVisibility(View.VISIBLE);
                        nErrorLayout.setVisibility(View.GONE);
                        fillNepData();
                    }
                });
            }
        });
   }


}