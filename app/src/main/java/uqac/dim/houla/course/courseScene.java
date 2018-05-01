package uqac.dim.houla.course;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import uqac.dim.houla.Constant;
import uqac.dim.houla.R;

/**
 * Gère la scène du mini-jeu course
 */
public class courseScene implements Scene {


    private gameActivity main; //Reference a l'activitée pour lancer la fin du jeu
    private Player player; //Objet du joueur
    private Point playerPoint; //Point sur lequel se trouve le joueur
    private ObstacleManager om; //Manager des obstacles
    private Bitmap background; // Image de fond
    private Rect backgroundRect; // Rectangle accueillant le fond

    private boolean movingPlayer = false; //Permet de savoir si le joueur est en mouvement
    private boolean gameOver = false; //Partie perdu
    private boolean win = false; // Partie gagnée
    private long initTime; //permet de calculer le temps restant au timer

    private static final int TIMER = 10; // Ajuste la durée du timer (en secondes)
    private static final int PLAYER_HEIGHT = Constant.SCREEN_HEIGHT*7/10; // Ajuste la hauteur du joueur sur l'ecran

    public courseScene(){

        //Rectangle accueillant le fond
        backgroundRect = new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT);

        //Chargement de l'image de fond
        BitmapFactory bf = new BitmapFactory();
        background = bf.decodeResource(Constant.CURRENT_CONTEXT.getResources(), R.drawable.course_background);


        //Creation du joueur
        Log.i("DICJ","Avant loading player");
        player = new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));
        Log.i("DICJ","Apres loading player");

        //Creation du point au centre du rectangle du joueur
        playerPoint = new Point(Constant.SCREEN_WIDTH/2,PLAYER_HEIGHT);


        //Place le joueur sur le point
        player.update(playerPoint);

        //Creation de l'obstacle manager qui gere les bananes
        om = new ObstacleManager(100,100);

        initTime = System.currentTimeMillis();
    }

    /**
     * Calcul le temps restant
     * @return nombre de secondes restant en int
     */
    public int getTimer(){
        long currTime = System.currentTimeMillis();
        int time = (int)((currTime - initTime) /1000);
        time = TIMER - time;
        if(time <= 0){
            win = true;
        }
        return time;
    }

    /**
     * Setter de main
     * @param main ACtivité
     */
    public void setActivity(gameActivity main){
        this.main = main;
    }

    /**
     * Effectue les calculs logiques
     */
    @Override
    public void update() {
        if(!gameOver && !win){
            player.update(playerPoint);
            om.update();
            //Fin du jeu
            if(om.playerCollide(player))
                gameOver = true;
        }
    }

    /**
     * Dessine les objets a l'ecran.
     * @param canvas le canvas sur lequel on dessine.
     */
    @Override
    public void draw(Canvas canvas) {
        //Dessine le fond
        canvas.drawBitmap(background,null,backgroundRect,new Paint());

        //Dessine le joueur
        player.draw(canvas);

        //Donne l'ordre a l'obstacle manager de dessiner les obstacles
        om.draw(canvas);

        //Affichage du text Timer
        Paint p = new Paint();
        p.setTextSize(50);
        p.setColor(Color.RED);

        int timeInt = getTimer();
        String timeStr = String.valueOf(timeInt);

        canvas.drawText(timeStr, 0,Constant.SCREEN_HEIGHT - 50,p);

        // Affichage de la barre de Timer
        Paint pRect = new Paint();
        pRect.setColor(Color.BLUE);

        // Calcul la longueur requise
        int longRect = Constant.SCREEN_WIDTH * timeInt /TIMER;

        Rect recTime = new Rect(0,0,longRect,50);
        canvas.drawRect(recTime,pRect);


        if(gameOver){
            main.lose();
        }

        if(win){
            main.win();
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENES = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlayer = true;// Debut du mouvement
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingPlayer && !gameOver)
                    playerPoint.set((int)event.getX(), Constant.SCREEN_HEIGHT*7/10); //Déplace le joueur mais le fait rester sur une ligne
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;//Fin du mouvement
                break;
        }
    }
}
