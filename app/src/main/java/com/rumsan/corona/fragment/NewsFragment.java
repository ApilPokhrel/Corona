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

import com.rumsan.corona.adapter.NewsPagination;
import com.rumsan.corona.adapter.NewsRecyclerAdapter;
import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.NewsModel;
import com.rumsan.corona.entity.NewsP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsFragment extends Fragment {
    private static final String TAG = "MainActivity";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    NewsRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    Button tryAgain;
    RecyclerView rv;
    LinearLayout error;
    private ApiEndpoint apiEndpoint;
    int firstCall = 0;


    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    private int start = PAGE_START;
    private int limit = 10;
    int total = 0;

    OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter  = new NewsRecyclerAdapter(getContext());
        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
         rv = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        tryAgain = view.findViewById(R.id.try_again);
        error = view.findViewById(R.id.error);

        LinearLayout toolbar = view.findViewById(R.id.toolbar_layout);

        ImageView back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.bar_title);

        title.setText("News");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mListener.onBackPressNews();
            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);


        rv.addOnScrollListener(new NewsPagination(linearLayoutManager) {
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
        Call<NewsP> pCall = apiEndpoint.news(start, limit, currentPage);
        pCall.enqueue(new Callback<NewsP>() {
            @Override
            public void onResponse(Call<NewsP> call, Response<NewsP> response) {
                progressBar.setVisibility(View.GONE);
                firstCall++;
                List<NewsModel> faqs = response.body().getNews();
                TOTAL_PAGES = (int) response.body().getTotal() / limit;
                total = (int) response.body().getTotal();
                adapter.addAll(faqs);
            }

            @Override
            public void onFailure(Call<NewsP> call, Throwable t) {
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
        Call<NewsP> pCall = apiEndpoint.news(start, limit, currentPage);
        pCall.enqueue(new Callback<NewsP>() {
            @Override
            public void onResponse(Call<NewsP> call, Response<NewsP> response) {
                if(response.isSuccessful()) {
                    List<NewsModel> faqs = response.body().getNews();
                    adapter.removeLoadingFooter();
                    isLoading = false;
                    adapter.addAll(faqs);                }
            }

            @Override
            public void onFailure(Call<NewsP> call, Throwable t) {
                Toast.makeText(getContext(), "Network Problem !", Toast.LENGTH_SHORT).show();
            }
        });

        if ((currentPage + 1) * limit != total) adapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewsFragment.OnFragmentInteractionListener) {
            mListener = (NewsFragment.OnFragmentInteractionListener) context;
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
        void onBackPressNews();
    }
}
