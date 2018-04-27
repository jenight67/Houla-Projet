package uqac.dim.houla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Hashtable;

import uqac.dim.houla.menu_options.OptionActivity;

import static uqac.dim.houla.ShowBitmap.decodeSampledBitmapFromResource;

public class PresentationActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constant.SCREEN_WIDTH = dm.widthPixels;
        Constant.SCREEN_HEIGHT = dm.heightPixels;

        setContentView(R.layout.activity_presentation);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //Afficher la vue d'accueil
        setContentView(R.layout.activity_presentation);
        //On affiche l'image de fond
        ImageView image_accueil = findViewById(R.id.imageAccueil);
        image_accueil.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.presentation_jeu, 500, 500));
    }

    public void launchGame(View v)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("win",false);
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }
}
