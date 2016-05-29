package idv.david.intentex;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

// 有新增加一個Activity 需要在AndroidManifest 裡面做登記

public class ResultActivity extends ActionBarActivity {
    private TextView tvTeam, tvPlayer, tvSalary, tvId, tvName;
    private ImageView ivLogo;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        findViews(); // 設計方式建立兩個方法，一個findView取得xml對應實體
        showResult(); // 結果方法
    }

    private void findViews() {
        tvTeam = (TextView)findViewById(R.id.tvTeam);
        tvPlayer = (TextView)findViewById(R.id.tvPlayer);
        tvSalary = (TextView)findViewById(R.id.tvSalary);
        tvId = (TextView)findViewById(R.id.tvId);
        tvName = (TextView)findViewById(R.id.tvName);
        ivLogo = (ImageView)findViewById(R.id.ivLogo);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultActivity.this.finish();
            }
        });
    }

    private void showResult() {
        Bundle bundle = this.getIntent().getExtras(); // 取得bundle物件內容
        if (bundle != null) {   // 判斷Bundle內容資料不為空（做檢查用）
            String team = bundle.getString("team"); // 取得資料給key
            String player = bundle.getString("player");
            double salary = bundle.getDouble("salary");
            Team teamObj = (Team) bundle.getSerializable("teamObj"); // 反序列化

            tvTeam.setText("輸入球隊名稱： " + team);
            tvPlayer.setText("輸入球員名稱： " + player);
            tvSalary.setText("輸入球員薪水： " + salary);

            tvId.setText("物件ID： " + teamObj.getId());
            tvName.setText("物件name：" + teamObj.getName());
            ivLogo.setImageResource(teamObj.getLogo());
        }
    }
}
