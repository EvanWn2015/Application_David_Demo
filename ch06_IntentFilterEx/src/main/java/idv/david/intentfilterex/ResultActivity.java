package idv.david.intentfilterex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class ResultActivity extends ActionBarActivity {
    private TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        findViews();
        showResult();
    }

    private void findViews() {
        tvResult = (TextView)findViewById(R.id.tvResult);
    }

    private void showResult() {
        Intent intent = getIntent();
        String act = intent.getAction();
        String scheme = intent.getScheme();
        if(scheme.equals("http")) {
            String s = "收到要求開啟網頁： " + intent.getData().toString(); // getData()得到的資料型態為uri
            tvResult.setText(s);
        } else if (scheme.equals("tel")) {
            String s = "收到要求撥打電話： " + intent.getData().toString();
            tvResult.setText(s);
        } else if (scheme.equals("file")) {
            if (act.equals("android.intent.action.VIEW")) {
                String s = "收到要求瀏覽： " + intent.getData().toString();
                tvResult.setText(s);
            } else if (act.equals("android.intent.action.EDIT")) {
                String s = "收到要求編輯： " + intent.getData().toString();
                tvResult.setText(s);
            }
        }
    }

}
