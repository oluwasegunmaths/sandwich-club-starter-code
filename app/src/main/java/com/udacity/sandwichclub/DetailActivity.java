package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this).load(sandwich.getImage()).into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView akaTv = findViewById(R.id.also_known_tv);


        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(getString(R.string.not_available));
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());

        }


        if (sandwich.getDescription().isEmpty()) {
            descriptionTv.setText(getString(R.string.not_available));
        } else {
            descriptionTv.setText(sandwich.getDescription());

        }
        ingredientsTv.setText(convertListToString(sandwich.getIngredients()));
        akaTv.setText(convertListToString(sandwich.getAlsoKnownAs()));


    }

    private String convertListToString(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) {
            return "Not available";
        } else {
            String stringFromList = "";
            for (int i = 0; i < stringList.size(); i++) {
                stringFromList = stringFromList.concat(stringList.get(i)).concat(", ");
            }

            // returns the final string removing the last two characters which are: ", "
            return stringFromList.substring(0, stringFromList.length() - 2);
        }
    }
}
