package com.rumsan.corona.fragment;



import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rumsan.corona.R;
import com.rumsan.corona.adapter.FaqPagination;
import com.rumsan.corona.adapter.FaqsRecyclerAdapter;
import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.FaqsModel;
import com.rumsan.corona.entity.FaqsP;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FAQsFragment extends Fragment {
    private static final String TAG = "MainActivity";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    FaqsRecyclerAdapter adapter;
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

    OnFragmentInteractionListener mListener = null;


    public FAQsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RequestPhoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FAQsFragment newInstance(String param1) {
        FAQsFragment fragment = new FAQsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter  = new FaqsRecyclerAdapter(getContext());
        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
         rv = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        tryAgain = view.findViewById(R.id.try_again);
        error = view.findViewById(R.id.error);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

        LinearLayout toolbar = view.findViewById(R.id.toolbar_layout);

        ImageView back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.bar_title);

        title.setText("FAQs");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackPressFaq();
            }
        });

        rv.addOnScrollListener(new FaqPagination(linearLayoutManager) {
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

    // TODO: Rename method, update argument and hook method into UI event


    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        Call<FaqsP> pCall = apiEndpoint.faqs(start, limit, currentPage);
        pCall.enqueue(new Callback<FaqsP>() {
            @Override
            public void onResponse(Call<FaqsP> call, Response<FaqsP> response) {
                progressBar.setVisibility(View.GONE);
                List<FaqsModel> faqs = response.body().getFaqs();
                TOTAL_PAGES = (int) response.body().getTotal() / limit;
                adapter.addAll(faqs);
                firstCall++;
            }

            @Override
            public void onFailure(Call<FaqsP> call, Throwable t) {
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
        Call<FaqsP> pCall = apiEndpoint.faqs(start, limit, currentPage);
        pCall.enqueue(new Callback<FaqsP>() {
            @Override
            public void onResponse(Call<FaqsP> call, Response<FaqsP> response) {
                if(response.isSuccessful()) {
                    List<FaqsModel> faqs = response.body().getFaqs();
                    adapter.removeLoadingFooter();
                    isLoading = false;
                    adapter.addAll(faqs);                }
            }

            @Override
            public void onFailure(Call<FaqsP> call, Throwable t) {
                Toast.makeText(getContext(), "Network Problem !", Toast.LENGTH_SHORT).show();
            }
        });

        if ((currentPage + 1) != TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FAQsFragment.OnFragmentInteractionListener) {
            mListener = (FAQsFragment.OnFragmentInteractionListener) context;
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
        void onBackPressFaq();
    }
}

