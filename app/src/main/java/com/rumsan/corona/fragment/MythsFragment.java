package com.rumsan.corona.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rumsan.corona.R;
import com.rumsan.corona.adapter.MythPagination;
import com.rumsan.corona.adapter.MythsRecyclerAdapter;
import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.MythsModel;
import com.rumsan.corona.entity.MythsP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MythsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "MainActivity";


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    MythsRecyclerAdapter mythsRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    Button tryAgain;
    RecyclerView rv;
    LinearLayout error;
    private ApiEndpoint apiEndpoint;


    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    private int start = PAGE_START;
    private int limit = 10;
    int firstCall = 0;



    public MythsFragment() {
        // Required empty public constructor
    }


    public static MythsFragment newInstance(String param1, String param2) {
        MythsFragment fragment = new MythsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mythsRecyclerAdapter  = new MythsRecyclerAdapter(getContext());
        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myths, container, false);
         rv = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        tryAgain = view.findViewById(R.id.try_again);
        error = view.findViewById(R.id.error);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(mythsRecyclerAdapter);




        rv.addOnScrollListener(new MythPagination(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                start = start + limit;
                loadNextPage();

            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();

        return view;

    }


    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        Call<MythsP> pCall = apiEndpoint.myths(start, limit, currentPage);
        pCall.enqueue(new Callback<MythsP>() {
            @Override
            public void onResponse(Call<MythsP> call, Response<MythsP> response) {
                progressBar.setVisibility(View.GONE);
                List<MythsModel> myths = response.body().getMyths();
                TOTAL_PAGES = (int) response.body().getTotal() / limit;
                    mythsRecyclerAdapter.addAll(myths);
                    firstCall++;
            }

            @Override
            public void onFailure(Call<MythsP> call, Throwable t) {
                if(firstCall < 1) {
                    rv.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);

                    tryAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rv.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            error.setVisibility(View.GONE);
                            loadFirstPage();
                        }
                    });
                }
            }
        });
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Call<MythsP> pCall = apiEndpoint.myths(start, limit, currentPage);
        pCall.enqueue(new Callback<MythsP>() {
            @Override
            public void onResponse(Call<MythsP> call, Response<MythsP> response) {
                if(response.isSuccessful()) {
                    List<MythsModel> myths = response.body().getMyths();
                    mythsRecyclerAdapter.removeLoadingFooter();
                    isLoading = false;
                    mythsRecyclerAdapter.addAll(myths);                }
            }

            @Override
            public void onFailure(Call<MythsP> call, Throwable t) {
                Toast.makeText(getContext(), "Network Problem !", Toast.LENGTH_SHORT).show();
            }
        });

        if ((currentPage + 1) != TOTAL_PAGES) mythsRecyclerAdapter.addLoadingFooter();
        else isLastPage = true;
    }
}
