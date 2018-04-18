package uqac.dim.houla.course;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class SceneManager {
    private GameplayScene gm;
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENES;

    public SceneManager(){
        ACTIVE_SCENES = 0;
        Log.i("DICJ","New GameplayScene");
        gm = new GameplayScene();
        scenes.add(gm);
    }

    public void receiveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENES).receiveTouch(event);
    }

    public void setActivity(courseActivity activity){
        gm.setActivity(activity);
    }


    public void update(){
        scenes.get(ACTIVE_SCENES).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENES).draw(canvas);
    }
}
