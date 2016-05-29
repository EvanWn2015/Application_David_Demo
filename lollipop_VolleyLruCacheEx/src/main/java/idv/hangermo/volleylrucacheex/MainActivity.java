package idv.hangermo.volleylrucacheex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;


public class MainActivity extends Activity {
    private GridView mPhotoWall;
    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPhotoWall = (GridView) findViewById(R.id.gvPhoto);
        adapter = new PhotoWallAdapter(this);
        mPhotoWall.setAdapter(adapter);
    }
}
