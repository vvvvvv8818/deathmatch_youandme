package com.example.minjeong.deathmatch.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minjeong.deathmatch.KoreanChessRule;
import com.example.minjeong.deathmatch.MainActivity;
import com.example.minjeong.deathmatch.R;

/**
 * Created by Minjeong on 2015-11-25.
 */
public class ViewPagerAdapter extends PagerAdapter {
    int size;
    Activity act;
    View layout;
    TextView pagenumber;
    ImageView imagenumber;
    Button click;
    String text;

    public ViewPagerAdapter(Activity mainActivity, int noofsize, String massage) {
        // TODO Auto-generated constructor stub
        size = noofsize;
        act = mainActivity;
        text = massage;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        LayoutInflater inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.rule, null);
        pagenumber = (TextView) layout.findViewById(R.id.pagenumber);
        imagenumber = (ImageView) layout.findViewById(R.id.imagenumber);
        int pagenumberTxt = position + 1;
        if (text.equals("KoreanChess Rule setting")) {
            if (pagenumberTxt == 1) {
                imagenumber.setImageResource(R.drawable.koreanchesspan);
                pagenumber.setText("십이장기는 가로 4칸, 세로 3칸 총 12칸으로 이루어진 게임 판에서 진행되며 플레이어들의 바로 앞쪽 3칸이 각자의 진영이 된다.");
            } else if (pagenumberTxt == 2) {
                imagenumber.setImageResource(R.drawable.koreanchesspan);
                pagenumber.setText("플레이어 2명에게는 4가지 종류의 말이 1개씩 주어지며 각 말은 지정된 위치에 놓인 상태로 게임을 시작한다.");
            } else if (pagenumberTxt == 3) {
                pagenumber.setText("각 말들은 말에 표시된 방향으로만 이동할 수 있으며 각각의 역할은 다음과 같다.");
            } else if (pagenumberTxt == 4) {
                imagenumber.setImageResource(R.drawable.jang);
                pagenumber.setText("장(將). 자신의 진영 오른쪽에 놓이는 말로 앞, 뒤와 좌, 우로 이동이 가능하다.");
            } else if (pagenumberTxt == 5) {
                imagenumber.setImageResource(R.drawable.sang);
                pagenumber.setText("상(相). 자신의 진영 왼쪽에 놓이며 대각선 4방향으로 이동할 수 있다.");
            } else if (pagenumberTxt == 6) {
                imagenumber.setImageResource(R.drawable.wang);
                pagenumber.setText("왕(王). 자신의 진영 중앙에 위치하며 앞, 뒤, 좌, 우, 대각선 방향까지 모든 방향으로 이동이 가능하다.");
            } else if (pagenumberTxt == 7) {
                imagenumber.setImageResource(R.drawable.ja);
                pagenumber.setText("자(子). 왕의 앞에 놓이며 오로지 앞으로만 이동할 수 있다.");
            } else if (pagenumberTxt == 8) {
                imagenumber.setImageResource(R.drawable.hu);
                pagenumber.setText("하지만, 자(子)는 상대 진영에 들어가면 후(侯)가 된다. 후(侯)는 대각선 뒤쪽 방향을 제외한 전 방향으로 이동할 수 있다.");
            } else if (pagenumberTxt == 9) {
                imagenumber.setImageResource(R.drawable.malmove);
                pagenumber.setText("게임이 시작되면 선 플레이어부터 말 1개를 1칸 이동시킬 수 있다. 말을 이동시켜 상대방의 말을 잡은 경우, 해당 말을 포로로 잡게 되며 포로로 잡은 말은 다음 턴부터 자신의 말로 사용할 수 있다.");
            } else if (pagenumberTxt == 10) {
                imagenumber.setImageResource(R.drawable.malset);
                pagenumber.setText("게임 판에 포로로 잡은 말을 내려놓는 행동도 턴을 소모하는 것이며 이미 말이 놓여진 곳이나 상대의 진영에는 말을 내려놓을 수 없다.");
            } else if (pagenumberTxt == 11) {
                imagenumber.setImageResource(R.drawable.ja);
                pagenumber.setText("상대방의 후(侯)를 잡아 자신의 말로 사용할 경우에는 자(子)로 사용된다.");
            } else if (pagenumberTxt == 12) {
                imagenumber.setImageResource(R.drawable.malset);
                pagenumber.setText("게임은 한 플레이어가 상대방의 왕(王)을 잡으면 해당 플레이어의 승리로 종료된다.");
            }




        } else if (text.equals("Comcon Rule setting")) {
            if (pagenumberTxt == 1) {
//                imagenumber.setImageResource(R.drawable.koreanchesspan);
                pagenumber.setText("1. '합'을 누르고\n" +
                        "[같은 모양] 또는 [같은 모양 색깔] 또는 [같은 배경 색깔]을 가진 그림을 <세 개> 골라주세요\n" +
                        "다시 '합'을 누르면 성공!");
            } else if (pagenumberTxt == 2) {
//                imagenumber.setImageResource(R.drawable.koreanchesspan);
                pagenumber.setText("2. 가능한 '합'을 모두 찾았을 경우 '결'을 터치!");
            } else if (pagenumberTxt == 3) {
                pagenumber.setText("3. '합'이나 '결'은 한 차례에 한 번의 기회가 주어져요.");
            } else if (pagenumberTxt == 4) {
//                imagenumber.setImageResource(R.drawable.jang);
                pagenumber.setText("4. 잘못된 '합'을 찾았을 경우 다음 사람에게 차례가 넘어갑니다.\n" +
                        "실수로 '결'을 눌러도 차례가 넘어가니 조심조심!!");
            }
        }

        ((ViewPager) container).

                addView(layout, 0);

        return layout;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
