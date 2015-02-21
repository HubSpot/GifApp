package com.hubspot.gifapp.api;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;

/**
 * Created by aroldan on 2/20/15.
 */
public class JacksonApiRequest<T> extends Request<T> {

  public static ObjectMapper mapper;
  protected Class<T> mClass;
  protected Response.Listener<T> mListener;

  static {
    mapper = new ObjectMapper();

    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

    // support snake case names from giphy api
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
  }

  public JacksonApiRequest(Class<T> type, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
    super(Method.GET, url, errorListener);
    mClass = type;
    mListener = listener;
  }

  @Override
  protected void deliverResponse(T response) {
    mListener.onResponse(response);
  }

  @Override
  protected Response<T> parseNetworkResponse(NetworkResponse response) {
    try {
      T result;

      result = mapper.readValue(response.data, mClass);

      return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    } catch (JsonParseException e) {
      return Response.error(new ParseError(e));
    } catch (JsonMappingException e) {
      return Response.error(new ParseError(e));
    } catch (IOException e) {
      return Response.error(new ParseError(e));
    }
  }
}