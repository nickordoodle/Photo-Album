package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.application;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ListView;

import java.util.IllegalFormatException;
import java.util.List;

import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.HomeActivity;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.adapters.PhotoAdapter;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Album;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Photo;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.User;


public class AlbumSlideshow extends AppCompatActivity {

    Album album;
    User user;
    int index;

    Button movePhotoButton;
    Button nextPhotoButton;
    Button prevPhotoButton;

    List<Photo> photos;

    ArrayAdapter<Photo> photoAdapter;
    private View.OnClickListener prevClickListener;
    private View.OnClickListener nextClickListener;
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

    }

    private void setWidgetActions(){

        nextPhotoButton.setOnClickListener(nextClickListener);
        prevPhotoButton.setOnClickListener(prevClickListener);

    }

}
