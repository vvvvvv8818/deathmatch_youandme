package com.example.minjeong.deathmatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.minjeong.deathmatch.adapter.ViewPagerAdapter;

/**
 * Created by Minjeong on 2015-11-25.
 */
public class PokerRule extends Activity {
    int noofsize = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poker_rule);
        ViewPagerAdapter adapter = new ViewPagerAdapter(PokerRule.this, noofsize,"Poker rule setting");
        ViewPager myPager = (ViewPager) findViewById(R.id.reviewpager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);

    }
}