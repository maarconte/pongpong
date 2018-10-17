package fr.thatmuch.helloworld;

import android.graphics.RectF;

/**
 * Created by marconte on 16/10/2018.
 */

public class Ball {
    private RectF ball;
    private float ballWidth;
    private float ballHeight;
    private float Xspeed;
    private float Yspeed;

   public Ball(int screenX, int screenY){

       // Definir la taille de la balle en fonction de l'écran
        ballWidth = screenX / 100;
        ballHeight= ballWidth;

        // Initialiser la balle la moitié de l'écran
       Yspeed = screenY / 2;
       Xspeed = Yspeed;

       // Representation graphique de la balle
       ball = new RectF();
    }

    public RectF getRect(){
       return ball;
    }

    public void update(long fps) {
        ball.left = ball.left + (Xspeed / fps);
        ball.top = ball.top + (Xspeed / fps);
        ball.right = ball.right + (Xspeed / fps);
        ball.bottom = ball.bottom + (Xspeed / fps);
    }

    public void reverseYspeed(){
        Yspeed = -Yspeed;
    }
    public void reverseXspeed(){
        Xspeed = -Xspeed;
    }

    public void increaseVelocity(){
        Xspeed = Xspeed + Xspeed / 10;
        Yspeed = Yspeed + Yspeed / 10;
    }

    public void bounceY(float y) {
        ball.bottom = y;
        ball.top = y - ballHeight;
    }

    public void bounceX(float x){
        ball.left = x;
        ball.right = x + ballWidth;
    }

    public void reset(int x, int y){
        ball.left = x / 2;
        ball.top = y - 20;
        ball.right = x / 2 + ballWidth;
        ball.bottom = y - 20 - ballHeight;
    }
}
