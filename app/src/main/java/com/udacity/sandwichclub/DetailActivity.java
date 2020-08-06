package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    public static final String SANDWICH_ARRAY_LIST="sandwich_array_list";

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        //intent.getStringArrayListExtra

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        getSandwichFile();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void getSandwichFile(){
        ArrayList<Sandwich> sandWichList = new ArrayList<Sandwich>();

        try
        {
            FileInputStream fis = new FileInputStream(new File(getString(R.string.pathToFile)));
            ObjectInputStream ois = new ObjectInputStream(fis);

            sandWichList = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }

        populateUI(sandWichList);

        //Verify list data
//        for (Sandwich sandwich : sandWichList) {
//            Log.i("DetailActivity","sand");
//        }
    }

    private void populateUI(ArrayList<Sandwich> sandWichList) {

//        TextView nameTV=findViewById(R.id.name);
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        TextView originTV = findViewById(R.id.origin_tv);
        TextView descriptionTV = findViewById(R.id.description_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);

        Sandwich sandwich = sandWichList.get(position);




        String alsoKnownAsString=listToString(sandwich);
        String originString=sandwich.getPlaceOfOrigin();
        String descriptionString=sandwich.getDescription();
        String ingredientsString=listToString(sandwich);


        if(alsoKnownAsString.isEmpty()){
            findViewById(R.id.also_known_as_title_tv).setVisibility(View.INVISIBLE);
            alsoKnownAsTV.setVisibility(View.INVISIBLE);
        }
        else{
            alsoKnownAsTV.setText(alsoKnownAsString);
        }

        if(originString.isEmpty()){
            findViewById(R.id.origin_title_tv).setVisibility(View.INVISIBLE);
            originTV.setVisibility(View.INVISIBLE);
        }
        else{
            originTV.setText(originString);
        }

        if(descriptionString.isEmpty()){
            findViewById(R.id.description_title_tv).setVisibility(View.INVISIBLE);
            descriptionTV.setVisibility(View.INVISIBLE);
        }

        else{
            descriptionTV.setText(descriptionString);
        }

        if(ingredientsString.isEmpty()){
            findViewById(R.id.ingredients_title_tv).setVisibility(View.INVISIBLE);
            ingredientsTV.setVisibility(View.INVISIBLE);
        }
        else{
            ingredientsTV.setText(ingredientsString);
        }
    }
    //json looks like

   //     {name
//        {mainName:club sandwich,
//        alsoKnownas:[clubhouse]}
//        placeofOrigin: Us
//        description:string,
//        image:string,
//        ingredients:[list]}


        //Verify list data
        private static String listToString(Sandwich sandwich) {
            List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
            StringBuilder alsoKnownAsString = new StringBuilder();


            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                alsoKnownAsString.append(alsoKnownAsList.get(i));
                if(alsoKnownAsList.size()==1|| i==alsoKnownAsList.size()-1){
                    continue;
                }
                //alsoKnownAsString.append(System.getProperty("line.separator"));
                alsoKnownAsString.append(", ");
            }

            return alsoKnownAsString.toString();


            //alsoKnownAsTV.setText(alsoKnownAsString.toString());

//        for (Sandwich sandwich : sandWichList) {
//            Log.i("DetailActivity","sand");
//        }
        }
    }

