package uqac.dim.houla.course;

import android.graphics.Canvas;

/**
 * Created by Jérémy on 10/03/2018.
 */

/**
 * Elements de base d'un mini-jeu
 */
public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
