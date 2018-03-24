package uqac.dim.houla.course;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;


/**
 * Obstacle Manager du Mini-Jeu ne rate pas le bus
 */

public class ObstacleManager {
    //Plus l'index est haut plus l'obstacle est bas.
    private ArrayList<Obstacle> obstacles;

    private long startTime;

    private int currY;

    public ObstacleManager(){

        obstacles = new ArrayList<>();
        startTime = System.currentTimeMillis();

        populateObstacles();

    }

    private void populateObstacles(){
        while(obstacles.get(obstacles.size()-1).getRectangle().bottom < 0){
            int StartX = (int)(Math.random()*(Constant.SCREEN_WIDTH));
            obstacles.add(new Obstacle(new Rect(),Color.WHITE));
            currY += 1 ;
        }
    }

    public void update(){

        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        for (Obstacle o: obstacles) {
            //TODO J'ai juste ajouté une valeur arbitraire pour retirer l'exception, à changer
            o.incrementY(0);
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle o: obstacles) {
            o.draw(canvas);
        }
    }
}
