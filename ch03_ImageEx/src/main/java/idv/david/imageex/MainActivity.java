package idv.david.imageex;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    private ImageButton ibClick;
    private ImageView ivTed;
    private boolean isClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        ibClick = (ImageButton)findViewById(R.id.ibClick);
        ivTed = (ImageView)findViewById(R.id.ivTed);

        ibClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isClicked) {
                    ivTed.setImageResource(R.drawable.ted);
                    isClicked = false;
                } else {
                    ivTed.setImageResource(R.drawable.ted2);
                    isClicked = true;
                }
            }
        });
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
