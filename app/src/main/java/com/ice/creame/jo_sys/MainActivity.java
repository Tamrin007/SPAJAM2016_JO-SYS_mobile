package com.ice.creame.jo_sys;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView menutext = (TextView) findViewById(R.id.menutext);
        menutext.setText("メニュー");

        TextView test = (TextView) findViewById(R.id.textView1);
        test.setText("投稿する");
        test.setOnClickListener(new View.OnClickListener() {
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

