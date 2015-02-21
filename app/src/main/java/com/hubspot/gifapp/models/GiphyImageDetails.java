package com.hubspot.gifapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aroldan on 2/20/15.
 */
public class GiphyImageDetails {
  private String url;
  private int width;
  private int height;

  @JsonProperty("mp4")
  private String mp4Url;

  public GiphyImageDetails() {
  }

  public String getUrl() {
    return url;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getMp4Url() {
    return mp4Url;
  }
}
