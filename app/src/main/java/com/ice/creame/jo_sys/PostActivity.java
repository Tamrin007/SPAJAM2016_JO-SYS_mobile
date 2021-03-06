package com.ice.creame.jo_sys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Created by hideya on 2016/04/23.
 */
public class PostActivity extends AppCompatActivity implements LocationListener {

    // GPS用
    private LocationManager locationManager;
    String latitude;
    String longitude;
    Globals globals;

    String result = "";
    HttpURLConnection urlCon = null;
    InputStream in = null; // URL連携した戻り値を取得して保持する用
    boolean uploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_post);
        setContentView(R.layout.contribute2);

        globals = (Globals) this.getApplication();

        /* GPS */
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Criteriaオブジェクトを生成
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        // LocationListenerを登録
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.requestLocationUpdates(provider, 0, 0, this);


//        TextView localtitletext = (TextView) findViewById(R.id.localtitletext);
//        localtitletext.setText("位置情報");

        TextView localtext = (TextView) findViewById(R.id.localtext);
        localtext.setTextColor(Color.BLACK);
        localtext.setText(globals.address);

//        TextView posttext = (TextView) findViewById(R.id.posttext);
//        posttext.setText("投稿情報入力");

//        TextView titletext = (TextView) findViewById(R.id.titletext);
//        titletext.setText("タイトル");

        /* 入力部 */
        EditText et = (EditText) findViewById(R.id.titleedit);
        et.setWidth(1000);

        EditText et2 = (EditText) findViewById(R.id.comentedit);
        et2.setWidth(1000);


//        TextView comenttext = (TextView) findViewById(R.id.comenttext);
//        comenttext.setText("コメント");


        Button postbutton2 = (Button) findViewById(R.id.postbutton2);
        postbutton2.setText("投稿");
        postbutton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.titleedit);

                EditText et2 = (EditText) findViewById(R.id.comentedit);

                //空白のとき
                if (et.getText().toString().equals("") || et2.getText().toString().equals("")) {
                    Toast.makeText(PostActivity.this, "項目を入力してください", Toast.LENGTH_SHORT).show();
                }else {

                    doPost(et.getText().toString(), et2.getText().toString());
                    Toast.makeText(PostActivity.this, "投稿しました", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    //遷移
                    intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
                    startActivity(intent);
                    PostActivity.this.finish();
                }

            }
        });

        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
//        cancelbutton.setText("キャンセル");
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
                startActivity(intent);
                PostActivity.this.finish();

            }
        });
    }


    @Override
    public void onStop() {

        super.onStop();

        // 位置情報の更新を止める
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(Location location) {
// 例としてラベルに取得した位置を表示
        latitude = Double.toString(location.getLatitude());
        longitude = Double.toString(location.getLongitude());

        globals.latitude = latitude;
        globals.longitude = longitude;


        // 住所の取得
        StringBuffer strAddr = new StringBuffer();
        Geocoder gcoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> lstAddrs = gcoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
            for (Address addr : lstAddrs) {
                int idx = addr.getMaxAddressLineIndex();


                for (int i = 1; i <= idx; i++) {
                    strAddr.append(addr.getAddressLine(i));
                }
            }

            Log.d("deb", "post:" + latitude);
            Log.d("deb", "post:" + longitude);
            Log.d("deb", strAddr.toString());
            globals.address = strAddr.toString();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        TextView localtext = (TextView) findViewById(R.id.localtext);
        localtext.setText(strAddr.toString());

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override

    public void onProviderDisabled(String provider) {

    }

    @Override

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    private void doPost(final String title, final String comment){
        new Thread(new Runnable() {
            @Override
            public void run() {
                postUrlConnection(title, comment);
                uploaded = true;
            }
        }).start();
        while(!uploaded);
    }

    private void postUrlConnection(String title, String comment) {
        String data = "";

        try{

            FileInputStream fileInputStream = new FileInputStream(globals.postFilePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            byte[] tmp;
            while((line = reader.readLine()) != null){
                line = Base64.encodeToString(line.getBytes(), Base64.DEFAULT);
                //data += line;
            }
            data="AAAAGGZ0eXAzZ3A0AAAAAGlzb20zZ3A0AAA";
            reader.close();
        }catch(Exception e){

        }


        try {
            // httpコネクションを確立し、urlを叩いて情報を取得
            URL url = new URL("http://sounds-goood.herokuapp.com/post_sound");
            urlCon = (HttpURLConnection)url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setDoOutput(true); // POSTでデータ送信可能に


            // POSTパラメータ
            String postData1 = "data=";
            String postData2 = "&title=" + title + "&comment=" + comment + "&lan=" + latitude + "&lon=" + longitude + "&user_id=" + 8;


            String para = postData1 + data + postData2;
            Log.d("ian", para);
            // OutPutStreamWriterを利用
            OutputStreamWriter writer = new OutputStreamWriter(urlCon.getOutputStream());
            writer.write(para);
            writer.flush();

            Log.d("ian", "hi");
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            while((line = reader.readLine()) != null){
                Log.d("ian", "jj");
                Log.d("ian", line);
            };


            writer.close();
            reader.close();
            urlCon.disconnect();

            Log.d("ian", "fin");
            // 結果をテキストビューに設定

        } catch (Exception e ) {
            e.printStackTrace();
        }

    }

    /* おれが作った屑ポスト */
    private void doPost() {

//        String mFilePath = Environment.getExternalStorageDirectory() + "/" + fileName;

        try {
            // httpコネクションを確立し、urlを叩いて情報を取得
            URL url = new URL("http://sounds-goood.herokuapp.com/");
            urlCon = (HttpURLConnection)url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setDoOutput(true); // POSTでデータ送信可能に

            // POSTパラメータ
            String postDataSample = "user_id=1&user_name=aaa";

            // PrintStreamを利用
            PrintStream ps = new PrintStream(urlCon.getOutputStream());
            ps.print(postDataSample);
            ps.close();

            // データを取得
            Toast.makeText(this, Integer.toString(urlCon.getResponseCode()), Toast.LENGTH_SHORT).show(); // -->405(Method not allowed)であったため、inputStreamが取得出来なかったみたい。
            in = urlCon.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            // InputStreamからbyteデータを取得するための変数
            StringBuffer bufStr = new StringBuffer();
            String temp = null;

            // InputStreamからのデータを文字列として取得する
            while((temp = br.readLine()) != null) {
                bufStr.append(temp);
            }

            // 結果をテキストビューに設定

        } catch (IOException ioe ) {
            Log.d(this.getClass().toString(),ioe.toString());
            Toast.makeText(this, "IOExceptionが発生しました。", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                urlCon.disconnect();
                in.close();

            } catch (IOException ioe ) {
                ioe.printStackTrace();
            }
        }

    }
}