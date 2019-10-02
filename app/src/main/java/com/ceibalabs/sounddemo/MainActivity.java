package com.ceibalabs.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar volumeControl;
    AudioManager audioManager; //for getting info about the volume in the device.

    SeekBar audioProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mediaPlayer = MediaPlayer.create(this, R.raw.birds);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //this allow us to work with audio
        //set max volume
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //parameter is the music volume
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        this.volumeControl = (SeekBar) findViewById(R.id.seekBar);
        this.audioProgress = (SeekBar) findViewById(R.id.audioProgress);

        //set max volume on seekbar
        this.volumeControl.setMax(maxVolume); //set max volume
        this.volumeControl.setProgress(currentVolume); //set current volume

        this.audioProgress.setMax(mediaPlayer.getDuration()); //max value for audio progress seekbar

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioProgress.setProgress(mediaPlayer.getCurrentPosition()); //update the value of the progress bar audio
            }
        }, 0, 100); //period miliseconds between job done, this means every 10th of second


        this.volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUSer){
                //Log.i("Seekbar value", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0); //alter the volume based on value of progress bar selected
            }

        });

        this.audioProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Log.i("AudioProgress value", Integer.toString(progress));
                mediaPlayer.seekTo(progress); //adjust the audio to the value selected in seekbar
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //stop audio here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //start audio here
            }
        });
    }

    public void playAudio(View view){
        this.mediaPlayer.start();
    }

    public void pauseAudio(View view){
        this.mediaPlayer.pause();
    }

    public void stopAudio(View view){
        this.mediaPlayer.stop();
    }
}
