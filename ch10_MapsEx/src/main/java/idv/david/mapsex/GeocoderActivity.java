package idv.david.mapsex;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class GeocoderActivity extends FragmentActivity {
    private final static String TAG = "GeocoderActivity";
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geocoder);
        initMap();
    }

    // 初始化地圖
    private void initMap() {
        // 檢查GoogleMap物件是否存在
        if (map == null) {
            // 從SupportMapFragment取得GoogleMap物件
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fmMap)).getMap();
            if (map != null) {
                setupMap();
            }
        }
    }

    private void setupMap() {
        map.setMyLocationEnabled(true);
    }

    // 執行與地圖有關的方法前應該先呼叫此方法以檢查GoogleMap物件是否存在
    private boolean isMapReady() {
        if (map == null) {
            Toast.makeText(this, getString(R.string.msg_MapNotReady), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 按下確定按鈕
    public void onLocationNameClick(View view) {
        if (!isMapReady()) {
            return;
        }

        // 檢查使用者是否輸入地名/地址
        EditText etLocationName = (EditText) findViewById(R.id.etLocationName);
        String locationName = etLocationName.getText().toString().trim();
        if (locationName.length() > 0) {
            locationNameToMarker(locationName);
        } else {
            Toast.makeText(this, getString(R.string.msg_LocationNameIsEmpty), Toast.LENGTH_SHORT).show();
        }
    }

    // 將地名或地址轉成位置後在地圖打上對應標記
    private void locationNameToMarker(String locationName) {
        // 增加新標記前，先清除舊有標記
        map.clear();
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;
        int maxResults = 1;
        try {
            // 解譯地名/地址後可能產生多筆位置資訊，所以回傳List<Address>
            // 將maxResults設為1，限定只回傳1筆
            addressList = geocoder.getFromLocationName(locationName, maxResults);

            // 如果無法連結到提供服務的伺服器，會拋出IOException
        } catch (IOException ie) {
            Log.e(TAG, ie.toString());
        }

        if(addressList == null || addressList.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_LocationNameNotFound), Toast.LENGTH_SHORT).show();
        } else {
            // 因為當初限定只回傳1筆，所以只要取得第1個Address物件即可
            Address address = addressList.get(0);

            // Address物件可以取出緯經度並轉成LatLng物件
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            // 將地址取出當作標記的描述文字
            String snippet = address.getAddressLine(0);

            // 將地名或地址轉成位置後在地圖打上對應標記
            map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(locationName)
                .snippet(snippet));

            // 將鏡頭焦點設定在使用者輸入的地點上
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(15)
                    .build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMap();
    }
}
