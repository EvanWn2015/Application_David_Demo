package idv.evan.my_spotex8_1;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView ivPic;
    private TextView tvRowCount, tvId, tvName, tvWeb, tvLocation;
    private Button btnBack, btnNext, btnSearchSQL, btnInsert, btnUpdate, btnDelete;
    private Dialog searchSQLDialog;
    private List<SpotVO> spotList;
    private int index;
    private SpotSQLLiteHelper helper;

    private EditText etSearchID;
    private Button btnSearch, btnCancelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        if (helper == null) {
            helper = new SpotSQLLiteHelper(this);
        }
    }

    public void findViews() {
        ivPic = (ImageView) findViewById(R.id.ivPic);
        tvRowCount = (TextView) findViewById(R.id.tvRowCount);
        tvId = (TextView) findViewById(R.id.tvId);
        tvName = (TextView) findViewById(R.id.tvName);
        tvWeb = (TextView) findViewById(R.id.tvWeb);
        tvWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web = tvWeb.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                startActivity(intent);
            }
        });
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if (index < 0)
                    index = spotList.size();
                showSpot(index);
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index >= spotList.size())
                    index = 0;
                showSpot(index);
            }
        });

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spotList.size() <= 0) {
                    Toast.makeText(MainActivity.this, R.string.toast_no_data, Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = Integer.parseInt(tvId.getText().toString());
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spotList.size() <= 0) {
                    Toast.makeText(MainActivity.this, R.string.toast_no_data, Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = Integer.parseInt(tvId.getText().toString());
                int count = helper.deleteSpotById(id);
                Toast.makeText(MainActivity.this, count + " ", Toast.LENGTH_SHORT).show();
                spotList = helper.getAll();
                showSpot(0);
            }
        });

//查詢資料Dialog
        btnSearchSQL = (Button) findViewById(R.id.btnSearchSQL);
        btnSearchSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spotList.size() <= 0) {
                    Toast.makeText(MainActivity.this, R.string.toast_no_data_search, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    searchSQL();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        spotList = helper.getAll();
        showSpot(0);
    }

    private void showSpot(int index) {
        if (spotList.size() > 0) {
            SpotVO spotVO = spotList.get(index);
            Bitmap bitmap = BitmapFactory.decodeByteArray(spotVO.getSpot_pic(), 0, spotVO.getSpot_pic().length);

            ivPic.setImageBitmap(bitmap);
            tvRowCount.setText((index + 1) + "/" + spotList.size());
            tvId.setText(Integer.toString(spotVO.getSpot_id()));
            tvName.setText(spotVO.getSpot_name());
            tvWeb.setText(spotVO.getSpot_web());
            tvLocation.setText(spotVO.getSpot_location());
        } else {
            ivPic.setImageResource(R.drawable.logo);
            tvRowCount.setText("0/0  " + getString(R.string.no_date_found));
            tvId.setText("");
            tvName.setText("");
            tvWeb.setText("");
            tvLocation.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null)
            helper.close();
    }

    private void searchSQL() {
        searchSQLDialog = new Dialog(MainActivity.this);
        searchSQLDialog.setTitle(R.string.dialog_Title);
        searchSQLDialog.setCancelable(false);
        searchSQLDialog.setContentView(R.layout.activity_search);

        Window dialogWindow = searchSQLDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.height = (int) (display.getHeight() * 0.6);
        layoutParams.width = (int) (display.getWidth() * 0.8);
        dialogWindow.setAttributes(layoutParams);

        btnSearch = (Button) searchSQLDialog.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearchID = (EditText) searchSQLDialog.findViewById(R.id.etSearchID);
                int searchID = Integer.parseInt(etSearchID.getText().toString());

                if (searchID <= 0) {
                    searchSQLDialog.cancel();
                } else {
                    helper.findSpotById(searchID - 1);
                    searchSQLDialog.cancel();
                }
            }
        });

        btnCancelable = (Button) searchSQLDialog.findViewById(R.id.btnCancelable);
        btnCancelable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSQLDialog.cancel();
            }
        });
        searchSQLDialog.show();

    }


}

