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
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import static com.ice.creame.jo_sys.DBHelper.readDB;
import static com.ice.creame.jo_sys.DBHelper.writeDB;

public class MainActivity extends BaseActivity implements LocationListener {

    // GPS用
    private LocationManager locationManager;
    String latitude;
    String longitude;
    Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2);

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



        /* ユーザ情報送信 */
        try {
            new HttpPostTask().execute(new URL("http://sounds-goood.herokuapp.com/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        TextView usertext = (TextView) findViewById(R.id.usertext);
        usertext.setText("ユーザ名：" + u);
        usertext.setTextColor(Color.BLACK);

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

        ImageButton mylistbutton = (ImageButton) findViewById(R.id.mylistbutton);
        mylistbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MyListActivity");
                startActivity(intent);
                MainActivity.this.finish();

            }
        });

        ImageButton settingbutton = (ImageButton) findViewById(R.id.settingbutton);
            settingbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.SettingActivity");
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

            Log.d("deb:", latitude);
            Log.d("deb", longitude);
            Log.d("deb", strAddr.toString());

            globals.address = strAddr.toString();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public final class HttpPostTask extends AsyncTask<URL, Void, Void> {
        @Override
        protected Void doInBackground(URL... urls) {

            final URL url = urls[0];
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setChunkedStreamingMode(0);
                con.connect();

                // POSTデータ送信処理
                OutputStream out = null;
                try {
                    out = con.getOutputStream();
                    out.write(globals.user_name.getBytes("UTF-8"));
                    out.write(globals.password.getBytes("UTF-8"));
                    out.flush();
                } catch (IOException e) {
                    // POST送信エラー
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }

                final int status = con.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK) {
                    // 正常
                    // レスポンス取得処理を実行
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return null;
        }

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


}

