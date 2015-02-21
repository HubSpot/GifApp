package com.hubspot.gifapp.models;

import java.util.Date;

/**
 * Created by aroldan on 2/20/15.
 */
public class GiphyGif {
  private String type;
  private String id;
  private String bitlyGifUrl;
  private String source;

//  private Date importDatetime;
//  private Date trendingDatetime;

  private GiphyGifImages images;

  public GiphyGif() {
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public String getBitlyGifUrl() {
    return bitlyGifUrl;
  }

  public String getSource() {
    return source;
  }
//
//  public Date getImportDatetime() {
//    return importDatetime;
//  }
//
//  public Date getTrendingDatetime() {
//    return trendingDatetime;
//  }


  public GiphyGifImages getImages() {
    return images;
  }
}
