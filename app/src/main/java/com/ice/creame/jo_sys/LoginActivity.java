package com.ice.creame.jo_sys;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.ice.creame.jo_sys.DBHelper.*;

/**
 * Created by hideya on 2016/04/23.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);


        if(u.equals("init")){

//            TextView logintext = (TextView) findViewById(R.id.logintext);
//            logintext.setText("ユーザー登録");
//
//            TextView usertext = (TextView) findViewById(R.id.usertext);
//            usertext.setText("ユーザー名");
//
//            TextView passtext = (TextView) findViewById(R.id.passtext);
//            passtext.setText("パスワード");


            EditText et = (EditText) findViewById(R.id.useredit);
            EditText et2 = (EditText) findViewById(R.id.passedit);
            et.setWidth(1000);
            et2.setWidth(1000);
            et.setTextColor(Color.BLACK);
            et2.setTextColor(Color.BLACK);
            //入力文字数制限
            InputFilter[] _inputFilter = new InputFilter[1];
            _inputFilter[0] = new InputFilter.LengthFilter(8); //文字数指定
            et.setFilters(_inputFilter);
            et2.setFilters(_inputFilter);


            Button loginbutton = (Button) findViewById(R.id.loginbutton);
            loginbutton.setText("登録");
            loginbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    EditText et = (EditText) findViewById(R.id.useredit);
                    EditText et2 = (EditText) findViewById(R.id.passedit);

                    if (et.getText().toString().equals("") || et2.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "項目を入力してください", Toast.LENGTH_SHORT).show();

                    } else {

                        try {
                            writeDB("0", et.getText().toString(), "testtable", db);
                            writeDB("1", et2.getText().toString(), "testtable", db);

                        } catch (Exception e) {
                            Log.d("deb", "dbError2");
                        }
                        Intent intent = new Intent();
                        //遷移
                        intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
                        startActivity(intent);
                        LoginActivity.this.finish();


                    }

                }
            });
        }else{
            Intent intent = new Intent();
            //遷移
            intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
            startActivity(intent);
            LoginActivity.this.finish();
        }



    }

}