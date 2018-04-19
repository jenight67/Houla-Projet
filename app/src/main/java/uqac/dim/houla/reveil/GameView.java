package uqac.dim.houla.reveil;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import uqac.dim.houla.Constant;
import uqac.dim.houla.MainActivity;
import uqac.dim.houla.R;

public class GameView extends AppCompatActivity {

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
        setContentView(R.layout.reveil_jeu);

        //On met le compteur à 0
        compteur = 0;

        //On récupère le layout de fin
        LinearLayout layoutFin = findViewById(R.id.layoutFin);
        //On masque le layout de fin
        layoutFin.setAlpha(0.0f);

        //On met le résultat de jeu à faux
        win = false;
    }

    public void launchGame(View v)
    {
        //Récupèreation du layout des règles et suppression à l'écran
        LinearLayout layoutRegles = findViewById(R.id.layoutRegles);
        layoutRegles.setVisibility(View.GONE);

        //Récupèreation du layout général et affichage à l'écran
        LinearLayout layoutPrincipal = findViewById(R.id.layoutGeneral);
        layoutPrincipal.setVisibility(View.VISIBLE);

        //On passe l'indicateur de partie à true
        partieEnCours = true;

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
                texteTimer.setText("00:0");
            }
        }.start();
    }

    protected void endGame() {
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

        if (compteur >= 8)
        {
            //On met le résultat de jeu à true
            win = true;
            //On adapte le titre de fin
            titreFin.setText("Gagné !");
            //On adapte le message de fin
            messageFin.setText("Bravo, tu as réussi à éteindre " + compteur + " fois ton réveil ! Tu peux aller en cours en toute tranquilité !");
            //On récupère l'instance de l'image des confettis
            ImageView imageConfettis = findViewById(R.id.imageConfettis);
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
            titreFin.setText("Perdu...");
            //On adapte le message de fin
            messageFin.setText("Zut, tu n'as réussi à éteindre ton réveil que " + compteur + " fois, bon allez, on continue...");
            titreFin.setTextColor(getResources().getColor(R.color.red));
            //On récupère l'instance de l'image triste
            ImageView imageTriste = findViewById(R.id.imageTriste);
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

        String ms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)),
                TimeUnit.MILLISECONDS.toMillis(milliSeconds) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliSeconds)));
        return ms;
    }

    public void clicReveil(View v)
    {
        Log.i("---", "clic");
        if (partieEnCours)
        {
            //On récupère l'instance du bouton
            ImageButton boutonReveil = findViewById(R.id.imageButton);
            //On récupère l'instance du layout dans lequel on va faire bouger le réveil
            ConstraintLayout layoutReveil = findViewById(R.id.reveilLayout);
            //On récupère l'affichage du nombre de clics
            TextView texteCompteur = findViewById(R.id.compteur);

            //On incrémente le compteur
            compteur++;
            texteCompteur.setText(compteur + "");

            //On change la position du bouton avec un nombre aléatoire inférieur à la taille du layout
            boutonReveil.setX(Constant.randomInt(0, Constant.SCREEN_WIDTH - boutonReveil.getWidth()));
            boutonReveil.setY(Constant.randomInt((int) layoutReveil.getX(), (int) (layoutReveil.getX() + layoutReveil.getHeight() - boutonReveil.getHeight())));
        }
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
        else
        {
            clicReveil(v);
        }
    }
}