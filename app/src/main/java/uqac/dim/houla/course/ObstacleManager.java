package uqac.dim.houla.course;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import uqac.dim.houla.Constant;
import uqac.dim.houla.R;


/**
 * Obstacle Manager du Mini-Jeu ne rate pas le bus
 */

public class ObstacleManager {

    //Plus l'index est haut plus l'obstacle est bas.
    private ArrayList<Obstacle> obstacles;

    private long startTime;
    private float speed = Constant.SCREEN_HEIGHT/10000.0f;
    private float speedMult = 3;

    private int currY;
    private int distObs;
    private int obsHeight;

    private Animation idle;
    private Bitmap[] idleBit;

    public ObstacleManager(int distObs, int obsHeight){
        this.distObs = distObs;
        this.obsHeight = obsHeight;

        obstacles = new ArrayList<>();
        startTime = System.currentTimeMillis();

        BitmapFactory bf = new BitmapFactory();
        idleBit = new Bitmap[3];
        idleBit[0] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_obstacle_idle0);
        idleBit[1] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_obstacle_idle1);
        idleBit[2] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_obstacle_idle2);

        idle = new Animation(idleBit,1f);

        populateObstacles();
    }

    public boolean playerCollide(Player player){
        boolean res = false;
        for (Obstacle o:obstacles) {
            if(o.playerCollide(player)){
                res = true;
            }
        }
        return res;
    }

    private void populateObstacles(){
        currY += -5*Constant.SCREEN_HEIGHT/4;

            while(currY < 0){
                int startX = (int)(Math.random()*(Constant.SCREEN_WIDTH));
                obstacles.add(new Obstacle(obsHeight,Color.BLACK,startX,currY,idle));
                currY += distObs + obsHeight ;
        }
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //Deplace les obstacles
        for (Obstacle o: obstacles) {
            o.incrementY(speed * elapsedTime * speedMult);
        }

        while(obstacles.get(obstacles.size()-1).getRectangle().top >= Constant.SCREEN_HEIGHT){


            int startX = (int)(Math.random()*(Constant.SCREEN_WIDTH));
        obstacles.add(0,new Obstacle(obsHeight,Color.BLACK,startX,currY,idle));
        obstacles.remove(obstacles.size()-1);
    }
    }

    public void draw(Canvas canvas){
        for (Obstacle o: obstacles) {
            o.draw(canvas);
        }
    }
}
