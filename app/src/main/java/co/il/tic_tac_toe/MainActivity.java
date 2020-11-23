package co.il.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons= new Button[3][3];

    private  boolean Player1turn=true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewTitle;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private Button playBtn;
    private LinearLayout layoutframe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTitle= findViewById(R.id.mainactivity_title);
        textViewPlayer1= findViewById(R.id.mainactivity_p1);
        textViewPlayer2= findViewById(R.id.mainactivity_p2);
        playBtn= findViewById(R.id.mainactivity_play_btn);
        layoutframe= findViewById(R.id.linearLayout);

        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                String buttonID= "mainactivity_btn_"+ i + j;
                int resID= getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.mainactivity_resat);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlayClick();
            }

            private void onPlayClick() {
                playBtn.setVisibility(View.INVISIBLE);
                layoutframe.setBackgroundResource(R.drawable.back);

                resetBoard();
                textViewTitle.setText(R.string.player1turn);

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")){
            return;
        }

        if(Player1turn){
            ((Button) view).setText("X");
            textViewTitle.setText(R.string.player2turn);

        } else {
            ((Button) view).setText("O");
            textViewTitle.setText(R.string.player1turn);
        }

        roundCount++;

        if(checkForWin()){
            if(Player1turn){
                Player1Wins();
            } else {
                Player2Wins();
            }
        }else if(roundCount ==9){
            draw();
        }else {
            Player1turn= !Player1turn;
        }

    }
    private boolean checkForWin(){
        String[][] field= new String[3][3];

        for (int i = 0; i < 3; i++ ){
            for(int j = 0; j < 3; j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }
        for(int i = 0; i < 3; i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    &&!field[i][0].equals("")){
                if(i == 0){
                    layoutframe.setBackgroundResource(R.drawable.i0);
                    return true;
                } else if(i == 1){
                    layoutframe.setBackgroundResource(R.drawable.i1);
                    return true;
                } else if(i == 2){
                    layoutframe.setBackgroundResource(R.drawable.i2);
                    return true;
                }
            }
        }
        for(int i = 0; i < 3; i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    &&!field[0][i].equals("")){
                if(i == 0){
                    layoutframe.setBackgroundResource(R.drawable.j2);
                } else if(i == 1){
                    layoutframe.setBackgroundResource(R.drawable.j1);
                }else if(i == 2){
                    layoutframe.setBackgroundResource(R.drawable.j0);
                }
                return true;
            }
        }
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                &&!field[0][0].equals("")){
            layoutframe.setBackgroundResource(R.drawable.d2);
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            layoutframe.setBackgroundResource(R.drawable.d1);
            return true;
        }
        return false;
    }
    private void Player1Wins(){
        player1Points++;
        textViewTitle.setText(R.string.player1win);
        updatePointText();
        playBtn.setText(R.string.playagain);
        playBtn.setVisibility(View.VISIBLE);
        FillEmpty();
    }

    private void Player2Wins(){
        player2Points++;
        textViewTitle.setText(R.string.player2win);
        updatePointText();
        playBtn.setText(R.string.playagain);
        playBtn.setVisibility(View.VISIBLE);
        FillEmpty();
    }

    private void draw(){
        textViewTitle.setText(R.string.draw);
        playBtn.setText(R.string.playagain);
        playBtn.setVisibility(View.VISIBLE);
    }

    private void FillEmpty(){
        for (int i = 0; i < 3; i++ ){
            for(int j = 0; j < 3; j++){
                String temp=buttons[i][j].getText().toString();
                if(temp.equals("")) {
                    buttons[i][j].setText(" ");
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private void updatePointText(){
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: "+ player2Points);
    }



    private void resetBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j <3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        Player1turn = true;
        textViewTitle.setText(R.string.maintitle);

    }
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointText();
        resetBoard();
        layoutframe.setBackgroundResource(R.drawable.back);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", Player1turn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        Player1turn = savedInstanceState.getBoolean("player1Turn");
    }

}