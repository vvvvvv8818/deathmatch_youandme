package com.example.minjeong.deathmatch;

import java.io.Serializable;

public class Pan implements Serializable {
    int[][] panstate;
    card[][] cardtype;
    public int selected;
    int temp_x;
    int temp_y;
    public int set_cnt;
    public boolean turn = true;
    int finis = 0;
    Pan() {
        set_cnt = 0;
        panstate = new int[4][3];
        cardtype = new card[4][2];
        selected = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++) {
                panstate[j][i] = 0;
            }
        panstate[0][1] = 1;  panstate[3][1] = 1;        // King
        panstate[0][2] = 2;  panstate[3][0] = 2;        // Sang
        panstate[0][0] = 3;  panstate[3][2] = 3;        // Cha
        panstate[1][1] = 4;  panstate[2][1] = 4;        // Ja
        cardtype[0][1] = new card(0, 1, 0, 1);  cardtype[0][0] = new card(1, 1, 3, 1);        // King
        cardtype[1][1] = new card(0, 2, 0, 2);  cardtype[1][0] = new card(1, 2, 3, 0);        // Sang
        cardtype[2][1] = new card(0, 3, 0, 0);  cardtype[2][0] = new card(1, 3, 3, 2);        // Cha
        cardtype[3][1] = new card(0, 4, 1, 1);  cardtype[3][0] = new card(1, 4, 2, 1);        // Ja
    }

    int choice(int x, int y) {
        if (panstate[x][y] != 0) {
            if (select(x, y).who == 1) {
                selected = 1;
                temp_x = x;
                temp_y = y;
                return 1; // 다음꺼 골라라
            } else {
                if (selected == 1) {
                    if (judge(x, y) == 1) {
                        card c = select(x, y);
                        card ct = select(temp_x, temp_y);

                        ct.x = c.x;
                        ct.y = c.y;
                        // 이동
                        panstate[x][y] = panstate[temp_x][temp_y];
                        panstate[temp_x][temp_y] = 0;
                        //판 설정
                        System.out.println("죽은 말의 주인은 " + c.who);
                        c.who = 1; // 내꺼!
                        if (x == 0 && ct.type == 4) {
                            ct.type = 5;
                            panstate[x][y] = 5;
                        }
                        if (c.type == 5)
                            c.type = 4;
                        System.out.println("지금 말의 주인은 " + c.who + " ");
                        c.state = 0; // 죽음!
                        selected = 0;
                        turn = false;
                        return 2; // 먹음
                    }
                }
            }
        } else {
            if (selected == 1) {
                if (judge(x, y) == 1) {
                    panstate[x][y] = panstate[temp_x][temp_y];
                    panstate[temp_x][temp_y] = 0;
                    card c = select(temp_x, temp_y);
                    if (x == 0 && c.type == 4) {
                        c.type = 5;
                        panstate[x][y] = 5;
                    }
                    c.x = x;
                    c.y = y;
                    selected = 0;
                    turn = false;
                    return 4; // 이동
                }
                System.out.println(panstate[temp_x][temp_y]);
            } else if (set_cnt != 0 && x != 0 && x != 3) {
                switch (set_cnt) {
                    case 1: {
                        if (cardtype[1][0].state == 0 && cardtype[1][0].who == 1) {
                            cardtype[1][0].state = 1;
                            cardtype[1][0].x = x;
                            cardtype[1][0].y = y;
                            panstate[x][y] = 2;
                            System.out.println("set the : 1 0");
                            set_cnt = 0;
                            turn = false;
                            return 3;
                        } else if (cardtype[1][1].state == 0 && cardtype[1][1].who == 1) {
                            cardtype[1][1].state = 1;
                            cardtype[1][1].x = x;
                            cardtype[1][1].y = y;
                            panstate[x][y] = 2;
                            System.out.println("set the : 1 1");
                            set_cnt = 0;
                            turn = false;
                            return 3;
                        }
                        break;
                    }
                    case 2: {
                        if (cardtype[2][0].state == 0 && cardtype[2][0].who == 1) {
                            cardtype[2][0].state = 1;
                            cardtype[2][0].x = x;
                            cardtype[2][0].y = y;
                            panstate[x][y] = 3;
                            set_cnt = 0;
                            System.out.println("set the : 2 0");
                            turn = false;
                            return 3;
                        } else if (cardtype[2][1].state == 0 && cardtype[2][1].who == 1) {
                            cardtype[2][1].state = 1;
                            cardtype[2][1].x = x;
                            cardtype[2][1].y = y;
                            panstate[x][y] = 3;
                            System.out.println(panstate[x][y]);
                            set_cnt = 0;
                            turn = false;
                            return 3;
                        }
                        break;
                    }
                    case 3: {
                        if (cardtype[3][0].state == 0 && cardtype[3][0].who == 1) {
                            cardtype[3][0].state = 1;
                            cardtype[3][0].x = x;
                            cardtype[3][0].y = y;
                            panstate[x][y] = 4;
                            set_cnt = 0;
                            System.out.println("set the : 3 0");
                            turn = false;
                            return 3;
                        } else if (cardtype[3][1].state == 0 && cardtype[3][1].who == 1) {
                            cardtype[3][1].state = 1;
                            cardtype[3][1].x = x;
                            cardtype[3][1].y = y;
                            panstate[x][y] = 4;
                            set_cnt = 0;
                            System.out.println("set the : 3 1");
                            turn = false;
                            return 3;
                        }
                        break;
                    }
                }
            }
        }
        return 0; // repick
    }

    int judge(int x, int y) {
        if (panstate[temp_x][temp_y] == 1) {
            if (Math.abs(temp_x - x) <= 1 && Math.abs(temp_y - y) <= 1) {
                return 1;
            }
        }
        if (panstate[temp_x][temp_y] == 2) {
            if (Math.abs(temp_x - x) == 1 && Math.abs(temp_y - y) == 1) {
                return 1;
            }
        }
        if (panstate[temp_x][temp_y] == 3) {
            if ((Math.abs(temp_x - x) == 1 && y - temp_y == 0) || (Math.abs(temp_y - y) == 1 && x - temp_x == 0)) {
                return 1;
            }
        }
        if (panstate[temp_x][temp_y] == 4) {
            if (temp_x - x == 1 && temp_y - y == 0) {
                return 1;
            }
        }
        if (panstate[temp_x][temp_y] == 5) {
            if ((temp_x - x == 1 && Math.abs(temp_y - y) <= 1) || (temp_x - x == -1 && temp_y == y)||(Math.abs(temp_x - x) == 1 && y - temp_y == 0) || (Math.abs(temp_y - y) == 1 && x - temp_x == 0)) {
                return 1;
            }
        }
        return 0;
    }

    card select(int x, int y) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++)
                if (cardtype[i][j].x == x && cardtype[i][j].y == y && cardtype[i][j].state == 1)
                    return cardtype[i][j];
        }
        return null;
    }

    int death1() {
        int cnt = 0;
        for (int j = 0; j < 2; j++) {
            if (cardtype[1][j].state == 0 && cardtype[1][j].who == 1)
                cnt++;
        }
        return cnt;
    }

    int death2() {
        int cnt = 0;
        for (int j = 0; j < 2; j++) {
            if (cardtype[2][j].state == 0 && cardtype[2][j].who == 1)
                cnt++;
        }
        return cnt;
    }

    int death3() {
        int cnt = 0;
        for (int j = 0; j < 2; j++) {
            if (cardtype[3][j].state == 0 && cardtype[3][j].who == 1)
                cnt++;
        }
        return cnt;
    }

    int counter_death1() {
        int cnt = 0;
        for (int j = 0; j < 2; j++) {
            if (cardtype[1][j].state == 0 && cardtype[1][j].who == 0)
                cnt++;
        }
        return cnt;
    }

    int counter_death2() {
        int cnt = 0;
        for (int j = 0; j < 2; j++) {
            if (cardtype[2][j].state == 0 && cardtype[2][j].who == 0)
                cnt++;
        }
        return cnt;
    }

    int counter_death3() {
        int cnt = 0;
        for (int j = 0; j < 2; j++) {
            if (cardtype[3][j].state == 0 && cardtype[3][j].who == 0)
                cnt++;
        }
        return cnt;
    }

    void receive(int what, int x, int y, int to_x, int to_y) {
        if (what == 2) {
            card ct = select(x, y);
            ct.state = 0;
            ct.who = 0;
            ct.x = 0;
            ct.y = 0;
            panstate[x][y] = panstate[to_x][to_y];
            panstate[to_x][to_y] = 0;
            card c = select(to_x, to_y);
            c.x = x;
            c.y = y;
        } else if (what == 3) {
            card c;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 2; j++)
                    if (cardtype[i][j].type == to_x && cardtype[i][j].state == 0 && cardtype[i][j].who == 0) {
                        c = cardtype[i][j];
                        c.state = 1;
                        c.x = x;
                        c.y = y;
                        return;
                    }
            }
        } else if (what == 4) {
            panstate[x][y] = panstate[to_x][to_y];
            panstate[to_x][to_y] = 0;
            card c = select(to_x, to_y);
            c.x = x;
            c.y = y;
        }

        finis = 1;

    }


}

class card {
    public int state; // 1 = live 0 = die
    public int who; // 1 = me 0 = other
    public int type; // king = 1 sang = 2 cha = 3 ja = 4 hu = 5
    public int x;
    public int y;

    card(int a, int b, int x, int y) {
        state = 1;
        who = a;
        type = b;
        this.x = x;
        this.y = y;
    }
}