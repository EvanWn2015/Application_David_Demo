package idv.hangermo.activitytransitionex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PageActivity extends Activity {
    private TextView textView1;
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_content);

        textView1 = (TextView) findViewById(R.id.textView1);
        imageView1 = (ImageView) findViewById(R.id.imageView1);

        String title = "台灣";
        int resId = R.drawable.banner1;
        String txt = getResources().getString(R.string.taiwan);
        setTitle(title);
        textView1.setText(txt);
        imageView1.setImageResource(resId);
    }
}

