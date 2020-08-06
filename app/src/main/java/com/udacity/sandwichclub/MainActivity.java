package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Sandwich> sandwichList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sandwiches);

        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });

        parseJsonObjects();
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        //intent.put(DetailActivity.SANDWICH_ARRAY_LIST,sandwichList);
        startActivity(intent);
    }

    private void parseJsonObjects() {
        String[] sandwichJsonAsString = getResources().getStringArray(R.array.sandwich_details);

        sandwichList=new ArrayList<>();

        for(int i = 0;i<sandwichJsonAsString.length; i++)
        {
            //JsonUtils.parseSandwichJson(sandwichJsonAsString[i]);
            Sandwich sandwich=JsonUtils.parseSandwichJson(sandwichJsonAsString[i]);
            sandwichList.add(i,sandwich);

        }
        writeToFile(sandwichList, this);

    }

    private void writeToFile(ArrayList<Sandwich> sandwichList, Context context){

        try {

            //FileOutputStream fileOut = new FileOutputStream(new File(getFilesDir(), "sandwichListFile.txt"));
            FileOutputStream fileOut = new FileOutputStream(new File(getString(R.string.pathToFile)));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(sandwichList);
            objectOut.close();
            fileOut.close();
            Log.i("MainActivity","file written successfully");
            Log.i("MainActivity",getFilesDir().getAbsolutePath());
            //System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
