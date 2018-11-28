package com.oviahealth.assignment.Movie.framework.network;

import okhttp3.Call;

public interface IServiceCallExecutorCallback {

    void onSuccess(Call call, Object result);
    void onError(Call call, Throwable e);
}
