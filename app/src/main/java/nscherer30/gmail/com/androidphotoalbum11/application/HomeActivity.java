package nscherer30.gmail.com.androidphotoalbum11.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nscherer30.gmail.com.androidphotoalbum11.R;
import nscherer30.gmail.com.androidphotoalbum11.model.Album;
import nscherer30.gmail.com.androidphotoalbum11.model.User;

public class HomeActivity extends Activity {

    Button openAlbumButton;
    Button createAlbumButton;
    Button renameAlbumButton;
    Button deleteAlbumButton;
    ListView albumListView;
    View empty;

    User user;
    ArrayList<Album> albums;

    AlbumAdapter albumAdapter;
    AlertDialog.Builder alert;

    View.OnClickListener dialogListener;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        initLayoutWidgets();
        setWidgetActions();


        sharedPref = getSharedPreferences("UserLoginInfo",MODE_PRIVATE);
        boolean hasRun = sharedPref.getBoolean("firstLogin", false);
        if(!hasRun){
            SharedPreferences.Editor prefEditor = sharedPref.edit();
            prefEditor.putBoolean("firstLogin", true);
            prefEditor.commit();

            Log.d("sharedPref", "this is the first login");
        } else {

            //get user data here and adjust adapter

            //read in the user data
            if(user == null){
                user = new User("me");
                albums = new ArrayList<>();
            } else {
                user = User.read();
                albums = user.getAlbums();
            }


            Log.d("sharedPref", "this is NOT the first login");

        }


        albumAdapter = new AlbumAdapter(getApplicationContext(), albums);
        albumListView.setAdapter(albumAdapter);


    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        if(albums == null || albums.size() < 1){
            View empty = findViewById(R.id.empty);
            ListView list = (ListView) findViewById(R.id.list);
            list.setEmptyView(empty);
        }
    }

    private void initLayoutWidgets(){

        openAlbumButton = (Button) findViewById(R.id.openAlbumButton);
        createAlbumButton = (Button) findViewById(R.id.createAlbumButton);
        renameAlbumButton = (Button) findViewById(R.id.renameAlbumButton);
        deleteAlbumButton = (Button) findViewById(R.id.deleteAlbumButton);

        albumListView = (ListView) findViewById(R.id.list);

        empty = findViewById(R.id.empty);

        dialogListener = new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.create_album_dialog);
                dialog.setTitle("Add Album");

                final EditText value = (EditText) dialog.findViewById(R.id.albumName);

                Button addButton = (Button) dialog.findViewById(R.id.add);
                Button cancelButton = (Button) dialog.findViewById(R.id.cancel);

                // if button is clicked, close the custom dialog
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Album newAlbum = new Album(value.getText().toString(), null);
                        albums.add(newAlbum);
                        albumAdapter.notifyDataSetChanged();

                        user.addAlbum(newAlbum);
                        User.write(user);

                        dialog.dismiss();

                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        };




    }

    private void setWidgetActions(){

        openAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        createAlbumButton.setOnClickListener(dialogListener);

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
