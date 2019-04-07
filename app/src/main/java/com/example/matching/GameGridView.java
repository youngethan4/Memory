package com.example.matching;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;


public class GameGridView extends AppCompatActivity {

    static GridView gv;
    static MyAdapter adapter;
    static int taps;
    ImageButton cardFlip1;
    ImageButton cardFlip2;
    String strCard1;
    int points;
    private  ArrayList<Character> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_grid_view);

        setCards();
    }

    private void setCards() {
        gv = findViewById(R.id.GridCard);
        cards = new ArrayList<>();
        SetCards setCards = new SetCards(getIntent().getExtras().getInt("size"));
        cards = setCards.getCards();
        System.out.println(cards.size());
        adapter = new MyAdapter(cards, this);
        gv.setAdapter(adapter);
    }

    public class MyAdapter extends BaseAdapter {
        ArrayList<Character> cd;
        Context con;

        MyAdapter(ArrayList data, Context context) {
            cd = data;
            con = context;
        }

        @Override
        public int getCount()
        {
            return cd.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            return R.layout.single_card_layout;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1
            final String cardChar = cd.get(position).toString();

            // 2
            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(con);
                convertView = layoutInflater.inflate(R.layout.single_card_layout, null);
            }

            // 3
            final ImageButton ib = convertView.findViewById(R.id.card);
            final TextView tv = convertView.findViewById(R.id.card_text);

            // 4
            tv.setText(cardChar);

            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (taps < 2) {
                        ib.setVisibility(View.INVISIBLE);
                        setTaps(ib, tv.getText().toString().trim());
                    }
                }
            });

            return convertView;
        }

        private void setTaps(ImageButton card, String s) {
            if (taps == 0) {
                cardFlip1 = card;
                strCard1 = s;
                ++taps;
            } else {
                ++taps;
                cardFlip2 = card;
                if (strCard1.equals(s)) {
                    points++;
                    //TODO:Winning screen
                    taps = 0;
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            flipBack();
                        }
                    }, 1000);   //1 seconds
                }
            }
        }

        private void flipBack() {
            cardFlip1.setVisibility(View.VISIBLE);
            cardFlip2.setVisibility(View.VISIBLE);
            taps = 0;
        }

    }

}
