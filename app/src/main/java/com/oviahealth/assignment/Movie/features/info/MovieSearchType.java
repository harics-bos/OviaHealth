package com.oviahealth.assignment.Movie.features.info;

import com.oviahealth.assignment.Movie.util.StringUtil;
import com.oviahealth.assignment.oviaapplication.R;

import java.util.HashMap;
import java.util.Map;

public enum MovieSearchType {


    IMDB_TITLE("i", StringUtil.diaplayableString(R.string.imdb_title_radio_button)),
    MOVIE_TITLE("t", StringUtil.diaplayableString(R.string.movie_title_radio_button));

    private String _movieSearchParameter;
    private String _movieSearchParameterMapKey;

    private static Map<String, MovieSearchType> _movieSearchParameterMap = new HashMap<>();

    MovieSearchType(String searchTypeParameter, String searchMappingKey){
        _movieSearchParameter = searchTypeParameter;
        _movieSearchParameterMapKey = searchMappingKey;
    }

    public String getMovieSearchParameter(){
        return _movieSearchParameter;
    }

    private String getMovieSearchParameterKey(){
        return _movieSearchParameterMapKey;
    }

    public static MovieSearchType getMovieSearchParameter(String searchKey){
        return _movieSearchParameterMap.get(searchKey);
    }


    static{
        for(MovieSearchType searchType : MovieSearchType.values()){
            _movieSearchParameterMap.put(searchType.getMovieSearchParameterKey(), searchType);
        }
    }


}