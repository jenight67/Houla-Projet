package uqac.dim.houla.course;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Gère les multiples animations d'un objet.
 */
public class AnimationManager {
    private Animation[] animations; //Differentes animations d'un objet
    private int animationIndex = 0; //Animation actuelle


    public AnimationManager(Animation[] animations){
        this.animations = animations;
    }

    /**
     * Joue l'animation a l'index spécifié
     * @param index Index de l'animation a jouer
     */
    public void playIndex(int index){
        for (int i = 0; i < animations.length; i++){
            if(i== index){
                if(!animations[animationIndex].isPlaying())
                    animations[i].play();
            }
            else
                animations[i].stop();
        }
        animationIndex = index;
    }

    public void draw(Canvas canvas, Rect rect){
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas,rect);
    }

    public void update(){
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }
}
