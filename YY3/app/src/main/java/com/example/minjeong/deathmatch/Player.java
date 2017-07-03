package com.example.minjeong.deathmatch;

import android.widget.TextView;

/**
 * Created by Minjeong on 2015-12-08.
 */
public class Player {

    private int score;
    private TextView textview;

    public Player(TextView view)
    {
        score = 0;
        textview = view;
    }

    public int getScore()
    {	return score;	}

    public void setScore(int s)
    {
        this.score = s;
        textview.setText( Integer.toString(score) );
    }

    public void score()
    {
        score += 10;
        textview.setText( Integer.toString(score) );
    }

}
