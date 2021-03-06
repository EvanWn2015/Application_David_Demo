package idv.david.listviewex;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ListView lvTeam;
    private List<Team> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    public void findViews() {
        lvTeam = (ListView)findViewById(R.id.lvTeam);
        // 呼叫setAdapter()方法設定掌控ListView內容物的Adapter
        lvTeam.setAdapter(new TeamAdapter(this));
        // 使用者點選ListView上的任一列時會呼叫onItemClick()方法並傳遞view參數，
        // view參數代表被點選的View元件，也就是TeamAdapter.getView()的convertView參數，
        // 在此為list_item.xml內的LinearLayout元件
        lvTeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = (Team)parent.getItemAtPosition(position);
                String info = team.getId() + " " + team.getName();
                Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class TeamAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public TeamAdapter(Context context) {
            // teamList儲存ListView各列對應的資料
            teamList = new ArrayList<>();
            teamList.add(new Team(1, R.drawable.p1, "巴爾的摩金鶯"));
            teamList.add(new Team(2, R.drawable.p2, "芝加哥白襪"));
            teamList.add(new Team(3, R.drawable.p3, "洛杉磯天使"));
            teamList.add(new Team(4, R.drawable.p4, "波士頓紅襪"));
            teamList.add(new Team(5, R.drawable.p5, "克里夫蘭印地安人"));
            teamList.add(new Team(6, R.drawable.p6, "奧克蘭運動家"));
            teamList.add(new Team(7, R.drawable.p7, "紐約洋基"));
            teamList.add(new Team(8, R.drawable.p8, "底特律老虎"));
            teamList.add(new Team(9, R.drawable.p9, "西雅圖水手"));
            teamList.add(new Team(10, R.drawable.p10, "坦帕灣光芒"));
            // 呼叫getSystemService()方法取得LayoutInflater物件，
            // 可以透過該物件取得指定layout檔案內容後初始化成View物件
            layoutInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            // 一樣做法
//            layoutInflater = LayoutInflater.from(context);

        }

        @Override
        // ListView總列數
        public int getCount() {
            return teamList.size();
        }


        @Override
        public Object getItem(int position) {
            return teamList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return teamList.get(position).getId();
        }

        @Override
        // 依照position回傳該列資料所需呈現的UI畫面(View)
        public View getView(int position, View convertView, ViewGroup parent) {
            // 一個convertView就是ListView一列資料的畫面，
            // 因為每一列資料外觀都一樣，只有資料值不同，所以載入相同layout檔案，
            // 第一次還未載入layout，所以必須呼叫layoutInflater.inflate()載入layout檔案並指派給convertView
            ViewHolder holder;
            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.ivLogo = (ImageView)convertView.findViewById(R.id.ivLogo);
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
                holder.tvId = (TextView)convertView.findViewById(R.id.tvId);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            // 依照position取得teamList內的team物件
            Team team = teamList.get(position);
            holder.ivLogo.setImageResource(team.getLogo());
            holder.tvName.setText(team.getName());
            holder.tvId.setText(Integer.toString(team.getId()));

            return convertView;

        }

        private class ViewHolder{
            ImageView ivLogo;
            TextView tvName, tvId;
        }

    };




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
