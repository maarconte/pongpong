package fr.thatmuch.helloworld;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.view.MotionEvent;
import android.view.View;
import android.os.Vibrator;

import java.io.IOException;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by marconte on 16/10/2018.
 */

public class PongView extends View implements View.OnTouchListener {
    Paint colorBlack;
    Paint colorWhite;
    Paint text = new Paint();

    MediaPlayer mediaPlayer = new MediaPlayer();
    public int colorPrimary;
    public int colorPrimaryDark;
    public int colorAccent;
    public int colorOverlay;
    public int canvasColor;
    public int scorePlayer1;
    public int scorePlayer2;
    public int canvasX = getContext().getResources().getDisplayMetrics().widthPixels;
    public int canvasY = getContext().getResources().getDisplayMetrics().heightPixels;
    public int ballX = canvasX / 2;
    public int ballY = canvasY / 2;
    public int speedX = 10;
    public int speedY = 12;
    public float ballRadius = (canvasX * (float)1.5) / 100;
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
        colorAccent = getResources().getColor(R.color.colorAccent);
        colorOverlay = getResources().getColor(R.color.black_overlay);
        canvasColor = colorOverlay;

        scorePlayer1 = 0;
        scorePlayer2 = 0;

        text.setColor(Color.WHITE);
        text.setTextSize(80);
        initSound();
    }

    // Dessiner la balle
    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(canvasColor);
        canvas.drawRect(( canvasX / 2 - 2 ), 0, ( canvasX / 2 + 2 ), canvasY, colorWhite);
        canvas.drawCircle(ballX, ballY, ballRadius, colorWhite);
        canvas.drawRect(racketOneLeft, racketOneTop, racketOneRight , racketOneBottom, colorWhite);
        canvas.drawRect(racketTwoLeft, racketTwoTop, racketTwoRight , racketTwoBottom, colorWhite);

        canvas.drawText(String.valueOf(scorePlayer1),(canvasX / 2 - 250), 100, text );
        canvas.drawText(String.valueOf(scorePlayer2),(canvasX / 2 + 200), 100, text );
        moveBall();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int touchs = event.getPointerCount();
        for(int i = 0; i < touchs; i++){
            moveRacket(event.getX(i), event.getY(i));
        }

        return true;
    }

    public void initSound(){
        try {
            mediaPlayer.reset();
            AssetFileDescriptor afd;
            afd = getContext().getAssets().openFd("NFF-blip.wav");
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shakeIt() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getContext().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getContext().getSystemService(VIBRATOR_SERVICE)).vibrate(500);
        }
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
            mediaPlayer.start();
            shakeIt();
            speedX = -speedX;
            speedX++;
        }

        // Rebond sur la raquette de droite (Player 2)
        if(
                (ballX + ballRadius < racketTwoRight &&
                 ballX + ballRadius > racketTwoLeft) &&
                (ballY + ballRadius > racketTwoTop &&
                ballY - ballRadius < racketTwoBottom)
                ){
            mediaPlayer.start();
            shakeIt();
            speedX = -speedX;
            speedX++;
        }

        if(ballX < 0){
            canvasColor = colorPrimary;
            scorePlayer1++;
            reset();
        }
        if(ballX + ballRadius > canvasX){
            canvasColor = colorAccent;
            scorePlayer2++;

            reset();

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

    public void reset(){
        ballX = canvasX / 2;
        ballY = canvasY / 2;
        speedX = -speedX;
    }

    public void endGame(){
        if(scorePlayer1 == 10) {
            System.out.println("Player 1 wins !");
        }
        if(scorePlayer2 == 10) {
            System.out.println("Player 2 wins !");
        }
    }


}
