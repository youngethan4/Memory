package com.example.matching;

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
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    static RecyclerView rv;
    static LinearLayoutManager llm;
    static MyAdapter adapter;
    private ArrayList<CardData> cards;
    static int taps;
    ImageButton cardFlip1;
    ImageButton cardFlip2;
    String strCard1;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setCards();

    }

    private void setCards(){
        rv = findViewById(R.id.rvCard);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        cards = new ArrayList<>();
        adapter = new MyAdapter(cards);
        rv.setAdapter(adapter);

        ArrayList<Character> arrList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            arrList.add((char) (65+i));
            arrList.add((char) (65+i));
        }
        Collections.shuffle(arrList);

        int sum = 0;
        for (int i = 0; i <6; i++) {
            cards.add(new CardData(arrList.get(sum), arrList.get(sum+1),
                     arrList.get(sum+2), (arrList.get(sum+3))));
            rv.refreshDrawableState();
            sum += 4;
        }

    }

    /**
     * Object to store strings as a card data type
     */
    private static class CardData  {
        char c1;
        char c2;
        char c3;
        char c4;

        public CardData(char c1, char c2, char c3, char c4)  {
            this.c1 = c1;
            this.c2 = c2;
            this.c3 = c3;
            this.c4 = c4;
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        ArrayList<CardData> cd;

        MyAdapter(ArrayList<CardData> data) {
            cd = data;
        }

        @Override
        public int getItemCount()
        {
            return cd.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            return R.layout.card_layout;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View v = null;
            MyViewHolder viewHolder = null;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            viewHolder = new CardViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder baseHolder, int position) {
            if(getItemViewType(position) == R.layout.card_layout)
            {
                CardViewHolder holder = (CardViewHolder) baseHolder;
                holder.text1.setText(String.valueOf(cd.get(position).c1));
                holder.text2.setText(String.valueOf(cd.get(position).c2));
                holder.text3.setText(String.valueOf(cd.get(position).c3));
                holder.text4.setText(String.valueOf(cd.get(position).c4));
            }
        }

        public abstract class MyViewHolder extends RecyclerView.ViewHolder
        {
            public MyViewHolder(View itemView)
            {
                super(itemView);
            }
        }

        /**
         * Creates a view holder for the cardview
         */
        public class CardViewHolder extends MyViewHolder {

            CardView commentCard;
            TextView text1;
            TextView text2;
            TextView text3;
            TextView text4;
            ImageButton card1;
            ImageButton card2;
            ImageButton card3;
            ImageButton card4;

            public CardViewHolder(View itemView){
                super(itemView);

                commentCard = itemView.findViewById(R.id.rvCard);
                text1 = itemView.findViewById(R.id.card_text_1);
                text2 = itemView.findViewById(R.id.card_text_2);
                text3 = itemView.findViewById(R.id.card_text_3);
                text4 = itemView.findViewById(R.id.card_text_4);
                card1 = itemView.findViewById(R.id.card_1);
                card2 = itemView.findViewById(R.id.card_2);
                card3 = itemView.findViewById(R.id.card_3);
                card4 = itemView.findViewById(R.id.card_4);


                card1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (taps < 2) {
                            card1.setVisibility(View.INVISIBLE);
                            setTaps(card1, text1.getText().toString().trim());
                        }
                    }
                });

                card2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (taps < 2) {
                            card2.setVisibility(View.INVISIBLE);
                            setTaps(card2, text2.getText().toString().trim());
                        }
                    }
                });

                card3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (taps < 2) {
                            card3.setVisibility(View.INVISIBLE);
                            setTaps(card3, text3.getText().toString().trim());
                        }
                    }
                });

                card4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (taps < 2) {
                            card4.setVisibility(View.INVISIBLE);
                            setTaps(card4, text4.getText().toString().trim());
                        }
                    }
                });

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

}
