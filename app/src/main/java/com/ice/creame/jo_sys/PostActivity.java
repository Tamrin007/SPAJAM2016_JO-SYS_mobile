package com.ice.creame.jo_sys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

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


        TextView localtitletext = (TextView) findViewById(R.id.localtitletext);
        localtitletext.setText("位置情報");

        TextView localtext = (TextView) findViewById(R.id.localtext);
        localtext.setText(globals.address);

        TextView posttext = (TextView) findViewById(R.id.posttext);
        posttext.setText("投稿情報入力");

        TextView titletext = (TextView) findViewById(R.id.titletext);
        titletext.setText("タイトル");

        /* 入力部 */
        EditText et = (EditText) findViewById(R.id.titleedit);
        et.setWidth(1000);

        EditText et2 = (EditText) findViewById(R.id.comentedit);
        et2.setWidth(1000);


        TextView comenttext = (TextView) findViewById(R.id.comenttext);
        comenttext.setText("コメント");


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

                    doPost();
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
        cancelbutton.setText("キャンセル");
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

    private void doPost() {

        String fileName = "hoge.3gp";
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + fileName;

        try {

            URL url = new URL("http://sounds-goood.herokuapp.com/ ");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/octet-stream");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setUseCaches(false);
            http.connect();

            // データを投げる
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write("user_id:1");
            out.flush();

//            InputStream in = new FileInputStream(mFilePath);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            result = reader.readLine();

//            reader.close();
//            in.close();
            out.close();
            http.disconnect();
            Log.d("deb", result);

        } catch (Exception e) {
            result = "Connection Error.";
            Log.d("Connection Error", e.getMessage());
        }

    }
}