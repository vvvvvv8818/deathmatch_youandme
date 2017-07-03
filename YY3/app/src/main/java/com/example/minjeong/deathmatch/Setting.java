package com.example.minjeong.deathmatch;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Minjeong on 2015-11-25.
 */
public class Setting extends Activity {

    Button play;
    Button stop;
    public MediaPlayer rPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        play = (Button)findViewById(R.id.audioplayer);
        stop = (Button)findViewById(R.id.audiostop);

        rPlayer = new MediaPlayer(); // 미디어 플레이어를 생성한다.
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rPlayer = MediaPlayer.create(Setting.this, R.raw.backaudio); // 꽥꽥소리를 재생하기 미디어 create를 실행한다.
                rPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                    }
                }); // ComListen completionlistener리스너를 셋 해준다.

                rPlayer.start(); // 음악파일을 실행한다.

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rPlayer.stop();

            }
        });


    }
}
