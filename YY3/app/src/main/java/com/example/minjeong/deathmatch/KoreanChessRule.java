package com.example.minjeong.deathmatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.minjeong.deathmatch.adapter.ViewPagerAdapter;

/**
 * Created by Minjeong on 2015-11-25.
 */
public class KoreanChessRule extends Activity{
    int noofsize = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koreanchess_rule);
        ViewPagerAdapter adapter = new ViewPagerAdapter(KoreanChessRule.this, noofsize,"KoreanChess Rule setting");
        ViewPager myPager = (ViewPager) findViewById(R.id.reviewpager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);

    }
}
