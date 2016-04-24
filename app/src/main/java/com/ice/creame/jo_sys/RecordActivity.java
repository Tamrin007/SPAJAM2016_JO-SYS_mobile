package com.ice.creame.jo_sys;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by hideya on 2016/04/23.
 */
public class RecordActivity extends AppCompatActivity {

    private MediaRecorder mMediaRecorder;
    private MediaPlayer mMediaPlayer;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        TextView recordtext = (TextView) findViewById(R.id.recordtext);
        recordtext.setText("音声録音");

        Button postbutton = (Button) findViewById(R.id.postbutton);
        postbutton.setText("投稿");
        postbutton.setVisibility(View.INVISIBLE);
        postbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //遷移

                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.PostActivity");/* koko */
                startActivity(intent);
                RecordActivity.this.finish();

            }
        });


        Button initbutton = (Button) findViewById(R.id.initbutton);
        initbutton.setText("再生");
        initbutton.setVisibility(View.INVISIBLE);
        initbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 再生用スレッドを起こす
                startPlay();
            }
        });


        Button recordbutton = (Button) findViewById(R.id.recordbutton);
        recordbutton.setText("録音開始");
        recordbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startRecord();

            }
        });

        Button stopbutton = (Button) findViewById(R.id.stopbutton);
        stopbutton.setText("録音停止");
        stopbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mMediaRecorder != null) {
                    mMediaRecorder.stop();
                    mMediaRecorder.reset();
                }
                Button initbutton = (Button) findViewById(R.id.initbutton);
                Button postbutton = (Button) findViewById(R.id.postbutton);
                initbutton.setVisibility(View.VISIBLE);
                postbutton.setVisibility(View.VISIBLE);

            }
        });

        Button returnbutton = (Button) findViewById(R.id.returnbutton);
        returnbutton.setText("戻る");
        returnbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //遷移
                intent.setClassName("com.ice.creame.jo_sys", "com.ice.creame.jo_sys.MainActivity");
                startActivity(intent);
                RecordActivity.this.finish();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        if (mMediaRecorder != null) {
            if (mMediaRecorder != null) {
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        }
    }


    private void startRecord() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        }

        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        String fileName = "hoge.3gp";
        mFilePath = Environment.getExternalStorageDirectory() + "/" + fileName;
        mMediaRecorder.setOutputFile(mFilePath);

        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPlay() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        } else {
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setDataSource(mFilePath);

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.start();
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
            mMediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}