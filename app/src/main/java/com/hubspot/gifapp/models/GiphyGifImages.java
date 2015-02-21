package com.hubspot.gifapp.models;

/**
 * Created by aroldan on 2/20/15.
 */
public class GiphyGifImages {
  GiphyImageDetails fixedHeight;
  GiphyImageDetails original;

  GiphyStillImageDetails originalStill;

  public GiphyGifImages() {
  }

  public GiphyImageDetails getFixedHeight() {
    return fixedHeight;
  }

  public GiphyImageDetails getOriginal() {
    return original;
  }

  public GiphyStillImageDetails getOriginalStill() {
    return originalStill;
  }
}
