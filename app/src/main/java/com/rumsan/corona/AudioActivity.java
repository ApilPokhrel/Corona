package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    ImageView toogle;
    TextView time;
    boolean isPlaying = false;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        mediaPlayer = new MediaPlayer();


        seekBar = findViewById(R.id.seek_bar);
        seekBar.setClickable(true);
        toogle = findViewById(R.id.toogle);
        time = findViewById(R.id.time);

//        play(url);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(AudioActivity.this,"Playing", Toast.LENGTH_LONG).show();
                togglePlayPause();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                toogle.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
                Toast.makeText(AudioActivity.this,"Completed", Toast.LENGTH_LONG).show();
            }
        });

        toogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayPause();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeWithSeek(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        play(url);
    }

    private void play(String url){

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Try Again", Toast.LENGTH_LONG).show();
        }

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();


//        time.setText(String.format("%d:%d",
//                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
//                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
//        );

        seekBar.setProgress((int)((100/ finalTime) * startTime));
//        myHandler.postDelayed(UpdateSongTime,100);
    }

    private void pause(){
        mediaPlayer.pause();
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            time.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) (finalTime - startTime)),
                    TimeUnit.MILLISECONDS.toSeconds((long) (finalTime - startTime)) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) (finalTime - startTime))))
            );
            seekBar.setProgress((int)((100/ finalTime) * startTime));
            myHandler.postDelayed(this, 100);
        }
    };

    private void changeWithSeek(int progress){
        startTime = (finalTime / 100) * progress;
        mediaPlayer.seekTo((int) startTime);
    }


    private void togglePlayPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            toogle.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
        } else {
            mediaPlayer.start();
            toogle.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
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
        }    }
}
