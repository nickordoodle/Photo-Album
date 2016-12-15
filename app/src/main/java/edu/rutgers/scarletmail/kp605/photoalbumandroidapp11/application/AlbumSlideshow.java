package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.application;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.HomeActivity;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.adapters.PhotoAdapter;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Album;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Photo;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.User;

import static android.R.attr.value;
import static edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R.id.albumName;


public class AlbumSlideshow extends AppCompatActivity {

    Album album;
    User user;
    int index;
    String path;

    Button movePhotoButton;
    Button nextPhotoButton;
    Button prevPhotoButton;

    List<Photo> photos;

    ArrayAdapter<Photo> photoAdapter;
    private View.OnClickListener prevClickListener;
    private View.OnClickListener nextClickListener;
    private View.OnClickListener moveClickListener;
    private AdapterView.OnItemClickListener itemClickListener;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_slideshow);

        user = HomeActivity.user;
        album = user.getAlbum(getIntent().getStringExtra("album"));
        index = Integer.parseInt(getIntent().getStringExtra("photo"));
        Context context = getApplicationContext();
        path = context.getFilesDir().getPath().toString() +  File.separator + "userData.dat";

        ImageView imageView = (ImageView) findViewById(R.id.slideshow_image_view);
        imageView.setImageBitmap(album.getPhoto(index).getBitmap());
        initLayoutWidgets();
        setWidgetActions();

        try{
            photos = album.getPhotos();
        } catch (IllegalFormatException e){
            e.printStackTrace();
        }

        //photoAdapter = new PhotoAdapter(PhotoActivity.this, context, photos, album, user);
        //photoListView.setAdapter(photoAdapter);
    }

    private void initLayoutWidgets(){

        prevPhotoButton = (Button) findViewById(R.id.prevPhotoButton);

        nextPhotoButton = (Button) findViewById(R.id.nextPhotoButton);

        movePhotoButton = (Button) findViewById(R.id.movePhotoButton);

        imageView = (ImageView) findViewById(R.id.slideshow_image_view);

        prevClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                if(index > 0) {
                    index = index - 1;
                    imageView.setImageBitmap(album.getPhoto(index).getBitmap());
                }
            }
        };

        nextClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                if(index < album.getAmountOfPhotos()-1) {
                    index = index + 1;
                    imageView.setImageBitmap(album.getPhoto(index).getBitmap());
                }
            }
        };

        moveClickListener = new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(AlbumSlideshow.this);
                dialog.setContentView(R.layout.move_dialog);
                dialog.setTitle("Move Photo");

                Button moveButton = (Button) dialog.findViewById(R.id.moveButton);
                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                final Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner);

                List<Album> list = new ArrayList<Album>();

                for(Album a : user.getAlbums()) {
                    if(!a.equals(album)) {
                        list.add(a);
                    }
                }

                ArrayAdapter<Album> adapter = new ArrayAdapter<Album>(AlbumSlideshow.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);

                // if button is clicked, close the custom dialog
                moveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Photo p = album.getPhoto(index);
                        String newAlbum = String.valueOf(dropdown.getSelectedItem());
                        user.getAlbum(newAlbum).addPhoto(p);
                        user.getAlbum(album.getName()).removePhoto(p);
                        dialog.dismiss();

                        User.write(user, path);

                        Intent intent = new Intent(AlbumSlideshow.this, PhotoActivity.class);
                        intent.putExtra("album", newAlbum);
                        startActivity(intent);

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

        prevPhotoButton.setOnClickListener(prevClickListener);
        nextPhotoButton.setOnClickListener(nextClickListener);
        movePhotoButton.setOnClickListener(moveClickListener);

    }

}
