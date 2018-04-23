package uqac.dim.houla.menu_options;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import uqac.dim.houla.R;
import uqac.dim.houla.course.gameActivity;
import uqac.dim.houla.reveil.GameView;

import static uqac.dim.houla.ShowBitmap.decodeSampledBitmapFromResource;

public class OptionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_option);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //Afficher la vue d'options
        setContentView(R.layout.activity_option);
        //On affiche l'image de fond
        ImageView image_accueil = findViewById(R.id.imageFondoptions);
        image_accueil.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.options_background, 500, 500));
    }

    //Au clic sur le bouton de choix de jeu
    public void choisirJeu(View v)
    {

        Intent intent;
        try {
            switch (v.getId()) {

                case R.id.bourre:

                    break;
                case R.id.cash:
                    intent = new Intent(this, uqac.dim.houla.cash.GameView.class);
                    startActivity(intent);
                    break;
                case R.id.couette:
                    intent = new Intent(this, gameActivity.class);
                    intent.putExtra("game",1);
                    startActivity(intent);
                    break;
                case R.id.course:
                    intent = new Intent(this, gameActivity.class);
                    intent.putExtra("game",0);
                    startActivity(intent);
                    break;
                case R.id.pote:
                    intent = new Intent(this, uqac.dim.houla.pote.GameView.class);
                    startActivity(intent);
                    break;
                case R.id.redac:
                    intent = new Intent(this, uqac.dim.houla.redac.GameView.class);
                    startActivity(intent);
                    break;
                case R.id.reveil:
                    intent = new Intent(this, GameView.class);
                    startActivity(intent);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retour(View v)
    {
        super.finish();
    }
}
