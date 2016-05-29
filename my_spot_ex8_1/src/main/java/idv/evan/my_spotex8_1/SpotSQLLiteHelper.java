package idv.evan.my_spotex8_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 淳彥 on 2015/11/2.
 */
public class SpotSQLLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "SpotDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "spot";

    //SQL columns
    private static final String COL_ID = "spot_id";
    private static final String COL_NAME = "spot_name";
    private static final String COL_WEB = "spot_web";
    private static final String COL_LOCATION = "spot_location";
    private static final String COL_PIC = "spot_pic";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " TEXT NOT NULL, " +
                    COL_WEB + " TEXT NOT NULL, " +
                    COL_LOCATION + " TEXT NOT NULL, " +
                    COL_PIC + " BLOB ); ";

    public SpotSQLLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insert(SpotVO spotVO) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, spotVO.getSpot_name());
        values.put(COL_WEB, spotVO.getSpot_web());
        values.put(COL_LOCATION, spotVO.getSpot_location());
        values.put(COL_PIC, spotVO.getSpot_pic());
        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowId;
    }

    public int update(SpotVO spotVO) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {Integer.toString(spotVO.getSpot_id())};
        ContentValues values = new ContentValues();
        values.put(COL_NAME, spotVO.getSpot_name());
        values.put(COL_WEB, spotVO.getSpot_web());
        values.put(COL_LOCATION, spotVO.getSpot_location());
        values.put(COL_PIC, spotVO.getSpot_pic());
        int count = db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        return count;
    }

    public int deleteSpotById(int spot_id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {Integer.toString(spot_id)};
        int count = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return count;
    }

    public SpotVO findSpotById(int spot_id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {COL_NAME, COL_WEB, COL_LOCATION, COL_PIC};
        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {Integer.toString(spot_id)};
        Cursor cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs, null, null, null);
        cursor.moveToNext();
        String name = cursor.getString(0);
        String web = cursor.getString(1);
        String location = cursor.getString(2);
        byte[] pic = cursor.getBlob(3);
        SpotVO spotVO = new SpotVO(spot_id, name, web, location, pic);
        cursor.close();
        db.close();
        return spotVO;
    }

    public List<SpotVO> getAll() {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {COL_ID, COL_NAME, COL_WEB, COL_LOCATION, COL_PIC};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        List<SpotVO> spotList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String web = cursor.getString(2);
            String location = cursor.getString(3);
            byte[] pic = cursor.getBlob(4);
            SpotVO spotVO = new SpotVO(id, name, web, location, pic);
            spotList.add(spotVO);
        }
        cursor.close();
        db.close();
        return spotList;
    }
}
