package uqac.dim.houla.course;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import uqac.dim.houla.Constant;


/**
 * Obstacle Manager du Mini-Jeu ne rate pas le bus
 */

public class ObstacleManager {

    //Plus l'index est haut plus l'obstacle est bas.
    private ArrayList<Obstacle> obstacles;

    private long startTime;
    private float speed;
    private float speedMult = 4;

    private int currY;
    private int distObs;
    private int obsHeight;

    public ObstacleManager(int distObs, int obsHeight){
        this.distObs = distObs;
        this.obsHeight = obsHeight;

        obstacles = new ArrayList<>();
        startTime = System.currentTimeMillis();

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
            obstacles.add(new Obstacle(obsHeight,Color.BLACK,startX,currY));
            currY += distObs + obsHeight ;
        }
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        speed = Constant.SCREEN_HEIGHT/10000.0f;


        for (Obstacle o: obstacles) {
            o.incrementY(speed * elapsedTime * speedMult);
        }

        while(obstacles.get(obstacles.size()-1).getRectangle().top >= Constant.SCREEN_HEIGHT){


            int startX = (int)(Math.random()*(Constant.SCREEN_WIDTH));
            obstacles.add(0,new Obstacle(obsHeight,Color.BLACK,startX,currY));
            obstacles.remove(obstacles.size()-1);
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle o: obstacles) {
            o.draw(canvas);
        }

    }
}
