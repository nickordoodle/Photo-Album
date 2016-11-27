package nscherer30.gmail.com.androidphotoalbum11;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class HomeActivity extends Activity {

    Button openAlbumButton;
    Button createAlbumButton;
    Button renameAlbumButton;
    Button deleteAlbumButton;
    ListView albumListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        initLayoutWidgets();
        setWidgetActions();
    }

    private void initLayoutWidgets(){

        openAlbumButton = (Button) findViewById(R.id.openAlbumButton);
        createAlbumButton = (Button) findViewById(R.id.createAlbumButton);
        renameAlbumButton = (Button) findViewById(R.id.renameAlbumButton);
        deleteAlbumButton = (Button) findViewById(R.id.deleteAlbumButton);

        albumListView = (ListView) findViewById(R.id.list);


    }

    private void setWidgetActions(){

        openAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        createAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        renameAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        deleteAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement user click actions
            }
        });

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }
}
