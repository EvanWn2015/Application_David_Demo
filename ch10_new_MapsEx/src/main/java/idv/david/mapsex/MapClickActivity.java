package idv.david.mapsex;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapClickActivity extends FragmentActivity {
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapclick);
        initMap();
        // 地圖點擊事件處理
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // 建立標記內容
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(latLng.latitude + " : " + latLng.longitude);
                // 將地圖先前的資料清空一遍
                map.clear();
                // 畫面移動到根據點擊所取得的緯經度座標
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                // 加入標記
                map.addMarker(options);
            }
        });
    }

    // 初始化地圖
    private void initMap() {
        // 檢查GoogleMap物件是否存在
        if (map == null) {
            // 從SupportMapFragment取得GoogleMap物件
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fmMap)).getMap();
        }
    }

    private boolean isMapReady() {
        if (map == null) {
            Toast.makeText(this, getString(R.string.msg_MapNotReady), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 按下「清除」按鈕清除所有標記
    public void onClearMapClick(View view) {
        if (!isMapReady()) {
            return;
        }
        map.clear();
    }

    // 按下「重置」按鈕重新打上標記
    public void onResetMapClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 先清除Map上的標記再重新標記以避免重複標記
        map.clear();
    }
}
