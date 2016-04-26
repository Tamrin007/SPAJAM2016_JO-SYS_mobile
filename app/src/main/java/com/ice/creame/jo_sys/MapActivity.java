package com.ice.creame.jo_sys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by hideya on 2016/04/23.
 */
public class MapActivity extends AppCompatActivity {

    MapFragment mf;
    private static  LatLng now_location; // add
    private static final LatLng spajam_osaka = new LatLng(34.7021876, 135.4998476);
    private static final LatLng st_osaka = new LatLng(34.4263, 135.29418);
    private static final LatLng st_wumeda = new LatLng(34.41585, 135.29444);
    private static final LatLng st_sumeda = new LatLng(34.4235, 135.29584);
    private static final LatLng doshisha_university = new LatLng(35.01471, 135.45387);
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private CameraUpdate camera;
    private MarkerOptions markers;

    Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        globals = (Globals) this.getApplication();
        Log.d("deb", "map:"+globals.latitude);
        Log.d("deb", "map:"+globals.longitude);

        now_location = new LatLng(Double.parseDouble(globals.latitude), Double.parseDouble(globals.longitude));


        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.moveCamera((CameraUpdateFactory.newLatLngZoom(now_location, 16)));
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setIndoorEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        /*
        mf = MapFragment.newInstance();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content,mf);
        ft.commit();
        */
    }
    protected void onResume(){
        super.onResume();
        if(googleMap == null){

        }
    }


}
