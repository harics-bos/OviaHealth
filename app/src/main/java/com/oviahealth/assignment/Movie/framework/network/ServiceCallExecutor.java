package com.oviahealth.assignment.Movie.framework.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.oviahealth.assignment.Movie.OviaHealthApplication;
import com.oviahealth.assignment.Movie.exception.NetworkConnectivityException;
import com.oviahealth.assignment.Movie.exception.UnSupportedContentType;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceCallExecutor {

    private Request _serviceRequest = null;
    private Call _serviceCall = null;


    private static OkHttpClient _OKHTTPCLIENT = null;

    private static final int _DEFAULT_SOCKET_READ_TIMEOUT = (60 * 1000);
    private static final int _DEFAULT_SOCKET_WRITE_TIMEOUT = (60 * 1000);
    private static final int _DEFAULT_SOCKET_CONNECT_TIMEOUT = (60 * 1000);

    public ServiceCallExecutor(Request request){
        if(!(request == null)){
            _serviceRequest = request;
            _serviceCall = _OKHTTPCLIENT.newCall(request);
        }
    }

    static {
        _OKHTTPCLIENT = okHttpBuilder().build();
    }

    private static OkHttpClient.Builder okHttpBuilder(){
        return new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(false)
                .followRedirects(true)
                .readTimeout(_DEFAULT_SOCKET_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(_DEFAULT_SOCKET_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(_DEFAULT_SOCKET_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private boolean isNetworkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) OviaHealthApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return !(networkInfo == null) && networkInfo.isAvailable();
    }

    public void exec(final ServiceCallResponseMapper responseMapper, final IServiceCallExecutorCallback responseCallback){
        _serviceCall.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                responseCallback.onError(_serviceCall, e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(!(responseCallback == null)){
                    if(response.isSuccessful()){
                        //Log.i("Response Body",response.body().string());
                        if(!(responseMapper == null) && !(response.body() == null)){
                            try{
                                MediaType mediaType = response.body().contentType();
                                String contentType = "";
                                String contentSubType = null;
                                if(!(mediaType == null)){
                                    contentSubType = mediaType.subtype();
                                    contentType = mediaType.type() + "/" + contentSubType;
                                }

                                if(contentType.equals("application/json")){
                                    String responseString = response.body().string();
                                    Log.i("Response Body", responseString);
                                    responseCallback.onSuccess(_serviceCall, responseMapper.mapResponse(responseString));

                                }else{
                                    responseCallback.onError(_serviceCall, new UnSupportedContentType("Response Content Type Not Supported"));
                                }

                            }catch(Exception ex){
                                responseCallback.onError(_serviceCall, ex);
                            }
                        }
                    }else{
                        if(!isNetworkConnected())
                            responseCallback.onError(_serviceCall, new NetworkConnectivityException("No Network Connected"));
                    }
                }
            }
        });
    }
}
