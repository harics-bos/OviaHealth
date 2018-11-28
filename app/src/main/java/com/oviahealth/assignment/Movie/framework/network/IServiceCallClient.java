package com.oviahealth.assignment.Movie.framework.network;

public interface IServiceCallClient {

    void serviceCallFinished(final ServiceCall call, Object responseData);

    void ServiceCallFailed(final ServiceCall call, Throwable exception);

    void serviceCallCancelled(final ServiceCall call);
}
