package uqac.dim.houla.course;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import uqac.dim.houla.couette.couetteScene;

public class SceneManager {
    private courseScene gm;
    private couetteScene cm;
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENES;

    public SceneManager(int game){
        ACTIVE_SCENES = 0;
        Log.i("DICJ","New courseScene");


        if(game == 0){
            gm = new courseScene();
            scenes.add(gm);
        } else {
            cm = new couetteScene();
            scenes.add(cm);
        }
        /*//Scene 0
        gm = new courseScene();
        scenes.add(gm);
        //Scene 1
        cm = new couetteScene();
        scenes.add(cm);*/
    }

    public void receiveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENES).receiveTouch(event);
    }

    public void setActivity(gameActivity activity){
        scenes.get(ACTIVE_SCENES).setActivity(activity);
    }


    public void update(){
        scenes.get(ACTIVE_SCENES).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENES).draw(canvas);
    }
}
