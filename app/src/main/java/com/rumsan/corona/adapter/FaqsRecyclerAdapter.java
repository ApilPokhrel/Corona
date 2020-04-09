package com.rumsan.corona.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumsan.corona.R;
import com.rumsan.corona.entity.FaqsModel;
import com.rumsan.corona.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class FaqsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private boolean isLoadingAdded = false;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<FaqsModel> faqs;
    private Context context;

    public FaqsRecyclerAdapter(Context context) {
        this.context = context;
        this.faqs = new ArrayList<>();

    }

    public FaqsRecyclerAdapter(List<FaqsModel> postItems) {
        this.faqs = postItems;
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
                viewHolder = new FaqsRecyclerAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final FaqsModel myth = faqs.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final FaqsRecyclerAdapter.ViewHolder vh = (FaqsRecyclerAdapter.ViewHolder) holder;
                vh.extra.setVisibility(View.GONE);
                String fD = DateUtil.parseDate(myth.getUpdatedAt());
                vh.time.setText(fD);
                vh.title.setText(myth.getQuestion());
                vh.title_np.setText(myth.getQuestionNp());
                vh.fact.setText(myth.getAnswer());
                vh.fact_np.setText(myth.getAnswerNp());

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
        return (position == faqs.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_faq, parent, false);
        viewHolder = new FaqsRecyclerAdapter.ViewHolder(v1);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return faqs == null ? 0 : faqs.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout extra;
        TextView time, title, title_np, fact, fact_np;


        public ViewHolder(View view){
            super(view);

            extra = view.findViewById(R.id.extra);
            time = view.findViewById(R.id.time);
            title = view.findViewById(R.id.title);
            title_np = view.findViewById(R.id.title_np);
            fact = view.findViewById(R.id.fact);
            fact_np = view.findViewById(R.id.fact_np);
        }
    }
    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


    public void add(FaqsModel fc) {
        faqs.add(fc);
        notifyItemInserted(faqs.size() - 1);
    }

    public void addAll(List <FaqsModel> mcList) {
        for (FaqsModel mc: mcList) {
            add(mc);
        }
    }

    public void remove(FaqsModel m) {
        int position = faqs.indexOf(m);
        if (position > -1) {
            faqs.remove(position);
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
        add(new FaqsModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = faqs.size() - 1;
        FaqsModel item = getItem(position);
        if (item != null) {
            faqs.remove(position);
            notifyItemRemoved(position);
        }
    }

    public FaqsModel getItem(int position) {
        return faqs.get(position);
    }
}

