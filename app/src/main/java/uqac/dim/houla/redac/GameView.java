package uqac.dim.houla.redac;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import uqac.dim.houla.R;

import static uqac.dim.houla.ShowBitmap.decodeSampledBitmapFromResource;

public class GameView extends AppCompatActivity {

    int index;
    boolean win;
    boolean partieEnCours;
    String[] texteRedac = { "A", " v", "a", " ê", "t", "r", "e", " b", "o", "n", "n", "e", " à", " s", "o", "i", "r", " d", "a", "n", "s", " l'", "s", "h", "a", "c", "k", " à", " H", "e", "c", "t", "o", "r",
            "\nO", "u", "v", "r", "e", " l", "a", " v", "a", "l", "i", "s", "e", " p", "i", "s", " s", "o", "r", "s", " l", "e", "s", " b", "o", "u", "t", "e", "i", "l", "l", "e", "s", " d", "e", " f","o", "r","t\n",
            "P", "i", "s", " s", "i", " t", "o", "n", " f", "o", "i", "e", " d", "é", "c", "i", "d", "e", " d'", "ê", "t", "r", "e", " m", "a", "l", "a", "d","e\n", ",",
            "A", "r", "r", "a", "n", "g", "e", " t", "o", "i", " p", "o", "u", "r", " p", "a", "s", " f", "a", "i", "r", "e", " ç", "a", " d", "a", "n", "s", " l'", "p", "l", "a", "t", " d'", "s", "a", "l", "a", "d", "e", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redac_jeu);

        //On récupère le layout de fin
        LinearLayout layoutFin = findViewById(R.id.layoutFin);
        //On masque le layout de fin
        layoutFin.setAlpha(0.0f);

        //On récupère l'instance de la zone de texte
        TextView zoneDeTexte = findViewById(R.id.zoneDeTexte);
        //On set le premier caractère
        zoneDeTexte.setText(texteRedac[0]);

        //On récupère l'instance du bouton
        Button boutonRedac = findViewById(R.id.boutonRedac);
        //On met son texte au second caractère
        boutonRedac.setText(texteRedac[1]);

        //On met le index à 1
        index = 1;

        //On met le boolean de retour de jeu à false
        win = false;

        //On affiche l'image de fond
        ImageView image_accueil = findViewById(R.id.imageFond);
        image_accueil.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.redac_regles_background, 500, 500));
    }

    public void launchGame(View v)
    {
        //On affiche l'image de fond
        ImageView image_partie = findViewById(R.id.imageFond);
        image_partie.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.redac_jeu_background, 500, 500));

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
                texteTimer.setText(R.string.redac_texte_compteur_fin);
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

        if (index >= 70)
        {
            //On dit que le jeu est gagné
            win = true;
            //On adapte le titre de fin
            titreFin.setText(R.string.redac_texte_victoire);
            //On adapte le message de fin
            String message = getString(R.string.reveil_message_victoire_1) + index + getString(R.string.redac_message_victoire_2);
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
            //On dit que le jeu est perdu
            win = false;
            //On adapte le titre de fin
            titreFin.setText(R.string.redac_texte_defaite);
            //On adapte le message de fin
            String message = getString(R.string.redac_message_defaite_1) + index + getString(R.string.redac_message_defaite_2);
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

    public void clicClavier(View v)
    {
        if (partieEnCours && index < texteRedac.length - 1)
        {
            //On récupère l'instance de la zone de texte
            TextView zoneDeTexte = findViewById(R.id.zoneDeTexte);
            //On récupère l'instance du bouton
            Button boutonRedac = findViewById(R.id.boutonRedac);

            //On incrémente le compteur de l'index
            //index++;

            //On affiche le caractère supplémentaire
            String message = zoneDeTexte.getText() + texteRedac[index];
            zoneDeTexte.setText(message);

            //On met à jour le bouton
            boutonRedac.setText(texteRedac[++index]);
        }
    }

    public void boutonSuivant(View v)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("win", win);
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }
}