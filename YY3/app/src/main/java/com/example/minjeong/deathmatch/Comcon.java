package com.example.minjeong.deathmatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minjeong.deathmatch.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by Minjeong on 2015-11-25.
 */
public class Comcon extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {


        boolean TURN = false;
        String RESULT = "";//(판, 합 배열 인덱스, 점수)

        private TextView score_yours;
        private TextView score_mine;
        private TextView turn;

        private final int[] shapes = {0, 1, 2}; //0:circle / 1:rectangle / 2:triangle
        private final int[] color = {0, 1, 2}; //0:red / 1:blue / 2:yellow
        private final int[] background = {0, 1, 2}; //0:black / 1:white / 2:green

        Item items[];
        Combinations combs; //가능한 '합' 저장

        private GridView board;
        private Button combine, conclude;
        static boolean combine_clicked = false;
        //'합'에서 눌린 아이템
        private ArrayList<Integer> clicked_index = new ArrayList<Integer>();

        static String receivedMessage = "";

        TextView timer_textview;
        CountDownTimer _time;

        AlertDialog.Builder builder;
        AlertDialog aDialog;
        AlertDialog bDialog;

    SoundPool sounds = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int soundID_item;
    int soundID_button;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.comcon);
             soundID_item = sounds.load(this, R.raw.choice_item, 1);
             soundID_button = sounds.load(this, R.raw.set_button, 1);

            items = new Item[9]; //board에 들어갈 아이템들
            combs = new Combinations();

            score_yours = (TextView) findViewById(R.id.score_yours);
            score_mine = (TextView) findViewById(R.id.score_mine);
            turn = (TextView) findViewById(R.id.turn);
            turn.setText(Boolean.toString(TURN));
        /* 타이머
        * 1) 내 턴일 때 -> 1.1) 결/합 찾으면 -> 리스너에서 메시지 보내고 턴 바꿈
        *                1.2) 시간 다 되면 턴 넘겨줌
        * 2) 내 턴이 아닐 때 -> 메시지 받으면 내 턴으로 넘어옴*/
            timer_textview = (TextView) findViewById(R.id.timer);
            _time = new CountDownTimer(15 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // change TextView
                    timer_textview.setText("" + millisUntilFinished / 1000);
                    // 1) 내 턴일 때 -> 리스너에서 턴 넘겨줌
                    // 2)내 턴이 아닐 때 -> 메시지 받으면 처리하고 -> changeTurn() 내 턴으로 바뀜
//                System.out.println("### checking... " + receivedMessage);
                    if (!receivedMessage.equals("")) {
                        handleReceivedMessage();
                    }
                }

                //턴 바뀌면 cancel()
                @Override
                public void onFinish() {
//                System.out.println("&&& sendMessage(onFinish) : "+RESULT);
//                matching.sendMessage(RESULT);
                    RESULT = "";
                }
            };
            _time.start(); //바로 시작하면 안되자나 ㅠㅠ

            AlertDialog.Builder builderB = new AlertDialog.Builder(this);
            builderB.setTitle("WATING...");
            bDialog = builderB.create();


            builder = new AlertDialog.Builder(this);
        /* 다이얼로그 띄우기 */
            builder.setTitle("준비 버튼을 눌러주세요.")
                    .setCancelable(false)
                    .setPositiveButton("READY!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TURN = true;
                            RESULT = "READY";
                            matching.sendMessage(RESULT);
                            System.out.println("&&& sendMessage (준비버튼) : " + RESULT);
                            //버튼 바꾸기
                            aDialog.dismiss();
                            bDialog.show();
                        }
                    });
            aDialog = builder.create();
            aDialog.show();

        }//onCreate() 끝

        private void setActivity() {
        /* items 띄우고 리스너 달기 */
            System.out.println("### setActivity()");

            board = (GridView) findViewById(R.id.board);
            board.setAdapter(new ImageAdapter(getApplicationContext(), items));
            combine = (Button) findViewById(R.id.combine);
            conclude = (Button) findViewById(R.id.conclude);
        /* 버튼 두개 '결' , '합'
         * '합' 리스너 : 누르면 items 세 개 선택되도록
         * '결' 리스너 : 적절한상황이니 ? */
            combine.setOnClickListener(this);
            conclude.setOnClickListener(this);
            board.setOnItemClickListener(this);
            System.out.println("@@@ 리스너 달기 끝!");

        } // setActivity()

        private void makeItem(Item[] items) {
            System.out.println("### makeItem()");
            //index : item 개수 체크용
            ArrayList<Integer> index = new ArrayList<Integer>();
            int i = 0;
            RESULT = "";
            while (index.size() < 9) {
                Random rand = new Random();
                int n = rand.nextInt(27);
                if (!index.contains(n)) //중복안되면
                {
                    items[i++] = new Item(n);
                    index.add(n);
                    RESULT = RESULT + n + ","; //item의 인덱스 9개를 순서대로 저장
                }
            }
            System.out.println("@@@ makeItem() 끝 RESULT : " + RESULT);
        }//makeItem() 끝


        private boolean makeComb() {
            System.out.println("### makeComb()");
            //'합' 개수 검사
            //1~15 범위 벗어나면 판 다시만들기

            //shape
            ArrayList<Integer> circle = new ArrayList<Integer>();
            ArrayList<Integer> rectangle = new ArrayList<Integer>();
            ArrayList<Integer> triangle = new ArrayList<Integer>();
            for (int i = 0; i < 9; i++) {
                if (0 <= items[i].getIndex() && items[i].getIndex() <= 8) //circle
                    circle.add(i);
                else if (9 <= items[i].getIndex() && items[i].getIndex() <= 17)
                    rectangle.add(i);
                else if ((18 <= items[i].getIndex() && items[i].getIndex() <= 26))
                    triangle.add(i);
            }
//        System.out.println("@@@ Check Shape");
//        System.out.println("@ cirle : "+circle.toString());
//        System.out.println("@ rectangle : "+rectangle.toString());
//        System.out.println("@ triangle : "+triangle.toString());

            //color
            ArrayList<Integer> red = new ArrayList<Integer>();
            ArrayList<Integer> blue = new ArrayList<Integer>();
            ArrayList<Integer> yellow = new ArrayList<Integer>();
            for (int i = 0; i < 9; i++) {
                if ((0 <= items[i].getIndex() && items[i].getIndex() <= 2) || (9 <= items[i].getIndex() && items[i].getIndex() <= 11) || (18 <= items[i].getIndex() && items[i].getIndex() <= 20)) //0,1,2, 9, 10, 11, 18, 19, 20
                    red.add(i);
                else if ((3 <= items[i].getIndex() && items[i].getIndex() <= 5) || (12 <= items[i].getIndex() && items[i].getIndex() <= 14) || (21 <= items[i].getIndex() && items[i].getIndex() <= 23)) //3,4,5, 12,13,14, 21,22,23
                    blue.add(i);
                else if ((6 <= items[i].getIndex() && items[i].getIndex() <= 8) || (15 <= items[i].getIndex() && items[i].getIndex() <= 17) || (24 <= items[i].getIndex() && items[i].getIndex() <= 26)) //6,7,8, 15,16,17, 24,25,26
                    yellow.add(i);
            }
//        System.out.println("@@@ Check Color");
//        System.out.println("@ red : "+red.toString());
//        System.out.println("@ blue : "+blue.toString());
//        System.out.println("@ yellow : "+yellow.toString());

            //background
            ArrayList<Integer> black = new ArrayList<Integer>();
            ArrayList<Integer> white = new ArrayList<Integer>();
            ArrayList<Integer> green = new ArrayList<Integer>();
            for (int i = 0; i < 9; i++) {
                if (items[i].getIndex() % 3 == 0)
                    black.add(i);
                else if (items[i].getIndex() % 3 == 1)
                    white.add(i);
                else if (items[i].getIndex() % 3 == 2)
                    green.add(i);
            }
//        System.out.println("@@@ Check Background");
//        System.out.println("@ black : "+black.toString());
//        System.out.println("@ white : "+white.toString());
//        System.out.println("@ green : "+green.toString());


            ArrayList<ArrayList<Integer>> conditions = new ArrayList<ArrayList<Integer>>();
            conditions.add(circle);
            conditions.add(rectangle);
            conditions.add(triangle);
            conditions.add(red);
            conditions.add(blue);
            conditions.add(yellow);
            conditions.add(black);
            conditions.add(white);
            conditions.add(green);

        /* 모든 결 계산해서 combs에 추가*/
            for (int index = 0; index < 9; index++) {
                if (conditions.get(index).size() >= 3)
                    for (int i = 0; i < conditions.get(index).size() - 2; i++)
                        for (int j = i + 1; j < conditions.get(index).size() - 1; j++)
                            for (int k = j + 1; k < conditions.get(index).size(); k++) {
                                combs.add(new Set(conditions.get(index).get(i), conditions.get(index).get(j), conditions.get(index).get(k)));
                                if (combs.size() > 10) //'합'이 15개보다 많아지면 false
                                {
                                    System.out.println("false / 249 / combs.size : " + combs.size());
                                    combs.clear();
                                    return false;
                                }
                            }
            }

            if (combs.size() < 1) //'결'이 한개도 없으면 false
            {
                System.out.println("false / 257");
                combs.clear();
                return false;
            }
            combs.deleteRepeated(); //중복되는 원소 제거

            System.out.println("### combs.size() : " + combs.size());
            return true;


        }//makeComb() 끝


        /* 결합 버튼 리스너 */
        @Override
        public void onClick(View v) {
            System.out.println("### myTURN? " + TURN);
            if (!TURN) {
                Toast.makeText(getApplicationContext(), "Not your Trun. Please wait.", Toast.LENGTH_SHORT).show();
                return;
            } else //내 턴이야
            {
                sounds.play(soundID_button, 1, 1, 0, 0, 1);

        /* combine 버튼 */
                if (v.getId() == combine.getId()) {
                    if (combine_clicked == false) //안눌린 상태였으면 눌린 상태->gridview 클릭 되게
                    {
                        combine_clicked = true;
                        //버튼 색깔 바꾸기
                        combine.setBackgroundResource(R.drawable.combine_clicked);
                    } else //눌린 상태였으면 gridview에서 눌린 인덱스 받아옴;
                    {
                        combine_clicked = false;
                        //인덱스 받아와서 '합'인지 확인
                        //세개 아닌 경우 또 걸러줘야함!!!!!!!!!!!!!!!!! => 지금은 아무일도 안일어남
                        if (clicked_index.size() == 3) {
                            board.setAlpha(100);
                            Set inputSet = new Set(clicked_index.get(0), clicked_index.get(1), clicked_index.get(2));
                            if (combs.contains(inputSet)) //있
                            {
                                if (score_mine.getText().toString().equals("")) {
                                    score_mine.setText("10");
                                } else {
                                    score_mine.setText(Integer.toString(Integer.parseInt(score_mine.getText().toString()) + 10));
                                }
                                RESULT = "-1 " + combs.indexOf(inputSet) + " " + score_mine.getText().toString();
                                combs.remove(inputSet);
                                System.out.println("### CORRECT 남은 합 : " + combs.size());
                                System.out.println(combs.toString());
                                Toast.makeText(getApplicationContext(), "CORRECT!", Toast.LENGTH_SHORT).show();
                            } else //없
                            {
                                RESULT = "-1 -1 " + score_mine.getText().toString();
                                Toast.makeText(getApplicationContext(), "WRONG!", Toast.LENGTH_SHORT).show();
                            }
                            System.out.println("### combine button / RESULT : " + RESULT);
                            //결과 보내기
                            matching.sendMessage(RESULT);
                            changeTurn();
                        }
                        clicked_index.clear();
                        //버튼 색깔 원래대로 #ffe5e5e5
                        combine.setBackgroundResource(R.drawable.combine);

                    }
                } //combine button 끝

        /* conclue 버튼 */
                else if (v.getId() == conclude.getId()) {
                    if (combs.size() < 1) //'합'이 안남아있다
                    {
                        //플레이어한테 점수 주고 게임 끝
                        System.out.println("@@@ CONCLUDE");
                        RESULT = "-1 999 " + Integer.parseInt(score_mine.getText().toString()) + 10;
                        gameResult();
                    } else //남아있다
                    {
                        RESULT = "-1 -1 " + score_mine.getText().toString();
                        Toast.makeText(getApplicationContext(), "WRONG!", Toast.LENGTH_SHORT).show();
                    }
                    matching.sendMessage(RESULT);
                    changeTurn();
                }
            } //else끝

        }//onClick() 끝

        /* board 리스너*/////////////아이템 눌리는거 바꾸기
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //conclude 버튼이 눌려있을 때만
            if (combine_clicked == true) {
                sounds.play(soundID_item, 1, 1, 0, 0, 1);
                view.setAlpha(10);
                System.out.println("### position : " + position);
                clicked_index.add(position);
            }

        }//onItemClick() 끝


        private void changeTurn() {
            //턴 넘기기
            TURN = (TURN == false) ? true : false;
            turn.setText(Boolean.toString(TURN));
            //타이머 리셋
            _time.onFinish();
            _time.start();
            System.out.println("### TURN -> " + TURN);

        }


        public static void receiveMessage(String str) {
            receivedMessage = str;
        }

        /* receivedMessage 처리가 끝나면 ""로 */
        public void handleReceivedMessage() //(판, 합 배열 인덱스, 점수)
        {
            System.out.println("### handleReceiveMessage() / msg : " + receivedMessage);
            changeTurn();
            if (receivedMessage.equals("READY")) //레디 요청 받음
            {
                aDialog.dismiss();
                bDialog.show();
                //판 만들어서 보내줘야돼
                do {
                    //판만들기
                    makeItem(items);
                    //'합'만들기
                } while (!makeComb());
                System.out.println("@@@ 판 만들기 끝!");

            /* 판 보내주기 */
                RESULT = RESULT + " -1 0"; //item인덱스9개 + 합 인덱스 없음 + 점수
                System.out.println("&&& sendMessage (setActivity) : " + RESULT);
                matching.sendMessage(RESULT);
                RESULT = "";

                setActivity();
                bDialog.dismiss();
                _time.onFinish();
                _time.start();
                System.out.println("타이머 재시작!!!!!");
            } else //게임 진행
            {
                //띄어쓰기로 나눔
                StringTokenizer tokenizer = new StringTokenizer(receivedMessage, " ", false);
                String board = tokenizer.nextToken();
                String index = tokenizer.nextToken();
                String score = tokenizer.nextToken();
                System.out.println("### board : " + board + " / index : " + index + " / score : " + score);
                if (!board.equals("-1")) //판 받아옴
                {
                    //stringTokenizer 구분자로 나눠서 items에 넣고
                    //makeComb()
                    StringTokenizer tokenizer2 = new StringTokenizer(board, ",", false);
                    // for (int i = 0; i < 9 && tokenizer2.hasMoreTokens(); i++) {
                    for (int i = 0; i < 9; i++) {
                        String tmp = tokenizer2.nextToken();
                        System.out.println("$$$ tmp : " + tmp);
                        System.out.println("@@@@@@@@@@" + items);
                        items[i] = new Item(Integer.parseInt(tmp));
                    }
                    System.out.println("### 받아온 items : " + items.toString());
                    makeComb();
                    setActivity();
                    bDialog.dismiss();
                    _time.onFinish();
                    _time.start();
                    System.out.println("타이머 재시작!!!!!");
                }

                if (index.equals("999")) //상대방이 '결'
                {
                    gameResult();
                } else if (!index.equals("-1")) {
                    //combs에서 remove
                    combs.remove(Integer.parseInt(index));
                    System.out.println("### 남은 합 : " + combs.size());
                    System.out.println(combs.toString());
                }

                System.out.println("$$$ score_yours : " + score_yours + " / score : " + score);
                score_yours.setText(score);
            }

            receivedMessage = "";
        }//receiveMessage() 끝


        /* 게임 결과 보여주는 다이얼로그 띄우기*/
        private void gameResult() {
            AlertDialog alert;
            if (Integer.parseInt(score_mine.getText().toString()) > Integer.parseInt(score_yours.getText().toString())) //내가 이김 ㅇ.ㅇ!!!!
            {
                alert = new AlertDialog.Builder(this)
                        .setTitle("* * * YOU WIN!! * * *")
                        .setMessage("Congratulations!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else if (Integer.parseInt(score_mine.getText().toString()) < Integer.parseInt(score_yours.getText().toString())) //짐 ㅠㅠ
            {
                alert = new AlertDialog.Builder(this)
                        .setTitle("+ + + YOU LOSE + + +")
                        .setMessage("Maybe next tiem...")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else //비김
            {
                alert = new AlertDialog.Builder(this)
                        .setTitle("= = = DRAW! = = =")
                        .setMessage("Same score!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }

        //게임 끝난 후에 액티비티를 종료하든가 그런걸 했으면?
}