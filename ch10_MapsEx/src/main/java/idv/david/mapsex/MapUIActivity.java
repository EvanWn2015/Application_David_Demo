package idv.david.mapsex;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;

public class MapUIActivity extends FragmentActivity {
    private final static String TAG = "MapUIActivity";
    // 存地圖資訊
    private GoogleMap map;
    // 存地圖UI設定
    private UiSettings uiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapui);
        initMap();
        setMapType();
    }

    // 地圖初始化
    private void initMap() {
        // 檢查map物件是否存在，null就取得物件參照
        if (map == null) {
            // 從SupportMapFragment取得GoogleMap物件
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fmMap)).getMap();
            if (map != null) {
                setupMap();
            }
        }
    }

    // 地圖初始相關設定
    private void setupMap() {
        // 開啟交通資訊
        map.setTrafficEnabled(true);
        // 顯示自己位置
        map.setMyLocationEnabled(true);
        // 取得地圖UI設定物件
        uiSettings = map.getUiSettings();
    }

    // 設定地圖種類
    private void setMapType() {
        // 建立地圖種類下拉選單，讓使用者可以選取欲顯示的地圖種類
        Spinner spMapType = (Spinner) findViewById(R.id.spMapType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mapTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMapType.setAdapter(adapter);

        spMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!isMapReady()) {
                    return;
                }

                // 將地圖設定成使用者選定的種類
                String mapType = parent.getItemAtPosition(position).toString();
                if (mapType.equals(getString(R.string.normal))) {
                    // 一般圖
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                } else if (mapType.equals(getString(R.string.hybrid))) {
                    // 混合圖
                    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                } else if (mapType.equals(getString(R.string.satellite))) {
                    // 衛星圖
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else if (mapType.equals(getString(R.string.terrain))) {
                    // 地形圖
                    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                } else {
                    Log.e(TAG, getString(R.string.msg_ErrorSettings));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //onNothing so that we do nothing :)
            }
        });
    }

    // 執行與地圖有關的方法前應該先呼叫此方法以檢查GoogleMap物件是否存在
    private boolean isMapReady() {
        if (map == null) {
            Toast.makeText(this, getString(R.string.msg_MapNotReady), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 點擊「交通資訊」CheckBox
    public void onTrafficClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 顯示/隱藏交通流量
        map.setTrafficEnabled(((CheckBox) view).isChecked());
    }

    // 點擊「縮放按鈕」CheckBox
    public void onZoomControlsClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 顯示/隱藏縮放按鈕
        uiSettings.setZoomControlsEnabled(((CheckBox) view).isChecked());
    }

    // 點擊「指北針」CheckBox
    public void onCompassClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 顯示/隱藏指北針
        uiSettings.setCompassEnabled(((CheckBox) view).isChecked());
    }

    // 點擊「自己位置按鈕」CheckBox
    public void onMyLocationButtonClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 顯示/隱藏自己位置按鈕
        uiSettings.setMyLocationButtonEnabled(((CheckBox) view).isChecked());
    }

    // 點擊「自己位置圖層」CheckBox
    public void onMyLocationLayerClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 顯示/隱藏自己位置圖層，如果未開啓則自己位置按鈕也無法顯示
        map.setMyLocationEnabled(((CheckBox) view).isChecked());
    }

    // 點擊「滑動手勢」CheckBox
    public void onScrollGesturesClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 開啟/關閉地圖捲動手勢
        uiSettings.setScrollGesturesEnabled(((CheckBox) view).isChecked());
    }

    // 點擊「縮放手勢」CheckBox
    public void onZoomGesturesClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 開啟/關閉地圖縮放手勢
        uiSettings.setZoomGesturesEnabled(((CheckBox) view).isChecked());
    }

    // 點擊「傾斜手勢」CheckBox
    public void onTiltGesturesClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 開啟/關閉地圖傾斜手勢
        uiSettings.setTiltGesturesEnabled(((CheckBox) view).isChecked());
    }

    // 點擊「旋轉手勢」CheckBox
    public void onRotateGesturesClick(View view) {
        if (!isMapReady()) {
            return;
        }
        // 開啟/關閉地圖旋轉手勢
        uiSettings.setRotateGesturesEnabled(((CheckBox) view).isChecked());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMap();
    }
}
