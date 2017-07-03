package com.example.minjeong.deathmatch;

/**
 * Created by LG on 2015-12-07.
 */
public class Set {

    private int set[];

    public Set(int a, int b, int c) {
        set = new int[3];

        if (a < b)
            if (b < c) {
                set[0] = a;
                set[1] = b;
                set[2] = c;
            } else if (a < c) {
                set[0] = a;
                set[1] = c;
                set[2] = b;
            } else {
                set[0] = c;
                set[1] = a;
                set[2] = b;
            }
        else //b<a
            if (c < b) {
                set[0] = c;
                set[1] = b;
                set[2] = a;
            } else if (c < a) {
                set[0] = b;
                set[1] = c;
                set[2] = a;
            } else {
                set[0] = b;
                set[1] = a;
                set[2] = c;
            }

    }

    public boolean equals(Set obj)
    {
        if(obj.get(0)==set[0] && obj.get(1)==set[1] && obj.get(2)==set[2])
            return true;
        else return false;
    }

    public int get(int index)
    {
        return set[index];
    }

    public String toString()
    {
        return set[0]+" "+set[1]+" "+set[2];
    }

}
