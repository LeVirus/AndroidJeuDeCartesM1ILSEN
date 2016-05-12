package Player;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Game.Carte;
import Game.IPlayer;
import ceri.androiddamepic.TapisJeu;
//import mad.damedepique.GameActivity;

/*  Classe s'ocupent de l'interaction entre l'IHM et la Partie

 */
public class UIPlayer extends abstractPlayer implements IPlayer
{
    protected Context context;
    protected TapisJeu gameActivity;
    protected Lock lock;

    public UIPlayer(TapisJeu gameActivity)
    {
        carteEnMain = new ArrayList<Carte>();
        points = 0;
        this.gameActivity = gameActivity;
        this.lock = new ReentrantLock();
    }


    @Override
    public Carte playCard()
    {
        //On rafraichie le plateau de jeu
        //gameActivity.refreshScreen();
        try {
            gameActivity.writeToast("A votre tour");
            //Toast.makeText(context, "A votre tour.", Toast.LENGTH_LONG).show();
        }catch (Exception e){System.out.println( e.toString() );}
        lock.lock();
        int c = gameActivity.playCard(carteEnMain);
        Carte r = carteEnMain.get(c);
        carteEnMain.remove(c);

        //gameActivity.setnbSelectableCard(1);
        lock.lock();lock.unlock();
        //TODO: retourner la carte chosie.
        return r;
       // return null;
    }

    @Override
    public Carte[] exchangeCards()
    {

        Log.d("TapisJeu", "C: Connecting...");

        try {
            gameActivity.writeToast("Choisisez 3 cartes à échanger");
        }catch (Exception e){System.out.println( e.toString() );}
        Lock lock = new ReentrantLock();
        lock.lock();

        Carte[] r = new Carte[3];
        int t[] = gameActivity.exchangeCardsPlayer(carteEnMain);
        for(int i=0;i<3;i++)
        {
            r[i]=carteEnMain.get(t[i]);
        }
        for(int i=2;i>=0;i--)
        {
            carteEnMain.remove(t[i]);
        }

        lock.lock();lock.unlock();
        //TODO: retourner les cartes chosie.
        return r;
    }

    /*@Override
    public void setBoard(IPlateau p)
    {
        plateau = p;
        gameActivity.setPlateau(p);
    }*/

    //Permet de débloquer le thread courant
    public void unlockThread()
    {
        lock.unlock();
    }

    //Retourne la main du joueur
    public ArrayList<Carte> getHand(){return carteEnMain;}

    public void linkActivity(TapisJeu tapisAndroid){
        gameActivity = tapisAndroid;
    }
}
