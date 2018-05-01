package uqac.dim.houla.course;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import uqac.dim.houla.Constant;
import uqac.dim.houla.MainThread;

/**
 * Created by User on 08/03/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread; //Threads donnant le tempo des calculs et des dessins (boucle de jeu)
    private SceneManager manager; //Manager de scène pour pouvoir changer entre les differentes scenes.

    public GameView(Context context, int jeu){
        super(context);

        getHolder().addCallback(this);

        Constant.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        Log.i("DICJ","New SceneManager");

        manager = new SceneManager(jeu);

        setFocusable(true);
    }

    public void setActivity(gameActivity activity){
        manager.setActivity(activity);
    }



    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch (Exception e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager.receiveTouch(event);
        return true;
    }

    public void update(){
        manager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        manager.draw(canvas);
    }
}
