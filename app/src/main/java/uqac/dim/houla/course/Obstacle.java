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

    private Animation idle;
    private AnimationManager am;

    public Rect getRectangle(){
        return rectangle;
    }

    public Obstacle(int height, int color, int startX, int startY){
        this.rectangle = new Rect(startX-100, startY, startX, startY+height );
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap[] idleBit = new Bitmap[3];
        idleBit[0] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_player_idle0);
        idleBit[1] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_player_idle1);
        idleBit[2] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_player_idle2);
    }

    public boolean playerCollide(Player player){
        return rectangle.intersect(player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {
    }


    public void update(Point point) {
        rectangle.set(point.x - rectangle.width()/2
                , point.y - rectangle.height()/2
                , point.x + rectangle.width()/2
                , point.y + rectangle.height()/2);
    }

    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
    }
}
