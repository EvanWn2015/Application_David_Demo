package idv.david.sqliteex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RestaurantSQLiteHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "RestaurantDB";
    private final static int DB_VERSION = 1;
    private final static String TABLE_NAME = "restaurant";
    //欄位名稱
    private final static String COL_ID = "rest_id";
    private final static String COL_NAME = "rest_name";
    private final static String COL_WEB = "rest_web";
    private final static String COL_PHONE = "rest_phone";
    private final static String COL_SPECIALITY = "rest_speciality";
    private final static String COL_PIC = "rest_pic";
    //建立表格的SQL語法
    //AUTOINCREMENT是每次新增一筆資料，就會自動加1；即自動產生流水編號
    private final static String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " TEXT NOT NULL, " +
                    COL_WEB + " TEXT, " +
                    COL_PHONE + " TEXT, " +
                    COL_SPECIALITY + " TEXT, " +
                    COL_PIC + " BLOB ); ";

    public RestaurantSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insert(RestaurantVO restaurantVO) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, restaurantVO.getRest_name());
        values.put(COL_WEB, restaurantVO.getRest_web());
        values.put(COL_PHONE, restaurantVO.getRest_phone());
        values.put(COL_SPECIALITY, restaurantVO.getRest_speciality());
        values.put(COL_PIC, restaurantVO.getRest_pic());
        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowId;
    }

    public int update(RestaurantVO restaurantVO) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {Integer.toString(restaurantVO.getRest_id())};
        ContentValues values = new ContentValues();
        values.put(COL_NAME, restaurantVO.getRest_name());
        values.put(COL_WEB, restaurantVO.getRest_web());
        values.put(COL_PHONE, restaurantVO.getRest_phone());
        values.put(COL_SPECIALITY, restaurantVO.getRest_speciality());
        values.put(COL_PIC, restaurantVO.getRest_pic());
        int count = db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        return count;
    }

    public int deleteById(int rest_id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {Integer.toString(rest_id)};
        int count = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return count;
    }

    public RestaurantVO findRestById(int rest_id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COL_NAME, COL_WEB, COL_PHONE, COL_SPECIALITY, COL_PIC};
        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {Integer.toString(rest_id)};
        Cursor cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs, null, null, null);
        cursor.moveToNext();
        String name = cursor.getString(0);
        String web = cursor.getString(1);
        String phone = cursor.getString(2);
        String speciality = cursor.getString(3);
        byte[] pic = cursor.getBlob(4);
        RestaurantVO restaurantVO = new RestaurantVO(rest_id, name, web, phone, speciality, pic);
        cursor.close();
        db.close();
        return restaurantVO;
    }

    public List<RestaurantVO> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COL_ID, COL_NAME, COL_WEB, COL_PHONE, COL_SPECIALITY, COL_PIC};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        List<RestaurantVO> restList = new ArrayList<>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String web = cursor.getString(2);
            String phone = cursor.getString(3);
            String speciality = cursor.getString(4);
            byte[] pic = cursor.getBlob(5);
            RestaurantVO restaurantVO = new RestaurantVO(id, name, web, phone, speciality, pic);
            restList.add(restaurantVO);
        }
        cursor.close();
        db.close();
        return restList;
    }

}
