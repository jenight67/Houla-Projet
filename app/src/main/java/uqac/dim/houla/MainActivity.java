package uqac.dim.houla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Hashtable;

import uqac.dim.houla.menu_options.OptionActivity;

public class MainActivity extends Activity
{
    //Tableau contenant les résultats des jeux
    Hashtable resultatsJeux = new Hashtable();

    //Liste contenant les mini-jeux par ordre
    Class[] ordreJeux = {
        uqac.dim.houla.reveil.GameView.class,
        uqac.dim.houla.course.courseActivity.class,
        uqac.dim.houla.redac.GameView.class
    };

    //Score du joueur
    private int score = 0;

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

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //Afficher la vue d'accueil
        setContentView(R.layout.activity_main);
    }

    //Lancemenent de tous les minijeux à la suite
    public void launchGame(View v)
    {
        //Lancement du premier minijeu
        Intent intent = new Intent(this, ordreJeux[0]);
        startActivityForResult(intent, 0);
    }

    public void launchOption(View v)
    {
        Intent intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        boolean result = true;

        //if(requestCode == 1 && resultCode == RESULT_OK){
        if(resultCode == RESULT_OK)
        {
            result = data.getBooleanExtra("win",true);
        }

        //On met le résultat du minjeu dans la table des résultats
        resultatsJeux.put(ordreJeux[requestCode], result);
        //On affiche le résultat du minijeu
        Log.i("Résultat minijeu :", resultatsJeux.get(ordreJeux[requestCode]) + "");

        //Si le min-jeu a réussi, passer au suivant, sinon arrêter la partie
        if (result)
        {
            Log.i("MainActivity", "Mini-jeu réussi !");
            score++;
            nextMiniGame(requestCode);
        }
        else
        {
            Log.i("MainActivity", "Mini-jeu perdu...");
            endGame();
        }
    }

    //Lancement du minijeu suivant
    public void nextMiniGame(int nbMinijeu)
    {
        Log.i("MainActivity", "Lancement du mini-jeu suivant !");
        //Incrément du compteur de minijeu
        nbMinijeu++;
        Intent intent = new Intent(this, ordreJeux[nbMinijeu]);
        startActivityForResult(intent, nbMinijeu);
    }

    public void endGame()
    {
        Log.i("MainActivity", "Debut EndGame");
        //Afficher Score
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("score",score);
        startActivity(intent);
    }
}
