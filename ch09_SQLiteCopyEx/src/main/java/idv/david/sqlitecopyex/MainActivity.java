package idv.david.sqlitecopyex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ImageView ivPic;
    private TextView tvRow, tvId, tvName, tvPhoneNo, tvAddress;
    private Button btnNext, btnBack;
    private RestDBHelper helper;
    private List<Rest> restList;
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDB();
        findViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showRests(index);
    }

    private void initDB() {
        if (helper == null) {
            helper = new RestDBHelper(this);
        }

        try {
            helper.createDB();
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }
        restList = helper.getAllRests();
    }

    private void findViews() {
        ivPic = (ImageView) findViewById(R.id.iv_product);
        tvRow = (TextView) findViewById(R.id.tvRow);
        tvId = (TextView) findViewById(R.id.tvId);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhoneNo = (TextView) findViewById(R.id.tvPhoneNo);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index >= restList.size())
                    index = 0;
                showRests(index);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if (index < 0)
                    index = restList.size() - 1;
                showRests(index);
            }
        });
    }

    private void showRests(int index) {
        if (restList.size() > 0) {
            Rest rest = restList.get(index);
            byte[] image = rest.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,
                    image.length);
            ivPic.setImageBitmap(bitmap);
            tvId.setText(rest.getId());
            tvName.setText(rest.getName());
            tvPhoneNo.setText(rest.getPhoneNo());
            tvAddress.setText(rest.getAddress());
            tvRow.setText((index + 1) + "/" + restList.size());
        } else {
            tvId.setText("");
            tvName.setText("");
            tvPhoneNo.setText("");
            tvAddress.setText("");
            tvRow.setText("0/0" + getString(R.string.noData));
        }
    }

    @Override
    protected void onDestroy() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
        restList.clear();
        super.onDestroy();
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
