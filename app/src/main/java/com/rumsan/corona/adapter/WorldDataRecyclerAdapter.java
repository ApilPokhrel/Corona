package com.rumsan.corona.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumsan.corona.R;
import com.rumsan.corona.entity.WorldDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WorldDataRecyclerAdapter extends RecyclerView.Adapter<WorldDataRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<WorldDataModel> datas, backup;

    public WorldDataRecyclerAdapter(Context context, List<WorldDataModel> datas) {
        for(int i = 0; i < datas.size(); i++){
            if(datas.get(i).getCountry() != null && datas.get(i).getCountry().trim().length() < 1){
                datas.remove(i);
            }
        }
        this.context = context;
        this.datas = datas;
        this.backup = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_world_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            if (datas.get(position).getCountry().trim().equalsIgnoreCase("world")) {
                holder.country.setText("All");
                holder.flag.setImageResource(R.drawable.earth);
            } else {
                holder.country.setText(datas.get(position).getCountry());
                if(datas.get(position).getCountryCode() != null) {
                    Picasso.get().load("https://www.countryflags.io/" + datas.get(position).getCountryCode() + "/shiny/64.png").placeholder(R.drawable.progress_animation).fit().into(holder.flag);
                } else {
                    holder.flag.setImageResource(R.drawable.earth);
                }
            }

            holder.infected.setText(String.valueOf(datas.get(position).getTotalCases()));
            holder.death.setText(String.valueOf(datas.get(position).getTotalDeaths()));
            holder.cured.setText(String.valueOf(datas.get(position).getTotalRecovered()));
            holder.new_infected.setText(String.valueOf(datas.get(position).getNewCases()));
            holder.new_death.setText(String.valueOf(datas.get(position).getNewDeaths()));
            holder.present_sick.setText(String.valueOf(datas.get(position).getActiveCases()));


    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView infected, death, cured, country, new_infected, present_sick, new_death;
        ImageView flag;
        LinearLayout main;

        public ViewHolder(@NonNull View view) {
            super(view);
            flag = view.findViewById(R.id.flag);
            infected = view.findViewById(R.id.infected);
            death = view.findViewById(R.id.death);
            cured = view.findViewById(R.id.cured);
            country = view.findViewById(R.id.country);
            new_death = view.findViewById(R.id.new_death);
            new_infected = view.findViewById(R.id.new_infected);
            present_sick = view.findViewById(R.id.present_sick);
            main = view.findViewById(R.id.main);
        }
    }


    public void add(WorldDataModel mc) {
        datas.add(mc);
        notifyItemInserted(datas.size() - 1);
    }

    public void addAll(List<WorldDataModel> mcList) {
        for (WorldDataModel mc: mcList) {
            add(mc);
        }
    }

    public void remove(WorldDataModel m) {
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

    public WorldDataModel getItem(int position) {
        return datas.get(position);
    }



    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                datas = (List<WorldDataModel>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<WorldDataModel> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = backup;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            private List<WorldDataModel> getFilteredResults(String toLowerCase) {
                List<WorldDataModel> results = new ArrayList<>();

                for (WorldDataModel item : backup) {
                    if (item.getCountry().toLowerCase().contains(toLowerCase)) {
                        results.add(item);
                    }
                }
                return results;
            }
        };
    }
}