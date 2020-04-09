package com.rumsan.corona.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumsan.corona.BrowserActivity;
import com.rumsan.corona.R;
import com.rumsan.corona.entity.NepWorldModel;

import java.util.ArrayList;
import java.util.List;

public class NepalDataRecyclerAdapter extends RecyclerView.Adapter<NepalDataRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<NepWorldModel> datas;

    public NepalDataRecyclerAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
    }

    @NonNull
    @Override
    public NepalDataRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nepal_data,parent,false);
        return new NepalDataRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NepalDataRecyclerAdapter.ViewHolder holder, final int position) {
        holder.country.setText(datas.get(position).getCountry());
        holder.death.setText(String.valueOf(datas.get(position).getDeaths()));
        holder.infected.setText(String.valueOf(datas.get(position).getTotalCases()));
        holder.miti.setText(String.valueOf(datas.get(position).getDate()));

        SpannableString src = new SpannableString(datas.get(position).getSource());
        src.setSpan(new UnderlineSpan(), 0, src.length(), 0);
        holder.source.setText(src);

        holder.source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BrowserActivity.class);
                intent.putExtra("url", datas.get(position).getSourceUrl());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView miti, death, country, infected, source;

        public ViewHolder(@NonNull View view) {
            super(view);

            miti = view.findViewById(R.id.date);
            infected = view.findViewById(R.id.infected);
            death = view.findViewById(R.id.death);
            country = view.findViewById(R.id.country);
            source = view.findViewById(R.id.source);

        }


    }




    public void add(NepWorldModel mc) {
        datas.add(mc);
        notifyItemInserted(datas.size() - 1);
    }

    public void addAll(List<NepWorldModel> mcList) {
        for (NepWorldModel mc: mcList) {
            add(mc);
        }
    }

    public void remove(NepWorldModel m) {
        int position = datas.indexOf(m);
        if (position > -1) {
            datas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public NepWorldModel getItem(int position) {
        return datas.get(position);
    }
}
