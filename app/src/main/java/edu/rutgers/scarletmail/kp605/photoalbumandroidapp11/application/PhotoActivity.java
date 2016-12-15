package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.IllegalFormatException;

import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.HomeActivity;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.adapters.PhotoAdapter;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Album;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Photo;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Tag;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.User;

import static android.R.attr.path;
import static edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R.id.albumName;
import static edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R.id.deleteButton;

public class PhotoActivity extends AppCompatActivity {

    Album album;
    User user;
    String path;

    ListView photoListView;
    Button addPhotoButton;
    Button searchPhotosButton;

    List<Photo> photos;

    ArrayAdapter<Photo> photoAdapter;
    private View.OnClickListener addPhotoListener;
    private View.OnClickListener searchPhotosListener;
    private AdapterView.OnItemClickListener itemClickListener;

    View empty;

    final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        user = HomeActivity.user;
        album = user.getAlbum(getIntent().getStringExtra("album"));
        Context context = getApplicationContext();
        path = context.getFilesDir().getPath().toString() +  File.separator + "userData.dat";
        if(album.getName().contains("Search__")) {
            setTitle("Search Results");
        } else {
            setTitle("Album: " + album.getName());
        }

        initLayoutWidgets();
        setWidgetActions();

    }

    public void onStart() {
        super.onStart();
        try{
            photos = album.getPhotos();
        } catch (IllegalFormatException e){
            e.printStackTrace();
        }

        Context context = getApplicationContext();
        photoAdapter = new PhotoAdapter(PhotoActivity.this, context, photos, album, user, path);
        photoListView.setAdapter(photoAdapter);

        if(album.getName().split("__").length > 0 && album.getName().split("__")[0].equals("Search")) {
            addPhotoButton.setEnabled(false);
            searchPhotosButton.setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        if(album == null || album.getAmountOfPhotos() < 1){
            View empty = findViewById(R.id.empty);
            ListView list = (ListView) findViewById(R.id.list);
            list.setEmptyView(empty);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                        Photo photo = new Photo(bitmap);
                        user.getAlbum(album.getName()).addPhoto(photo);
                        album = user.getAlbum(album.getName());
                        photos = album.getPhotos();
                        photoAdapter.notifyDataSetChanged();
                        User.write(user, path);

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
            }
        }
    }

    private void initLayoutWidgets(){

        addPhotoButton = (Button) findViewById(R.id.addPhotoButton);

        searchPhotosButton = (Button) findViewById(R.id.searchPhotosButton);

        photoListView = (ListView) findViewById(R.id.list);

        empty = findViewById(R.id.empty);

        addPhotoListener = new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        };

        searchPhotosListener = new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(PhotoActivity.this);
                dialog.setContentView(R.layout.search_photos_dialog);
                dialog.setTitle("Search Photos");

                Button searchButton = (Button) dialog.findViewById(R.id.searchButton);
                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                final Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner);
                final EditText value = (EditText) dialog.findViewById(R.id.searchText);

                List<String> list = new ArrayList<String>();
                list.add("location");
                list.add("person");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PhotoActivity.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);

                // if button is clicked, close the custom dialog
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String type = String.valueOf(dropdown.getSelectedItem());
                        String val = value.getText().toString();

                        List<Photo> searchResults = new ArrayList<Photo>();
                        for(Photo p : album.getPhotos()) {
                            for(Tag tag : p.getTags()) {
                                if(tag.getType().toLowerCase().equals(type.toLowerCase())
                                        && tag.getValue().toLowerCase().contains(val.toLowerCase())) {
                                    searchResults.add(p);
                                }
                            }
                        }

                        Album a = new Album("Search__" + album.getName() , searchResults);
                        user.addAlbum(a);
                        dialog.dismiss();

                        Intent intent = new Intent(PhotoActivity.this, PhotoActivity.class);
                        intent.putExtra("album", a.getName());
                        startActivity(intent);

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

        addPhotoButton.setOnClickListener(addPhotoListener);
        searchPhotosButton.setOnClickListener(searchPhotosListener);

        photoListView.setOnItemClickListener(itemClickListener);

    }

}