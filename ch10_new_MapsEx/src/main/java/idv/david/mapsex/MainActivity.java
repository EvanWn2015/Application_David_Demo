package idv.david.mapsex;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

// ListActivity內容即為ListView，點擊每個選項列會開啓一個範例
public class MainActivity extends ListActivity {
    private List<MapPage> mapPagesList;

    private class MapPage {
        // 新頁面的標題id
        private int pageId;
        // 新頁面屬於哪種類別
        private Class<? extends FragmentActivity> mapActivity;

        public MapPage(int pageId, Class<? extends FragmentActivity> mapActivity) {
            this.pageId = pageId;
            this.mapActivity = mapActivity;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapPagesList = new ArrayList<>();
        // 「基本Google地圖展示」範例
        mapPagesList.add(new MapPage(R.string.title_BasicMap, BasicMapActivity.class));
        // 「地圖種類與操作界面設定」範例
        mapPagesList.add(new MapPage(R.string.title_MapUi, MapUIActivity.class));
        // 「在地圖上做標記」範例
        mapPagesList.add(new MapPage(R.string.title_Markers, MarkerActivity.class));
        // 「在地圖上繪製直線與多邊形」範例
        mapPagesList.add(new MapPage(R.string.title_Polygons, PolygonActivity.class));
        // 「將地址或地名轉成地圖上的標記」範例
        mapPagesList.add(new MapPage(R.string.title_Geocoder, GeocoderActivity.class));
        // 「點擊地圖建立標記」範例
        mapPagesList.add(new MapPage(R.string.title_MapClick, MapClickActivity.class));

        // 將各個範例標題文字存入List後套用在ListView選項列上
        List<String> titleList = new ArrayList<>();
        for(MapPage mp : mapPagesList) {
            titleList.add(getString(mp.pageId));
        }
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titleList));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // 點擊ListView選項列後即開啓對應的Activity頁面
        MapPage mapPage = mapPagesList.get(position);
        Intent intent = new Intent(this, mapPage.mapActivity);
        startActivity(intent);
    }
}
