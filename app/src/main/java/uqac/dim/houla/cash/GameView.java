package uqac.dim.houla.cash;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import uqac.dim.houla.R;

import static uqac.dim.houla.ShowBitmap.decodeSampledBitmapFromResource;

public class GameView extends AppCompatActivity {

    private int clic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_jeu);

        //On affiche la vidéo
        VideoView vid = findViewById(R.id.videoView);

        MediaController m = new MediaController(this);
        vid.setMediaController(m);

        //On retire les contrôles de la vidéo
        m.setVisibility(View.GONE);

        //Chemin d'accès à la vidéo
        String path = "android.resource://" + getPackageName() + "/" + R.raw.cash_jeu_asset;

        //Conversion au format URI
        Uri u = Uri.parse(path);

        //Déclaration du chemin d'accès à la vidéo
        vid.setVideoURI(u);

        //À la fin de la vidéo, appeler la fonction endGame()
        vid.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                endGame();
            }
        });

        //Affichage de la preview
        vid.seekTo(100);

        //On met en place l'image de fond
        ImageView imageAccueil = findViewById(R.id.imageAccueil);
        imageAccueil.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cash_regles_background, 500, 500));

        //On récupère le layout de fin
        LinearLayout layoutFin = findViewById(R.id.layoutFin);
        //On masque le layout de fin
        layoutFin.setAlpha(0.0f);
    }

    public void launchGame(View v)
    {
        //Récupéreation du layout des règles et suppression à l'écran
        LinearLayout layoutRegles = findViewById(R.id.layoutRegles);
        layoutRegles.setVisibility(View.GONE);

        //Récupèreation du layout général et affichage à l'écran
        LinearLayout layoutPrincipal = findViewById(R.id.layoutGeneral);
        layoutPrincipal.setVisibility(View.VISIBLE);
    }

    public void launchVideo(View v)
    {
        if (clic == 0)
        {
            //On affiche la vidéo
            VideoView vid = findViewById(R.id.videoView);

            //Lancement de la vidéo
            vid.start();

            //Incérment du compteur pour éviter d'autres clics
            clic++;
        }
    }

    protected void endGame()
    {
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
    }

    public void boutonSuivant(View v)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("win", true);
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }
}