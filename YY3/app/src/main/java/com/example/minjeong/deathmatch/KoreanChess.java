package com.example.minjeong.deathmatch;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Minjeong on 2015-11-25.
 */

public class KoreanChess extends Activity {
    static Pan board = new Pan();
    static Button[] btn = new Button[12];
    Drawable D;
    Button tempButton;
    int i;
    int mydeath1;
    int mydeath2;
    int mydeath3;
    int codeath1;
    int codeath2;
    int codeath3;
    ImageView die1;
    ImageView die2;
    ImageView die3;
    ImageView codie1;
    ImageView codie2;
    ImageView codie3;
    int temp_a;
    String send;
    Drawable[] Dd = new Drawable[12];
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koreanchess);

        timer = (TextView) findViewById(R.id.timer);


        final SoundPool sound_chess = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        final int soundID_chess = sound_chess.load(this, R.raw.janggi, 1);


        int[] btn_id = new int[]{R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12};
        die1 = (ImageView) findViewById(R.id.myplayer_eat1);
        die2 = (ImageView) findViewById(R.id.myplayer_eat2);
        die3 = (ImageView) findViewById(R.id.myplayer_eat3);
        codie1 = (ImageView) findViewById(R.id.counter_eat1);
        codie2 = (ImageView) findViewById(R.id.counter_eat2);
        codie3 = (ImageView) findViewById(R.id.counter_eat3);
        die1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.set_cnt = 1;
            }
        });
        die2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.set_cnt = 2;
            }
        });
        die3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.set_cnt = 3;
            }
        });

        if (MainActivity.host) {
            board.turn = true;
        } else
            board.turn = false;

        for (i = 0; i < 12; i++) {
            btn[i] = (Button) findViewById(btn_id[i]);
            Dd[i] = btn[i].getBackground();
            btn[i].setOnClickListener(new View.OnClickListener() {
                int a = i;
                int s;

                /**
                 * return 0 = repick
                 * return 3 = set
                 * return 1 = selected = 1로 갱신
                 * return 2 = eat
                 * return 4 = move
                 */
                @Override
                public void onClick(View v) {
                    if (board.turn)
                        timer.setText("Your Turn");
                    else
                        timer.setText("");

                    if (board.turn) {
                        int b = board.set_cnt;
                        s = board.choice(a / 3, a % 3);
                        System.out.println(s);
                        switch (s) {
                            case 0: {
                                break;
                            }
                            case 1: {
                                D = btn[a].getBackground();
                                tempButton = btn[a];
                                temp_a = a;
                                break;
                            }
                            case 2: {
                                sound_chess.play(soundID_chess, 1, 1, 0, 0, 1);
                                if (board.panstate[a / 3][a % 3] == 5) {
                                    btn[a].setBackgroundResource(R.drawable.hu);
                                } else
                                    btn[a].setBackground(D);
                                if (temp_a / 3 == 3)
                                    tempButton.setBackgroundResource(R.drawable.basered);
                                else if (temp_a / 3 == 0)
                                    tempButton.setBackgroundResource(R.drawable.basegreen);
                                else
                                    tempButton.setBackgroundResource(R.drawable.base);
                                System.out.println(board.cardtype[0][0].who + " " + board.cardtype[0][1].who + board.cardtype[0][0].state + " " + board.cardtype[0][1].state + "\n" + board.cardtype[1][0].who + " " + board.cardtype[1][1].who + board.cardtype[1][0].state + " " + board.cardtype[1][1].state + "\n" + board.cardtype[2][0].who + " " + board.cardtype[2][1].who + board.cardtype[2][0].state + " " + board.cardtype[2][1].state + "\n" + board.cardtype[3][0].who + " " + board.cardtype[3][1].who + board.cardtype[3][0].state + " " + board.cardtype[3][1].state + "\n");
                                mydeath2 = board.death2();
                                mydeath1 = board.death1();
                                mydeath3 = board.death3();
                                codeath1 = board.counter_death1();
                                codeath2 = board.counter_death2();
                                codeath3 = board.counter_death3();
                                System.out.println(mydeath1 + " " + mydeath2 + " " + mydeath3);
                                if (mydeath2 > 0) {
                                    die2.setBackgroundResource(R.drawable.jang);
                                }
                                if (mydeath1 > 0) {
                                    die1.setBackgroundResource(R.drawable.sang);
                                }
                                if (mydeath3 > 0) {
                                    die3.setBackgroundResource(R.drawable.ja);
                                }
                                if (codeath2 > 0) {
                                    codie2.setBackgroundResource(R.drawable.jang2);
                                }
                                if (codeath1 > 0) {
                                    codie1.setBackgroundResource(R.drawable.sang2);
                                }
                                if (codeath3 > 0) {
                                    codie3.setBackgroundResource(R.drawable.ja2);
                                }
                                send = send_data();
                                sendDt();
                                break;
                            }
                            case 3: {
                                sound_chess.play(soundID_chess, 1, 1, 0, 0, 1);
                                if (b == 1) {
                                    btn[a].setBackgroundResource(R.drawable.sang);
                                    die1.setBackgroundResource(R.drawable.noeat);
                                } else if (b == 2) {
                                    btn[a].setBackgroundResource(R.drawable.jang);
                                    die2.setBackgroundResource(R.drawable.noeat);
                                } else if (b == 3) {
                                    btn[a].setBackgroundResource(R.drawable.ja);
                                    die3.setBackgroundResource(R.drawable.noeat);
                                }
                                send = send_data();
                                sendDt();
                                break;
                            }
                            case 4: {
                                sound_chess.play(soundID_chess, 1, 1, 0, 0, 1);
                                if (board.panstate[a / 3][a % 3] == 5) {
                                    btn[a].setBackgroundResource(R.drawable.hu);
                                } else
                                    btn[a].setBackground(D);
                                if (temp_a / 3 == 3)
                                    tempButton.setBackgroundResource(R.drawable.basered);
                                else if (temp_a / 3 == 0)
                                    tempButton.setBackgroundResource(R.drawable.basegreen);
                                else
                                    tempButton.setBackgroundResource(R.drawable.base);
                                send = send_data();
                                sendDt();
                                break;
                            }
                        }
                    } else {
                        mydeath2 = board.death2();
                        mydeath1 = board.death1();
                        mydeath3 = board.death3();
                        codeath1 = board.counter_death1();
                        codeath2 = board.counter_death2();
                        codeath3 = board.counter_death3();
                        System.out.println(mydeath1 + " " + mydeath2 + " " + mydeath3);
                        if (mydeath2 > 0) {
                            die2.setBackgroundResource(R.drawable.jang);
                        }
                        if (mydeath1 > 0) {
                            die1.setBackgroundResource(R.drawable.sang);
                        }
                        if (mydeath3 > 0) {
                            die3.setBackgroundResource(R.drawable.ja);
                        }
                        if (codeath2 > 0) {
                            codie2.setBackgroundResource(R.drawable.jang2);
                        }
                        if (codeath1 > 0) {
                            codie1.setBackgroundResource(R.drawable.sang2);
                        }
                        if (codeath3 > 0) {
                            codie3.setBackgroundResource(R.drawable.ja2);
                        }
                        judge_finish();
                    }
                }

                String send_data() {
                    String u = "";
                    u += s;
                    u += a / 3;
                    u += a % 3;
                    if (s == 2 || s == 4) {
                        u += board.temp_x;
                        u += board.temp_y;
                    } else {
                        u += board.select(a / 3, a % 3).type;
                    }
                    return u;
                }
            });
        }
    }

    static void receive_data(String msg) {
        int index; // 이동후
        int temp_index; // 이동전
        String temps = "";
        temps += msg.charAt(0);
        int what = Integer.parseInt(temps);
        temps = "";
        temps += msg.charAt(1);
        int x = 3 - Integer.parseInt(temps);
        temps = "";
        temps += msg.charAt(2);
        int y = 2 - Integer.parseInt(temps);
        temps = "";
        temps += msg.charAt(3);
        int i = Integer.parseInt(temps);
        int j = 0;

        index = x * 3 + y;
        if (what != 3) {
            temps = "";
            temps += msg.charAt(4);
            j = 2 - Integer.parseInt(temps);
            i = 3 - i;
            temp_index = i * 3 + j;
            Drawable te = KoreanChess.btn[temp_index].getBackground();
            KoreanChess.btn[index].setBackground(te);
            if (x == 3 && board.panstate[i][j] == 4)
                KoreanChess.btn[index].setBackgroundResource(R.drawable.hu2);
            if (i == 3)
                KoreanChess.btn[temp_index].setBackgroundResource(R.drawable.basered);
            else if (i == 0)
                KoreanChess.btn[temp_index].setBackgroundResource(R.drawable.basegreen);
            else
                KoreanChess.btn[temp_index].setBackgroundResource(R.drawable.base);
        } else {
            if (i == 2)
                KoreanChess.btn[index].setBackgroundResource(R.drawable.sang2);
            else if (i == 3)
                KoreanChess.btn[index].setBackgroundResource(R.drawable.jang2);
            else if (i == 4)
                KoreanChess.btn[index].setBackgroundResource(R.drawable.ja2);
            else if (i == 5)
                KoreanChess.btn[index].setBackgroundResource(R.drawable.hu2);
        }

        System.out.println("### receiver_data : " + what + " " + x + " " + y + " " + i + " " + j + " ");
        board.receive(what, x, y, i, j);
        KoreanChess.btn[0].callOnClick();
        board.turn = true;
    }

    void judge_finish() {
        if (board.cardtype[0][1].state == 0) {
            Toast.makeText(getBaseContext(), "You Win!!", Toast.LENGTH_LONG).show();
            finish();
        } else if (board.cardtype[0][0].state == 0) {
            Toast.makeText(getBaseContext(), "You Lose!!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    void sendDt() {
        System.out.println("##### Send!!");
        board.turn = false;
        matching.sendMessage(send);
    }
}