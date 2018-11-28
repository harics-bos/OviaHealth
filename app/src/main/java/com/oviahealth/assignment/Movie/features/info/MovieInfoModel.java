package com.oviahealth.assignment.Movie.features.info;

import com.oviahealth.assignment.Movie.framework.network.IServiceCallClient;
import com.oviahealth.assignment.Movie.framework.network.ServiceCall;
import com.oviahealth.assignment.Movie.service.IMovieSearchService;
import com.oviahealth.assignment.Movie.service.MovieSearchService;

public class MovieInfoModel implements IServiceCallClient, IMovieInfoModel {

    private IMovieSearchService _movieSearchService = null;
    private ServiceCall _movieSearchServiceCall = null;
    private IMovieInfoModel.IMovieSearchResultListener _movieSearchResultListener = null;

    public MovieInfoModel(){
        _movieSearchService = new MovieSearchService();
    }



    @Override
    public void serviceCallFinished(ServiceCall call, Object responseData) {

        if(!(_movieSearchResultListener == null)){
            if(responseData instanceof MovieDetail){
                MovieDetail movieDetail = (MovieDetail) responseData;
                _movieSearchResultListener.movieSearchSuccess(movieDetail);
            }
        }


    }

    @Override
    public void ServiceCallFailed(ServiceCall call, Throwable exception) {
        if(!(_movieSearchResultListener == null)){
            _movieSearchResultListener.movieSearchFailure(exception);
        }

    }

    @Override
    public void serviceCallCancelled(ServiceCall call) {
        // do nothing
    }

    @Override
    public void setMovieSearchListener(IMovieSearchResultListener movieSearchResultListener) {
        _movieSearchResultListener = movieSearchResultListener;
    }

    @Override
    public void movieSearchService(String movie, MovieSearchType searchBy) {

        _movieSearchServiceCall = _movieSearchService.findMovie(this, movie, searchBy);

        if(!(_movieSearchServiceCall == null))
            _movieSearchServiceCall.exec();
    }

    @Override
    public void cancelMovieSearchService() {
        if(!(_movieSearchServiceCall == null))
            _movieSearchServiceCall.exec();
    }
}
