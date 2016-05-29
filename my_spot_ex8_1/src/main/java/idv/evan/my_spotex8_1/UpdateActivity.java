package idv.evan.my_spotex8_1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by 淳彥 on 2015/10/31.
 */
public class UpdateActivity extends AppCompatActivity {
//    private int id;
    private EditText etName, etWeb, etLocation;
    private ImageView ivPic;
    private Button btnTakePicture, btnLoadPicture, btnUpdate, btnUpdateCancel;
    private SpotSQLLiteHelper helper;
    private byte[] pic;
    private File file;
    private static final int REQUEST_TAKE_PIC = 0;
    private static final int REQUEST_PICK_PIC = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        findViews();
        loadData();
    }

    private void findViews() {
        etName = (EditText) findViewById(R.id.etName);
        etWeb = (EditText) findViewById(R.id.etWeb);
        etLocation = (EditText) findViewById(R.id.etLocation);
        ivPic = (ImageView) findViewById(R.id.ivPic);

        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                file = new File(file, "picture.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                if (isIntentAvailable(UpdateActivity.this, intent)) {
                    startActivityForResult(intent, REQUEST_TAKE_PIC);
                } else {
                    Toast.makeText(UpdateActivity.this, R.string.no_camera_Apps_found, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLoadPicture = (Button) findViewById(R.id.btnLoadPicture);
        btnLoadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_PIC);
            }
        });

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String web = etWeb.getText().toString();
                String location = etLocation.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "Spot name is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pic == null) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    pic = byteArrayOutputStream.toByteArray();
                }

                SpotVO spotVO = new SpotVO(name, web, location, pic);
                long count = helper.update(spotVO);
                Toast.makeText(UpdateActivity.this, count + " ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnUpdateCancel = (Button) findViewById(R.id.btnUpdateCancel);
        btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void loadData() {
        if (helper == null) {
            helper = new SpotSQLLiteHelper(this);
        }
        int id = getIntent().getExtras().getInt("id");
        SpotVO spotVO = helper.findSpotById(id);
        if (spotVO == null) {
            Toast.makeText(this, R.string.no_date_found, Toast.LENGTH_SHORT).show();
            return;
        }

        pic = spotVO.getSpot_pic();

        Bitmap bitmap = BitmapFactory.decodeByteArray(spotVO.getSpot_pic(), 0, spotVO.getSpot_pic().length);
        ivPic.setImageBitmap(bitmap);
//        tvId.setText(Integer.toString(spotVO.getSpot_id()));
        etName.setText(spotVO.getSpot_name());
        etWeb.setText(spotVO.getSpot_web());
        etLocation.setText(spotVO.getSpot_location());
    }
}
