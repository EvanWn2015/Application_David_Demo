package idv.david.datetimepickerdialogex;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    private static TextView tvDateTime;
    private static int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        showRightNow();
    }

    private static void showRightNow() { // 實體變數為static 所以方法為靜態方法
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        updateInfo();
    }

    // 將指定的日期顯示在TextView上
    private static void updateInfo() {
        tvDateTime.setText(new StringBuilder().append(year).append("-")
                //「month + 1」是因為一月的值是0而非1
                .append(parseNum(month + 1)).append("-").append(parseNum(day)).append(" ")
                .append(hour).append(":").append(parseNum(minute)));
    }

    // 若數字有十位數，直接顯示；若只有個位數則補0後再顯示。例如7會改成07後再顯示
    private static String parseNum(int day) {
        if (day >= 10)
            return String.valueOf(day);
        else
            return "0" + String.valueOf(day);
    }

    // 按下「日期」按鈕
    public void onDateClick(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        FragmentManager fm = getSupportFragmentManager();
        datePickerFragment.show(fm, "datePicker");
    }

    // 按下「時間」按鈕
    public void onTimeClick(View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        FragmentManager fm = getSupportFragmentManager();
        timePickerFragment.show(fm, "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        // 改寫此方法以提供Dialog內容
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // 建立DatePickerDialog物件
            // this為OnDateSetListener物件
            // year、month、day會成為日期挑選器預選的年月日
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), this, year, month, day);
            return datePickerDialog;
        }

        @Override
        // 日期挑選完成會呼叫此方法，並傳入選取的年月日
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            year = y;
            month = m;
            day = d;
            updateInfo();
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        // 改寫此方法以提供Dialog內容
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // 建立TimePickerDialog物件
            // this為OnTimeSetListener物件
            // hour、minute會成為時間挑選器預選的時與分
            // false 設定是否為24小時制顯示
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getActivity(), this, hour, minute, false);
            return timePickerDialog;
        }

        @Override
        // 時間挑選完成會呼叫此方法，並傳入選取的時與分
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            hour = h;
            minute = m;
            updateInfo();
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
