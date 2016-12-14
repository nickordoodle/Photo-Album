package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;

import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.HomeActivity;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.adapters.PhotoAdapter;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Album;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Photo;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.User;

public class PhotoActivity extends Activity {

    Album album;
    User user;

    ListView photoListView;
    Button addPhotoButton;

    ArrayList<Photo> photos;

    ArrayAdapter<Photo> photoAdapter;
    private View.OnClickListener addPhotoListener;
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

        initLayoutWidgets();
        setWidgetActions();

        try{
            photos = (ArrayList)album.getPhotos();
        } catch (IllegalFormatException e){
            e.printStackTrace();
        }

        photoAdapter = new PhotoAdapter(PhotoActivity.this, context, photos, album, user);
        photoListView.setAdapter(photoAdapter);

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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initLayoutWidgets(){

        addPhotoButton = (Button) findViewById(R.id.addPhotoButton);

        photoListView = (ListView) findViewById(R.id.list);

        empty = findViewById(R.id.empty);

        addPhotoListener = new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                Photo photo = new Photo("test", "test");
                photos.add(photo);
                album.addPhoto(photo);
                photoAdapter.notifyDataSetChanged();
            }
        };

    }

    private void setWidgetActions(){

        addPhotoButton.setOnClickListener(addPhotoListener);

        photoListView.setOnItemClickListener(itemClickListener);

    }

}