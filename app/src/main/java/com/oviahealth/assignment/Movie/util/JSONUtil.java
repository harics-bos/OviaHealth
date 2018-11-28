package com.oviahealth.assignment.Movie.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {

    public static String safeString(JSONObject jsonObject, String jsonField){
        Object jsonFieldValue = jsonObject.opt(jsonField);
        return jsonFieldValue == null ? null : (String)jsonFieldValue;
    }

    public static JSONArray safeJsonArray(JSONObject jsonObject, String jsonField){
        Object jsonFieldValue = jsonObject.optJSONArray(jsonField);
        return jsonFieldValue == null ? null : (JSONArray)jsonFieldValue;

    }
}
