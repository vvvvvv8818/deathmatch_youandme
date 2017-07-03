package com.example.minjeong.deathmatch.adapter;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.minjeong.deathmatch.ComconRule;
import com.example.minjeong.deathmatch.Item;
import com.example.minjeong.deathmatch.MainActivity;

import java.lang.reflect.Method;

/**
 * Created by LG on 2015-12-07.
 */
public class ImageAdapter extends BaseAdapter{
    private Context context;

    private Item[] items = new Item[9];

    public ImageAdapter(Context context, Item[] i)
    {
        this.context = context;
        items = i;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if( convertView == null ){
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(MainActivity.realWidth/4, MainActivity.realHeight/6));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2,2,2,2);
        }else{
            imageView=(ImageView)convertView;
        }

        imageView.setImageResource(items[position].getResource());

        return imageView;
    }

}
