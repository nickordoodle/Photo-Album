package nscherer30.gmail.com.androidphotoalbum11.application;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;

import nscherer30.gmail.com.androidphotoalbum11.R;
import nscherer30.gmail.com.androidphotoalbum11.adapters.PhotoAdapter;
import nscherer30.gmail.com.androidphotoalbum11.model.Album;
import nscherer30.gmail.com.androidphotoalbum11.model.Photo;
import nscherer30.gmail.com.androidphotoalbum11.model.User;

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

        initLayoutWidgets();
        setWidgetActions();

        try{
            photos = (ArrayList)album.getPhotos();
        } catch (IllegalFormatException e){
            e.printStackTrace();
        }

        photoAdapter = new PhotoAdapter(getApplicationContext(), photos, album, user);
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

        itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                Intent intent = new Intent(PhotoActivity.this, AlbumSlideshow.class);
                intent.putExtra("album", album.getName());
                startActivity(intent);
            }
        };
    }

    private void setWidgetActions(){

        addPhotoButton.setOnClickListener(addPhotoListener);

        photoListView.setOnItemClickListener(itemClickListener);

    }

}
