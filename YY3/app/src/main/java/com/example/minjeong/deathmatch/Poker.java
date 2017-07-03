package com.example.minjeong.deathmatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by Minjeong on 2015-11-25.
 */
public class Poker extends Activity {

    int[][] card = new int[100][2];
    int[][] batting = new int[2][2];
    int where;
    int mychip;
    int yourchip;
    int turn = 1;
    int myname;
    int yourname;
    boolean mytwofacebatting = false;
    boolean yourtwofacebatting = false;
    int[] front;
    int[] back;
    int cardnum = 0;
    boolean choicebatting = false;
    int count;
    int winner;
    int yourwhere;
    int[] yourcard = new int[2];
    int[] mycard = new int[2];
    int basechip;
    int draw;
    int iamgivup;
    int yourcount;
    int yourgivup;
    String sendmsg;
    int giveCard = 0;
    static String readmsg;
    static String[] message = new String[]{"", "", "", "", "", ""};
    boolean nextturn;

    Button counterfrontbatting;
    Button counterbackbatting;
    Button myfrontbatting;
    Button mybackbatting;
    Button battingcoin;
    Button twobatting;
    Button battingfinish;
    Button pass;

    CountDownTimer _time;

    TextView baseText1;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView countercoin;
    TextView mycoin;
    TextView mybattingcoin;
    TextView timer;

    ImageView counterfront;
    ImageView counterback;
    ImageView myfront;
    ImageView myback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poker);

        timer = (TextView)findViewById(R.id.timer);
        counterfrontbatting = (Button) findViewById(R.id.btn1);
        counterbackbatting = (Button) findViewById(R.id.btn2);
        myfrontbatting = (Button) findViewById(R.id.btn3);
        mybackbatting = (Button) findViewById(R.id.btn4);
        battingcoin = (Button) findViewById(R.id.battingcoin);
        twobatting = (Button) findViewById(R.id.twobatting);
        battingfinish = (Button) findViewById(R.id.battingfinish);
        pass = (Button) findViewById(R.id.pass);


        baseText1 = (TextView) findViewById(R.id.basetext1);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        countercoin = (TextView) findViewById(R.id.player1coin);
        mycoin = (TextView) findViewById(R.id.player2coin);
        mybattingcoin = (TextView) findViewById(R.id.battingcoinnumber);

        counterfront = (ImageView) findViewById(R.id.counterfront);
        counterback = (ImageView) findViewById(R.id.counterback);
        myfront = (ImageView) findViewById(R.id.myfront);
        myback = (ImageView) findViewById(R.id.myback);
        _time = new CountDownTimer(30 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("xxx");
                if(timer.getCurrentTextColor()==getResources().getColor(android.R.color.holo_blue_light) && message[0]!=""){ //상대방턴에 상대방에게 메세지가 왓을 때
                    yourwhere = Integer.parseInt(message[0]);
                    countercoin.setText(message[1]);
                    if (message[2] == "false")
                        yourtwofacebatting = false;
                    else
                        yourtwofacebatting = true;
                    yourgivup = Integer.parseInt(message[3]);
                    mychip = Integer.parseInt(message[4]);
                    yourchip = Integer.parseInt(message[5]);

                    for(int j = 0; j<message.length;j++){
                        message[j] = "";

                        if (yourgivup == 1) { //상대방의 포기 yourgivup
                            Toast.makeText(getBaseContext(), "상대방의 포기!", Toast.LENGTH_SHORT).show();
                            giveup(yourname, batting[yourname][yourwhere]);
                            winner = yourname;
                            basechip = 0;
                            baseText1.setText("" + 0);
                            nextcard();
                        }
                    }
                    judgement();
                    onStop();
                    timer.setText("" + 30);
                    timer.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    turn++;
                }
                if(nextturn==true) {
                    onStop();
                    timer.setText("" + 30);
                    timer.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    nextturn=false;
                }
                else
                    timer.setText("" + millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                if(timer.getCurrentTextColor()==getResources().getColor(android.R.color.holo_blue_light)){

                }
                else
                if (nextturn == false) {
                    if (turn % 2 == myname && where != -1) {
                        Toast.makeText(getBaseContext(), "배팅 포기할게요ㅜㅜ" + where, Toast.LENGTH_SHORT).show();
                        iamgivup = 1;
                        giveup(myname, batting[myname][where]);
                    } else if (turn % 2 == myname && where == -1) {
                        Toast.makeText(getBaseContext(), "배팅 포기할게요ㅜㅜ" + where, Toast.LENGTH_SHORT).show();
                        iamgivup = 1;
                        giveup(myname, 0);
                    }
                }

            }
        };


        _time.start();

        boolean gameset = true;


        winner = 0;


        draw = 0;

        front = new int[]{R.drawable.front1, R.drawable.front2, R.drawable.front3, R.drawable.front4, R.drawable.front5, R.drawable.front6,
                R.drawable.front7, R.drawable.front8, R.drawable.front9, R.drawable.front10};
        back = new int[]{R.drawable.back1, R.drawable.back2, R.drawable.back3, R.drawable.back4, R.drawable.back5, R.drawable.back6,
                R.drawable.back7, R.drawable.back8, R.drawable.back9, R.drawable.back10};

        mychip = 30;
        yourchip = 30;
        myname = 0; //후에 플레이어 지정해야함.같은코드가 돌아가므로(0,1)
        yourname = 1;
        iamgivup = 0;
        nextturn = false;
        shuffle();

        //      while (gameset) {


        if (myname == 0) {
            mycard[0] = card[giveCard][0];
            mycard[1] = card[giveCard][1];
            yourcard[0] = card[giveCard + 1][0];
            yourcard[1] = card[giveCard + 1][1];
            giveCard += 2;
        }

        myfront.setImageResource(front[mycard[0] - 1]);
        myback.setImageResource(back[mycard[1] - 1]);
        counterfront.setImageResource(front[yourcard[0] - 1]);
        //    counterback.setImageResource(back[yourcard[1]-1]);


        if (winner == 0)
            turn = 2;
        else
            turn = winner;


        basechip = 2 + draw;
        baseText1.setText("" + basechip);
        mychip -= 1;
        mycoin.setText("" + mychip);
        yourchip -= 1;
        where = -1;
        countercoin.setText("" + yourchip);
        yourwhere = -1;
        count = 0;
        yourcount = 0;

        //한 패의 승패 결정
        //   while (turn != 0) {

        Toast.makeText(this, "turn은" + turn + "번째, 당신의 차례입니다", Toast.LENGTH_SHORT).show();

        myfrontbatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn % 2 == myname && where == -1) //내턴이면서 배팅 위치 선정(처음 내턴)일때
                    if (!choicebatting) {
                        where = 0;
                        choicebatting = true;
                        Toast.makeText(getBaseContext(), "배팅 위치는~" + where, Toast.LENGTH_SHORT).show();
                    }
            }
        });
        mybackbatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn % 2 == myname && where == -1)
                    if (!choicebatting) {
                        where = 1;
                        choicebatting = true;
                        Toast.makeText(getBaseContext(), "배팅 위치는~" + where, Toast.LENGTH_SHORT).show();
                    }
            }
        });
        battingcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn % 2 == myname) {
                    int cnt = Integer.parseInt(mybattingcoin.getText().toString());
                    mybattingcoin.setText(Integer.toString(cnt + 1));
                    count = Integer.parseInt(mybattingcoin.getText().toString());

                    int cnt2 = Integer.parseInt(mycoin.getText().toString());
                    if (mytwofacebatting)
                        mycoin.setText(Integer.toString(cnt2 - 2));
                    else
                        mycoin.setText(Integer.toString(cnt2 - 1));


                    if ((count > mychip) || ((mytwofacebatting == true) && (count > mychip / 2))) {
                        Toast.makeText(getBaseContext(), "배팅 칩이 부족합니다. 다시 설정하세요", Toast.LENGTH_SHORT).show();
                        if (mytwofacebatting == true)
                            mycoin.setText("" + ((count * 2) - 1));
                        else
                            mycoin.setText("" + (count - 1));
                        mybattingcoin.setText("0");

                    }
                }
            }
        });

        battingfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextturn = true;
                if (turn % 2 == myname && choicebatting) {
                    if (where == 0)
                        text3.setText("" + batting[myname][where]); //후에 batting[]으로 변경
                    else if (where == 1)
                        text4.setText("" + batting[myname][where]);
                    //후에 batting[]으로 변경
                    mybattingcoin.setText("0");
                    Toast.makeText(getBaseContext(), "배팅완료 내턴 끝!" + where, Toast.LENGTH_SHORT).show();
                    //배팅 완료했으니 턴 변경해야함!
                    //turn++;
                } else if (turn % 2 == myname && mytwofacebatting == true) {
                    text3.setText("" + count);
                    text4.setText("" + count);
                    mybattingcoin.setText("0");
                    Toast.makeText(getBaseContext(), "양면배팅완료 내턴 끝!" + where, Toast.LENGTH_SHORT).show();
                    //turn++;
                }
                judgement();
                sendMsg();
                turn++;
                matching.sendMessage(sendmsg);
                _time.start();

            }
            /*
            public void next(){


                if (turn % 2 == yourname) { //상대턴
                    Toast.makeText(getBaseContext(), "상대턴!", Toast.LENGTH_SHORT).show();
                    nextturn = false;
                    if (yourwhere == -1) {  //배팅 위치선정
                        yourcount = 1;//받아오는값이라 그냥 임의로 정함.(칩1개) 나중에 message[1]
                        yourwhere = 0; //받아오는 값이라 근야 임의로 정함.(위치 앞면0 뒷면1) message[0]
                        Toast.makeText(getBaseContext(), "배팅값 " + yourcount, Toast.LENGTH_SHORT).show();
                    } else if ((count != yourcount)) { //상대턴일대 안끝낫을때(임의로 숫자 추가)
                        Toast.makeText(getBaseContext(), "상대방의 배팅 추가!", Toast.LENGTH_SHORT).show();
                        yourcount = 1;
                    }





                if (turn % 2 == myname) { //내턴일때
                    Toast.makeText(getBaseContext(), "내턴이당~", Toast.LENGTH_SHORT).show();
                    if (turn % 2 == myname && where == -1 && choicebatting == false)
                        Toast.makeText(getBaseContext(), "배팅 위치 고르는 타임!", Toast.LENGTH_SHORT).show();
                }

                if ((cardnum == 50) || (yourchip <= 0) || (mychip <= 0)) {
                    Toast.makeText(getBaseContext(), "Game end", Toast.LENGTH_SHORT).show();

                }

            }
            */
        });


        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn % 2 == myname && where != -1) {
                    Toast.makeText(getBaseContext(), "배팅 포기할게요ㅜㅜ" + where, Toast.LENGTH_SHORT).show();
                    iamgivup = 1;
                    giveup(myname, batting[myname][where]);
                } else if (turn % 2 == myname && where == -1) {
                    Toast.makeText(getBaseContext(), "배팅 포기할게요ㅜㅜ" + where, Toast.LENGTH_SHORT).show();
                    iamgivup = 1;
                    giveup(myname, 0);
                }
            }
        });


        twobatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yourtwofacebatting == false) {
                    Toast.makeText(getBaseContext(), "양면배팅입니다", Toast.LENGTH_SHORT).show();
                    mytwofacebatting = true;
                } else
                    Toast.makeText(getBaseContext(), "상대방이 양면배팅을 이미 하였습니다", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //      }
    //   }

    public void giveup(int name, int namerchip) {//포기선언

        if (myname == name) {//패스를 외친 플레이어가 나일경우
            yourchip += (namerchip + basechip);
            if (yourtwofacebatting == true) {
                mychip -= 10;
                yourchip += 10;
            }
            mycoin.setText("" + mychip);
            countercoin.setText("" + yourchip);
            basechip = 0;
            baseText1.setText("" + basechip);
            winner = yourname;
            sendMsg();
            turn++;
            matching.sendMessage(sendmsg);
            nextcard();

        } else { //패스를 외친 플레이어가 상대일경우
            yourchip -= namerchip;
            mychip += (namerchip + 2);
            if (mytwofacebatting == true) {
                yourchip -= 10;
                mychip += 10;
            }
            mycoin.setText("" + mychip);
            countercoin.setText("" + yourchip);
            basechip = 0;
            baseText1.setText("" + basechip);
            winner = myname;
            nextcard();
        }
    }


    public void shuffle() {
        int front_num, back_num;
        boolean[] sfle = new boolean[100];
        Random r = new Random();

        for (front_num = 1; front_num <= 10; front_num++)
            for (back_num = 1; back_num <= 10; back_num++) {

                while (true) {
                    int order = r.nextInt(100);
                    if (sfle[order] == false) {
                        card[order][0] = front_num;
                        card[order][1] = back_num;
                        sfle[order] = true;
                        break;
                    }
                }

            }
    }

    public void mywin(int multi, int plus) {

        counterback.setImageResource(back[yourcard[1] - 1]);
        mychip += (batting[yourname][yourwhere] * multi) + basechip + plus;
        mycoin.setText("" + mychip);
        Toast.makeText(getBaseContext(), "내가이김"+mychip+"/"+text1.getText(), Toast.LENGTH_SHORT).show();



        yourtwofacebatting = false;
        mytwofacebatting = false;
        batting[0][0] = 0;
        batting[0][1] = 0;
        batting[1][0] = 0;
        batting[1][1] = 0;
        text1.setText("" + 0);
        text2.setText("" + 0);
        text3.setText("" + 0);
        text4.setText("" + 0);
        baseText1.setText("" + 0);
        draw = 0;
        winner = myname;
        cardnum++;
        nextcard();
    }

    public void yourwin(int multi, int plus) {
        counterback.setImageResource(back[yourcard[1] - 1]);
        yourchip += (batting[myname][where] * multi) + basechip + plus;
        countercoin.setText("" + yourchip);

        Toast.makeText(getBaseContext(), "너가이김"+yourchip, Toast.LENGTH_SHORT).show();
        //초기화
        yourtwofacebatting = false;
        mytwofacebatting = false;
        batting[0][0] = 0;
        batting[0][1] = 0;
        batting[1][0] = 0;
        batting[1][1] = 0;
        text1.setText("" + 0);
        text2.setText("" + 0);
        text3.setText("" + 0);
        text4.setText("" + 0);
        baseText1.setText("" + 0);
        count = 0;
        where = -1;
        draw = 0;
        winner = yourname;
        nextcard();
        cardnum++;
    }

    public void draw() {
        counterback.setImageResource(back[yourcard[1] - 1]);

        //초기화
        yourtwofacebatting = false;
        mytwofacebatting = false;
        batting[0][0] = 0;
        batting[0][1] = 0;
        batting[1][0] = 0;
        batting[1][1] = 0;
        text1.setText("" + 0);
        text2.setText("" + 0);
        text3.setText("" + 0);
        text4.setText("" + 0);

        draw = (batting[myname][where] + batting[yourname][yourwhere] + basechip);
        baseText1.setText("" + draw);
        winner = 0;
        nextcard();
        cardnum++;
    }

    public void sendMsg() { //블루투스 통신으로 보낼 내 정보 (어느쪽에배팅/얼마를배팅/양면배팅/내가포기함/내칩/상대칩)

        sendmsg = "";
        sendmsg += (where + "/" + mybattingcoin + "/" + mytwofacebatting + "/" + iamgivup + "/" + mychip + "/" + yourchip);


    }

    public static void readMsg() {//블루투스 통신으로 받은 상대정보 분석(어느쪽에배팅/얼마를배팅/양면배팅/상대가포기함/상대칩/내칩)
        System.out.println("통신으로 받아옴");

        int start = 0;
        int index = 0;
        for (int i = 0; i < readmsg.length(); i++) {

            if (readmsg.charAt(i) == '/') {
                for (int j = start; j < i; j++) {
                    message[index] += readmsg.charAt(j);
                }
                start = i + 1;
                index++;
            }
            if (i == readmsg.length() - 1) {
                for (int j = start; j < i + 1; j++) {
                    message[index] += readmsg.charAt(j);
                }
            }
        }



    }

    public void judgement() {
        //  Toast.makeText(getBaseContext(), "판정!", Toast.LENGTH_SHORT).show();
        if ((where != -1) && (yourwhere != -1)) { //매 판마다 판정(둘다 배팅 햇을 때)
            Toast.makeText(getBaseContext(), "둘의 갯수 비교!", Toast.LENGTH_SHORT).show();
            if (turn % 2 == myname) {
                batting[myname][where] += count;
            } else {
                batting[yourname][yourwhere] += yourcount;
            }
            text1.setText("" + batting[0][0]);
            text2.setText("" + batting[0][1]);
            text3.setText("" + batting[1][0]);
            text4.setText("" + batting[1][1]);
            if (batting[myname][where] == batting[yourname][yourwhere]) { //칩의 수가 같을 때(판정가능할때)
                Toast.makeText(getBaseContext(), "판정 가능할때", Toast.LENGTH_SHORT).show();
                if (mycard[where] > yourcard[yourwhere]) { //나의 승리(일반)
                    if (mytwofacebatting == true) { //내가양면배팅일때는
                        if (mycard[0] > yourcard[0] && mycard[1] > yourcard[1]) { //양면일때 두면 다 이겨야만 승리
                            mywin(3, 10); // 내패와 남패 회수하고 10개 더 얻음
                        } else // 양면배팅 실패. 잃음.
                            yourwin(3, 0); //한쪽에 배팅한 칩의  2배 잃음.
                    } else if (yourtwofacebatting == true) //내가 아닌 상대방 양면배팅일 경우
                        mywin(3, 0);
                    else
                        mywin(2, 0);
                    //System.out.println("*****나의 승리*****");
                    turn = 0;
//                    break;
                } else if (mycard[where] < yourcard[yourwhere]) { //상대의 승리(일반)
                    if (yourtwofacebatting == true) { // 상대가 양면배팅일때
                        if (mycard[0] < yourcard[0] && mycard[1] < yourcard[1]) { //상대 승리시
                            yourwin(3, 10);
                        } else //상대의 양면배팅 실패
                            mywin(3, 0);
                    } else if (mytwofacebatting == true) //내가 양면배팅했을때(내 패 다 뺏길때)
                        yourwin(3, 0);
                    else
                        yourwin(2, 0);
                    //System.out.println("*****상대방의 승리*****");
                    turn = 0;
                    //                 break;
                } else { //비겼을때
                    draw();
                    //System.out.println("*****비김*****");
                    turn = 0;
                    //                   break;
                }

            } else {//판정이 아직 날 수 없을 때 nextturn 삭제
                if (turn % 2 == myname) {
                    batting[myname][where] += count;
                } else {
                    batting[yourname][yourwhere] += yourcount;
                }
                text1.setText("" + batting[0][0]);
                text2.setText("" + batting[0][1]);
                text3.setText("" + batting[1][0]);
                text4.setText("" + batting[1][1]);
            }
        } else //둘다 처음일때(첫번째 턴인 사람이 처음 배팅걸 때) nextturn 삭제
        {
            if (turn % 2 == myname) {
                batting[myname][where] += count;
            } else {
                batting[yourname][yourwhere] += yourcount;
            }
            Toast.makeText(this, "값바꿔주기~.~", Toast.LENGTH_SHORT).show();
            text1.setText("" + batting[0][0]);
            text2.setText("" + batting[0][1]);
            text3.setText("" + batting[1][0]);
            text4.setText("" + batting[1][1]);
        }
    }

    public void nextcard() {
        mycard[0] = card[giveCard][0];
        mycard[1] = card[giveCard][1];
        yourcard[0] = card[giveCard + 1][0];
        yourcard[1] = card[giveCard + 1][1];
        giveCard += 2;

        myfront.setImageResource(front[mycard[0] - 1]);
        myback.setImageResource(back[mycard[1] - 1]);
        counterfront.setImageResource(front[yourcard[0] - 1]);
        counterback.setImageResource(R.drawable.counterback);


        if (winner == 0)
            turn = 2;
        else
            turn = winner;


        basechip = 2 + draw;
        baseText1.setText("" + basechip);
        mychip -= 1;
        mycoin.setText("" + mychip);
        yourchip -= 1;
        where = -1;
        countercoin.setText("" + yourchip);
        yourwhere = -1;
        count = 0;
        yourcount = 0;

        Toast.makeText(this, "turn은" + turn + "번째, ", Toast.LENGTH_SHORT).show();
    }
}