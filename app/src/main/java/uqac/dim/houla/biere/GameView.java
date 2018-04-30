package uqac.dim.houla.biere;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.view.MotionEvent;
import android.util.DisplayMetrics;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import uqac.dim.houla.Constant;
import uqac.dim.houla.R;

import static uqac.dim.houla.ShowBitmap.decodeSampledBitmapFromResource;

public class GameView extends AppCompatActivity implements OnTouchListener{

    int compteur;
    boolean partieEnCours;
    boolean win;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.biere_jeu);

        //On met le compteur à 0
        compteur = 0;

        //On récupère le layout de fin
        LinearLayout layoutFin = findViewById(R.id.layoutFin);
        //On masque le layout de fin
        layoutFin.setAlpha(0.0f);

        //On met le résultat de jeu à faux
        win = false;

        //On affiche l'image de fond
        ImageView image_accueil = findViewById(R.id.imageAccueil);
        image_accueil.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.reveil_regles_background, 500, 500));
    }

    public void launchGame(View v)
    {

        //Récupéreation du layout des règles et suppression à l'écran
        LinearLayout layoutRegles = findViewById(R.id.layoutRegles);
        layoutRegles.setVisibility(View.GONE);

        //Récupèreation du layout général et affichage à l'écran
        LinearLayout layoutPrincipal = findViewById(R.id.layoutGeneral);
        layoutPrincipal.setVisibility(View.VISIBLE);

        //On passe l'indicateur de partie à true
        partieEnCours = true;

        //ajout d'un listener sur la balle du bière pong
        ImageButton boutonBoule = findViewById(R.id.boule);
        boutonBoule.setOnTouchListener(this);

        //On récuèpère l'instance du texte du timer
        final TextView texteTimer = findViewById(R.id.timer);

        //On créé un timer de 10 secondes et on affiche ses valeurs sur l'interface
        new CountDownTimer(10000, 100) {

            public void onTick(long millisUntilFinished) {
                texteTimer.setText(TimeFormatter(millisUntilFinished).substring(0, 4));
                ProgressBar progressionJeu = findViewById(R.id.progressionJeu);
                progressionJeu.setProgress((int) millisUntilFinished / 100);
            }

            public void onFinish() {
                endGame();
                texteTimer.setText(R.string.reveil_texte_compteur_depart);
            }
        }.start();


    }

    protected void endGame()
    {
        //On passe partieEnCours à faux
        partieEnCours = false;

        //On récuèpère l'instance du texte du layout
        LinearLayout layoutGeneral = findViewById(R.id.layoutGeneral);
        //On créé un effet d'atténuation
        Animation attenuation = new AlphaAnimation(1.0f, 0.1f);
        attenuation.setInterpolator(new AccelerateInterpolator());
        attenuation.setDuration(1000);
        attenuation.setFillAfter(true);

        //On atténue la vue du jeu
        layoutGeneral.startAnimation(attenuation);


        //On récupère le layout de fin
        LinearLayout layoutFin = findViewById(R.id.layoutFin);
        layoutFin.setAlpha(1.0f);
        Animation apparition = new AlphaAnimation(0.0f, 1.0f);
        apparition.setInterpolator(new AccelerateInterpolator());
        apparition.setDuration(1000);
        apparition.setFillAfter(false);
        apparition.setFillBefore(true);
        layoutFin.startAnimation(apparition);

        //On récupère le titre de fin
        TextView titreFin = findViewById(R.id.texteFin1);

        //On récupère le message de fin
        TextView messageFin = findViewById(R.id.texteFin2);

        if (compteur >= 3)
        {
            //On met le résultat de jeu à true
            win = true;
            //On adapte le titre de fin
            titreFin.setText(R.string.reveil_texte_victoire);
            //On adapte le message de fin
            String message = getString(R.string.biere_victoire) + " " + compteur + " " + getString(R.string.biere_victoire_2);
            messageFin.setText(message);
            //On récupère l'instance de l'image des confettis
            ImageView imageConfettis = findViewById(R.id.imageConfettis);
            imageConfettis.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.reveil_gagne_background, 500, 500));
            //On rend l'image visible
            imageConfettis.setAlpha(1.0f);
            Animation apparition_courte = new AlphaAnimation(0.0f, 0.5f);
            apparition_courte.setInterpolator(new AccelerateInterpolator());
            apparition_courte.setDuration(1500);
            apparition_courte.setFillAfter(true);
            apparition_courte.setFillBefore(true);
            imageConfettis.startAnimation(apparition_courte);
        }
        else
        {
            //On met le résultat de jeu à faux
            win = false;
            //On adapte le titre de fin
            titreFin.setText(R.string.reveil_message_defaite);
            //On adapte le message de fin
            String message = getString(R.string.biere_defaite);
            messageFin.setText(message);
            titreFin.setTextColor(getResources().getColor(R.color.red));
            //On récupère l'instance de l'image triste
            ImageView imageTriste = findViewById(R.id.imageTriste);
            imageTriste.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.reveil_perdu_background, 500, 500));
            //On rend l'image visible
            imageTriste.setAlpha(1.0f);
            Animation apparition_courte = new AlphaAnimation(0.0f, 0.5f);
            apparition_courte.setInterpolator(new AccelerateInterpolator());
            apparition_courte.setDuration(1500);
            apparition_courte.setFillAfter(true);
            apparition_courte.setFillBefore(true);
            imageTriste.startAnimation(apparition_courte);
        }
    }

    //Créé un format de temps
    private String TimeFormatter(long milliSeconds) {

        @SuppressLint("DefaultLocale") String ms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)),
                TimeUnit.MILLISECONDS.toMillis(milliSeconds) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliSeconds)));
        return ms;
    }


    public boolean onTouch(View v, MotionEvent event) {

        if(partieEnCours) {

            //reccupération de la taille de l'écran pour positionner la balle
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);


            //détection des événements et déplacement de la balle
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setX(event.getX() + v.getX());
                    v.setY(event.getY() + v.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.setX(event.getX() + v.getX());
                    v.setY(event.getY() + v.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    float x = event.getX() + v.getX();
                    float y = event.getY() + v.getY();
                    v.setX(x);
                    v.setY(y);

                    //réccupération des coordonnées du gobelet pour le score
                    ImageView gobelet = findViewById(R.id.gobelet);

                    float valXSucces = gobelet.getX() + (gobelet.getWidth() / 2);
                    float valYSucces = gobelet.getY() + (gobelet.getHeight() / 2);

                    if(x > valXSucces - 500 && x < valXSucces + 500 && y > valYSucces - 500 && y < valXSucces + 500 )
                    {

                        //On incrémente le compteur
                        TextView texteCompteur = findViewById(R.id.compteur);
                        compteur++;
                        String message = compteur + "";
                        texteCompteur.setText(message);
                    }

                    //on fait une pause pour permettre a l'utilisateur de voir ou il a posé la balle
                    try {
                        Thread.sleep(500);
                    }
                    catch(InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    //positionnement de la balle à sa place de départ
                    v.setX(metrics.heightPixels / 4);
                    v.setY(metrics.widthPixels + 290);

                    //coordonnée aléatoire pour déplacer le verre
                    Random r = new Random();
                    int yG = r.nextInt((metrics.heightPixels - 1000) - 50) + 50;
                    int xG = r.nextInt((metrics.widthPixels - 1000) - 50) + 50;

                    //positionnement du verre
                    gobelet.setY(yG);
                    gobelet.setX(xG);
                    break;
            }
            return true;
        }
        return false;
    }



    public void boutonSuivant(View v)
    {
        if (!partieEnCours)
        {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("win", win);
            setResult(Activity.RESULT_OK,returnIntent);
            super.finish();
        }
    }
}