package com.example.minjeong.deathmatch;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.example.minjeong.deathmatch.adapter.ViewPagerAdapter;

import java.lang.reflect.Method;

/**
 * Created by Minjeong on 2015-11-25.
 */
public class ComconRule extends Activity {
    int noofsize = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comcon_rule);
        ViewPagerAdapter adapter = new ViewPagerAdapter(ComconRule.this, noofsize,"Comcon Rule setting");
        ViewPager myPager = (ViewPager) findViewById(R.id.reviewpager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }



}

