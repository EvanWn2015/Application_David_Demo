package idv.david.tabsex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private Toolbar myToolBar;
    private SlidingTabLayout stLayout; // 5.0後SlidingTabLayout沒放在Android SDK裡，要使用需copy下來
    private ViewPager viewPager;
    private CharSequence[] tabNames = {"最新消息", "聯絡我們"}; //這裡需要用上String.xml字庫表才可以使用多國語言
//    private int tabNums = 2;
    private  int tabNums = tabNames.length; // 這樣比較動態

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        myToolBar = (Toolbar) findViewById(R.id.myToolBar);
        myToolBar.setTitle(getString(R.string.toolbar_title));
        setSupportActionBar(myToolBar);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabNames, tabNums));
        stLayout = (SlidingTabLayout)findViewById(R.id.stLayout);
        stLayout.setDistributeEvenly(true); //填滿SlidingTabLayout 所設定的寬度
        stLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        stLayout.setViewPager(viewPager);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter { // 資料少量可考慮使用FragmentPagerAdapter
        private CharSequence[] tabNames;
        private int tabNums;

        public PagerAdapter(FragmentManager fm, CharSequence[] tabNames, int tabNums) {
            super(fm);
            this.tabNames = tabNames;
            this.tabNums = tabNums;
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0)
                return new TabOneFragment();
            else
                return new TabTwoFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }

        @Override
        public int getCount() {
            return tabNums;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
