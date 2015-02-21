package com.hubspot.gifapp.models;

import java.util.List;

/**
 * Created by aroldan on 2/20/15.
 */
public class GiphyApiResponse {
  private List<GiphyGif> data;
  private PaginationInfo pagination;

  public GiphyApiResponse() {
  }

  public List<GiphyGif> getData() {
    return data;
  }

  public PaginationInfo getPagination() {
    return pagination;
  }
}
