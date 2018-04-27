package uqac.dim.houla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static uqac.dim.houla.ShowBitmap.decodeSampledBitmapFromResource;

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
        String message = getString(R.string.score_texte_final1) + " " + String.valueOf(score) + " " + getString(R.string.score_texte_final2);
        textView.setText(message);

        //On récupère l'imageView
        ImageView fond = findViewById(R.id.fond_score);

        if (score < 4)
        {
            //On affiche la vue de perte
            fond.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.score_perdu_background, 500, 500));
        }
        else
        {
            //On affiche la vue de victoire
            fond.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.score_gagne_background, 500, 500));
        }
    }
}

