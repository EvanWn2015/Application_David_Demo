package idv.hangermo.asynctaskex;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {
    private ImageView imageView1;
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        myAsyncTask = new MyAsyncTask();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                imageView1.setImageBitmap(null);
                String image_url = getResources().getString(R.string.image_url_get);
                if (!myAsyncTask.isCancelled()) {
                    myAsyncTask.execute(image_url);
                } else {
                    Toast.makeText(this, "AsyncTask 已被取消 !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button2:
                myAsyncTask.cancel(true);
                break;
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        private Bitmap bitmap = null;
        private InputStream inputStream = null;
        private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "onPreExecute()", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            setTitle(values[0] + " %");
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                inputStream = conn.getInputStream();
                // 總長度
                double fullSize = conn.getContentLength();
                // buffer (每次讀取長度)
                byte[] buffer = new byte[64];
                int readSize = 0; // 當下讀取長度

                double sum = 0;
                while ((readSize = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readSize);
                    // 累計讀取進度
                    sum += (readSize / fullSize) * 100;
                    publishProgress((int) sum);
                }

                // 將 outputStream 轉 byte[] 再轉 Bitmap
                byte[] result = outputStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (Exception e) {
                    // handle exception here
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView1.setImageBitmap(bitmap);
            imageView1.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled(Bitmap result) {
            Toast.makeText(MainActivity.this, "onCancelled (Bitmap result)",
                    Toast.LENGTH_SHORT).show();
            super.onCancelled(result);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(MainActivity.this, "onCancelled()", Toast.LENGTH_SHORT).show();
            super.onCancelled();
        }
    }

}
