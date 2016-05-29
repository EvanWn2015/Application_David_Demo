package idv.david.sqliteex;

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
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class UpdateActivity extends ActionBarActivity {
    private EditText etName, etWeb, etPhone, etSpeciality;
    private ImageView ivPic;
    private TextView tvId;
    private Button btnLoadPicture, btnTakePicture, btnConfirmUpdate, btnUpdateCancel;
    private RestaurantSQLiteHelper helper;
    private byte[] pic;
    private File file;
    private final static int REQUEST_TAKE_PIC = 0;
    private final static int REQUEST_PICK_PIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        findViews();
        loadData();
    }

    private void findViews() {
        ivPic = (ImageView) findViewById(R.id.ivPic);
        tvId = (TextView) findViewById(R.id.tvId);
        etName = (EditText) findViewById(R.id.etName);
        etWeb = (EditText) findViewById(R.id.etWeb);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etSpeciality = (EditText) findViewById(R.id.etSpeciality);

        btnTakePicture = (Button)findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                file = new File(file, "picture.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                if (isIntentAvailable(UpdateActivity.this, intent)) {
                    startActivityForResult(intent, REQUEST_TAKE_PIC);
                } else {
                    Toast.makeText(UpdateActivity.this, getString(R.string.msg_NoCameraAppsFound), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLoadPicture = (Button)findViewById(R.id.btnLoadPicture);
        btnLoadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_PIC);
            }
        });

        btnConfirmUpdate = (Button)findViewById(R.id.btnConfirmUpdate);
        btnConfirmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(tvId.getText().toString());
                String name = etName.getText().toString();
                String web = etWeb.getText().toString();
                String phone = etPhone.getText().toString();
                String speciality = etSpeciality.getText().toString();

                if(name.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, getString(R.string.msg_NameIsInvalid), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pic == null) {
                    //若無圖片就使用預設的logo圖片存入，需要做drawable → Bitmap → byte[]轉換處理
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    pic = baos.toByteArray();
                }

                RestaurantVO restVO = new RestaurantVO(id, name, web, phone, speciality, pic);
                int count = helper.update(restVO);
                Toast.makeText(UpdateActivity.this, count + " " + getString(R.string.msg_RowUpdated), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnUpdateCancel = (Button)findViewById(R.id.btnUpdateCancel);
        btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void loadData() {
        if (helper == null) {
            helper = new RestaurantSQLiteHelper(this);
        }
        int id = getIntent().getExtras().getInt("id");
        RestaurantVO restVO = helper.findRestById(id);
        if(restVO == null) {
            Toast.makeText(this, getString(R.string.msg_NoDataFound), Toast.LENGTH_SHORT).show();
            return;
        }

        pic = restVO.getRest_pic();

        Bitmap bitmap = BitmapFactory.decodeByteArray(restVO.getRest_pic(), 0, restVO.getRest_pic().length);
        ivPic.setImageBitmap(bitmap);
        tvId.setText(Integer.toString(restVO.getRest_id()));
        etName.setText(restVO.getRest_name());
        etWeb.setText(restVO.getRest_web());
        etPhone.setText(restVO.getRest_phone());
        etSpeciality.setText(restVO.getRest_speciality());
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
                    if(inWidth > inHeight){
                        outWidth = maxSize;
                        outHeight = (inHeight * maxSize) / inWidth;
                    } else {
                        outHeight = maxSize;
                        outWidth = (inWidth * maxSize) / inHeight;
                    }
                    bitmapPic.createScaledBitmap(bitmapPic, outWidth, outHeight, false);
                    ivPic.setImageBitmap(bitmapPic);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmapPic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    pic = baos.toByteArray();
                    break;
                case REQUEST_PICK_PIC:
                    Uri uri = data.getData();
                    String[] columns = {MediaStore.Images.Media.DATA};
                    Cursor cursor = UpdateActivity.this.getContentResolver().query(uri, columns, null, null, null);
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
        if (helper != null) {
            helper.close();
        }
    }
}
