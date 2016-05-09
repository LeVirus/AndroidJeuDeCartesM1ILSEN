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

    /**
     * Fonction appelé en fin de tours(les 4 joueurs ont joués).
     * Attend que le joueur est préssé valider.
     * A MODIFIER AJOUTER  RETOUR UTILISAZTEUR SIGNALANT LA FON DU TOUR.
     */
    public void refreshScreenEndTurn(){

        miseAJourTapis(true);
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

    /**
     * Initialisation du contexte graphique.
     * Récupération des éléments graphique provenant du fichier XML et
     * initalisation des classes.
     */
    void initPlateau() {
        memPlace = -1;
        initValidateButton();
        linearCards = (LinearLayout) findViewById(R.id.linear);

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
        //Cartes jouées

        tapisJeuActivity = this;
    }

    /**
     * Récupération du bouton valider en provenance du fichier XML.
     */
    void initValidateButton(){
        valdButton = (Button) findViewById(R.id.buttonVal);
        valdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validGranted = true;
            }
        });

    }

    /**
     * Création du contexte de jeu.
     * Initialisations des classes contenues dans Game et dans Player.
     */
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


    /**
     * Initialisation du joueur d'interaction avec l'UI.
     */
    IPlayer creerJoueurInteract(String idd) {
        InteractePlayer player = new InteractePlayer();
        player.linkActivity(this);
        return player;
    }

    /**
     *Fonction d'affichage des cartes jouées sur le plateau par les autres joueurs.
     */
    public void miseAJourTapis(boolean mem) {
        reinitCardsPlateau();
        boolean finTour = false;

        System.out.println( "tout ex " + memPlace);
        //récup des cartes jouées du plateau
        Carte[] carteP = plateau.getPlayCards();

        if(mem) {
            //mémorisation de la taille du tableau pour l'affichage des cartes à la fin du tour
            if (memPlace == -1) memPlace = carteP.length;
            else {
                finTour = true;
                System.out.println("fintour");
            }
        }
        else{
            memPlace = -1;
        }

        System.out.println( "debut ex " + memPlace);
        if(carteP.length == 0)return;

        int current = 2;
        if(carteP.length == 1)current = 1;
        else if(carteP.length == 2)current = 0;
        else if(carteP.length == 3)current = 3;
        else if(carteP.length == 4){
            if(memPlace == 1){current = 1;  System.out.println( "fin ex " + memPlace); }
            else if(memPlace == 2){current = 0; System.out.println( "fin ex " + memPlace); }
            else if(memPlace == 3){current = 3; System.out.println( "fin ex " + memPlace); }
            else if(memPlace == 4){current = 3; System.out.println( "fin ex " + memPlace); }
            else if(memPlace == 0){current = 2; System.out.println( "fin ex " + memPlace); }
        }

        //affichage des cartes, leurs position est déterminé en fonction
        //de la taille du tableau retourné
        for(int i = 0 ; i < carteP.length; ++i){



            //mise a jour de l'apparence de la carte
            cartePlateauUI[current].setParam(carteP[i].getColor().getValue(), carteP[i].getValue());

            //application de la mise a jour
            cartePlateauUI[current].confImage();
            current++;
            if(current >= 4)current = 0;
        }
        if(finTour){
            memPlace = -1;

        }
    }

    /**
     * Affichage graphique de la main du joueur.
     * Configuration des paramètres des CarteUIs et affichages dans le layout de la main du joueur.
     */
    public void afficherCarteJoueur(ArrayList<Carte> mainJoueur) {
        majGraph = false;
        reinitCards();

        int i = 0;

        for (Carte c : mainJoueur) {
            //récup des valeur dans Game
            int couleur = c.getColor().getValue();
            int val = c.getValue();
            if (i >= mainJoueurUI.length || mainJoueurUI[i] == null) {
                System.out.print(i + "erreur ajout carte\n");
                return;
            }
            //application de ces valeurs coté UI
            mainJoueurUI[i].setParam(couleur, val);
           mainJoueurUI[i].confImage();
            //System.out.print(i + "affichCarte===============================================\n");
            i++;
        }
        try {
            re.postInvalidate();
        }catch (Exception e){
        }
    }

    //fonction appelé par interactJoueur via le mThread
    /**
     *  Application de l'échange des cartes coté graphique
     */
    public int[] exchangeCardsPlayer(final ArrayList<Carte> mainJoueur) {
        mainJoueurT = mainJoueur;
        //maj cartes joueur
        afficherCarteJoueur(mainJoueur);
        miseAJourTapis(false);
        majGraph = true;
        try {
            valdButton.setVisibility(View.VISIBLE);
        }catch (Exception e){
        }
        int it[] = null;
        do {//tant que les cartes n'ont pas été selectionnées et validées
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

    /**
     * application jouer carte point de vue graphique.
     */
    public int playCard(final ArrayList<Carte> mainJoueur){
        int it = -1;
        afficherCarteJoueur(mainJoueur);
        miseAJourTapis(true);


        Carte[] cartt =Arrays.copyOf(mainJoueur.toArray(), mainJoueur.toArray().length, Carte[].class);
        Carte[] cart = plateau.playableCards( cartt );
        setCardPlayable(cart);


        do {//tant que la carte n'a pas été jouée
            it = grantedExchangePlay();
            try {
                mThread.sleep(500);//attendre pour eviter trop de ressources consommées
            } catch (InterruptedException e) {}

        }
        while(-1 == it);
        afficherCarteJoueur(mainJoueur);

        return it;
    }

    /**
     *   Détermine a partir du package Game quelles cartes sont jouables et fais les modifications en
     *   conséquences dans la main du joueur.
     */
    void setCardPlayable(Carte[] cart){
        boolean notPlayable = true;
        for(CarteUI c: mainJoueurUI){
            notPlayable = true;
            //parcour de la liste de carte provenant de game
            for(Carte d : cart){
                if(c.isMatches(d)){
                    notPlayable = false;
                    break;
                }
            }
            if(notPlayable) c.setActive(false);
        }
    }

    /**
     * Fonction de validation de la phase JOUER.
     * Vérifie si la carte a bien été selectionné après que le bouton valider es été pressé.
     */
    public int grantedExchangePlay(){
        int mem = -1;
        int i = 0, cmpt = 0;

        if(!validGranted ){//bouton valider n'a pas été pressé
            return -1;
        }
        validGranted = false;
        //parcour des cartes du joueur
        for(CarteUI c: mainJoueurUI){
            if(c.isSelected()){
                if(i >= 1)return -1;//erreur plus de 1 carte selectionné
                mem = cmpt;
                i++;
            }
            cmpt++;
        }
        return mem;
    }

    /**
     * Fonction de validation de la phaase jouer.
     * Vérifie si les cartes ont bien été selectionnées après que le bouton valider est été pressé.
     * A CORRIGER POUR LES TOURS AVEC 2 ET UNE CARTE A ECHANGER
     */
    public int[] grantedExchange(){
        int mem[] = new int[3];
        int cmpt = 0, i = 0;

        if(!validGranted ){
            return null;
        }
        validGranted = false;
        //parcour des cartes du joueur
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

    /**
     * Réinitialisation de la main du joueur.
     * Déselection des cartes et effacements des images associées aux cartes précédemment affichées
     */
    void reinitCards(){
        for(int i = 0; i < mainJoueurUI.length ;++i){
            mainJoueurUI[ i ].setSelected( false );
            mainJoueurUI[i ].erasePic();
            mainJoueurUI[i].setActive(true);
        }

    }

    /**
     * Réinitialisation des cartes du plateau.
     * Effacements des images associées aux cartes précédemment affichées.
     */
    void reinitCardsPlateau(){

        for(int i = 0; i < cartePlateauUI.length ;++i){
            cartePlateauUI[i ].erasePic();
        }
    }


    /**
     * Lancement du thread Jeu
     */
    private class GameThread extends Thread {
        @Override
        public void run() {
            lancerPartie();
        }
    }

}


