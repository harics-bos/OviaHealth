package com.oviahealth.assignment.Movie.service;

import com.oviahealth.assignment.Movie.features.info.MovieSearchType;
import com.oviahealth.assignment.Movie.framework.network.IServiceCallClient;
import com.oviahealth.assignment.Movie.framework.network.ServiceCall;

public interface IMovieSearchService {

    ServiceCall findMovie(IServiceCallClient client, String movie, MovieSearchType searchParameterType);
}
