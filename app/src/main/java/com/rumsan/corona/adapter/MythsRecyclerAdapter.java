package com.rumsan.corona.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumsan.corona.BrowserActivity;
import com.rumsan.corona.R;
import com.rumsan.corona.entity.MythsModel;
import com.rumsan.corona.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;


public class MythsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private boolean isLoadingAdded = false;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<MythsModel> mMythsItems;
    private Context context;

    public MythsRecyclerAdapter(Context context) {
        this.context = context;
        this.mMythsItems = new ArrayList<>();

    }

    public MythsRecyclerAdapter(List<MythsModel> postItems) {
        this.mMythsItems = postItems;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MythsModel myth = mMythsItems.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final ViewHolder vh = (ViewHolder) holder;
                vh.extra.setVisibility(View.GONE);
                String fD = DateUtil.parseDate(myth.getUpdatedAt());
                vh.time.setText(fD);
                vh.title.setText(myth.getMyth());
                vh.title_np.setText(myth.getMythNp());
                vh.fact.setText(myth.getReality());
                vh.fact_np.setText(myth.getRealityNp());
                vh.source.setText(myth.getSourceName());
                vh.source.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BrowserActivity.class);
                        intent.putExtra("url", myth.getSourceUrl());
                        context.startActivity(intent);
                    }
                });
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(vh.extra.getVisibility() == View.VISIBLE){
                            vh.extra.setVisibility(View.GONE);
                        } else {
                            vh.extra.setVisibility(View.VISIBLE);
                        }
                    }
                });


                break;
            case LOADING:
//                Do nothing
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mMythsItems.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_myth, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return mMythsItems == null ? 0 : mMythsItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout extra;
        TextView time, title, title_np, fact, fact_np;
        Button source;


        public ViewHolder(View view){
            super(view);

            extra = view.findViewById(R.id.extra);
            time = view.findViewById(R.id.time);
            title = view.findViewById(R.id.title);
            title_np = view.findViewById(R.id.title_np);
            fact = view.findViewById(R.id.fact);
            fact_np = view.findViewById(R.id.fact_np);
            source = view.findViewById(R.id.source);
        }
    }
    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


    public void add(MythsModel mc) {
        mMythsItems.add(mc);
        notifyItemInserted(mMythsItems.size() - 1);
    }

    public void addAll(List <MythsModel> mcList) {
        for (MythsModel mc: mcList) {
            add(mc);
        }
    }

    public void remove(MythsModel m) {
        int position = mMythsItems.indexOf(m);
        if (position > -1) {
            mMythsItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new MythsModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mMythsItems.size() - 1;
        MythsModel item = getItem(position);
        if (item != null) {
            mMythsItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public MythsModel getItem(int position) {
        return mMythsItems.get(position);
    }
}
