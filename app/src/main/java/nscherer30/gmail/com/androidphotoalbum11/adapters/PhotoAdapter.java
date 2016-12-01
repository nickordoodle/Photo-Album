package nscherer30.gmail.com.androidphotoalbum11.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import nscherer30.gmail.com.androidphotoalbum11.application.AlbumSlideshow;
import nscherer30.gmail.com.androidphotoalbum11.application.PhotoActivity;
import nscherer30.gmail.com.androidphotoalbum11.model.Album;
import nscherer30.gmail.com.androidphotoalbum11.model.Photo;

import nscherer30.gmail.com.androidphotoalbum11.R;
import nscherer30.gmail.com.androidphotoalbum11.model.User;


/**
 * Created by Nick on 11/29/2016.
 */
public class PhotoAdapter extends GenericArrayAdapter<Photo> {

    private Album album;
    private User user;
    private Activity activity;

    public PhotoAdapter(Activity activity, Context context, ArrayList<Photo> photos, Album album, User user) {
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

        String caption = data.get(position).getCaption();
        if(caption != null)
            text.setText(data.get(position).getCaption());
        else
            text.setText("No caption");

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album.removePhoto(data.get(position));
                data.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Intent intent = new Intent(context, AlbumSlideshow.class);
                intent.putExtra("album", album.getName());
                context.startActivity(intent);
            }
        });

        return vi;
    }


}

