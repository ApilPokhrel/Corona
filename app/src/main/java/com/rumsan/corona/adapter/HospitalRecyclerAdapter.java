package com.rumsan.corona.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumsan.corona.R;
import com.rumsan.corona.entity.HospitalModel;

import java.util.ArrayList;
import java.util.List;

public class HospitalRecyclerAdapter extends  RecyclerView.Adapter<HospitalRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<HospitalModel> datas, backup;

    public HospitalRecyclerAdapter(Context context, List<HospitalModel> datas) {
        this.context = context;
        this.datas = datas;
        this.backup = datas;
    }

    public HospitalRecyclerAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
        this.backup = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int position) {

        vh.contact.setText(datas.get(position).getPhone().trim().length() > 0 ? datas.get(position).getPhone() :"N/A");
        vh.name.setText(datas.get(position).getName());
        vh.address.setText(datas.get(position).getAddress().trim().length() > 0 ? datas.get(position).getAddress() :"N/A");
        vh.contactPerson.setText(datas.get(position).getContactPerson().trim().length() > 0 ? datas.get(position).getContactPerson() :"N/A");
        vh.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", datas.get(position).getPhone(), null));
                context.startActivity(intent);
            }
        });

        vh.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+datas.get(position).getLocation().getCoordinates().get(0)+","+datas.get(position).getLocation().getCoordinates().get(1)));
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout call, direction;
        TextView name, address, contactPerson, contact;


        public ViewHolder(View view){
            super(view);
            call = view.findViewById(R.id.call_layout);
            direction = view.findViewById(R.id.direction_layout);
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            contactPerson = view.findViewById(R.id.contact_person);
            contact = view.findViewById(R.id.contact);
        }
    }


    public void add(HospitalModel mc) {
        datas.add(mc);
        backup.add(mc);
        notifyItemInserted(datas.size() - 1);
    }

    public void addAll(List<HospitalModel> mcList) {
        for (HospitalModel mc: mcList) {
            add(mc);
        }
    }

    public void remove(HospitalModel m) {
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

    public HospitalModel getItem(int position) {
        return datas.get(position);
    }



    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                datas = (List<HospitalModel>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<HospitalModel> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = backup;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            private List<HospitalModel> getFilteredResults(String toLowerCase) {
                List<HospitalModel> results = new ArrayList<>();

                for (HospitalModel item : backup) {
                    if (item.getName().toLowerCase().contains(toLowerCase)) {
                        results.add(item);
                    }
                }
                return results;
            }
        };
    }
}

