package com.oviahealth.assignment.Movie.service;

import com.oviahealth.assignment.Movie.config.EndPoints;
import com.oviahealth.assignment.Movie.config.Keys;
import com.oviahealth.assignment.Movie.features.info.MovieResponseMapper;
import com.oviahealth.assignment.Movie.features.info.MovieSearchType;
import com.oviahealth.assignment.Movie.framework.network.IServiceCallClient;
import com.oviahealth.assignment.Movie.framework.network.ServiceCall;
import com.oviahealth.assignment.Movie.framework.network.ServiceCallExecutor;
import com.oviahealth.assignment.Movie.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class MovieSearchService implements IMovieSearchService {

    public ServiceCall findMovie(IServiceCallClient client, String movie, MovieSearchType searchParameterType){

        MovieSearchServiceCall _serviceCall = null;

        Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("apikey", Keys.OMDBAPI_KEY);
        queryParamMap.put(searchParameterType.getMovieSearchParameter(), movie);

        //String movieUrlString = StringUtil.buildUrl("http", "www.omdbapi.com", "", queryParamMap);
        String movieUrlString = StringUtil.buildUrl(EndPoints.OMDBAPI_URL, "", queryParamMap);

        if(!StringUtil.isEmpty(movieUrlString)){

            Request movieSearchRequest = new Request.Builder()
                    .url(movieUrlString)
                    .get()
                    .build();

            _serviceCall = new MovieSearchServiceCall(client, new ServiceCallExecutor(movieSearchRequest), new MovieResponseMapper());

        }


        return _serviceCall;
    }
}