package com.hubspot.gifapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.VideoView;

import com.hubspot.gifapp.api.JacksonApiRequest;
import com.hubspot.gifapp.models.GiphyGif;

import java.io.IOException;

/**
 * Created by aroldan on 2/20/15.
 */
public class GifDetailActivity extends ActionBarActivity {

  VideoView mVideoView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_detail);
    mVideoView = (VideoView) findViewById(R.id.gif_video_view);

    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
      @Override
      public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
      }
    });

    try {
      GiphyGif thisGif = JacksonApiRequest.mapper.readValue(getIntent().getStringExtra("gifdetails"), GiphyGif.class);

      String videoUrl = thisGif.getImages().getOriginal().getMp4Url();
      Uri videoURI = Uri.parse(videoUrl);
      mVideoView.setVideoURI(videoURI);
      mVideoView.start();
    } catch (IOException e) {

    }
  }
}
