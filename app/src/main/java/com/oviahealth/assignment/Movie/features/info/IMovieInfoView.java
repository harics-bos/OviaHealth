package com.oviahealth.assignment.Movie.features.info;

public interface IMovieInfoView {

    interface IMovieUIEventListener {

        void onSearchTextChange(String movieTextChange);

        void processSearch(String movieSearchText, String movieSearchType);

        void releaseResources();

        void onSearchTypeChange();

    }

    void setMovieSearchListener(IMovieUIEventListener movieSearchListener);

    void enableSearch(boolean show, int color);

    void showMoviePoster(String imageUrl);

    void showMovieTitle(String title);

    void displayError(String errorTitle, String errorMessage);

    void clearSearchField();



}