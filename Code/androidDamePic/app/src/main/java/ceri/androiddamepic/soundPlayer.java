package ceri.androiddamepic;

import android.app.Activity;
import android.media.MediaPlayer;

/**
 * Created by Mad on 22/05/2016.
 */
public class soundPlayer
{
    private static MediaPlayer mPlayer = null;

    /*  Permet de jouer un son simple une fois sans boucler
    *   Activity acti : activitéen cours d'execution
    *   int resId : idantifiant de la musique à jouer (ex: R.raw.carte_play)
     */

    public static void playSong(int resId, Activity acti)
    {
        // Suppresion du précédant MediaPleyer
        if(mPlayer != null){mPlayer.stop();mPlayer.release();}

        mPlayer = MediaPlayer.create(acti, resId);
        mPlayer.start();

    }

}
