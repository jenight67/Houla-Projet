package uqac.dim.houla;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import uqac.dim.houla.course.GameView;

/**
 *  Classe contenant le thread principal du jeu.
 */

public class MainThread extends Thread {
    //On set cette constante car sinon le telephone calcul trop vite et appel plus que 30 fois par seconde update ce qui ne sert a rien
    private static final int MAX_FPS = 30;

    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;

    public static Canvas canvas;

    public void setRunning(boolean running){
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = timeMillis;

        //Game Loop
        while(running) {
            //Peut aussi utiliser milliTime mais moins precis et moins couteux en ressources
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    //Ces deux lignes sont le coeur de la boucle de jeu
                    //effectue les calculs logiques
                    this.gameView.update();
                    //Dessine l'ecran
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = System.nanoTime() - startTime/1000000;
            waitTime = targetTime - timeMillis;
            try{
                if(waitTime > 0){
                    //Attend le temps necessaire avant de generer une nouvelle frame
                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Compteur de FPS dans la console
            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == MAX_FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
}
