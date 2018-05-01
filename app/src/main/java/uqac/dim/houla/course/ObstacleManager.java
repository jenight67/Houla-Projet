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

    private long startTime; //Temps de debut
    private float speed = Constant.SCREEN_HEIGHT/10000.0f; // Vitesse des obstacles
    private float speedMult = 3; // multiplicateur de vitesse simplifiant la mise en place d'un système de difficulté par la suite en l'augmentant

    private int currY; // Position actuelle de l'obstacle
    private int distObs; // Distance (horizontal entre les obstacles)
    private int obsHeight; // Hauteur d'un obstacle

    private Animation idle; // Animation de l'obstacle
    private Bitmap[] idleBit; // Frames de l'animation de l'obstacle

    public ObstacleManager(int distObs, int obsHeight){
        this.distObs = distObs;
        this.obsHeight = obsHeight;

        obstacles = new ArrayList<>();
        startTime = System.currentTimeMillis();

        // Chargement de l'animation a partir des ressources
        BitmapFactory bf = new BitmapFactory();
        idleBit = new Bitmap[3];
        idleBit[0] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_obstacle_idle0);
        idleBit[1] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_obstacle_idle1);
        idleBit[2] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_obstacle_idle2);

        //Créel l'animation
        idle = new Animation(idleBit,1f);

        // Crée les premier obstacles
        populateObstacles();
    }

    /**
     * Verifie la collision entre tout les obstacles et le joueur
     * @param player Joueur
     * @return Si le joueur est en collision avec n'importe quel obstacle
     */
    public boolean playerCollide(Player player){
        boolean res = false;
        for (Obstacle o:obstacles) {
            if(o.playerCollide(player)){
                res = true;
            }
        }
        return res;
    }

    /**
     * Première initialiation dde l'aire de jeu et creation des premiers obstacles
     */
    private void populateObstacles(){
        currY += -5*Constant.SCREEN_HEIGHT/4;

            while(currY < 0){
                int startX = (int)(Math.random()*(Constant.SCREEN_WIDTH));
                obstacles.add(new Obstacle(obsHeight,Color.BLACK,startX,currY,idle));
                currY += distObs + obsHeight ;
        }
    }

    /**
     * Crée les obstacles a intervalle regulier quand ils disparaisse de l'ecran et les descend
     */
    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //Deplace les obstacles
        for (Obstacle o: obstacles) {
            o.incrementY(speed * elapsedTime * speedMult);
        }

        //Supprime te recrée les obstaclers quand ils arrivent en bas de l'écran
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
