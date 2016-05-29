package idv.david.flexiblefragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.content.res.Configuration;
import android.os.Bundle;

public class InfoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //取得螢幕目前為landscape或portrait
        Configuration configuration = getResources().getConfiguration();
        //若為橫向就不需要此Activity，馬上結束
        if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {  //判斷是否為水平
            finish();
        }

        int position = this.getIntent().getExtras().getInt("position");
        InfoFragment infoFragment = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        infoFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frameLayout, infoFragment).commit();
    }
}
