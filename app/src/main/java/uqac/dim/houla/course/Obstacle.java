package uqac.dim.houla.course;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import uqac.dim.houla.Constant;
import uqac.dim.houla.R;

/**
 * Created by Jérémy on 10/03/2018.
 */

/**
 * Objet correspondant a un obstacle
 */
public class Obstacle implements GameObject {

    private Rect rectangle; //Rectangle acceuillant l'obstacle

    //Credit pour l'animation (trouvée sur OpenGameArt.org):
    // © 2005-2013 Julien Jorge <julien.jorge@stuff-o-matic.com>
    // Animation de la banane
    private AnimationManager am;

    public Rect getRectangle(){
        return rectangle;
    }

    public Obstacle(int height, int color, int startX, int startY, Animation idle){
        this.rectangle = new Rect(startX-100, startY, startX, startY+height );

        // 0 = idle
        am = new AnimationManager(new Animation[]{idle});
    }

    /**
     * Verifie la collision entre le joueur et un obstacle
     * @param player Joueur
     * @return Si les objets sont en collision
     */
    public boolean playerCollide(Player player){
        return rectangle.intersect(player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas) {
        //Paint paint = new Paint();
        //paint.setColor(color);

        //canvas.drawRect(rectangle, paint);

        am.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        am.update();
    }

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width()/2
                , point.y - rectangle.height()/2
                , point.x + rectangle.width()/2
                , point.y + rectangle.height()/2);

        //Il n'y a qu'une seule animation.
        am.playIndex(0);
        am.update();
    }

    /**
     * Permet de faire descendre les obstacles.
     * @param y nombre de pixel de déplacement vers le bas
     */
    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        //Laisser ça là sinon les bananes sont invisibles.
        am.playIndex(0);
        am.update();

    }
}
