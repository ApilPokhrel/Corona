package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ImageView btn;
    SeekBar seekBar;
    TextView totalTime, currentTime;
    private Handler myHandler = new Handler();
    boolean isLoading = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        mediaPlayer = new MediaPlayer();
        btn = findViewById(R.id.toogle);
        seekBar = findViewById(R.id.seek_bar);
        totalTime = findViewById(R.id.time_total);
        currentTime = findViewById(R.id.time_current);

        btn.setImageResource(R.drawable.progress_animation);

        init(url);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLoading) {
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
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                }
            }
        });
    }


    public void init(String url){
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        try {
                            mediaPlayer.setDataSource(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    } else {
                        mediaPlayer.start();
                    }
                    btn.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
                    int duration = mediaPlayer.getDuration();
                    seekBar.setMax(duration/1000);
                    myHandler.postDelayed(UpdateSongStatus,100);

                }
            });
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    btn.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
                    mediaPlayer.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            if(mediaPlayer != null) {

                long totalDuration = mediaPlayer.getDuration();
                long currentDuration = mediaPlayer.getCurrentPosition();

                if(TimeUnit.MILLISECONDS.toHours(totalDuration) == 0)
                    {
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
                else
                     {
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
