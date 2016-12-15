package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.adapters;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.HomeActivity;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.application.AlbumSlideshow;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.application.PhotoActivity;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Album;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.User;
import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model.Photo;

import edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.R;


/**
 * Created by Nick on 11/29/2016.
 */
public class PhotoAdapter extends GenericArrayAdapter<Photo> {

    private Album album;
    private User user;
    private Activity activity;

    public PhotoAdapter(Activity activity, Context context, List<Photo> photos, Album album, User user) {
        super(context, photos);
        this.activity = activity;
        this.album = album;
        this.user = user;
    }

    @Override
    public void drawText(TextView textView, Photo object) {

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (vi == null)
            vi = mInflater.inflate(R.layout.photo_row, null);
        TextView text = (TextView) vi.findViewById(R.id.photo_row_text_view);
        Button deleteButton = (Button) vi.findViewById(R.id.photo_row_delete_button);
        ImageView imageView = (ImageView) vi.findViewById(R.id.photo_row_image_view);
        imageView.setImageBitmap(data.get(position).getBitmap());

        String caption = data.get(position).getTagsString();
        if(caption != null)
            text.setText(data.get(position).getTagsString());
        else
            text.setText("No caption");

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album.removePhoto(data.get(position));
                notifyDataSetChanged();
            }
        });

        text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Intent intent = new Intent(context, AlbumSlideshow.class);
                intent.putExtra("photo", position + "");
                intent.putExtra("album", album.getName());
                context.startActivity(intent);
            }
        });

        return vi;
    }


}