package com.rumsan.corona.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.rumsan.corona.BrowserActivity;
import com.rumsan.corona.R;
import com.rumsan.corona.entity.NewsModel;
import com.rumsan.corona.utils.DateUtil;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


private boolean isLoadingAdded = false;
private static final int ITEM = 0;
private static final int LOADING = 1;

private List<NewsModel> news;
private Context context;

public NewsRecyclerAdapter(Context context) {
        this.context = context;
        this.news = new ArrayList<>();

        }

public NewsRecyclerAdapter(List<NewsModel> postItems) {
        this.news = postItems;
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
final NewsModel n = news.get(position);

        switch (getItemViewType(position)) {
        case ITEM:
        final ViewHolder vh = (ViewHolder) holder;
        String fD = DateUtil.parseDate(n.getUpdatedAt());
        vh.time.setText(fD);
        vh.title.setText(n.getTitle());
        vh.source.setText(n.getSource());
        vh.summary.setText(n.getSummary());
            vh.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrowserActivity.class);
                    intent.putExtra("url", n.getUrl());
                    context.startActivity(intent);
                }
            });

            Picasso.get().load(n.getImageUrl()).placeholder(R.drawable.progress_animation).fit().into(vh.image);


        break;
        case LOADING:
//                Do nothing
        break;
        }
        }

@Override
public int getItemViewType(int position) {
        return (position == news.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }


@NonNull
private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_news, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
        }


@Override
public int getItemCount() {
        return news == null ? 0 : news.size();

        }

public class ViewHolder extends RecyclerView.ViewHolder{
    LinearLayout main;
    TextView time, title, summary;
    Chip source;
    ImageView image;


    public ViewHolder(View view){
        super(view);

        main = view.findViewById(R.id.main);
        time = view.findViewById(R.id.time);
        title = view.findViewById(R.id.title);
        source = view.findViewById(R.id.source);
        image = view.findViewById(R.id.image);
        summary = view.findViewById(R.id.summary);
    }
}
protected class LoadingVH extends RecyclerView.ViewHolder {

    public LoadingVH(View itemView) {
        super(itemView);
    }
}


    public void add(NewsModel mc) {
        news.add(mc);
        notifyItemInserted(news.size() - 1);
    }

    public void addAll(List<NewsModel> mcList) {
        for (NewsModel mc: mcList) {
            add(mc);
        }
    }

    public void remove(NewsModel m) {
        int position = news.indexOf(m);
        if (position > -1) {
            news.remove(position);
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
        add(new NewsModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = news.size() - 1;
        NewsModel item = getItem(position);
        if (item != null) {
            news.remove(position);
            notifyItemRemoved(position);
        }
    }

    public NewsModel getItem(int position) {
        return news.get(position);
    }
}