package uqac.dim.houla;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import uqac.dim.houla.menu_options.OptionActivity;
import uqac.dim.houla.reveil.GameView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);


    }

    public void launchGame(View v){

    }

    //Au clic sur le bouton de choix de jeu
    public void choisirJeu(View v)
    {

        Intent intent;
        try {
            switch (v.getId()) {

                case R.id.baston:

                    break;
                case R.id.biere:

                    break;
                case R.id.bourre:

                    break;
                case R.id.cash:

                    break;
                case R.id.couette:

                    break;
                case R.id.course:
                    Log.i("MainActivity", "Clic sur course");
                    intent = new Intent(this, uqac.dim.houla.course.courseActivity.class);
                    startActivityForResult(intent,1); // Le request code est le numero du minijeu dans le futur tableau
                    break;
                case R.id.motivation:

                    break;
                case R.id.pote:
                    Log.i("MainActivity", "Clic sur pote");
                    intent = new Intent(this, uqac.dim.houla.pote.GameView.class);
                    startActivity(intent);
                    break;
                case R.id.redac:

                    break;
                case R.id.reveil:
                    Log.i("MainActivity", "Clic sur réveil");
                    intent = new Intent(this, GameView.class);
                    startActivity(intent);
                    break;
                case R.id.sleep:

                    break;
                case R.id.vaisselle:

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void launchOption(View v){
        Intent intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean result = true;
        if(requestCode == 1 && resultCode == RESULT_OK){
            result = data.getBooleanExtra("win",true);
        }

        if (result){
            nextMiniGame(requestCode);
        } else {
            endGame();
        }
    }

    public void nextMiniGame(int nbMinijeu){
        //Prochain mini jeu
        Log.i("MainActivity", "Lancement de réveil");
        Intent intent = new Intent(this, GameView.class);
        startActivity(intent);

    }

    public void endGame(){
        //Afficher Score
    }
}
