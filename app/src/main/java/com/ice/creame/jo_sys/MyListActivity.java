package com.ice.creame.jo_sys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by hideya on 2016/04/24.
 */
public class MyListActivity extends AppCompatActivity {

    Globals globals;

    String[] locates = {"〒530-0012 大阪府大阪市北区芝田１丁目１０−１０","〒530-0012 大阪府大阪市北区芝田１丁目１０−１０","〒530-0012 大阪府大阪市北区芝田１丁目１０−１０", "〒530-0012 大阪府大阪市北区芝田１丁目１０−１０", "〒530-0012 大阪府大阪市北区芝田１丁目１０−１０", "〒530-0012 大阪府大阪市北区芝田１丁目１０−１０", "〒530-0012 大阪府大阪市北区芝田１丁目１０−１０", "〒530-0012 大阪府大阪市北区芝田１丁目１０−１０", "〒530-0012 大阪府大阪市北区芝田１丁目１０−１０", "〒530-0012 大阪府大阪市北区芝田１丁目１０−１０", "〒530-0012 大阪府大阪市北区芝田１丁目１０−１０"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        globals = (Globals) this.getApplication();

        TextView mylisttext = (TextView) findViewById(R.id.mylisttext);
        mylisttext.setText("自分の投稿");

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        TextView test= new TextView(this);
        test.setText("test");
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
                startActivity(intent);
                MyListActivity.this.finish();
            }
        });
        ll.addView(test);
        for(String locate : locates){
            TextView tv = new TextView(this);
            tv.setText(locate);
            ll.addView(tv);

            TextView tv2 = new TextView(this);
            tv2.setText(" ");
            ll.addView(tv2);
        }

        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        cancelbutton.setText("戻る");
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
                startActivity(intent);
                MyListActivity.this.finish();
            }
        });

    }
}
