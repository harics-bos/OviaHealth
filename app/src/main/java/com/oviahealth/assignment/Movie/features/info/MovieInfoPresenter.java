package com.oviahealth.assignment.Movie.features.info;

import android.support.v4.content.ContextCompat;

import com.oviahealth.assignment.Movie.OviaHealthApplication;
import com.oviahealth.assignment.Movie.exception.NetworkConnectivityException;
import com.oviahealth.assignment.Movie.exception.NoResultFoundException;
import com.oviahealth.assignment.Movie.util.StringUtil;
import com.oviahealth.assignment.oviaapplication.R;

public class MovieInfoPresenter implements IMovieInfoView.IMovieUIEventListener, IMovieInfoModel.IMovieSearchResultListener {

    private IMovieInfoView _movieView = null;
    private IMovieInfoModel _movieInfoModel = null;

    private boolean _isEmptySearch = true;

    public MovieInfoPresenter(IMovieInfoView movieView, IMovieInfoModel movieInfoModel){
        _movieView = movieView;
        _movieInfoModel = movieInfoModel;

        if(!(_movieView == null))
            _movieView.setMovieSearchListener(this);

        if(!(movieInfoModel == null))
            _movieInfoModel.setMovieSearchListener(this);
    }

    @Override
    public void onSearchTextChange(String movieTextChange) {

        if(StringUtil.isEmpty(movieTextChange) && _isEmptySearch)
            return;

        if(!StringUtil.isEmpty(movieTextChange) && !_isEmptySearch)
            return;

        if(!StringUtil.isEmpty(movieTextChange) && _isEmptySearch){
            enableSearch();
        } else if(StringUtil.isEmpty(movieTextChange) && !_isEmptySearch){
            disableSearch();
        }
    }

    @Override
    public void processSearch(String movieSearchText, String movieSearchType) {
        MovieSearchType movieSearchTypeEnum = MovieSearchType.getMovieSearchParameter(movieSearchType);
        _movieInfoModel.movieSearchService(movieSearchText, movieSearchTypeEnum);
    }

    @Override
    public void releaseResources() {
        _movieInfoModel.cancelMovieSearchService();
    }

    @Override
    public void onSearchTypeChange() {
        _movieView.clearSearchField();
        disableSearch();
    }

    @Override
    public void movieSearchSuccess(MovieDetail movieData) {
        //Populate View
        if(!(movieData == null)){
            _movieView.showMoviePoster(movieData.posterImageUrl);
            _movieView.showMovieTitle(StringUtil.isEmpty(movieData.title) ? "N/A" : movieData.title);
        }
    }

    private void enableSearch(){
        _isEmptySearch = false;
        _movieView.enableSearch(true, ContextCompat.getColor(OviaHealthApplication.getContext(), R.color.colorEnabledBlue));
    }

    private void disableSearch(){
        _isEmptySearch = true;
        _movieView.enableSearch(false, ContextCompat.getColor(OviaHealthApplication.getContext(), R.color.colorDisabledBlue));
    }

    @Override
    public void movieSearchFailure(Throwable e) {
        String errorAlertTitle;
        String errorAlertMessage;
        if(e instanceof NetworkConnectivityException){
            errorAlertTitle = StringUtil.diaplayableString(R.string.network_disconnectivity_error_title);
            errorAlertMessage = StringUtil.diaplayableString(R.string.network_disconnectivity_error_message);
        }else if(e instanceof NoResultFoundException){
            errorAlertTitle = StringUtil.diaplayableString(R.string.movie_info_notfound_title);
            errorAlertMessage = StringUtil.diaplayableString(R.string.movie_info_notfound_message);
        }else{
            errorAlertTitle = StringUtil.diaplayableString(R.string.generic_service_failure_title);
            errorAlertMessage = StringUtil.diaplayableString(R.string.generic_service_failure_message);
        }

        _movieView.displayError(errorAlertTitle, errorAlertMessage);
    }
}