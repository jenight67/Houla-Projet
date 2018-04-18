package uqac.dim.houla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int score = getIntent().getIntExtra("score",0);

        TextView textView = findViewById(R.id.score);
        textView.setText(String.valueOf(score));


        setContentView(R.layout.activity_score);

    }
}
