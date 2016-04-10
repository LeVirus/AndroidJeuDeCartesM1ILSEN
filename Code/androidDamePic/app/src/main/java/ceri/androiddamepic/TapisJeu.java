package ceri.androiddamepic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapis_jeu);
        //lancerPartie();
        /*ImageButton buttonLaunch = (ImageButton) findViewById(R.id.imageButton);
        buttonLaunch.setVisibility(ImageButton.VISIBLE);
        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }

        });*/

    }

    void lancerPartie(){
        if( partieLance == true )return;
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

    IPlayer creerJoueurInteract(String idd){
        IPlayer player = new InteractePlayer();
        return player;
    }

    public int[] exchangeCardsPlayer( ArrayList<Carte> mainJoueur){
        /*for(int i = 0;i<mainJoueur.size();++i){
            mainJoueur.get(i);
        }*/
        afficherCarteJoueur(mainJoueur);
return null;
    }

    public void afficherCarteJoueur(ArrayList<Carte> mainJoueur){
        for(Carte c :mainJoueur){
            int couleur = c.getColor().getValue();
            int val = c.getValue();
        }
    }

    public void miseAJourTapis(){

    }
}
