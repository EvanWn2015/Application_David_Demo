package idv.david.broadcastreceiverex;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = (TextView)findViewById(R.id.tvResult);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        String type = bundle.getString("type");

        switch (type) {
            case "sms":
                String sender = bundle.getString("sender");
                String smsContent = bundle.getString("smsContent");
                String date = bundle.getString("date");
                tvResult.setText("Sender： " + sender + "\n" +
                        "SMS_Content： " + smsContent + "\n" +
                        "Date： " + date);
                break;
            case "phone":
                String incomingPhone = bundle.getString("incomePhone");
                String phoneState = bundle.getString("phoneState");
                tvResult.setText("Incoming Number： " + incomingPhone + "\n" +
                                 "Phone State： " + phoneState);
                break;
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
