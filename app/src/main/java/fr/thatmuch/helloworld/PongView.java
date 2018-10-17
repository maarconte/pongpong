package fr.thatmuch.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by marconte on 16/10/2018.
 */

public class PongView extends View implements View.OnTouchListener {
    Paint colorBlack;
    Paint colorWhite;
    Paint text = new Paint();
    int colorPrimary;
    int colorPrimaryDark;

    public int scorePlayer1;
    public int scorePlayer2;
    public int canvasX = getContext().getResources().getDisplayMetrics().widthPixels;
    public int canvasY = getContext().getResources().getDisplayMetrics().heightPixels;
    public int ballX = 500;
    public int ballY = 500;
    public int speedX = 8;
    public int speedY = 10;
    public int ballRadius = 30;
    public float racketOneTop = 0;
    public float racketOneBottom = (float)(canvasY * 24) / 100 ;
    public float racketOneLeft = (float)(canvasX * 3) / 100;
    public float racketOneRight = (float)(canvasX * 5) / 100;
    public float racketTwoTop = (float)(canvasY * 76) / 100;
    public float racketTwoBottom = (float) canvasY;
    public float racketTwoLeft = (float)(canvasX * 95) / 100;
    public float racketTwoRight = (float)(canvasX * 97) / 100;;

    public PongView(Context context) {
        super(context);
        this.setOnTouchListener(this);
        colorBlack = new Paint();
        colorBlack.setColor(Color.BLACK);
        colorWhite = new Paint();
        colorWhite.setColor(Color.WHITE);

        colorPrimary = getResources().getColor(R.color.colorPrimary);
        colorPrimaryDark = getResources().getColor(R.color.colorPrimaryDark);

        scorePlayer1 = 0;
        scorePlayer2 = 0;
        text.setColor(Color.WHITE);
        text.setStyle(Paint.Style.FILL);
        text.setTextSize(500);
    }

    // Dessiner la balle
    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(colorPrimary);
        canvas.drawRect(( canvasX / 2 - 2 ), 0, ( canvasX / 2 + 2 ), canvasY, colorWhite);
        canvas.drawCircle(ballX, ballY, (float) ballRadius, colorWhite);
        canvas.drawRect(racketOneLeft, racketOneTop, racketOneRight , racketOneBottom, colorWhite);
        canvas.drawRect(racketTwoLeft, racketTwoTop, racketTwoRight , racketTwoBottom, colorWhite);

        canvas.drawText(String.valueOf(scorePlayer1),(canvasX / 2 - 100), 50, colorWhite );
        canvas.drawText(String.valueOf(scorePlayer2),(canvasX / 2 + 100), 50, colorWhite );
        moveBall();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        moveRacket(event.getX(), event.getY());
        return true;
    }

    // Déplacement de la balle
    public void moveBall(){
        ballX += speedX;
        ballY += speedY;

        // Rebond de la balle sur les côtés
        if(ballY - ballRadius < 0 || ballY + ballRadius > canvasY) {
            speedY = -speedY;
        }

        // Rebond sur la raquette de gauche (Player 1)
        if(
                (ballX - ballRadius < racketOneRight &&
                 ballX - ballRadius > racketOneLeft) &&
                (ballY - ballRadius > racketOneTop &&
                ballY + ballRadius < racketOneBottom)
        ){
            speedX = -speedX;
            speedX += 10;
        }

        // Rebond sur la raquette de droite (Player 2)
        if(
                (ballX + ballRadius < racketTwoRight &&
                 ballX + ballRadius > racketTwoLeft) &&
                (ballY + ballRadius < racketTwoTop &&
                ballY - ballRadius > racketTwoBottom)
                ){
            speedX = -speedX;
            speedX += 10;
        }

        if(ballX < 0){
            scorePlayer1++;
            System.out.println(scorePlayer1);
        }
        if(ballX + ballRadius > canvasX){
            scorePlayer2++;
            System.out.println("Score Player 2 :" + scorePlayer2);

        }

        invalidate();
    }

    // Déplacement de la raquette
    public void moveRacket(float touchX, float touchY){
        float racketOneHalf = ((racketOneBottom - racketOneTop) / 2);
        float racketTwoHalf = ((racketTwoBottom - racketTwoTop) / 2);

        if(touchX < (canvasX * 50 / 100)){
            racketOneTop = touchY - racketOneHalf;
            racketOneBottom = touchY + racketOneHalf;
        } else if(touchX >= (canvasX * 50 / 100)){
            racketTwoTop = touchY - racketTwoHalf;
            racketTwoBottom = touchY + racketTwoHalf;
        }
    }


}
