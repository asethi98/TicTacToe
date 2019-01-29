package com.avneetksethi.tic_tac_toe;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    //The symbol variable stands for 'x' and 'o'
    ImageView symbol;
    LinearLayout playAgainLayout;
    TextView winnerMessageTV;
    boolean gameIsActive = true;

    //0 represents 'o', 1 represents 'x'
    int activePlayer = 0;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //2 represents an 'unplayed' state

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};

    public void dropIn(View view) {
        symbol = (ImageView) view;

        int tappedSymbol = Integer.parseInt(symbol.getTag().toString());

        //A player can only make a move if the square
        // is not occupied by another symbol
        if (gameState[tappedSymbol] == 2 && gameIsActive) {

            gameState[tappedSymbol] = activePlayer;

            symbol.setTranslationY(-1000f);

            if (activePlayer == 0) {

                symbol.setImageResource(R.drawable.o);

                activePlayer = 1;

            } else {

                symbol.setImageResource(R.drawable.x);

                activePlayer = 0;
            }
            symbol.animate().translationYBy(1000f).setDuration(300);

            for (int[] positions : winningPositions) {
                if (gameState[positions[0]] == gameState[positions[1]] &&
                        gameState[positions[1]] == gameState[positions[2]] &&
                        gameState[positions[0]] != 2) {

                    //We have a winner!
                    //The game should no longer be active in the background.
                    gameIsActive = false;

                    winnerMessageTV = (TextView) findViewById(R.id.winnerMessageTV);
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Shink.ttf");
                    winnerMessageTV.setTypeface(typeface);

                    if (gameState[positions[0]] == 0) {
                        winnerMessageTV.setText("O Wins!");
                    } else {
                        winnerMessageTV.setText("X Wins!");
                    }

                    playAgainLayout = (LinearLayout) findViewById(R.id.linearLayout);
                    playAgainLayout.setVisibility(View.VISIBLE);

                } else { //check if the game has ended

                    boolean gameIsOver = true;
                    //if any of the squares are empty (state == 2),
                    //then the game is not yet over
                    for (int state : gameState) {
                        if (state == 2) {
                            gameIsOver = false;
                        }
                    }

                    if (gameIsOver) {  //game ended with no winner
                        winnerMessageTV = (TextView) findViewById(R.id.winnerMessageTV);
                        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Shink.ttf");
                        winnerMessageTV.setTypeface(typeface);

                        winnerMessageTV.setText("It's a draw!");
                        playAgainLayout = (LinearLayout) findViewById(R.id.linearLayout);
                        playAgainLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        playAgainLayout = (LinearLayout) findViewById(R.id.linearLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for(int i = 0; i < gameState.length; i++) {
            gameState[i] = 2; //set gameState for each space to 2 = "unplayed"
        }

        android.support.v7.widget.GridLayout gridLayout = (android.support.v7.widget.GridLayout)findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView gameTitle = (TextView) findViewById(R.id.gameTitleTV);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Shink.ttf");
        gameTitle.setTypeface(typeface);
    }
}
