package idv.david.sqlitecopyex;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RestDBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "Restaurant";
    private static final String TABLE_NAME = "restaurant";
    private static final String COL_id = "id";
    private static final String COL_name = "name";
    private static final String COL_phoneNo = "phoneNo";
    private static final String COL_address = "address";
    private static final String COL_image = "image";
    private File dbPath;
    private Context context;

    // 傳遞context的目的就是為了取得App的assets與res目錄內資源
    public RestDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        // 取得指定資料庫檔案的路徑
        dbPath = context.getDatabasePath(DB_NAME);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 如果資料庫不存在就建立一個空資料庫並拷貝assets內資料庫檔案以取代其內容
    public void createDB() throws IOException {
        if (!dbExist()) {
            // 不呼叫此方法就不會建立目錄與資料庫檔案，
            // 沒有目錄則無法將assets的資料庫檔案拷貝寫入，會產生FileNotFoundException
            getReadableDatabase();
            try {
                copyDB();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 檢查資料庫是否存在，如果存在就該避免重複複製，否則每次開啟App都會發生複製動作。
    private boolean dbExist() {
        SQLiteDatabase db;
        try {
            // 指定flag為OPEN_READONLY，只要指定的資料庫不存在就會回傳null
            // 第一次沒有資料庫時會無法開啓而產生SQLiteException，並在LogCat上顯示
            db = SQLiteDatabase.openDatabase(dbPath.toString(), null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // 資料庫無法開啓就設為null
            db = null;
        }

        if (db != null) {
            db.close();
        }

        return db != null;
    }

    // 將assets目錄內的資料庫檔案複製到該App可以存取的目錄內
    private void copyDB() throws IOException {
        InputStream in = context.getAssets().open(DB_NAME);
        OutputStream out = new FileOutputStream(dbPath);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.flush();
        out.close();
        in.close();
    }

    public List<Rest> getAllRests() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { COL_id, COL_name, COL_phoneNo, COL_address,
                COL_image };
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null,
                null);
        List<Rest> restList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String phoneNo = cursor.getString(2);
            String address = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            Rest rest = new Rest(id, name, phoneNo, address, image);
            restList.add(rest);
        }
        cursor.close();
        db.close();
        return restList;
    }

}
