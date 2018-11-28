package com.oviahealth.assignment.Movie.framework.network;

import com.oviahealth.assignment.Movie.exception.InvalidJsonException;

import org.json.JSONTokener;

public abstract class ServiceCallResponseMapper {

    public Object mapResponse(String jsonResponse) throws Exception{
        //return mapResult(parseJson(jsonResponse));
        Object responseObject = mapResult(parseJson(jsonResponse));

        Exception postMapException = postMap(responseObject);
        if((postMapException == null))
            return responseObject;

        throw postMapException;


    }

    public Object parseJson(String jsonResponse) throws Exception{
        Object jsonObject = null;

        try{
            jsonObject = (new JSONTokener(jsonResponse)).nextValue();
        }catch(ClassCastException ex){
            throw new InvalidJsonException("Response JSON Syntax Exception");
        }

        return jsonObject;
    }

    protected abstract Object mapResult(Object jsonObject) throws Exception;

    protected abstract Exception postMap(Object jsonObject);

}
