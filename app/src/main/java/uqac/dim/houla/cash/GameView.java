package uqac.dim.houla.cash;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import uqac.dim.houla.R;

import static uqac.dim.houla.ShowBitmap.decodeSampledBitmapFromResource;

public class GameView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_jeu);
        Log.i("hello", "ony est");

        VideoView vid = findViewById(R.id.videoView);

        MediaController m = new MediaController(this);
        vid.setMediaController(m);

        String path = "android.resource://"+getPackageName()+"/"+R.raw.cash_jeu_asset;

        Uri u = Uri.parse(path);

        vid.setVideoURI(u);

        vid.start();

        vid.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                endGame();
            }
        });
    }

    public void launchGame(View v)
    {
        //On affiche l'image de fond
        ImageView image_partie = findViewById(R.id.imagePartie);
        image_partie.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.reveil_jeu_background, 500, 500));

        //Récupéreation du layout des règles et suppression à l'écran
        LinearLayout layoutRegles = findViewById(R.id.layoutRegles);
        layoutRegles.setVisibility(View.GONE);

        //Récupèreation du layout général et affichage à l'écran
        LinearLayout layoutPrincipal = findViewById(R.id.layoutGeneral);
        layoutPrincipal.setVisibility(View.VISIBLE);

        //On récuèpère l'instance du texte du timer
        final TextView texteTimer = findViewById(R.id.timer);

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

        //On récupère le titre de fin
        TextView titreFin = findViewById(R.id.texteFin1);

        //On récupère le message de fin
        TextView messageFin = findViewById(R.id.texteFin2);

    }

    public void boutonSuivant(View v)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("win", true);
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }
}