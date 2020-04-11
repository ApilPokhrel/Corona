package com.rumsan.corona.fragment;


import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.rumsan.corona.BrowserActivity;
import com.rumsan.corona.CreditActivity;
import com.rumsan.corona.DataActivity;
import com.rumsan.corona.R;
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
    private HomeFragment.OnFragmentInteractionListener mListener;

    private List<WorldDataModel> datas;


    LinearLayout symptoms, hospitals, liveData, news, myths, faq, explain, podcast, wError, nError;
    TextView wInfected, wDeath, wDeathPercent, wCured, nTest, nPos, nNeg, wMore, nMore, wTryAgain, nTryAgain;
    GridLayout worldGrid;
    CardView nepCard;

    private ApiEndpoint apiEndpoint;
    int firstCall = 0;
   

    public HomeFragment() {
    }


    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        View view = inflater.inflate(R.layout.home_layout_2, container, false);




        ImageView optionMenu = view.findViewById(R.id.popup_menu);
        optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), optionMenu);
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id){
                            case R.id.emergency:
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","1115", null));
                                startActivity(intent);
                                return true;
                            case R.id.credit:
                                Intent intent1 = new Intent(getContext(), CreditActivity.class);
                                startActivity(intent1);
                                return true;
                            default:
                                return true;
                        }
                    }
                });

                popup.show();
            }
        });

        worldGrid = view.findViewById(R.id.worldGrid);
        nepCard = view.findViewById(R.id.nepCard);

        wError = view.findViewById(R.id.w_error);
        nError = view.findViewById(R.id.n_error);

        wTryAgain = wError.findViewById(R.id.try_again);
        nTryAgain = nError.findViewById(R.id.try_again);

        wMore = view.findViewById(R.id.d_world_more);
        nMore = view.findViewById(R.id.d_nep_more);
        wMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DataActivity.class);
                intent.putExtra("datas", (Serializable) datas);
                startActivity(intent);
            }
        });

        nMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BrowserActivity.class);
                intent.putExtra("url", "https://nepalcorona.info/live");
                startActivity(intent);
            }
        });

        wInfected = view.findViewById(R.id.w_infected);
        wDeath = view.findViewById(R.id.w_death);
        wCured = view.findViewById(R.id.w_cured);
        wDeathPercent = view.findViewById(R.id.w_death_percent);

        nTest = view.findViewById(R.id.n_test);
        nPos = view.findViewById(R.id.pos);
        nNeg = view.findViewById(R.id.neg);

        symptoms = view.findViewById(R.id.symptoms);
        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoadSymptoms();
            }
        });
        hospitals = view.findViewById(R.id.hospitals);
        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoadHospitals();
            }
        });
        liveData = view.findViewById(R.id.live_data);
        liveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoadLiveData(datas);
            }
        });
        news = view.findViewById(R.id.news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoadNews();
            }
        });
        myths = view.findViewById(R.id.myth);
        myths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoadMyths();
            }
        });
        faq = view.findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoadFaq();
            }
        });
        explain = view.findViewById(R.id.explain);
        explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoadExplain();
            }
        });
        podcast = view.findViewById(R.id.podcast);
        podcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoadPodcast();
            }
        });




        //TODO set world Data and Nep Data
        worldGrid.setVisibility(View.VISIBLE);
        wError.setVisibility(View.GONE);
        wMore.setVisibility(View.GONE);

        nepCard.setVisibility(View.VISIBLE);
        nError.setVisibility(View.GONE);
        nMore.setVisibility(View.GONE);
        fillData();
        fillNepData();
        return view;
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
                    if(m.getCountry().trim().equalsIgnoreCase("world"))
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnFragmentInteractionListener) {
            mListener = (HomeFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onLoadSymptoms();

        void onLoadHospitals();

        void onLoadLiveData(List<WorldDataModel> wd);

        void onLoadNews();

        void onLoadMyths();

        void onLoadFaq();

        void onLoadExplain();

        void onLoadPodcast();

    }
}