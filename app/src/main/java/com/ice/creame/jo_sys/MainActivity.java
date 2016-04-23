package com.ice.creame.jo_sys;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.ice.creame.jo_sys.DBHelper.readDB;
import static com.ice.creame.jo_sys.DBHelper.writeDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

//        TextView menutext = (TextView) findViewById(R.id.menutext);
//        menutext.setText("メニュー");

        ImageButton mapbutton = (ImageButton) findViewById(R.id.mapbutton);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MapActivity");
                startActivity(intent);
                MainActivity.this.finish();

            }

        });

        ImageButton recordbutton = (ImageButton) findViewById(R.id.recordbutton);
        recordbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.RecordActivity");
                startActivity(intent);
                MainActivity.this.finish();

            }
        });
    }

}

