package com.oviahealth.assignment.Movie.util;

import android.net.Uri;

import com.oviahealth.assignment.Movie.OviaHealthApplication;

import java.util.Map;

import okhttp3.HttpUrl;

public class StringUtil {

    public static boolean isEmpty(String value){
        return (value == null || value.trim().length() == 0);
    }

    public static String buildUrl(String endpoint, String path, Map<String, String> queryParameterMap){

        if(isEmpty(endpoint))
            return null;

        HttpUrl endPointHttpUrl = HttpUrl.parse(endpoint);
        String protocol = null;
        String domain = null;

        if(endPointHttpUrl == null)
            return null;
        else{
            protocol = endPointHttpUrl.url().getProtocol();
            domain = endPointHttpUrl.url().getAuthority();

        }

        if(isEmpty(protocol) || isEmpty(domain))
            return null;

        Uri.Builder urlBuilder = new Uri.Builder()
                .scheme(protocol)
                .authority(domain)
                .appendPath(isEmpty(path) ? "" : path);

        if(!(queryParameterMap == null)){
            for (Map.Entry<String, String> entry : queryParameterMap.entrySet())
            {
                String queryParameter = entry.getKey();
                String queryParameterValue = isEmpty(entry.getValue()) ? "" : entry.getValue();

                if(!(isEmpty(queryParameter))){
                    urlBuilder.appendQueryParameter(queryParameter, queryParameterValue);
                }
            }
        }

        String url = urlBuilder.build().toString();
        return isUrlValid(url) ? url : null;
    }

    public static boolean isUrlValid(String url){
        return !(HttpUrl.parse(url) == null);
    }

    public static String diaplayableString(int stringResId){
        return  OviaHealthApplication.getContext().getResources().getString(stringResId);
    }
}
