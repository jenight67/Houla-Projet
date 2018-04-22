package uqac.dim.houla.couette;

import android.graphics.Canvas;
import android.view.MotionEvent;

import uqac.dim.houla.course.Scene;
import uqac.dim.houla.course.gameActivity;

public class couetteScene implements Scene {
    private gameActivity main;

    private boolean gameOver = false;
    private boolean win = false;
    private long initTime;
    private static final int TIMER = 10;

    public couetteScene(){

    }

    public int getTimer(){
        long currTime = System.currentTimeMillis();
        int time = (int)((currTime - initTime) /1000);
        time = TIMER - time;
        if(time <= 0){
            win = true;
        }
        return time;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update() {

    }

    @Override
    public void terminate() {

    }

    @Override
    public void setActivity(gameActivity activity) {
        this.main = activity;
    }

    @Override
    public void receiveTouch(MotionEvent event) {

    }
}
