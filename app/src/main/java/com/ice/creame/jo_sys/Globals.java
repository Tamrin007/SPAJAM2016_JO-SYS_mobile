package com.ice.creame.jo_sys;

import android.app.Application;

/**
 * Created by hideya on 2016/04/23.
 */
public class Globals extends Application {
    //グローバルに使用する変数たち
    String user_name = "init";
    String password = "init";

    String latitude = "0";
    String longitude = "0";
    String address = "位置情報取得中…";


    //ぜんぶ初期化するメソッド
    public void GlobalsAllInit() {
        user_name = "init";
        password = "init";
        latitude = "init";
        longitude = "init";
        address = "位置情報取得中…";
    }

}