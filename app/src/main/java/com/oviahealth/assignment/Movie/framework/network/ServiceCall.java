package com.oviahealth.assignment.Movie.framework.network;

import android.os.Handler;

import com.oviahealth.assignment.Movie.OviaHealthApplication;

import java.lang.ref.WeakReference;

import okhttp3.Call;

public class ServiceCall {

    private Handler _mainHandler = new Handler(OviaHealthApplication.getContext().getMainLooper());
    private WeakReference<IServiceCallClient> _weakRefClient = null;
    private IServiceCallClient _client = null;
    private ServiceCallExecutor _serviceCallExecutor = null;
    private ServiceCallResponseMapper _responseMapper = null;
    private IServiceCallExecutorCallback _executorResponseCallback = null;

    private boolean _isCancelled = false;

    public static class ServiceCallResult{
        private Object _result = null;
        private Throwable _exception = null;

        public ServiceCallResult(Object result, Throwable exception){
            _result = result;
            _exception = exception;
        }

        public Object getResult(){
            return _result;
        }

        public Throwable getException(){
            return _exception;
        }
    }

    private ServiceCall(){
        //prevent no-arg constructor
    }

    protected ServiceCall(IServiceCallClient client, ServiceCallExecutor executor, ServiceCallResponseMapper responseMapper){
        _weakRefClient = new WeakReference<>(client);
        _serviceCallExecutor = executor;
        _responseMapper = responseMapper;
    }

    public void exec(){

        _executorResponseCallback = new IServiceCallExecutorCallback() {
            @Override
            public void onSuccess(Call call, Object result) {
                ServiceCallResult serviceCallresult = new ServiceCallResult(result, null);
                invokeCallBack(serviceCallresult);
            }

            @Override
            public void onError(Call call, Throwable e) {
                ServiceCallResult serviceCallresult = new ServiceCallResult(null, e);
                invokeCallBack(serviceCallresult);
            }
        };

        _serviceCallExecutor.exec(_responseMapper, _executorResponseCallback);
    }

    private void invokeCallBack(final ServiceCallResult serviceResult){
        _mainHandler.post(new Runnable() {
            @Override
            public void run() {
                IServiceCallClient serviceCallClient = _weakRefClient.get();
                if(!(serviceCallClient == null)){
                    Object serviceResponseData = serviceResult._result;
                    Throwable serviceException = serviceResult._exception;
                    if((serviceException == null)){
                        serviceCallClient.serviceCallFinished(ServiceCall.this, serviceResponseData);
                    }else{
                        serviceCallClient.ServiceCallFailed(ServiceCall.this, serviceException);
                    }

                }
            }
        });

    }

    public void cancel(){
        //TODO: hand it over to ServiceCallExceutor to cancel the service call
    }

}
