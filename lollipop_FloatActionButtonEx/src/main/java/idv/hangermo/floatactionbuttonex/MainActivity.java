package idv.hangermo.floatactionbuttonex;

import android.app.Activity;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton fab = (ImageButton) findViewById(R.id.fab);

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                // Or read size directly from the view's width/height
                int size = getResources().getDimensionPixelSize(
                        R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
            }
        };
        fab.setOutlineProvider(viewOutlineProvider);
    }
}
