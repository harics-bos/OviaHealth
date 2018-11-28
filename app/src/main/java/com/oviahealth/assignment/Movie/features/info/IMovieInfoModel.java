package com.oviahealth.assignment.Movie.features.info;

public interface IMovieInfoModel {

    interface IMovieSearchResultListener{

        void movieSearchSuccess(MovieDetail moviedata);

        void movieSearchFailure(Throwable e);
    }

    void setMovieSearchListener(IMovieSearchResultListener movieSearchResultListener);

    void movieSearchService(String movie, MovieSearchType searchBy);

    void cancelMovieSearchService();
}
