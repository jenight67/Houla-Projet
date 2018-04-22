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
 * Created by User on 10/03/2018.
 */

public class Obstacle implements GameObject {

    private Rect rectangle;
    private int color;

    // © 2005-2013 Julien Jorge <julien.jorge@stuff-o-matic.com>
    private AnimationManager am;

    public Rect getRectangle(){
        return rectangle;
    }

    public Obstacle(int height, int color, int startX, int startY, Animation idle){
        this.rectangle = new Rect(startX-100, startY, startX, startY+height );
        this.color = color;

        // 0 = idle
        am = new AnimationManager(new Animation[]{idle});
    }

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

    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        //Laisser ça là sinon les bananes sont invisibles.
        am.playIndex(0);
        am.update();

    }
}
