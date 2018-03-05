package uqac.dim.houla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchGame(View v){

    }

    public void launchOption(View v){
        Intent intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
    }
}
