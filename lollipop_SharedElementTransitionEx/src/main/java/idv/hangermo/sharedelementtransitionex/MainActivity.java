package idv.hangermo.sharedelementtransitionex;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        ActivityOptions activityOptions = null;
        Intent intent = new Intent(this, PageActivity.class);
        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="@string/transition_album_cover"
        switch (view.getId()) {
            case R.id.imageView1:

//                getWindow().setExitTransition(new Explode());
//                getWindow().setEnterTransition(new Fade());

                String transitionName_photo = getString(R.string.transition_album_cover);
                String transitionName_text = getString(R.string.transition_album_text);

                // Single
                activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        MainActivity.this, findViewById(R.id.imageView1), transitionName_photo
                );

                // Multi
//                activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
//                        // Now we provide a list of Pair items which contain the view we can transitioning
//                        // from, and the name of the view it is transitioning to, in the launched activity
//                        Pair.create(findViewById(R.id.imageView1), transitionName_photo),
//                        Pair.create(findViewById(R.id.textView1), transitionName_text)
//                );

                break;
        }

        if (activityOptions != null) {
            startActivity(intent, activityOptions.toBundle());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
