package uqac.dim.houla.course;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Gère les frames d'un animation
 */
public class Animation {
    private Bitmap[] frames; //Array de bitmap contenant les differentes frames
    private int frameIndex; //Frame actuellement dessinée

    private boolean playing = false;

    private float frameTime;//Temps d'apparition d'une frame

    private long lastFrame;// temps ou la derniere frame a ete dessinée

    public boolean isPlaying(){
        return playing;
    }

    public Animation(Bitmap[] frames, float animTime){
        this.frames = frames;
        frameIndex = 0;

        frameTime = animTime/frames.length;

        lastFrame = System.currentTimeMillis();
    }

    public void play(){
        playing = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop(){
        playing = false;
    }


    public void draw(Canvas canvas,Rect destination){
        if(!playing)
            return;

        //Arrange (normalement) la taille des sprites ( mais rend les banananes transgéniques).
        //scaleRect(destination);

        canvas.drawBitmap(frames[frameIndex],null,destination,new Paint());
    }

    /**
     * Transforme l'immage pour la rendre plus petite mais en garder les proportions
     * @param rect Rectangle a tranformer
     */
    private void scaleRect(Rect rect){
        float whRatio = (float)(frames[frameIndex].getWidth())/frames[frameIndex].getHeight();

        if(rect.width() > rect.height())
            rect.left = rect.right - (int)((rect.height()*whRatio));
        else
            rect.top = rect.bottom - (int)((rect.width()*whRatio));
    }

    public void update(){
        if(!playing)
            return;

        //Si on a depasser le temps entre la derniere frame et le temps actuel prend la nouvelle frame.
        if(System.currentTimeMillis()-lastFrame > frameTime * 1000){
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }
}
