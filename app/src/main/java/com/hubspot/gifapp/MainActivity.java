package com.hubspot.gifapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hubspot.gifapp.api.GiphyApi;
import com.hubspot.gifapp.api.JacksonApiRequest;
import com.hubspot.gifapp.models.GiphyApiResponse;
import com.hubspot.gifapp.models.GiphyGif;


public class MainActivity extends ActionBarActivity {

  private ListView mListView;
  private ArrayAdapter<GiphyGif> mGifAdapter;
  private GiphyApi apiClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    apiClient = GiphyApi.getInstance(getApplicationContext()); // pass app context to API class

    setContentView(R.layout.activity_main);

    mListView = (ListView) findViewById(R.id.main_gif_listview);
    mGifAdapter = new GifAdapter(this);
    mListView.setAdapter(mGifAdapter);

    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDetailActivity(mGifAdapter.getItem(position));
      }
    });

    makeRequest();
  }

  private void showDetailActivity(GiphyGif gif) {
    try {
      Intent intent = new Intent(this, GifDetailActivity.class);
      String serializedGif = JacksonApiRequest.mapper.writeValueAsString(gif);
      Log.i("json dump", serializedGif);
      intent.putExtra("gifdetails", serializedGif);

      startActivity(intent);
    } catch (JsonProcessingException e) {
      Log.e("error", "Could not serialize json");
    }
  }

  private void makeRequest() {
    JacksonApiRequest<GiphyApiResponse> request = new JacksonApiRequest<>(GiphyApiResponse.class, "http://api.giphy.com/v1/gifs/trending?api_key=dc6zaTOxFJmzC", new Response.Listener<GiphyApiResponse>() {
      @Override
      public void onResponse(GiphyApiResponse response) {
        Log.i("log", "here's what we got: " + response.getData());
        mGifAdapter.addAll(response.getData());
      }
    }, new Response.ErrorListener(){
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e("uh oh", error.getMessage());
      }
    });
    apiClient.getRequestQueue().add(request);
  }

  private class GifAdapter extends ArrayAdapter<GiphyGif> {
    private Context mContext;
    private GifAdapter(Context context) {
      super(context, R.layout.gif_row, R.id.gif_title);
      mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View theView;
      GifViewHolder viewHolder;
      if (convertView == null) {
        theView = View.inflate(mContext, R.layout.gif_row, null);
        viewHolder = new GifViewHolder(theView);
        theView.setTag(viewHolder);
      } else {
        theView = convertView;
        viewHolder = (GifViewHolder) theView.getTag();
      }

      GiphyGif thisGif = getItem(position);
      String url = thisGif.getImages().getFixedWidthSmallStill().getUrl();
      viewHolder.gifPreview.setImageUrl(url, apiClient.getImageLoader());
      viewHolder.gifTitle.setText(thisGif.getSource());

      return theView;
    }
  }

  private static class GifViewHolder {
    public NetworkImageView gifPreview;
    public TextView gifTitle;

    public GifViewHolder(View base) {
      gifPreview = (NetworkImageView) base.findViewById(R.id.gif_preview);
      gifTitle = (TextView) base.findViewById(R.id.gif_title);
    }
  }
}
