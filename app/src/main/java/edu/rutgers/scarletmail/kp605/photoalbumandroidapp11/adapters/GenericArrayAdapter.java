package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public abstract class GenericArrayAdapter<T> extends ArrayAdapter<T> {

    // Vars
    protected LayoutInflater mInflater;
    protected List<T> data;

    public GenericArrayAdapter(Context context, List<T> objects) {
        super(context, 0, objects);
        init(context);
        this.data = objects;
    }

    // Headers
    public abstract void drawText(TextView textView, T object);

    private void init(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        drawText(vh.textView, getItem(position));


        return convertView;
    }

    static class ViewHolder {

        TextView textView;

        private ViewHolder(View rootView) {
            textView = (TextView) rootView.findViewById(android.R.id.text1);
        }
    }
}