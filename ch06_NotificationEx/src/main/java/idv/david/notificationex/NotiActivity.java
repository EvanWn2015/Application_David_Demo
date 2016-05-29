package idv.david.notificationex;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class NotiActivity extends ActionBarActivity {
    private TextView tvTitle, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noti_result);
        findViews();
        showInfo();
    }

    private void findViews() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    private void showInfo() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        String title = bundle.getString("title");
        String content = bundle.getString("content");
        tvTitle.setText(title);
        tvContent.setText(content);
    }
}
