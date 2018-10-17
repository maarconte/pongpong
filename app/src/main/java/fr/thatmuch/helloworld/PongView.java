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
    Paint mypaint;

    public int ballX = 500;
    public int ballY = 500;
    public int speedX = 8;
    public int speedY = 10;
    public int ballRadius = 20;

    public PongView(Context context) {
        super(context);
        mypaint = new Paint();
        mypaint.setColor(Color.BLACK);
    }

    // Dessiner la balle
    @Override
    public void onDraw(Canvas canvas) {


        canvas.drawCircle(ballX, ballY, (float) ballRadius++, mypaint);
        canvas.drawRect(0, 0, 100 , 500, mypaint);
        canvas.drawRect(100, 500, 0 , 0, mypaint);

        moveBall();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        event.getX();
        return false;
    }

    // Déplacement de la balle
    public void moveBall(){
        ballX += speedX;
        ballY += speedY;

        invalidate();
    }


}
