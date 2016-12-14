package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.application.PhotoActivity;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Album;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Photo;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.User;

import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.adapters.*;



public class HomeActivity extends Activity {

    Button createAlbumButton;
    ListView albumListView;
    View empty;

    public static User user;
    ArrayList<Album> albums;

    AlbumAdapter albumAdapter;

    private View.OnClickListener createDialogListener;
    private AdapterView.OnItemClickListener itemClickListener;

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
                albums = new ArrayList<Album>();
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

        if(albums == null){
            View empty = findViewById(R.id.empty);
            ListView list = (ListView) findViewById(R.id.list);
            list.setEmptyView(empty);
        }
    }

    private void initLayoutWidgets(){

        createAlbumButton = (Button) findViewById(R.id.createAlbumButton);

        albumListView = (ListView) findViewById(R.id.list);

        empty = findViewById(R.id.empty);

        createDialogListener = new View.OnClickListener() {
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

                        List<Photo> emptyList = new ArrayList<Photo>();
                        Album newAlbum = new Album(value.getText().toString(), emptyList);
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

        itemClickListener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.item_click_dialog);
                dialog.setTitle("Open, Edit or Delete Album");

                final EditText value = (EditText) dialog.findViewById(R.id.albumEditText);

                Button openButton = (Button) dialog.findViewById(R.id.openAlbumButton);
                Button saveButton = (Button) dialog.findViewById(R.id.saveEditButton);
                Button deleteButton = (Button) dialog.findViewById(R.id.deleteAlbumButton);
                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

                final String albumName = ((TextView) view).getText().toString();
                final Album album = user.getAlbum(albumName);
                value.setText( albumName );

                // if button is clicked, close the custom dialog
                openButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(HomeActivity.this, PhotoActivity.class);
                        intent.putExtra("album", albumName);
                        startActivity(intent);

                        dialog.dismiss();

                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String newAlbumName = value.getText().toString();
                        album.setName(newAlbumName);
                        albums.get(i).setName(newAlbumName);
                        albumAdapter.notifyDataSetChanged();
                        //Need a write operation here?

                        dialog.dismiss();

                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        user.removeAlbum(album);
                        albums.remove(i);
                        albumAdapter.notifyDataSetChanged();

                        //write operation?
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

        createAlbumButton.setOnClickListener(createDialogListener);

        albumListView.setOnItemClickListener(itemClickListener);

    }
}
