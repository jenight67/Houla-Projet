package uqac.dim.houla.course;

import android.graphics.Canvas;
import android.graphics.Color;
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

    private int currY;

    public ObstacleManager(){

        obstacles = new ArrayList<>();
        startTime = System.currentTimeMillis();

        populateObstacles();

    }

    private void populateObstacles(){
        currY += -5*Constant.SCREEN_HEIGHT/4;
        while(obstacles.get(obstacles.size()-1).getRectangle().bottom < 0){
            int StartX = (int)(Math.random()*(Constant.SCREEN_WIDTH));
            obstacles.add(new Obstacle(new Rect(),Color.WHITE));
            currY += -50 ;
        }
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        speed = Constant.SCREEN_HEIGHT/10000.0f;


        for (Obstacle o: obstacles) {
            o.incrementY(speed*elapsedTime);
        }

        while(obstacles.get(obstacles.size()-1).getRectangle().top >= Constant.SCREEN_HEIGHT){
            obstacles.remove(obstacles.size()-1);
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle o: obstacles) {
            o.draw(canvas);
        }
    }
}
