package ceri.androiddamepic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

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
    boolean majGraph = false;
    IPlayer playerss[] = null;
    Partie partie = null;
    Plateau plateau = null;
    LinearLayout linearCards = null;
    LinearLayout surface[] = null;
    RelativeLayout re = null;
    CarteUI[] mainJoueurUI = null, cartePlateauUI;
    Activity tapisJeuActivity = null;
    Button valdButton = null;
    GameThread mThread = new GameThread();
    ArrayList<Carte> mainJoueurT;
    int memPlace = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapis_jeu);

        initPlateau();

        mThread.start();

    }

    public void refreshScreenEndTurn(){

        miseAJourTapis();
        try {
            re.postInvalidate();
        }catch (Exception e){
        }


        do{
            try{
                mThread.sleep(500);
            }catch (Exception e){

            }

        }while(!validGranted);
        validGranted = false;

        reinitCards();
        try {
            re.postInvalidate();
        }catch (Exception e){
        }
    }

    void initPlateau() {
        initValidateButton();
        linearCards = (LinearLayout) findViewById(R.id.linear);
        //grid = (GridLayout) findViewById(R.id.gridPlat);

        //grid.addView(new CarteUI(this), new GridLayout.LayoutParams(1, 1));

       // re = (RelativeLayout) findViewById(R.id.relativeL);
        linearCards.setPadding(0, 15, 0, 0);
        mainJoueurUI = new CarteUI[13];

        surface = new LinearLayout[4];
        cartePlateauUI= new CarteUI[4];

        re = (RelativeLayout) findViewById(R.id.relativeL);


        //Main joueur
        for(int i = 0; i < 13; ++i) {
            mainJoueurUI[i] = new CarteUI(this);
            linearCards.addView(mainJoueurUI[i]);
        }

        surface[0] = (LinearLayout) findViewById(R.id.carteJ1);
        surface[1] = (LinearLayout) findViewById(R.id.carteJ2);
        surface[2] = (LinearLayout) findViewById(R.id.carteJ3);
        surface[3] = (LinearLayout) findViewById(R.id.carteJ4);

        for(int i = 0; i < 4; ++i) {
            cartePlateauUI[i] = new CarteUI(this);
            surface[i].addView(cartePlateauUI[i]);
        }
        //cartePlateauUI[0] = new CarteUI(this);
        //surface[0].addView(cartePlateauUI[0]);
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
        partie.linkActivity(this);


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
        boolean finTour = false;
        Carte[] carteP = plateau.getPlayCards();
        //Canvas canvas = new Canvas();
        if(memPlace == -1)memPlace = carteP.length;
        else finTour = true;
        for(int i = 0 ; i < carteP.length; ++i){
            int current;
            if(carteP.length == 1 || carteP.length == 3)
                current= (i+carteP.length)%4;
            else if(carteP.length == 4 ) {
                /*if(finTour) {
                    if (memPlace == 1 || memPlace == 3)
                        current = (i + memPlace) % 4;
                    else if(memPlace == 4){
                        current = (i + 2) % 4;
                    }
                    else current = i;

                    memPlace =-1;
                }
                else{*/
                    current = (i + 2) % 4;
               // }
            }
            else
                current = i;
            if(carteP[i] == null)continue;
            cartePlateauUI[current].setParam(carteP[i].getColor().getValue(), carteP[i].getValue());

            System.out.println(carteP[i].getColor().getValue() + "deus ex" + carteP[i].getValue());

            cartePlateauUI[current].confImage();
               // surface[0].setBackground(cartePlateauUI[i].getDrawable());
             //surface[0].setBackground(cartePlateauUI[i].getDrawable());
        }
    }

    public void afficherCarteJoueur(ArrayList<Carte> mainJoueur) {
        majGraph = false;
        reinitCards();
        miseAJourTapis();

        int i = 0;

        for (Carte c : mainJoueur) {
            int couleur = c.getColor().getValue();
            int val = c.getValue();
            //System.out.print(couleur + "coul\n");
            //System.out.print(val + "val\n");
            if (i >= mainJoueurUI.length || mainJoueurUI[i] == null) {
                System.out.print(i + "erreur ajout carte\n");
                return;
            }
            mainJoueurUI[i].setParam(couleur, val);
           mainJoueurUI[i].confImage();
            //System.out.print(i + "affichCarte===============================================\n");
            i++;
        }
        try {
            re.postInvalidate();
        }catch (Exception e){
            re.postInvalidate();
        }
    }

    //fonction appelé par interactJoueur via le mThread
    public int[] exchangeCardsPlayer(final ArrayList<Carte> mainJoueur) {
        mainJoueurT = mainJoueur;
        afficherCarteJoueur(mainJoueur);
        majGraph = true;
        try {
            valdButton.setVisibility(View.VISIBLE);
        }catch (Exception e){
            valdButton.setVisibility(View.VISIBLE);
        }
        int it[] = null;
        do {
            it = grantedExchange();
            try {
                mThread.sleep(500);//attendre pour eviter trop de ressources pompées
            } catch (InterruptedException e) {}

        }
        while(null == it);
//        valdButton.setVisibility(View.GONE);
        for(int i=0;i<3;++i)
            System.out.print(it[i]+"\n");

        afficherCarteJoueur(mainJoueur);
        return it;
    }

    public int playCard(final ArrayList<Carte> mainJoueur){
        int it = -1;
        afficherCarteJoueur(mainJoueur);

        Carte[] cartt =Arrays.copyOf(mainJoueur.toArray(), mainJoueur.toArray().length, Carte[].class);
        Carte[] cart = plateau.playableCards( cartt );
        setCardPlayable(cart);


        do {
            it = grantedExchangePlay();
            try {
                mThread.sleep(500);//attendre pour eviter trop de ressources consommées
            } catch (InterruptedException e) {}

        }
        while(-1 == it);
        afficherCarteJoueur(mainJoueur);

        System.out.println("Carteeee" + it);
        return it;
    }

    void setCardPlayable(Carte[] cart){
        boolean notPlayable = true;
        for(CarteUI c: mainJoueurUI){
            notPlayable = true;
            for(Carte d : cart){
                if(c.isMatches(d)){
                    notPlayable = false;
                    break;
                }
            }
            if(notPlayable) c.setActive(false);
        }
    }

    public int grantedExchangePlay(){
        int mem = -1;
        int i = 0, cmpt = 0;

        if(!validGranted ){//bouton valider a été pressé
            return -1;
        }
        validGranted = false;
        for(CarteUI c: mainJoueurUI){
            if(c.isSelected()){
                if(i >= 1)return -1;//erreur plus de 1 carte selectionné
                mem = cmpt;
                i++;
                System.out.println("mem"+mem);
            }
            cmpt++;
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
            mainJoueurUI[i].setActive(true);
        }
        for(int i = 0; i < cartePlateauUI.length ;++i){
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


