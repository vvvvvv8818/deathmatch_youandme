package com.example.minjeong.deathmatch;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Minjeong on 2015-11-25.
 */
public class ruleDialog extends DialogFragment {

    Button comcon;
    Button chess;
    Button poker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rule_choice,container,false);
        getDialog().setTitle("Choose Game");

        comcon = (Button)view.findViewById(R.id.comcon);
        chess = (Button)view.findViewById(R.id.chess);
        poker = (Button)view.findViewById(R.id.poker);

        comcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ComconRule.class);
                startActivity(intent);
                dismiss();
            }
        });

        chess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KoreanChessRule.class);
                startActivity(intent);
                dismiss();
            }
        });
        poker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PokerRule.class);
                startActivity(intent);
                dismiss();
            }
        });


        return view;
    }
}