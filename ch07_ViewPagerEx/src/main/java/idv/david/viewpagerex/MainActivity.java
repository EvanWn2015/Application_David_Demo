package idv.david.viewpagerex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private List<TeamVO> teamList;
    private ViewPager myViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teamList = getTeamList();
        myViewPager = (ViewPager)findViewById(R.id.myViewPager);
        TeamAdapter teamAdapter = new TeamAdapter(getSupportFragmentManager(), teamList);
        myViewPager.setAdapter(teamAdapter);
    }

    private List<TeamVO> getTeamList() {
        List<TeamVO> teamList = new ArrayList<>();
        teamList.add(new TeamVO(R.drawable.p1, "巴爾的摩金鶯"));
        teamList.add(new TeamVO(R.drawable.p2, "芝加哥白襪"));
        teamList.add(new TeamVO(R.drawable.p3, "洛杉磯天使"));
        teamList.add(new TeamVO(R.drawable.p4, "波士頓紅襪"));
        teamList.add(new TeamVO(R.drawable.p5, "克里夫蘭印地安人"));
        teamList.add(new TeamVO(R.drawable.p6, "奧克蘭運動家"));
        teamList.add(new TeamVO(R.drawable.p7, "紐約洋基"));
        teamList.add(new TeamVO(R.drawable.p8, "底特律老虎"));
        teamList.add(new TeamVO(R.drawable.p9, "西雅圖水手"));
        teamList.add(new TeamVO(R.drawable.p10, "坦帕灣光芒"));
        return teamList;
    }

    private class TeamAdapter extends FragmentStatePagerAdapter { // 資料太多這個類別會自動保留前一個跟後一個，其餘先刪除
        private List<TeamVO> teamList;

        private TeamAdapter(FragmentManager fm, List<TeamVO> teamList) { // 必須寫入
            super(fm);
            this.teamList = teamList;
        }

        @Override
        public Fragment getItem(int position) {
            return TeamFragment.newInstance(teamList.get(position));
        }

        @Override
        public int getCount() {
            return teamList.size();
        }
    }

    public void onFirstClick(View view) {
        myViewPager.setCurrentItem(0);
    }

    public void onLastClick(View view) {
        myViewPager.setCurrentItem(teamList.size() - 1);
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
