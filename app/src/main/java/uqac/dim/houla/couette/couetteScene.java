package uqac.dim.houla.couette;

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
import uqac.dim.houla.course.Scene;
import uqac.dim.houla.course.gameActivity;

public class couetteScene implements Scene {
    private gameActivity main; //Activité principale

    private Point couettePoint; //Point centrale de la couette
    private Couette couette; //Objet couette (joueur)
    private boolean movingCouette = false; //Est ce que la couette bouge

    private long startTime; //Temps de debut pour le timer
    private float speed = Constant.SCREEN_HEIGHT/10000.0f; //Vitesse de descente de la couette
    private float speedMult = 3; //Multiplicateur de vitesse (difficulté)

    private Bitmap backgroundEndormi; //Image de fond endormi
    private Bitmap backgroundReveille; //Image de fond reveillé (perdu)
    private Rect backgroundRect; //Rectangle accueillant le fond


    private boolean gameOver = false; //perdu
    private boolean win = false; //gagné
    private long initTime;
    private static final int TIMER = 10; //Temps du timer en secondes

    private static final int COUETTE_HEIGHT = Constant.SCREEN_HEIGHT*4/6; //Hauteur initiale de la couette

    public couetteScene(){

        backgroundRect = new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT);

        startTime = initTime = System.currentTimeMillis();

        //Reduit la taille de l'image car trop grande en mémoire.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = 500;
        options.outWidth = 200;
        backgroundEndormi = BitmapFactory.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.couette_background_endormi,options);
        Log.i("DICJ","Image chargée");


        couettePoint = new Point(Constant.SCREEN_WIDTH/2,COUETTE_HEIGHT);
        couette = new Couette(new Rect(0,3*Constant.SCREEN_HEIGHT/5, Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT));
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
        canvas.drawBitmap(backgroundEndormi,null,backgroundRect,new Paint());

        couette.draw(canvas);

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

        canvas.drawRect(recTime,pRect);


        if(gameOver){
            main.lose();
        }

        if(win){
            main.win();
        }
    }

    @Override
    public void update() {
        if(!gameOver && !win){
            couette.update(couettePoint);

            couette.incrementY(5);

            int elapsedTime = (int)(System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();

            couette.incrementY(speed * elapsedTime * speedMult);

            //Fin du jeu si la couette arrive trop bas.
            if(couettePoint.y >= Constant.SCREEN_HEIGHT)
                gameOver = true;
        }
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
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && couette.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingCouette = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingCouette && !gameOver)
                    couettePoint.set(Constant.SCREEN_WIDTH/2, (int)event.getY()); //Déplace la couette mais le fait rester sur une ligne verticale
                break;
            case MotionEvent.ACTION_UP:
                movingCouette = false;
                break;
        }
    }
}
