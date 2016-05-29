package idv.hangermo.littleappleex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private static final String URL = "http://www.appledaily.com.tw";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        // 設定Refresh時圈圈的顏色變化
        swipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RetrieveRealTimeNewsTask().execute();
            }
        });

        myRecyclerview = (RecyclerView)findViewById(R.id.myRecyclerview);
        //設定每個List是否為固定尺寸
        myRecyclerview.setHasFixedSize(true);
        //產生一個LinearLayoutManger
        layoutManager = new LinearLayoutManager(this);
        //設定LayoutManger
        myRecyclerview.setLayoutManager(layoutManager);

        fab = (ImageButton)findViewById(R.id.fab);
        //藉由ViewOutlineProvider設定浮動按鈕的陰影輪廓
        final ViewOutlineProvider mOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
            }
        };
        fab.setOutlineProvider(mOutlineProvider);
        //設定float action button click事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RetrieveRealTimeNewsTask().execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //開啟Activity時就先抓取一次資料
        new RetrieveRealTimeNewsTask().execute();
    }

    class RetrieveRealTimeNewsTask extends AsyncTask<String, Integer, List<NewsVO>> {

        @Override
        protected List<NewsVO> doInBackground(String... strings) {
            Document doc = null;
            List<NewsVO> newsCopyList = null;
            try {
                doc = Jsoup.connect(URL).get();
                if (doc != null) {
                    List<NewsVO> newsList = new ArrayList<>();
                    Elements times = doc.select(".mrt > li");
                    for (Element e : times) {
                        // 取得的文字進行切割出時間
                        NewsVO news = new NewsVO();
                        String time = e.text().substring(0, 5);
                        news.setTime(time);
                        newsList.add(news);
                    }

                    newsCopyList = new ArrayList<>();
                    Elements e = doc.select(".mrt > li > a");
                    for (int i = 0; i < e.size(); i++) {
                        Element element = e.get(i);
                        String url = URL + element.attr("href");
                        String title = element.attr("title");
                        NewsVO news = newsList.get(i);
                        news.setTitle(title);
                        news.setUrl(url);
                        newsCopyList.add(news);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsCopyList;
        }

        @Override
        protected void onPostExecute(List<NewsVO> result) {
            swipeRefreshLayout.setRefreshing(false);
            showResult(result);
        }
    }

    private void showResult(final List<NewsVO> result) {
        myRecyclerview.setAdapter(new NewsAdapter(result));
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<NewsVO> newsList;

        public NewsAdapter(List<NewsVO> newsList) {
            this.newsList = newsList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTitle, tvTime;

            public ViewHolder(View view) {
                super(view);
                tvTitle = (TextView)view.findViewById(R.id.tvTitle);
                tvTime = (TextView)view.findViewById(R.id.tvTime);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getAdapterPosition() == RecyclerView.NO_POSITION) {
                            Toast.makeText(MainActivity.this, getString(R.string.no_choose), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        NewsVO news = newsList.get(getAdapterPosition());
                        Uri uri = Uri.parse(news.getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }

            public TextView getTvTitle() {
                return tvTitle;
            }

            public TextView getTvTime() {
                return tvTime;
            }
        }

        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_news, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
            //將資料注入到View裡
            NewsVO news = newsList.get(position);
            holder.getTvTitle().setText(news.getTitle());
            holder.getTvTime().setText(news.getTime());
        }

        @Override
        public int getItemCount() {
            return newsList.size();
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
