package uqac.dim.houla.course;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class courseActivity extends Activity {
    private boolean win;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        uqac.dim.houla.course.GameView gm = new uqac.dim.houla.course.GameView(this);
        setContentView(gm);
        gm.setActivity(this);
        super.onCreate(savedInstanceState);
    }

    public void win(){
        win = true;
        finish();

    }

    public void lose(){
        win = false;
        finish();
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("win",win);
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }
}
