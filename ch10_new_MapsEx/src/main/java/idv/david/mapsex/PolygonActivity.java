package idv.david.mapsex;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class PolygonActivity extends FragmentActivity {
    // 存地圖資訊
    private GoogleMap map;
    // 各地標記：太魯閣、玉山、墾丁、陽明山
    private Marker marker_taroko, marker_yushan, marker_kenting, marker_yangmingshan;
    // 各地緯經度：太魯閣、玉山、墾丁、陽明山
    private LatLng taroko, yushan, kenting, yangmingshan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon);
        initPoints();
    }

    // 初始化所有地點的緯經度
    private void initPoints() {
        taroko = new LatLng(24.151287, 121.625537);
        yushan = new LatLng(23.791952, 120.861379);
        kenting = new LatLng(21.985712, 120.813217);
        yangmingshan = new LatLng(25.091075, 121.559834);
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
        CameraPosition cameraPosition = new CameraPosition.Builder()
                // 鏡頭焦點在玉山
                .target(yushan)
                        // 地圖縮放層級定為7
                .zoom(7)
                .build();
        // 改變鏡頭焦點到指定的新地點
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);

        addMarkersToMap();

        map.setInfoWindowAdapter(new MyInfoWindowAdapter());
        MyMarkerListener listener = new MyMarkerListener();
        // 註冊OnMarkerClickListener，當標記被點擊時會自動呼叫該Listener的方法
        map.setOnMarkerClickListener(listener);
        // 註冊OnInfoWindowClickListener，當標記訊息視窗被點擊時會自動呼叫該Listener的方法
        map.setOnInfoWindowClickListener(listener);

        addPolygonsToMap();
    }

    // 在地圖上加入多個標記
    private void addMarkersToMap() {
        marker_taroko = map.addMarker(new MarkerOptions()
                .position(taroko)
                .title(getString(R.string.marker_title_taroko))
                .snippet(getString(R.string.marker_snippet_taroko))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        marker_yushan = map.addMarker(new MarkerOptions()
                .position(yushan)
                .title(getString(R.string.marker_title_yushan))
                .snippet(getString(R.string.marker_snippet_yushan))
                .draggable(true));

        marker_kenting = map.addMarker(new MarkerOptions()
                .position(kenting)
                .title(getString(R.string.marker_title_kenting))
                .snippet(getString(R.string.marker_snippet_kenting))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        marker_yangmingshan = map.addMarker(new MarkerOptions()
                .position(yangmingshan)
                .title(getString(R.string.marker_title_yangmingshan))
                .snippet(getString(R.string.marker_snippet_yangmingshan))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    private void addPolygonsToMap() {
        // 連續線
        Polyline polyline = map.addPolyline(new PolylineOptions()
                        // 加入頂點，會依照加入順序繪製直線
                .add(yushan, yangmingshan, taroko)
                        // 設定線的粗細(像素)，預設為10像素
                .width(5)
                        // 設定線的顏色(ARGB)，預設為黑色
                .color(Color.BLUE)
                        // 與其他圖形在Z軸上的高低順序，預設為0
                        // 數字大的圖形會蓋掉小的圖形
                .zIndex(1));
        // 可以利用Polyline物件改變原來屬性設定
        polyline.setWidth(6);

        // 多邊形
        map.addPolygon(new PolygonOptions()
                        // 加入頂點
                .add(yushan, taroko, kenting)
                        // 設定外框線的粗細(像素)，預設為10像素
                .strokeWidth(5)
                        // 設定外框線的顏色(ARGB)，預設為黑色
                .strokeColor(Color.MAGENTA)
                        // 設定填充的顏色(ARGB)，預設為黑色
                .fillColor(Color.argb(200, 100, 150, 0)));

        // 圓形
        map.addCircle(new CircleOptions()
                        // 必須設定圓心，因為沒有預設值
                .center(yushan)
                        // 半徑長度(公尺)
                .radius(100000)
                        // 設定外框線的粗細(像素)，預設為10像素
                .strokeWidth(5)
                        // 顏色為TRANSPARENT代表完全透明
                .strokeColor(Color.TRANSPARENT)
                        // 設定填充的顏色(ARGB)，預設為黑色
                .fillColor(Color.argb(100, 0, 0, 100)));
    }


    // 自訂InfoWindowAdapter，當點擊標記時會跳出自訂風格的訊息視窗
    private class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View infoWindow;

        MyInfoWindowAdapter() {
            infoWindow = LayoutInflater.from(PolygonActivity.this)
                    .inflate(R.layout.custom_infowindow, null);
        }

        @Override
        // 回傳設計好的訊息視窗樣式
        // 回傳null會自動呼叫getInfoContents(Marker)
        public View getInfoWindow(Marker marker) {
            int logoId;
            // 使用equals()方法檢查2個標記是否相同，千萬別用「==」檢查
            if (marker.equals(marker_yangmingshan)) {
                logoId = R.drawable.logo_yangmingshan;
            } else if (marker.equals(marker_taroko)) {
                logoId = R.drawable.logo_taroko;
            } else if (marker.equals(marker_yushan)) {
                logoId = R.drawable.logo_yushan;
            } else if (marker.equals(marker_kenting)) {
                logoId = R.drawable.logo_kenting;
            } else {
                // 呼叫setImageResource(int)傳遞0則不會顯示任何圖形
                logoId = 0;
            }

            // 顯示圖示
            ImageView ivLogo = ((ImageView) infoWindow.findViewById(R.id.ivLogo));
            ivLogo.setImageResource(logoId);

            // 顯示標題
            String title = marker.getTitle();
            TextView tvTitle = ((TextView) infoWindow.findViewById(R.id.tvTitle));
            tvTitle.setText(title);

            // 顯示描述
            String snippet = marker.getSnippet();
            TextView tvSnippet = ((TextView) infoWindow.findViewById(R.id.tvSnippet));
            tvSnippet.setText(snippet);

            return infoWindow;
        }

        @Override
        // 當getInfoWindow(Marker)回傳null時才會呼叫此方法
        // 此方法如果再回傳null，代表套用預設視窗樣式
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

    private class MyMarkerListener implements GoogleMap.OnMarkerClickListener,
            GoogleMap.OnInfoWindowClickListener {

        @Override
        // 點擊地圖上的標記
        public boolean onMarkerClick(Marker marker) {
            return false;
        }

        @Override
        // 點擊標記的訊息視窗
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(PolygonActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
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
        addMarkersToMap();
        addPolygonsToMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMap();
    }
}
