package idv.david.recyclerviewex;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private RecyclerView myRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private TeamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        myRecyclerview = (RecyclerView)findViewById(R.id.myRecyclerview);
        //設定每個List是否為固定尺寸
        myRecyclerview.setHasFixedSize(true);
        //產生一個LinearLayoutManger
        layoutManager = new LinearLayoutManager(this);
        //設定LayoutManger
        myRecyclerview.setLayoutManager(layoutManager);

        final List<Team> teamList = new ArrayList<>();
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

        adapter = new TeamAdapter(teamList);
        myRecyclerview.setAdapter(adapter);
    }

    private class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>{
        private List<Team> teamList;

        public TeamAdapter(List<Team> teamList) {
            this.teamList = teamList;
        }


        //建立ViewHolder，藉由ViewHolder做元件參照
        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivLogo;
            private TextView tvId, tvName;

            public ViewHolder(View view) {
                super(view);
                ivLogo = (ImageView)view.findViewById(R.id.ivLogo);
                tvId = (TextView)view.findViewById(R.id.tvId);
                tvName = (TextView)view.findViewById(R.id.tvName);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getPosition() == RecyclerView.NO_POSITION) {
                            Toast.makeText(MainActivity.this, getString(R.string.no_choose), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Team team = teamList.get(getPosition());
                        Toast.makeText(MainActivity.this, team.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            public ImageView getIvLogo() {
                return ivLogo;
            }

            public TextView getTvId() {
                return tvId;
            }

            public TextView getTvName() {
                return tvName;
            }
        }

        @Override
        // RecyclerView總列數
        public int getItemCount() {
            return teamList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            //將資料注入到View裡
            Team team = teamList.get(position);
            holder.getIvLogo().setImageResource(team.getLogo());
            holder.getTvId().setText(Integer.toString(team.getId()));
            holder.getTvName().setText(team.getName());
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
