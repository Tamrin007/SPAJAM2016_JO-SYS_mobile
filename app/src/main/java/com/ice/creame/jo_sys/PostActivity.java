package com.ice.creame.jo_sys;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hideya on 2016/04/23.
 */
public class PostActivity extends AppCompatActivity {

    // GPS用
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        /* GPS */
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gpsFlg = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d("GPS Enabled", gpsFlg ? "OK" : "NG");


        TextView posttext = (TextView) findViewById(R.id.posttext);
        posttext.setText("投稿情報入力");

        TextView titletext = (TextView) findViewById(R.id.titletext);
        titletext.setText("タイトル");

        TextView comenttext = (TextView) findViewById(R.id.comenttext);
        comenttext.setText("コメント");


        Button postbutton2 = (Button) findViewById(R.id.postbutton2);
        postbutton2.setText("投稿");
        postbutton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


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
    protected void onPause() {
        // ƒƒP[ƒVƒ‡ƒ“ƒ}ƒl[ƒWƒƒ‚ÌƒŠƒXƒi[‚ð‰ðœ
        mLocationManager.removeUpdates(this);
        super.onPause();
    }

    // GPSボタン
    public void onBtnGpsClicked(View view) {
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, //LocationManager.NETWORK_PROVIDER,
                3000, // 通知のための最小時間間隔（ミリ秒）
                10, // 通知のための最小距離間隔（メートル）
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        String msg = "Lat=" + location.getLatitude()
                                + "\nLng=" + location.getLongitude();
                        Log.d("GPS", msg);
                        mLocationManager.removeUpdates(this);
                    }
                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                    @Override
                    public void onProviderEnabled(String provider) {
                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                }
        );
    }

}