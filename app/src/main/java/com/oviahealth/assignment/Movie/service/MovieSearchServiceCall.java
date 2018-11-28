package com.oviahealth.assignment.Movie.service;

import com.oviahealth.assignment.Movie.framework.network.IServiceCallClient;
import com.oviahealth.assignment.Movie.framework.network.ServiceCall;
import com.oviahealth.assignment.Movie.framework.network.ServiceCallExecutor;
import com.oviahealth.assignment.Movie.framework.network.ServiceCallResponseMapper;

public class MovieSearchServiceCall extends ServiceCall {

    public MovieSearchServiceCall(IServiceCallClient client, ServiceCallExecutor executor, ServiceCallResponseMapper responseMapper) {
        super(client, executor, responseMapper);
    }
}
