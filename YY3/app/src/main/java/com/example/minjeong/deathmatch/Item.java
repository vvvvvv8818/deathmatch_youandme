package com.example.minjeong.deathmatch;

import com.example.minjeong.deathmatch.R;

/**
 * Created by LG on 2015-12-07.
 */
public class Item {
    static int images[] = new int[]{ R.drawable.aaa, R.drawable.aab, R.drawable.aac,
            R.drawable.aba, R.drawable.abb, R.drawable.abc,
            R.drawable.aca, R.drawable.acb, R.drawable.acc,
            R.drawable.baa, R.drawable. bab, R.drawable.bac,
            R.drawable.bba, R.drawable.bbb, R.drawable.bbc,
            R.drawable.bca, R.drawable.bcb, R.drawable.bcc,
            R.drawable.caa, R.drawable.cab, R.drawable.cac,
            R.drawable.cba, R.drawable.cbb, R.drawable.cbc,
            R.drawable.cca, R.drawable.ccb, R.drawable.ccc};

    private int index;
    private int image;


    public Item(int n)
    {
        index = n;
        image = images[n];
    }

    public int getIndex()
    {   return index;   }

    public int getResource()
    {
        return image;
    }
}
