package com.ice.creame.jo_sys;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static com.ice.creame.jo_sys.DBHelper.readDB;

/**
 * Created by hideya on 2016/04/24.
 */
public class BaseActivity extends AppCompatActivity {

    SQLiteDatabase db; //データベースオブジェクト
    String u = "init";
    String p = "init";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* データベースオブジェクトの取得 */
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        try {
            u = readDB("0", "testtable", db)[1];
            p = readDB("1", "testtable", db)[1];

        } catch (Exception e) {
            Log.d("deb", "dbError1");
        }
    }

}
