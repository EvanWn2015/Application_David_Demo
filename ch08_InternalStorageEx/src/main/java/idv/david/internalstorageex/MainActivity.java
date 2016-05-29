package idv.david.internalstorageex;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {
    private final static String TAG = "MainActivity";
    private final static String FILE_NAME = "input.txt";
    private EditText etInput;
    private TextView tvInput;
    private Button btnSave, btnAppend, btnOpen, btnClear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        etInput = (EditText)findViewById(R.id.etInput);
        tvInput = (TextView)findViewById(R.id.tvInput);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BufferedWriter bw = null;
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write(etInput.getText().toString());
                } catch (IOException ie) {
                    Log.e(TAG, ie.toString());
                } finally {
                    if(bw != null) {
                        try {
                            bw.close();
                        } catch (IOException ie) {
                            Log.e(TAG, ie.toString());
                        }
                    }
                }
                Toast.makeText(MainActivity.this, getString(R.string.fileSaved), Toast.LENGTH_SHORT).show();
            }
        });

        btnAppend = (Button)findViewById(R.id.btnAppend);
        btnAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BufferedWriter bw = null;
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write(etInput.getText().toString());
                } catch (IOException ie) {
                    Log.e(TAG, ie.toString());
                } finally {
                    if(bw != null) {
                        try {
                            bw.close();
                        } catch (IOException ie) {
                            Log.e(TAG, ie.toString());
                        }
                    }
                }
                Toast.makeText(MainActivity.this, getString(R.string.wordsAppended), Toast.LENGTH_SHORT).show();
            }
        });

        btnOpen = (Button)findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = null;
                try {
                    FileInputStream fis = openFileInput(FILE_NAME);
                    br = new BufferedReader(new InputStreamReader(fis));
                    String text;
                    while ((text = br.readLine()) != null) {
                        sb.append(text);
                        sb.append("\n");
                    }
                } catch (IOException ie) {
                    Log.e(TAG, ie.toString());
                } finally {
                    if(br != null) {
                        try {
                            br.close();
                        } catch (IOException ie) {
                            Log.e(TAG, ie.toString());
                        }
                    }
                }
                tvInput.setText(sb);
            }
        });

        btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etInput.setText("");
                tvInput.setText("");
            }
        });
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
