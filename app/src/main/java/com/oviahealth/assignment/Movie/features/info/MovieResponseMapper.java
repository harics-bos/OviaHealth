package com.oviahealth.assignment.Movie.features.info;

import com.oviahealth.assignment.Movie.exception.NoResultFoundException;
import com.oviahealth.assignment.Movie.framework.network.ServiceCallResponseMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.oviahealth.assignment.Movie.util.JSONUtil.safeJsonArray;
import static com.oviahealth.assignment.Movie.util.JSONUtil.safeString;

public class MovieResponseMapper extends ServiceCallResponseMapper {

    public MovieDetail mapResult(Object jsonObject) throws Exception{
        MovieDetail movieDetail = new MovieDetail();

        //JSONObject rootNode = new JSONObject(response);

        JSONObject rootNode = (JSONObject) jsonObject;
        movieDetail.title = safeString(rootNode, "Title");
        movieDetail.year = safeString(rootNode, "Year");
        movieDetail.censorRating = safeString(rootNode, "Rated");
        movieDetail.releaseTime = safeString(rootNode, "Released");
        movieDetail.duration = safeString(rootNode, "Runtime");
        movieDetail.genre = safeString(rootNode, "Genre");
        movieDetail.director = safeString(rootNode, "Director");
        movieDetail.writer = safeString(rootNode, "Writer");
        movieDetail.actors = safeString(rootNode, "Actors");
        movieDetail.plot = safeString(rootNode, "Plot");
        movieDetail.language = safeString(rootNode, "Language");
        movieDetail.country = safeString(rootNode, "Country");
        movieDetail.awards = safeString(rootNode, "Awards");
        movieDetail.posterImageUrl = safeString(rootNode, "Poster");
        movieDetail.metaScore = safeString(rootNode, "Metascore");
        movieDetail.imdbRating = safeString(rootNode, "imdbRating");
        movieDetail.imdbVotes = safeString(rootNode, "imdbVotes");
        movieDetail.imdbID = safeString(rootNode, "imdbID");
        movieDetail.type = safeString(rootNode, "Type");
        movieDetail.dvd = safeString(rootNode, "DVD");
        movieDetail.boxOffice = safeString(rootNode, "BoxOffice");
        movieDetail.production = safeString(rootNode, "Production");
        movieDetail.website = safeString(rootNode, "Website");
        movieDetail.response = safeString(rootNode, "Response");

        List<RatingSource> ratingSourceList = new ArrayList<>();
        JSONArray ratingSourceArray = safeJsonArray(rootNode, "Ratings");

        if(!(ratingSourceArray == null)){

            for(byte ratingSourceArrayIndex = 0; ratingSourceArrayIndex < ratingSourceArray.length(); ratingSourceArrayIndex++ ){
                JSONObject ratingSourceObject = ratingSourceArray.getJSONObject(ratingSourceArrayIndex);
                RatingSource ratingSource = new RatingSource();
                ratingSource.source = safeString(ratingSourceObject, "Source");
                ratingSource.ratingScore = safeString(ratingSourceObject, "Value");

                ratingSourceList.add(ratingSource);
            }
        }

        movieDetail.ratingSourceList = ratingSourceList;

        return movieDetail;
    }

    @Override
    protected Exception postMap(Object jsonObject) {
        NoResultFoundException resultNotFoundException = null;

        if(jsonObject instanceof MovieDetail){
            MovieDetail movieDetail = (MovieDetail)jsonObject;
            String resultResponse = movieDetail.response;
            if(!(resultResponse == null) && !resultResponse.toLowerCase().equals("true"))
                resultNotFoundException = new NoResultFoundException("No Result Found");
        }

        return resultNotFoundException;
    }

}
