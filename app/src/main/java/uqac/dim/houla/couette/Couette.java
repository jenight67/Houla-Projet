package uqac.dim.houla.couette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import uqac.dim.houla.Constant;
import uqac.dim.houla.R;
import uqac.dim.houla.course.GameObject;

public class Couette implements GameObject {

    private Rect rectangle;
    private Bitmap couette;

    public Rect getRectangle(){return rectangle;}

    public Couette(Rect rectangle){
        this.rectangle = rectangle;

        BitmapFactory bf = new BitmapFactory();
        couette = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.couette_background_endormi);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(couette,null,rectangle,new Paint());
    }

    @Override
    public void update() {

    }

    public void update(Point point){
        rectangle.set(point.x - rectangle.width()/2
                , point.y - rectangle.height()/2
                , point.x + rectangle.width()/2
                , point.y + rectangle.height()/2);
    }

    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        //Laisser ça là sinon les bananes sont invisibles.

        this.update();

    }
}
