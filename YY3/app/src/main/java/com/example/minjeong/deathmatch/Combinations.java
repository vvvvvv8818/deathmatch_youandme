package com.example.minjeong.deathmatch;

import java.util.ArrayList;

/**
 * Created by LG on 2015-12-07.
 */
public class Combinations {
    static ArrayList<Set> list;

    public Combinations() {
        list = new ArrayList<Set>();
    }

//    public Combinations getInstance()
//    {
//        if(list==null)
//            new ArrayList<Set>();
//        return this;
//    }

    public void clear()
    {
        list.clear();
    }

    //Set 추가
    public void add(Set set) {
        list.add(set);
    }



    //중복된 원소 제거하기
    public void deleteRepeated()
    {
//        ArrayList<Set> tmp = (ArrayList<Set>)list.clone();
        for(int i=0; i < list.size()-1; i++)
            for(int j = i+1; j < list.size(); j++)
            {
                if( list.get(i).equals(list.get(j)) )
                    list.remove(j);
            }
        System.out.println("### deleteRepeated() : ");
        System.out.println(list.toString());

    }

    //입력으로 들어온 Set 찾기
    //있으면 인덱스 리턴 / 없으면 -1
    public int indexOf(Set set)
    {
        for(int i = 0; i<list.size(); i++)
        {
            if( list.get(i).equals(set) )
                return i;
        }
        return -1;
    }


    public boolean contains(Set set)
    {
        for(int i = 0; i<list.size(); i++)
        {
            if(list.get(i).equals(set))
                return true;
        }
        return false;
    }


    public int size()
    {
        return list.size();
    }

    public String toString()
    {
        return list.toString();
    }

    public void remove(Set target)
    {
        int n = this.indexOf(target);
        System.out.println("### target ~> index : "+n);
        if(0<=n && n<list.size())
            list.remove(n);
    }

    public void remove(int n)
    {
        list.remove(n);
    }
}
