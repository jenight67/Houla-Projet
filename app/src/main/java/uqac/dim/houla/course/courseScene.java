package uqac.dim.houla.course;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import uqac.dim.houla.Constant;
import uqac.dim.houla.R;

public class courseScene implements Scene {


    private gameActivity main;
    private Player player;
    private Point playerPoint;
    private ObstacleManager om;
    private Bitmap background;
    private Rect backgroundRect;

    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private boolean win = false;
    private long initTime;

    private static final int TIMER = 10;
    private static final int PLAYER_HEIGHT = Constant.SCREEN_HEIGHT*7/10;

    public courseScene(){

        backgroundRect = new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT);

        BitmapFactory bf = new BitmapFactory();
        background = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_background);


        Log.i("DICJ","Avant loading player");
        player = new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));
        Log.i("DICJ","Apres loading player");

        playerPoint = new Point(Constant.SCREEN_WIDTH/2,PLAYER_HEIGHT);

        player.update(playerPoint);

        om = new ObstacleManager(100,100);

        initTime = System.currentTimeMillis();
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

    public void setActivity(gameActivity main){
        this.main = main;
    }

    @Override
    public void update() {
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
        canvas.drawBitmap(background,null,backgroundRect,new Paint());

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

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENES = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlayer = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingPlayer && !gameOver)
                    playerPoint.set((int)event.getX(), Constant.SCREEN_HEIGHT*7/10); //Déplace le joueur mais le fait rester sur une ligne
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }
}
