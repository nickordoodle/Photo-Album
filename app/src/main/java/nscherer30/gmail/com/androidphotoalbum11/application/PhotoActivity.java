package nscherer30.gmail.com.androidphotoalbum11.application;

import android.app.Activity;
import android.os.Bundle;

import nscherer30.gmail.com.androidphotoalbum11.model.User;
import nscherer30.gmail.com.androidphotoalbum11.R;

public class PhotoActivity extends Activity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


    }
}
