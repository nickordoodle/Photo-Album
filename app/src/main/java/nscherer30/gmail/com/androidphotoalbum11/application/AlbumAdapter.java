package nscherer30.gmail.com.androidphotoalbum11.application;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nscherer30.gmail.com.androidphotoalbum11.model.Album;

/**
 * Created by Nick on 11/27/2016.
 */

public class AlbumAdapter extends GenericArrayAdapter<Album> {

    public AlbumAdapter(Context context, ArrayList<Album> objects) {
        super(context, objects);
    }

    @Override public void drawText(TextView textView, Album object) {
        if(object == null){
            textView.setText("No items to display");

        } else {
            textView.setText(object.getName());
        }
    }

}