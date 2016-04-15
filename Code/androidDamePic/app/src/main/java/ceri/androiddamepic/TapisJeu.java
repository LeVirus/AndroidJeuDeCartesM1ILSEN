package ceri.androiddamepic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    boolean validGranted = false;
    IPlayer playerss[] = null;
    Partie partie = null;
    Plateau plateau = null;
    LinearLayout linearCards = null;
    RelativeLayout re = null;
    RelativeLayout surface[] = null;
    CarteUI[] mainJoueurUI = null, cartePlateauUI;
    Activity tapisJeuActivity = null;
    Button valdButton = null;
    GameThread mThread = new GameThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapis_jeu);

        initPlateau();

        mThread.start();

    }

    void initPlateau() {
        initValidateButton();
        linearCards = (LinearLayout) findViewById(R.id.linear);
        //grid = (GridLayout) findViewById(R.id.gridPlat);

        //grid.addView(new CarteUI(this), new GridLayout.LayoutParams(1, 1));

       // re = (RelativeLayout) findViewById(R.id.relativeL);
        linearCards.setPadding(0, 15, 0, 0);
        mainJoueurUI = new CarteUI[13];

        surface = new RelativeLayout[4];
        cartePlateauUI= new CarteUI[4];

        //Main joueur
        for(int i = 0; i < 13; ++i) {
            mainJoueurUI[i] = new CarteUI(this);
            linearCards.addView(mainJoueurUI[i]);
        }

        surface[0] = (RelativeLayout) findViewById(R.id.carteJ1);
        surface[1] = (RelativeLayout) findViewById(R.id.carteJ2);
        surface[2] = (RelativeLayout) findViewById(R.id.carteJ3);
        surface[3] = (RelativeLayout) findViewById(R.id.carteJ4);

        for(int i = 0; i < 4; ++i) {
            cartePlateauUI[i] = new CarteUI(this);
            surface[i].addView(cartePlateauUI[i]);
        }

        //Cartes jouées

        tapisJeuActivity = this;
    }

    void initValidateButton(){
        valdButton = (Button) findViewById(R.id.buttonVal);
        valdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validGranted = true;
            }
        });

    }

    void lancerPartie() {
        if (partieLance == true) return;

        partieLance = true;
        playerss = new IPlayer[4];
        for (int i = 0; i < playerss.length; i++) {
            playerss[i] = new IAPlayer("J" + i);
        }

        playerss[3] = creerJoueurInteract("joueur1");
        partie = new Partie(playerss);
        plateau = partie.getPlateau();
        structGameStat str = partie.newGame();
        partieLance = false;
    }


    IPlayer creerJoueurInteract(String idd) {
        InteractePlayer player = new InteractePlayer();
        player.linkActivity(this);
        return player;
    }

    public void miseAJourTapis() {
        Carte[] carteP = plateau.getPlayCards();
        //Canvas canvas = new Canvas();

        for(int i = 0 ; i < carteP.length; ++i){
            if(carteP[i] == null)continue;
            cartePlateauUI[i].setParam(carteP[i].getColor().getValue(), carteP[i].getValue());
            //BitmapDrawable bm = cartePlateauUI[i].confImage();
        }
    }

    public void afficherCarteJoueur(ArrayList<Carte> mainJoueur) {

        reinitCards();
        miseAJourTapis();

        int i = 0;
        for (Carte c : mainJoueur) {
            int couleur = c.getColor().getValue();
            int val = c.getValue();
            System.out.print(couleur + "coul\n");
            System.out.print(val + "val\n");
            if (i >= mainJoueurUI.length || mainJoueurUI[i] == null) {
                System.out.print(i + "erreur ajout carte\n");
                return;
            }
            mainJoueurUI[i].setParam(couleur, val);
            mainJoueurUI[i].confImage();
            System.out.print(i + "affichCarte===============================================\n");
            i++;
        }
    }

    //fonction appelé par interactJoueur via le mThread
    public int[] exchangeCardsPlayer(final ArrayList<Carte> mainJoueur) {
        reinitCards();
        afficherCarteJoueur(mainJoueur);
        try {
            valdButton.setVisibility(View.VISIBLE);
        }catch (Exception e){
            valdButton.setVisibility(View.VISIBLE);
        }
        int it[] = null;
        do {
            it = grantedExchange();
            try {
                mThread.sleep(1000);//attendre pour eviter trop de ressources pompées
            } catch (InterruptedException e) {}

        }
        while(null == it);
//        valdButton.setVisibility(View.GONE);
        for(int i=0;i<3;++i)
            System.out.print(it[i]+"\n");
        return it;
    }

    public int playCard(final ArrayList<Carte> mainJoueur){
        int it = -1;

        reinitCards();
        afficherCarteJoueur(mainJoueur);
        do {
            it = grantedExchangePlay();
            try {
                mThread.sleep(1000);//attendre pour eviter trop de ressources pompées
            } catch (InterruptedException e) {}

        }
        while(-1 == it);
        return it;
    }

    public int grantedExchangePlay(){
        int mem = -1;
        int i = 0;

        if(!validGranted ){//bouton valider a été pressé
            return -1;
        }
        validGranted = false;
        for(CarteUI c: mainJoueurUI){
            if(c.isSelected()){
                if(i >= 1)return -1;//erreur plus de 1 carte selectionné
                mem = i;
                i++;
            }
        }
        return mem;
    }


    public int[] grantedExchange(){
        int mem[] = new int[3];
        int cmpt = 0, i = 0;

        if(!validGranted ){
            return null;
        }
        validGranted = false;
        for(CarteUI c: mainJoueurUI){
            if(c.isSelected()){
                if(i >= 3)break;//erreur plus de 3 cartes selectionné
                mem[i] = cmpt;
                i++;
            }
            cmpt++;
        }
        if(cmpt == 13 && i == 3)return mem;
        return null;
    }

    void reinitCards(){
        for(int i = 0; i < mainJoueurUI.length ;++i){
            mainJoueurUI[ i ].setSelected( false );
            mainJoueurUI[i ].erasePic();
        }
        for(int i = 0; i < cartePlateauUI.length ;++i){
            mainJoueurUI[ i ].setSelected( false );
            mainJoueurUI[i ].erasePic();
        }
    }



    private class GameThread extends Thread {
        @Override
        public void run() {
            lancerPartie();
        }
    }

}


