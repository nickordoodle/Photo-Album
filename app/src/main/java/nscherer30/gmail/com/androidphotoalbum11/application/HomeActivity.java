package nscherer30.gmail.com.androidphotoalbum11.application;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import nscherer30.gmail.com.androidphotoalbum11.R;

public class HomeActivity extends Activity {

    Button openAlbumButton;
    Button createAlbumButton;
    Button renameAlbumButton;
    Button deleteAlbumButton;
    ListView albumListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        initLayoutWidgets();
        setWidgetActions();


        SharedPreferences sharedPref = getSharedPreferences("androidphotoalbum11",MODE_PRIVATE);
        if(!sharedPref.getBoolean("firstLogin", false)){
            SharedPreferences.Editor prefEditor = sharedPref.edit();
            prefEditor.putBoolean("firstLogin", true);
            prefEditor.commit();
        } else {


            //get user data here and adjust adapter
        }


        // Defined Array values to show in ListView
        String[] values = new String[]{"Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        albumListView.setAdapter(adapter);

    }

    private void initLayoutWidgets(){

        openAlbumButton = (Button) findViewById(R.id.openAlbumButton);
        createAlbumButton = (Button) findViewById(R.id.createAlbumButton);
        renameAlbumButton = (Button) findViewById(R.id.renameAlbumButton);
        deleteAlbumButton = (Button) findViewById(R.id.deleteAlbumButton);

        albumListView = (ListView) findViewById(R.id.list);


    }

    private void setWidgetActions(){

        openAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        createAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        renameAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        deleteAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }
}
