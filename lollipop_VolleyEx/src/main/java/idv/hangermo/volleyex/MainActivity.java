package idv.hangermo.volleyex;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends Activity {

    private ImageView ivPic; // 宣告 ImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPic = (ImageView) findViewById(R.id.ivPic);
    }

    // Button 監聽器
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                ivPic.setVisibility(View.INVISIBLE);
                RequestQueue mQueue = Volley.newRequestQueue(this);

                /*
                    Constructor:
                    public ImageRequest(
                    String url,
                    Response.Listener<Bitmap> listener,
                    int maxWidth,
                    int maxHeight,
                    Config decodeConfig,
                    Response.ErrorListener errorListener)

                    params:
                    url - URL of the image
                    listener - Listener to receive the decoded bitmap
                    maxWidth - Maximum width to decode this bitmap to, or zero for none
                    maxHeight - Maximum height to decode this bitmap to, or zero for none
                    decodeConfig - Format to decode the bitmap to
                    errorListener - Error listener, or null to ignore errors
                 */


                ImageRequest imageRequest = new ImageRequest(
                        getResources().getString(R.string.image_url),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                ivPic.setVisibility(View.VISIBLE);
                                ivPic.setImageBitmap(response);
                            }
                        }, 0, 0,
                        Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                ivPic.setImageResource(R.drawable.android_icon);
                            }
                        });
                mQueue.add(imageRequest);

                break;
        }

    }
}
