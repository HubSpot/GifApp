package com.hubspot.gifapp.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by aroldan on 2/20/15.
 */
public class GiphyApi  {
  private Context mContext;
  private static GiphyApi mInstance;
  private ImageLoader imageLoader;
  private RequestQueue requestQueue;

  private GiphyApi (Context context) {
    mContext = context;
    requestQueue = Volley.newRequestQueue(mContext);

    imageLoader = new ImageLoader(requestQueue,
            new ImageLoader.ImageCache() {
              private final LruCache<String, Bitmap>
                      cache = new LruCache<String, Bitmap>(20);

              @Override
              public Bitmap getBitmap(String url) {
                return cache.get(url);
              }

              @Override
              public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
              }
            });
  }

  public static synchronized GiphyApi getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new GiphyApi(context);
    }
    return mInstance;
  }

  public ImageLoader getImageLoader() {
    return imageLoader;
  }

  public RequestQueue getRequestQueue() {
    return requestQueue;
  }
}
