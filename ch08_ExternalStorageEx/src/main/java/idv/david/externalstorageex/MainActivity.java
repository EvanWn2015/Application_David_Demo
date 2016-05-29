package idv.david.externalstorageex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {
    private final static String TAG = "MainActivity";
    private final static String FILE_NAME_PUBLIC = "al.png";
    private final static String FILE_NAME_PRIVATE = "nl.png";
    private Button btnSavePublic, btnSavePrivate, btnLoadPublic, btnLoadPrivate;
    private ImageView ivPicture;
    private TextView tvMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        ivPicture = (ImageView) findViewById(R.id.ivPicture);
        btnSavePublic = (Button) findViewById(R.id.btnSavePublic);
        btnSavePublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                saveFile(dir, FILE_NAME_PUBLIC);
            }
        });

        btnLoadPublic = (Button)findViewById(R.id.btnLoadPublic);
        btnLoadPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                loadFile(dir, FILE_NAME_PUBLIC);
            }
        });

        btnSavePrivate = (Button)findViewById(R.id.btnSavePrivate);
        btnSavePrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                saveFile(dir, FILE_NAME_PRIVATE);
            }
        });

        btnLoadPrivate = (Button)findViewById(R.id.btnLoadPrivate);
        btnLoadPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                loadFile(dir, FILE_NAME_PRIVATE);
            }
        });

    }

    private void saveFile(File dir, String fileName) {
        if (!isStorageMounted()) {
            Toast.makeText(this, getString(R.string.msg_ExternalStorageNotFound), Toast.LENGTH_SHORT).show();
            return;
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            // 若此資料夾不存在
            if (!dir.exists()) {
                // 若此資料夾沒被建立
                if (!dir.mkdirs()) {
                    Toast.makeText(this, getString(R.string.msg_DirectoryNotCreated), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            is = getAssets().open(fileName);
            File file = new File(dir, fileName);
            os = new FileOutputStream(file);
            byte[] data = new byte[is.available()];
            while (is.read(data) != -1) {
                os.write(data);
            }
            tvMessage.setText(getString(R.string.msg_SavedFilePath) + "\n" + file.toString());

        } catch (IOException ie) {
            Log.e(TAG, ie.toString());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException ie) {
                Log.e(TAG, ie.toString());
            }
        }
    }

    private void loadFile(File dir, String fileName) {
        if(!isStorageMounted()) {
            Toast.makeText(this, getString(R.string.msg_ExternalStorageNotFound), Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(dir, fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
        if(bitmap != null) {
            ivPicture.setImageBitmap(bitmap);
            tvMessage.setText(getString(R.string.msg_LoadedFilePath) + "\n" + file.toString());
        } else {
            ivPicture.setImageResource(R.drawable.not_found);
            Toast.makeText(this, getString(R.string.msg_FileNotFound), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isStorageMounted() {
        String result = Environment.getExternalStorageState();
        // MEDIA_MOUNTED代表可對外部媒體進行存取
        return result.equals(Environment.MEDIA_MOUNTED);
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
