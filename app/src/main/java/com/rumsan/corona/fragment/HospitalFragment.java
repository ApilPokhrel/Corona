package com.rumsan.corona.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rumsan.corona.R;
import com.rumsan.corona.adapter.HospitalRecyclerAdapter;
import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.HospitalModel;
import com.rumsan.corona.entity.HospitalP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HospitalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "HospitalFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;

    HospitalRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    Button tryAgain;
    RecyclerView rv;
    LinearLayout error;
    EditText search;
    ImageView back;
    private ApiEndpoint apiEndpoint;


    public HospitalFragment() {
        // Required empty public constructor
    }

    OnFragmentInteractionListener mListener = null;

    // TODO: Rename and change types and number of parameters
    public static HospitalFragment newInstance(String param1) {
        HospitalFragment fragment = new HospitalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
//        adapter = new HospitalRecyclerAdapter(getContext());
        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hospital, container, false);
        rv = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        tryAgain = view.findViewById(R.id.try_again);
        error = view.findViewById(R.id.error);
        back = view.findViewById(R.id.back);
        search = view.findViewById(R.id.search_input);

//        adapter = new HospitalRecyclerAdapter(getContext());
//        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        rv.setLayoutManager(linearLayoutManager);
//        rv.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackPressHospital();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(adapter != null && adapter.getItemCount() > 0) adapter.getFilter().filter(s);
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        load();

        return view;
    }


    private void load() {
        Log.d(TAG, "loading: ");
        Call<HospitalP> pCall = apiEndpoint.hospitals(0, 0, 0, null, null);
        pCall.enqueue(new Callback<HospitalP>() {
            @Override
            public void onResponse(Call<HospitalP> call, Response<HospitalP> response) {
                List<HospitalModel> hospitals = response.body().getHospitals();

                adapter = new HospitalRecyclerAdapter(getContext(), hospitals);
                linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                rv.setLayoutManager(linearLayoutManager);
                rv.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<HospitalP> call, Throwable t) {
                    rv.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);

                    tryAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rv.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            error.setVisibility(View.GONE);
                            load();
                        }
                    });
                }
        });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HospitalFragment.OnFragmentInteractionListener) {
            mListener = (HospitalFragment.OnFragmentInteractionListener) context;
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
        void onBackPressHospital();
    }
}
