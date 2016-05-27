package ceri.androiddamepic;

import android.app.Activity;
import android.media.MediaPlayer;
import java.util.ArrayList;


/**
 * Created by Mad on 22/05/2016.
 */
public class soundPlayer
{
    private static MediaPlayer mPlayerr = null;
    protected static ArrayList<MediaPlayer> mPlayer = new ArrayList<MediaPlayer>();


    /*  Permet de jouer un son simple une fois sans boucler
    *   Activity acti : activitéen cours d'execution
    *   int resId : idantifiant de la musique à jouer (ex: R.raw.carte_play)
     */

    public static void playSong(int resId, Activity acti)
    {
        // Suppresion du précédant MediaPleyer
        if(mPlayerr != null){mPlayerr.stop();mPlayerr.release();}

        mPlayerr = MediaPlayer.create(acti, resId);
        mPlayerr.start();

    }


    /*  Permet de jouer des son et musique
    *   Activity acti : activitéen cours d'execution
    *   int resId : idantifiant de la musique à jouer (ex: R.raw.carte_play)
     */
    public static void playSongOnce(int resId, Activity acti)
    {
        MediaPlayer mp = MediaPlayer.create(acti, resId);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){@Override public void onCompletion(MediaPlayer mp){mPlayer.remove(mp);}});
        mPlayer.add(mp);
    }

    public static void playSongLoop(int resId, Activity acti)
    {
        MediaPlayer mp = MediaPlayer.create(acti, resId);
        mp.start();
        mp.setLooping(true);
        mPlayer.add(mp);
    }

    public static void killAllSong()
    {
        for(MediaPlayer mp : mPlayer)
        {
            mp.stop();
            mp.release();
            mPlayer.remove(mp);
        }
    }

}
