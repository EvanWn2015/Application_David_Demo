package idv.david.sqliteex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ImageView ivPic;
    private TextView tvRowCount, tvId, tvName, tvWeb, tvPhone, tvSpeciality;
    private Button btnBack, btnNext, btnInsert, btnUpdate, btnDelete;
    private List<RestaurantVO> restList;
    private int index;
    private RestaurantSQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        if (helper == null) {
            helper = new RestaurantSQLiteHelper(this);
        }
    }

    private void findViews() {
        ivPic = (ImageView) findViewById(R.id.ivPic);
        tvRowCount = (TextView) findViewById(R.id.tvRowCount);
        tvId = (TextView) findViewById(R.id.tvId);
        tvName = (TextView) findViewById(R.id.tvName);
        tvWeb = (TextView) findViewById(R.id.tvWeb);
        tvWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String web = tvWeb.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                startActivity(intent);
            }
        });
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = tvPhone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });
        tvSpeciality = (TextView) findViewById(R.id.tvSpeciality);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if (index >= restList.size())
                    index = 0;
                showRest(index);
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                if (index < 0)
                    index = restList.size() - 1;
                showRest(index);
            }
        });

        btnInsert = (Button) findViewById(R.id.btnConfirmInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restList.size() <= 0) {
                    Toast.makeText(MainActivity.this, getString(R.string.msg_NoDataFound), Toast.LENGTH_SHORT).show();
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
            public void onClick(View view) {
                if (restList.size() <= 0) {
                    Toast.makeText(MainActivity.this, getString(R.string.msg_NoDataFound), Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = Integer.parseInt(tvId.getText().toString());
                int count = helper.deleteById(id);
                Toast.makeText(MainActivity.this, count + " " + getString(R.string.msg_RowDeleted), Toast.LENGTH_SHORT).show();
                restList = helper.getAll();
                showRest(0);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        restList = helper.getAll();
        showRest(0);
    }

    private void showRest(int index) {
        if (restList.size() > 0) {
            RestaurantVO restVO = restList.get(index);
            Bitmap bitmap = BitmapFactory.decodeByteArray(restVO.getRest_pic(), 0, restVO.getRest_pic().length);
            ivPic.setImageBitmap(bitmap);
            tvId.setText(Integer.toString(restVO.getRest_id()));
            tvName.setText(restVO.getRest_name());
            tvWeb.setText(restVO.getRest_web());
            tvPhone.setText(restVO.getRest_phone());
            tvSpeciality.setText(restVO.getRest_speciality());
            tvRowCount.setText((index + 1) + "/" + restList.size());
        } else {
            ivPic.setImageResource(R.drawable.logo);
            tvId.setText("");
            tvName.setText("");
            tvWeb.setText("");
            tvPhone.setText("");
            tvSpeciality.setText("");
            tvRowCount.setText(" 0/0 " + getString(R.string.msg_NoDataFound));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null)
            helper.close();

    }
}
