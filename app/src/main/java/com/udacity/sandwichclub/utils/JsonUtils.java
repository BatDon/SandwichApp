package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {


    //    public static Sandwich parseSandwichJson(String jsonAsArray) {
    public static Sandwich parseSandwichJson(String jsonAsArray) {

        JSONObject jsonObject = null;

        JSONObject jsonObjectName = null;
        JSONObject jsonObjectList = null;

        JSONArray alsoKnownAsJsonArray = null;

        JSONArray ingredientsAsJsonArray = null;

        JSONObject alsoKnownAsObject = null;

        Sandwich sandwich = null;

        try {
            jsonObject = new JSONObject(jsonAsArray);//Java Object

            jsonObjectName = (JSONObject) jsonObject.get("name");

            //alsoKnownAsjsonArray=new JSONArray();

            Log.i("JsonUtils", " called");

            String mainName = jsonObjectName.get("mainName").toString();
            alsoKnownAsJsonArray = (JSONArray) jsonObjectName.get("alsoKnownAs");
            String placeOfOrigin = jsonObject.get("placeOfOrigin").toString();
            String description = jsonObject.get("description").toString();
            String image = jsonObject.get("image").toString();
            ingredientsAsJsonArray = (JSONArray) jsonObject.get("ingredients");


            List<String> alsoKnownAsList = getList(alsoKnownAsJsonArray);
            List<String> ingredientsList = getList(ingredientsAsJsonArray);


            sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);


            //jsonArray.put(jsonObjectAKA);
            //jsonArray =jsonObjectName.get("alsoKnownAs");
            //jsonObjectName.get("alsoKnownAs");

            Object origin = jsonObjectName.has("placeOfOrigin");
            Log.i("JsonUtils", "origin= " + origin);
            Log.i("JsonUtils", "" + jsonObjectName.length());


            //jsonObject.get("placeOfOrigin");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> getList(JSONArray jsonArray) throws JSONException {

        List<String> list = new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {
            String listValue = jsonArray.getString(i);
            list.add(listValue);
        }
        return list;

    }

    private static String testNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}

//{\"name\":{\"mainName\":\"Club sandwich\",\"alsoKnownAs\":[\"Clubhouse
//        sandwich\"]},\"placeOfOrigin\":\"United States\",\"description\":\"A club sandwich, also
//        called a clubhouse sandwich, is a sandwich of bread (occasionally toasted), sliced
//        cooked poultry, fried bacon, lettuce, tomato, and mayonnaise. It is often cut into
//        quarters or halves and held together by cocktail sticks. Modern versions frequently have
//        two layers which are separated by an additional slice of
//        bread.\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Club_sandwich.png/800px-Club_sandwich.png\",\"ingredients\":[\"Toasted
//        bread\",\"Turkey or chicken\",\"Bacon\",\"Lettuce\",\"Tomato\",\"Mayonnaise\"]}


//json object
//
//        {name
//        {mainName:club sandwich,
//        alsoKnownas:[clubhouse]}
//        placeofOrigin: Us
//        description:string,
//        image:string,
//        ingredients:[list]}
