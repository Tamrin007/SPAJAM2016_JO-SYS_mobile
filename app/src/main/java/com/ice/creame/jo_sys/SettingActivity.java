package com.ice.creame.jo_sys;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.ice.creame.jo_sys.DBHelper.writeDB;

/**
 * Created by hideya on 2016/04/24.
 */
public class SettingActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        TextView settingtext = (TextView) findViewById(R.id.settingtext);
        settingtext.setText("ユーザー情報変更");

        TextView usertext = (TextView) findViewById(R.id.usertext);
        usertext.setText("ユーザー名");

        TextView passtext = (TextView) findViewById(R.id.passtext);
        passtext.setText("パスワード");


        EditText et = (EditText) findViewById(R.id.useredit);
        EditText et2 = (EditText) findViewById(R.id.passedit);
        et.setWidth(500);
        et2.setWidth(500);
        et.setText(u);
        et2.setText(p);
        //入力文字数制限
        InputFilter[] _inputFilter = new InputFilter[1];
        _inputFilter[0] = new InputFilter.LengthFilter(8); //文字数指定
        et.setFilters(_inputFilter);
        et2.setFilters(_inputFilter);


        Button editbutton = (Button) findViewById(R.id.editbutton);
        editbutton.setText("変更");
        editbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.useredit);
                EditText et2 = (EditText) findViewById(R.id.passedit);

                if (et.getText().toString().equals("") || et2.getText().toString().equals("")) {
                    Toast.makeText(SettingActivity.this, "項目を入力してください", Toast.LENGTH_SHORT).show();

                } else {

                    try {
                        ContentValues values = new ContentValues();
                        values.put("date", et.getText().toString());
                        db.update("testtable", values, "id = 0", null);
                        values.put("date", et2.getText().toString());
                        db.update("testtable", values, "id = 1", null);

                    } catch (Exception e) {
                        Log.d("deb", "dbError2");
                    }
                    Intent intent = new Intent();
                    //遷移
                    intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
                    startActivity(intent);
                    SettingActivity.this.finish();


                }

            }
        });

        Button backbutton = (Button) findViewById(R.id.backbutton);
        backbutton.setText("戻る");
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
                startActivity(intent);
                SettingActivity.this.finish();

            }
        });
    }
}
