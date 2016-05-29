package idv.hangermo.okhttpex;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;


public class MainActivity extends Activity {
    private OkHttpClient client;
    private String[] imageUrls;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageUrls = getResources().getStringArray(R.array.image_urls_get);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        client = new OkHttpClient();
    }

    public void onClick(View view) {
        linearLayout.removeAllViews();
        for(String url : imageUrls) {
            ImageView imageView = new ImageView(this);
            ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setMax(100);

            linearLayout.addView(imageView);
            linearLayout.addView(progressBar);

            new LoadPicture(url, imageView, progressBar).start();
        }
    }

    private class LoadPicture extends Thread {
        private String image_url;
        private ImageView imageView;
        private ProgressBar progressBar;

        private Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // 取得 msg 所帶入的 object 參數
                double sum = (Double)msg.obj;
                progressBar.setProgress((int)sum);
            }

        };

        private Bitmap bitmap = null;
        private InputStream inputStream = null;
        private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        public LoadPicture(String image_url, ImageView imageView, ProgressBar progressBar) {
            this.image_url = image_url;
            this.imageView = imageView;
            this.progressBar = progressBar;
        }

        public void run() {
            try {
                URL url = new URL(image_url);

                // 替換成 OkHttp 2.0 ---------------------------------------
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                inputStream = response.body().byteStream();
                // 總長度
                double fullSize = response.body().contentLength();
                //---------------------------------------------------------

                // buffer (每次讀取長度)
                byte[] buffer = new byte[64];
                // 當下讀取長度
                int readSize = 0;

                double sum = 0;
                while ((readSize = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readSize);
                    // 累計讀取進度
                    sum += (readSize / fullSize) * 100;
                    Message message = handler.obtainMessage(1, sum);
                    handler.sendMessage(message);
                }

                // 將 outputStream 轉 byte[] 再轉 Bitmap
                byte[] result = outputStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);

                // 資料處理完畢修改 View
                handler.post(new Runnable() {
                    public void run() {
                        // 將 Bitmap 注入 ImageView
                        imageView.setImageBitmap(bitmap);
                        imageView.setAdjustViewBounds(true);
                        linearLayout.removeView(progressBar);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch(Exception e) {

                }
                //setTitle("完成");
            }
        }

    }


}
