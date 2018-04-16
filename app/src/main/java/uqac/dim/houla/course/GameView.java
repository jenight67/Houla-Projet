package uqac.dim.houla.course;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import uqac.dim.houla.Constant;
import uqac.dim.houla.MainActivity;
import uqac.dim.houla.MainThread;

/**
 * Created by User on 08/03/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private courseActivity main;
    private MainThread thread;

    private Player player;
    private Point playerPoint;
    private ObstacleManager om = new ObstacleManager(100,100);

    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private boolean win = false;

    private static final int TIMER = 10;
    private static final int PLAYER_HEIGHT = Constant.SCREEN_HEIGHT*7/10;

    private long initTime;


    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));

        playerPoint = new Point(Constant.SCREEN_WIDTH/2,PLAYER_HEIGHT);

        setFocusable(true);

        initTime = System.currentTimeMillis();
    }

    private int getTimer(){
        long currTime = System.currentTimeMillis();
        int time = (int)((currTime - initTime) /1000);
        time = TIMER - time;
        if(time <= 0){
            win = true;
        }
        return time;
    }

    public void setActivity(courseActivity main){
        this.main = main;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while(true){
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch (Exception e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlayer = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingPlayer && !gameOver)
                    playerPoint.set((int)event.getX(),Constant.SCREEN_HEIGHT*7/10); //Déplace le joueur mais le fait rester sur une ligne
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }


        return true;
    }

    public void update(){
        if(!gameOver && !win){
            player.update(playerPoint);
            om.update();
            //Fin du jeu
            if(om.playerCollide(player))
                gameOver = true;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);

        om.draw(canvas);

        //Affichage du Timer
        Paint p = new Paint();
        p.setTextSize(50);
        p.setColor(Color.RED);

        int timeInt = getTimer();
        String timeStr = String.valueOf(timeInt);

        canvas.drawText(timeStr, 0,Constant.SCREEN_HEIGHT - 50,p);

        Paint pRect = new Paint();
        pRect.setColor(Color.BLUE);

        int longRect = Constant.SCREEN_WIDTH * timeInt /TIMER;

        Rect recTime = new Rect(0,0,longRect,50);
        //Rect recTime = new Rect(0,0,longRect,50);
        canvas.drawRect(recTime,pRect);


        if(gameOver){
            main.lose();
        }

        if(win){
            main.win();
        }


    }
}
