package com.rumsan.corona.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumsan.corona.AudioActivity;
import com.rumsan.corona.R;
import com.rumsan.corona.entity.PodcastModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PodcastRecyclerAdapter extends RecyclerView.Adapter<PodcastRecyclerAdapter.ViewHolder>{

    List<PodcastModel> datas;
    Context context;
    OnAudioLinkChanged mListener;

    public PodcastRecyclerAdapter(Context context){
        this.context = context;
        this.datas = new ArrayList<>();
        this.mListener = (OnAudioLinkChanged) context; //Activity must implement interface
    }

    @NonNull
    @Override
    public PodcastRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_podcast,parent,false);
        return new PodcastRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastRecyclerAdapter.ViewHolder holder, final int position) {
        Picasso.get().load(datas.get(position).getImageUrl()).placeholder(R.drawable.progress_animation).fit().into(holder.image);
        holder.title.setText(datas.get(position).getTitle());
        holder.detail.setText(datas.get(position).getSummary());
        holder.audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, AudioActivity.class);
//                intent.putExtra("url", datas.get(position).getAudioUrl());
//                context.startActivity(intent);
                mListener.play(datas.get(position).getAudioUrl(), datas.get(position).getTitle());
            }
        });

        holder.seekBar.setEnabled(false);
        holder.seekBar.setClickable(false);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout audio;
        ImageView image;
        TextView title, detail;
        SeekBar seekBar;
        public ViewHolder(@NonNull View view) {
            super(view);
            audio = view.findViewById(R.id.audio);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            detail = view.findViewById(R.id.detail);
            seekBar = view.findViewById(R.id.seek_bar);
        }
    }


    public void add(PodcastModel mc) {
        datas.add(mc);
        notifyItemInserted(datas.size() - 1);
    }

    public void addAll(List<PodcastModel> mcList) {
        for (PodcastModel mc: mcList) {
            add(mc);
        }
    }

    public void remove(PodcastModel m) {
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

    public PodcastModel getItem(int position) {
        return datas.get(position);
    }

    public interface OnAudioLinkChanged {
        void play(String url, String title);
    }
}

