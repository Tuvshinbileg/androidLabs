package mn.edu.num.tuvshinbileg.lab04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private String TAG = "lab04.DatabaseHelper";
    public static final String DATABASE_NAME = "Users.db";
    public static final String TABLE_NAME = "users_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "PHONENUMBER";
    public static final String COL_5 = "AGE";
    public static final String COL_6 = "SEX";
    public static final String COL_7 = "BIRTHDAY";
    private User passuser;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT,PHONENUMBER INTEGER,AGE INTEGER,SEX TEXT,BIRTHDAY DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insert(ContentValues contentValues) {
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getCursorForAll() {
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7}
                , null, null, null, null, null, null);
        return cursor;
    }

    public Cursor getCursorForSpecificPlase(String place) {
        String select = "select *from";
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COL_1, COL_2, COL_3, COL_4, COL_5, COL_6,},
                COL_7 + "LIKE '%" + place + "%'", null, null, null, null);
        return cursor;
    }

    public Cursor getCount() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        return cursor;
    }

    public boolean insertUsers(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_2, user.getName());
        values.put(COL_3, user.getPassword());
        values.put(COL_4, user.getPhonenumber());
        values.put(COL_5, user.getAge());
        values.put(COL_6, user.getSex().toLowerCase());
        values.put(COL_7, user.getDate());
        long result = sqLiteDatabase.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Log.i("Lab04", "false");
            return false;
        } else {
            Log.i("Lab04", "Added on db:true");
            return true;
        }
    }

    //will be use provider
    public int delete(String whereClause, String[] whereValues) {
        return sqLiteDatabase.delete(TABLE_NAME, whereClause, whereValues);
    }


    public boolean delete(int taskId) {
        return sqLiteDatabase.delete(TABLE_NAME, COL_1 + "=" + taskId, null) > 0;
    }

    //Will be used in the content provider
    public int update(ContentValues contentValues, String s, String[] strings) {
        return sqLiteDatabase.update(TABLE_NAME, contentValues, s, strings);
    }

    public void deleteRow(int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL_1 + "='" + value + "'");
        db.close();
    }

    public User checkUserExist(String username, String password) {
        Log.i(TAG, "check users");
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM users_table  WHERE USERNAME ='" + username + "' AND PASSWORD='" + password + "'";
        Cursor c = db.rawQuery(select, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            passuser = new User(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2),
                    Integer.parseInt(c.getString(3)),
                    Integer.parseInt(c.getString(4)),
                    c.getString(5), c.getString(6));
            c.moveToNext();
        }
        int count = c.getCount();
        Log.i(TAG, "selected row number:" + String.valueOf(count));
        if (count > 0) {
            Log.d(TAG, "User exits");
            Log.i(TAG, passuser.toString());
            return passuser;
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return null;
    }

    public boolean update(int id, String username, int age, int phonenumber, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username);
        values.put(COL_4, phonenumber);
        values.put(COL_5, age);
        values.put(COL_7, date);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean updatePass(int id, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3, pass);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
        return true;
    }

    public ArrayList getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> array_list = new ArrayList<User>();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(new User(Integer.parseInt(res.getString(0)), res.getString(1), res.getString(2),
                    Integer.parseInt(res.getString(3)),
                    Integer.parseInt(res.getString(4)),
                    res.getString(5), res.getString(6)));
            res.moveToNext();
        }
        return array_list;
    }


}
