package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rumsan.corona.adapter.PodcastRecyclerAdapter;
import com.rumsan.corona.api.ApiEndpoint;
import com.rumsan.corona.api.RetrofitInstance;
import com.rumsan.corona.entity.PodcastModel;
import com.rumsan.corona.entity.PodcastP;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PodcastActivity extends AppCompatActivity implements PodcastRecyclerAdapter.OnAudioLinkChanged {

    RecyclerView rv;
    ProgressBar progressBar;
    LinearLayout error;
    ApiEndpoint apiEndpoint;
    PodcastRecyclerAdapter adapter;
    Button tryAgain;
    LinearLayoutManager linearLayoutManager;
    MediaPlayer mediaPlayer;
    ImageView btn;
    SeekBar seekBar;
    TextView totalTime, currentTime, titleAudio;
    RelativeLayout player;
    private Handler myHandler = new Handler();
    boolean isLoading = true;
    boolean isInit, isError, isReload = false;
    String url, title;
    long totalDuration, currentDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);


        rv = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress);
        error = findViewById(R.id.error);
        tryAgain = findViewById(R.id.try_again);
        player = findViewById(R.id.player);

        player.setVisibility(View.GONE);

        LinearLayout toolbar = findViewById(R.id.toolbar_layout);

        ImageView back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.bar_title);

        title.setText("Podcasts");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

        adapter = new PodcastRecyclerAdapter(this);
        Retrofit retrofit = RetrofitInstance.getInstance();
        this.apiEndpoint = retrofit.create(ApiEndpoint.class);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        btn = findViewById(R.id.toogle);
        seekBar = findViewById(R.id.seek_bar);
        totalTime = findViewById(R.id.time_total);
        currentTime = findViewById(R.id.time_current);
        titleAudio = findViewById(R.id.title_audio);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isError || isReload){
                    init(url);
                    return;
                }

                if (!isLoading) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        btn.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
                    } else {
                        mediaPlayer.start();
                        btn.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
                    }
                }
            }
        });


        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {

            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        btn.setImageResource(R.drawable.progress_animation);
                        isLoading = true;
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        btn.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
                        isLoading = false;
                        break;
                }
                return false;
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btn.setImageResource(R.drawable.ic_refresh_white_24dp);
                myHandler.removeCallbacks(UpdateSongStatus);
                mediaPlayer.release();
                isReload = true;
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(PodcastActivity.this, "Oops! Something went wrong", Toast.LENGTH_LONG).show();
                isError = true;
                mp.reset();
                return true;
            }
        });

        load();
    }


    private void load() {
        Call<PodcastP> pCall = apiEndpoint.podcast();
        pCall.enqueue(new Callback<PodcastP>() {
            @Override
            public void onResponse(Call<PodcastP> call, Response<PodcastP> response) {
                progressBar.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                List<PodcastModel> pods = response.body().getPods();
                adapter.addAll(pods);
            }

            @Override
            public void onFailure(Call<PodcastP> call, Throwable t) {
                rv.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);

                tryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rv.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        error.setVisibility(View.GONE);
                        load();
                    }
                });
            }
        });
    }

    @Override
    public void play(String url, String title) {
        titleAudio.setText(title);
        this.url = url;
        this.title = title;
        init(url);
    }


    public void init(String url) {
        try {
            btn.setImageResource(R.drawable.progress_animation);
            myHandler.removeCallbacks(UpdateSongStatus);
               if(isError || isReload){
                   mediaPlayer = new MediaPlayer();
               }
                mediaPlayer.stop();
                mediaPlayer.reset();

                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    titleAudio.setSelected(true);
                    mediaPlayer.start();
                    btn.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
                    int duration = mediaPlayer.getDuration();
                    seekBar.setMax(duration / 1000);
                    if(isError){
                        mediaPlayer.seekTo((int) currentDuration);
                    }

                    isError = false;
                    myHandler.postDelayed(UpdateSongStatus, 100);
                 }
                });

        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setVisibility(View.VISIBLE);
        isInit = true;
        isReload = false;

    }

    private void decreaseVolume() {
        AudioManager audioManager = ((AudioManager) getSystemService(Context.AUDIO_SERVICE));
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (currentVolume > 0) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume - 1, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private Runnable UpdateSongStatus = new Runnable() {
        public void run() {
                if (mediaPlayer != null) {

                     totalDuration = mediaPlayer.getDuration();
                     currentDuration = mediaPlayer.getCurrentPosition();

                    if (TimeUnit.MILLISECONDS.toHours(totalDuration) == 0) {
                        totalTime.setText(String.format("%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(totalDuration),
                                TimeUnit.MILLISECONDS.toSeconds(totalDuration) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalDuration))
                        ));


                        currentTime.setText(String.format("%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(currentDuration),
                                TimeUnit.MILLISECONDS.toSeconds(currentDuration) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentDuration))
                        ));

                    }
                    else {
                        totalTime.setText(String.format("%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(totalDuration),
                                TimeUnit.MILLISECONDS.toMinutes(totalDuration),
                                TimeUnit.MILLISECONDS.toSeconds(totalDuration) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalDuration))
                        ));


                        currentTime.setText(String.format("%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(currentDuration),
                                TimeUnit.MILLISECONDS.toMinutes(currentDuration),
                                TimeUnit.MILLISECONDS.toSeconds(currentDuration) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentDuration))
                        ));
                    }

                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                }
                myHandler.postDelayed(this, 100);
        }
    };

}
