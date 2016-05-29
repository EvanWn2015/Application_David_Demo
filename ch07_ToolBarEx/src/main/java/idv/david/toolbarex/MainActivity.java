package idv.david.toolbarex;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private Toolbar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        myToolBar = (Toolbar) findViewById(R.id.myToolBar);
        myToolBar.setLogo(R.mipmap.ic_launcher);
        myToolBar.setTitle(getString(R.string.toolbar_title));
        myToolBar.setSubtitle(getString(R.string.toolbar_subtitle));
        setSupportActionBar(myToolBar);
        // Navigation Icon要設定在setSupoortActionBar之後才有作用，否則會出現back bottom
        myToolBar.setNavigationIcon(R.drawable.android);
        // Menu item click的監聽事件一樣要設定在setSupportActionBar之後才有作用
        myToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() { // 跟menu監聽事件處理一樣
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String msg = "";
                switch (menuItem.getItemId()) {
                    case R.id.action_search:
                        msg += "Click search";
                        break;
                    case R.id.action_share:
                        msg += "Click share";
                        break;
                    case R.id.action_settings:
                        msg += "Click setting";
                        break;
                }
                if (!msg.equals("")) {
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓Toolbar的 Menu有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
