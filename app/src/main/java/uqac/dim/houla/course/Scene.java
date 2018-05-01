package uqac.dim.houla.course;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Chaque scene represente un mini-jeu
 */
public interface Scene {
    void update();
    void draw(Canvas canvas);
    void terminate();
    void receiveTouch(MotionEvent event);
    void setActivity(gameActivity activity);
}
