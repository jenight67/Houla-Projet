package uqac.dim.houla;

import android.content.Context;

import java.util.Random;

/**
 * Created by User on 17/03/2018.
 */

public class Constant {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static Context CURRENT_CONTEXT;

    public static int randomInt(int borneInferieure, int borneSuperieure)
    {
        Random r = new Random();
        int res = r.nextInt(borneSuperieure - borneInferieure) + borneInferieure;
        return res;
    }
}
