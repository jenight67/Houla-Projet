package uqac.dim.houla.course;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import uqac.dim.houla.Constant;
import uqac.dim.houla.R;

/**
 * Created by User on 10/03/2018.
 */

public class Player implements GameObject {

    private Rect rectangle;
    private int color;

    // Credit pour l'animation :
    // Curt - cjc83486
    // https://opengameart.org/content/rpg-character
    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager am;

    public Rect getRectangle(){return rectangle;}

    public Player(Rect rectangle, int color){
        Log.i("DICJ","Debut Loading Player");

        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap[] idleBit = new Bitmap[3];
        idleBit[0] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.idle0);
        idleBit[1] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.idle1);
        idleBit[2] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.idle2);

        idle = new Animation(idleBit,1f);

        Bitmap[] walkRightBit = new Bitmap[3];
        walkRightBit[0] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.walkright0);
        walkRightBit[1] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.walkright1);
        walkRightBit[2] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.walkright2);

        walkRight = new Animation(walkRightBit, 1f);

        Bitmap[] walkLeftBit = new Bitmap[3];
        walkLeftBit[0] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.walkleft0);
        walkLeftBit[1] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.walkleft1);
        walkLeftBit[2] = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.walkleft2);

        walkLeft = new Animation(walkLeftBit, 1f);

        // 0 = idle | 1 = walkRight | 2 = walkLeft ( voir update)
        am = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});

        Log.i("DICJ","PlayerLoaded");
    }

    @Override
    public void draw(Canvas canvas) {
        am.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        am.update();
    }

    public void update(Point point){
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2
                , point.y - rectangle.height()/2
                , point.x + rectangle.width()/2
                , point.y + rectangle.height()/2);

        // 0 = idle | 1 = walkRight | 2 = walkLeft
        int state = 0;
        if(rectangle.left - oldLeft > 5)
            state = 1;
        else if (rectangle.left - oldLeft < -5)
            state = 2;

        am.playIndex(state);
        am.update();
    }
}
