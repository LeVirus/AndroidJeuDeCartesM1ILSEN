package ceri.androiddamepic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;

import Game.Carte;
import Game.IPlayer;
import Game.Partie;
import Game.Plateau;
import Game.structGameStat;
import Player.IAPlayer;
import Player.InteractePlayer;

public class TapisJeu extends AppCompatActivity {

    boolean partieLance = false;
    IPlayer playerss[] = null;
    Partie partie = null;
    Plateau plateau = null;
    LinearLayout ddd=null;
    CarteUI[] mainJoueurUI=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapis_jeu);


        initPlateau();
        for(int i = 0;i<13;++i)
        if(mainJoueurUI[i] == null){
            mainJoueurUI[i] = new CarteUI(this);
            mainJoueurUI[i].setParam(0, 2);
            mainJoueurUI[i].confImage();
            ddd.addView(mainJoueurUI[i]);
        }

       // ddd.removeAllViews();
        for(int i = 0;i<13;++i){

                mainJoueurUI[i].setParam(3, 13);
                mainJoueurUI[i].confImage();
                //ddd.addView(mainJoueurUI[i]);
            }




        //lancerPartie();


    }

    void lancerPartie(){
        if( partieLance == true )return;

        initPlateau();
        partieLance = true;
        playerss = new IPlayer[4];
        for( int i = 0; i < playerss.length; i++ )
        {
            playerss[i] = new IAPlayer("J" + i);
        }

         playerss[3] = creerJoueurInteract("joueur1");
        partie = new Partie(playerss);
        plateau = partie.getPlateau();
        structGameStat str = partie.newGame();
        partieLance = false;
    }

    void initPlateau(){
        //CarteUI ca = new CarteUI(this);
        ddd = (LinearLayout) findViewById(R.id.linear);
        ddd .setPadding(0, 15, 0, 0);
        mainJoueurUI = new CarteUI[13];

        //ddd.addView(ca);
    }

    IPlayer creerJoueurInteract(String idd){
        InteractePlayer player = new InteractePlayer();
        player.linkActivity(this);
        return player;
    }

    public int[] exchangeCardsPlayer( ArrayList<Carte> mainJoueur){
        int c[] = new int[]{1,2,3};
        /*for(int i = 0;i<mainJoueur.size();++i){
            mainJoueur.get(i);
        }*/
        afficherCarteJoueur(mainJoueur);
return c;
    }

    public void afficherCarteJoueur(ArrayList<Carte> mainJoueur){

        int i = 0;
        for(Carte c :mainJoueur){
            int couleur = c.getColor().getValue();
            int val = c.getValue();
           if(mainJoueurUI[i] == null){
               mainJoueurUI[i] = new CarteUI(this);
           }
               mainJoueurUI[i].setParam(couleur, val);
            mainJoueurUI[i].confImage();
            i++;
        }
    }

    public void miseAJourTapis(){

    }
}
