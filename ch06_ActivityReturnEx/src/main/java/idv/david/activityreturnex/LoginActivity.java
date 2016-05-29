package idv.david.activityreturnex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {
    private EditText etAccount, etPassword;
    private Button btnSubmit, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViews();
    }

    private void findViews() {
        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();

                if (!(account.equals("abc") && password.equals("123"))) {
                    Toast.makeText(LoginActivity.this, getString(R.string.inputError), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("result", "美圖在此！");
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent); // 回傳給MainActivity，給參數（int requestCode（結果代碼）,intent）
                finish(); // 結束這個Activity

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                LoginActivity.this.finish();
            }
        });
    }
}
