package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.application;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

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
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Tag;

import static android.R.attr.value;
import static edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R.id.addButton;
import static edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R.id.addTagButton;
import static edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R.id.albumName;
import static edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R.id.deleteTagButton;
import static edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R.id.text;


public class AlbumSlideshow extends AppCompatActivity {

    Album album;
    User user;
    int index;
    String path;

    Button movePhotoButton;
    Button nextPhotoButton;
    Button prevPhotoButton;
    Button addTagButton;
    Button deleteTagButton;

    List<Photo> photos;

    ArrayAdapter<Photo> photoAdapter;
    private View.OnClickListener prevClickListener;
    private View.OnClickListener nextClickListener;
    private View.OnClickListener moveClickListener;
    private View.OnClickListener addTagClickListener;
    private View.OnClickListener deleteTagClickListener;
    private AdapterView.OnItemClickListener itemClickListener;

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_slideshow);

        user = HomeActivity.user;
        album = user.getAlbum(getIntent().getStringExtra("album"));
        index = Integer.parseInt(getIntent().getStringExtra("photo"));
        Context context = getApplicationContext();
        path = context.getFilesDir().getPath().toString() +  File.separator + "userData.dat";
        setTitle("Album: " + album.getName());

        ImageView imageView = (ImageView) findViewById(R.id.slideshow_image_view);
        imageView.setImageBitmap(album.getPhoto(index).getBitmap());

        TextView textView = (TextView) findViewById(R.id.tagsView);
        textView.setText(album.getPhoto(index).getTagsString());

        initLayoutWidgets();
        setWidgetActions();

        try{
            photos = album.getPhotos();
        } catch (IllegalFormatException e){
            e.printStackTrace();
        }

        if(index == 0) {
            prevPhotoButton.setEnabled(false);
        } else {
            prevPhotoButton.setEnabled(true);
        }
        if(index == album.getAmountOfPhotos()-1) {
            nextPhotoButton.setEnabled(false);
        } else {
            nextPhotoButton.setEnabled(true);
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

    private void initLayoutWidgets(){

        prevPhotoButton = (Button) findViewById(R.id.prevPhotoButton);

        nextPhotoButton = (Button) findViewById(R.id.nextPhotoButton);

        movePhotoButton = (Button) findViewById(R.id.movePhotoButton);

        addTagButton = (Button) findViewById(R.id.addTagButton);

        deleteTagButton = (Button) findViewById(R.id.deleteTagButton);

        imageView = (ImageView) findViewById(R.id.slideshow_image_view);

        textView = (TextView) findViewById(R.id.tagsView);

        prevClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                if(index > 0) {
                    index = index - 1;
                    imageView.setImageBitmap(album.getPhoto(index).getBitmap());
                    textView.setText(album.getPhoto(index).getTagsString());

                    if(index == 0) {
                        prevPhotoButton.setEnabled(false);
                    } else {
                        prevPhotoButton.setEnabled(true);
                    }
                    if(index == album.getAmountOfPhotos()-1) {
                        nextPhotoButton.setEnabled(false);
                    } else {
                        nextPhotoButton.setEnabled(true);
                    }
                }
            }
        };

        nextClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                if(index < album.getAmountOfPhotos()-1) {
                    index = index + 1;
                    imageView.setImageBitmap(album.getPhoto(index).getBitmap());
                    textView.setText(album.getPhoto(index).getTagsString());

                    if(index == 0) {
                        prevPhotoButton.setEnabled(false);
                    } else {
                        prevPhotoButton.setEnabled(true);
                    }
                    if(index == album.getAmountOfPhotos()-1) {
                        nextPhotoButton.setEnabled(false);
                    } else {
                        nextPhotoButton.setEnabled(true);
                    }
                }
            }
        };

        addTagClickListener = new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(AlbumSlideshow.this);
                dialog.setContentView(R.layout.add_tag_dialog);
                dialog.setTitle("Add Tag");

                Button addButton = (Button) dialog.findViewById(R.id.addButton);
                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                final Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner);
                final EditText value = (EditText) dialog.findViewById(R.id.tagText);

                List<String> list = new ArrayList<String>();
                list.add("location");
                list.add("person");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AlbumSlideshow.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);

                // if button is clicked, close the custom dialog
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tag tag = new Tag(String.valueOf(dropdown.getSelectedItem()), value.getText().toString());
                        if(!user.getAlbum(album.getName()).getPhoto(index).hasTag(tag)) {
                            user.getAlbum(album.getName()).getPhoto(index).addTag(tag);
                            album = user.getAlbum(album.getName());
                            photos = album.getPhotos();

                            textView.setText(user.getAlbum(album.getName()).getPhoto(index).getTagsString());
                            User.write(user, path);

                            dialog.dismiss();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(AlbumSlideshow.this).create();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Photo has this tag already");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }

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

        deleteTagClickListener = new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(AlbumSlideshow.this);
                dialog.setContentView(R.layout.delete_tag_dialog);
                dialog.setTitle("Delete Tag");

                Button deleteButton = (Button) dialog.findViewById(R.id.deleteButton);
                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                final Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner);

                List<Tag> list = album.getPhoto(index).getTags();

                ArrayAdapter<Tag> adapter = new ArrayAdapter<Tag>(AlbumSlideshow.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);

                // if button is clicked, close the custom dialog
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tag tag = (Tag) dropdown.getSelectedItem();
                        user.getAlbum(album.getName()).getPhoto(index).removeTag(tag);
                        album = user.getAlbum(album.getName());
                        photos = album.getPhotos();

                        textView.setText(user.getAlbum(album.getName()).getPhoto(index).getTagsString());
                        User.write(user, path);

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
        addTagButton.setOnClickListener(addTagClickListener);
        deleteTagButton.setOnClickListener(deleteTagClickListener);

    }

}
