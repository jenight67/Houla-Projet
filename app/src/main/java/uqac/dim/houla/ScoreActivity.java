package uqac.dim.houla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //On récupère le score passé dans l'intent
        int score = getIntent().getIntExtra("score",0);
        setContentView(R.layout.activity_score);

        Log.i("score",score + "");
        //On récupère le champ du score et on l'affiche
        TextView textView = findViewById(R.id.score);
        textView.setText("Votre score est de " + String.valueOf(score) + " points");

        //On récupère l'imageView
        ImageView fond = findViewById(R.id.fond_score);

        if (score < 2)
        {
            //On affiche la vue de perte
            fond.setImageResource(R.drawable.score_perdu_background);
        }
        else
        {
            //On affiche la vue de victore
            fond.setImageResource(R.drawable.score_gagne_background);
        }
    }
}
