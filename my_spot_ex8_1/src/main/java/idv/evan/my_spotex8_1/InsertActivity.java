package idv.evan.my_spotex8_1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
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
public class InsertActivity extends AppCompatActivity {
    private EditText etName, etWeb, etLocation;
    private ImageView ivPic;
    private Button btnTakePicture, btnLoadPicture, btnInsert, btnInsertCancel;
    private SpotSQLLiteHelper helper;
    private byte[] pic;
    private File file;
    private static final int REQUEST_TAKE_PIC = 0;
    private static final int REQUEST_PICK_PIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        findViews();
        if (helper == null) {
            helper = new SpotSQLLiteHelper(this);
        }
    }

    public void findViews() {
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
                if (isIntentAvailable(InsertActivity.this, intent)) {
                    startActivityForResult(intent, REQUEST_TAKE_PIC);
                } else {
                    Toast.makeText(InsertActivity.this, R.string.no_camera_Apps_found, Toast.LENGTH_SHORT).show();
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
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String web = etWeb.getText().toString();
                String location = etLocation.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(InsertActivity.this, "Spot name is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pic == null) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    pic = baos.toByteArray();
                }

                SpotVO spotVO = new SpotVO(name, web, location, pic);
                long id = helper.insert(spotVO);

                if (id != -1) {
                    Toast.makeText(InsertActivity.this, R.string.insert_successfully, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InsertActivity.this, R.string.insert_fail, Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });
        btnInsertCancel = (Button) findViewById(R.id.btnInsertCancel);
        btnInsertCancel.setOnClickListener(new View.OnClickListener() {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PIC:
                    Bitmap bitmapPic = BitmapFactory.decodeFile(file.getPath());
                    final int maxSize = 640;  //dp
                    int outWidth;
                    int outHeight;
                    int inWidth = bitmapPic.getWidth();
                    int inHeight = bitmapPic.getHeight();
                    if (inWidth > inHeight) {
                        outWidth = maxSize;
                        outHeight = (inHeight * maxSize) / inWidth;
                    } else {
                        outHeight = maxSize;
                        outWidth = (inWidth * maxSize) / inHeight;
                    }
                    bitmapPic = Bitmap.createScaledBitmap(bitmapPic, outWidth, outHeight, false);
                    ivPic.setImageBitmap(bitmapPic);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmapPic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    pic = baos.toByteArray();
                    break;
                case REQUEST_PICK_PIC:
                    Uri uri = data.getData();
                    String[] columns = {MediaStore.Images.Media.DATA};
                    Cursor cursor = InsertActivity.this.getContentResolver().query(uri, columns, null, null, null);
                    cursor.moveToNext();
                    String imgPath = cursor.getString(0);
                    cursor.close();
                    Bitmap bitmapPic2 = BitmapFactory.decodeFile(imgPath);
                    ivPic.setImageBitmap(bitmapPic2);
                    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                    bitmapPic2.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
                    pic = baos2.toByteArray();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null)
            helper.close();
    }
}
