package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        if (json == null) {
            return null;
        } else {
            try {
                JSONObject sandwichJson = new JSONObject(json);
                JSONObject nameJsonObject = sandwichJson.getJSONObject("name");


                String sandwichName = nameJsonObject.getString("mainName");
                JSONArray sandwichAkaJsonArray = nameJsonObject.getJSONArray("alsoKnownAs");
                List<String> sandwichAkaList = null;

                if (sandwichAkaJsonArray != null) {
                    sandwichAkaList = parseJsonArrayToList(sandwichAkaJsonArray);
                }
                String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
                String description = sandwichJson.getString("description");
                String imageUrl = sandwichJson.getString("image");
                JSONArray ingredientsJsonArray = sandwichJson.getJSONArray("ingredients");
                List<String> ingredientsList = null;

                if (ingredientsJsonArray != null) {
                    ingredientsList = parseJsonArrayToList(ingredientsJsonArray);
                }
                return new Sandwich(sandwichName, sandwichAkaList, placeOfOrigin, description, imageUrl, ingredientsList);

            } catch (JSONException e) {

                e.printStackTrace();
                Log.i("JsonUtils", "Error parsing json");
                return null;
            }


        }
    }

    private static List<String> parseJsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String arrayString = jsonArray.getString(i);
            strings.add(arrayString);
        }
        return strings;

    }
}
