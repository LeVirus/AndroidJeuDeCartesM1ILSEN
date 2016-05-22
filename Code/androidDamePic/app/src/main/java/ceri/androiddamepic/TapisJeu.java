package ceri.androiddamepic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import Game.Carte;
import Game.IPlayer;
import Game.Partie;
import Game.Plateau;
import Game.structGameStat;
import Player.IAPlayer;
import Player.UIPlayer;

public class TapisJeu extends AppCompatActivity {

    boolean partieLance = false;
    boolean validGranted = false;
    boolean majGraph = false;
    boolean phaseJeu = false;
    IPlayer playerss[] = null;
    Partie partie = null;
    Plateau plateau = null;
    LinearLayout linearCards = null;
    FrameLayout surface[] = null;
    TextView labelPoints[] = null;
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
     */
    public void refreshScreenEndTurn(){

        miseAJourTapis(true);
        try {
            valdButton.setText("tour suivant");
        }catch (Exception e) {
        }

        showHandGained();

        mThread.writeLabel();

        //maj graphique
        try{
            re.postInvalidate();
        }catch (Exception e) {
        }

        writeToast("Appuyer sur valider pour continuer");
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
     * Montre le joueur ayant remporté le pli.
     */
    void showHandGained(){
        IPlayer player = plateau.whoIsbig();
        for( int i = 0 ; i < playerss.length ; ++i ){
            if(player == playerss[i]){
                try {
                    int iconColor = Color.RED;
                    cartePlateauUI[i].getBackground().setColorFilter(iconColor, PorterDuff.Mode.MULTIPLY );
                }catch(Exception e){

                }
                break;
            }
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

        surface = new FrameLayout[4];
        cartePlateauUI= new CarteUI[4];
        labelPoints = new TextView[4];

        re = (RelativeLayout) findViewById(R.id.relativeL);


        //Main joueur
        for(int i = 0; i < 13; ++i) {
            mainJoueurUI[i] = new CarteUI(this);
            mainJoueurUI[i].linkTapisJeu(this);
            linearCards.addView(mainJoueurUI[i]);
        }

        labelPoints[0] = (TextView) findViewById(R.id.textView2);
        labelPoints[1] = (TextView) findViewById(R.id.textView);
        labelPoints[2] = (TextView) findViewById(R.id.textView3);
        labelPoints[3] = (TextView) findViewById(R.id.textView4);

        surface[0] = (FrameLayout) findViewById(R.id.carteJ1);
        surface[1] = (FrameLayout) findViewById(R.id.carteJ2);
        surface[2] = (FrameLayout) findViewById(R.id.carteJ3);
        surface[3] = (FrameLayout) findViewById(R.id.carteJ4);
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
        for (int i = 0; i < playerss.length - 1; i++) {
            playerss[i] = new IAPlayer("J" + i);
        }
        playerss[3] = creerJoueurInteract("joueur1");
        partie = new Partie(playerss);
        partie.linkActivity(this);

        plateau = partie.getPlateau();
        structGameStat str = partie.newGame();



        partieLance = false;
    }

   public void afficherRecap(){
        int[] i = new int[4];
        i[0] = playerss[0].getPoint();
        i[1] = playerss[1].getPoint();
        i[2] = playerss[2].getPoint();
        i[3] = playerss[3].getPoint();

        String[] s = new String[4];
        s[0] = "Ouest";
        s[1] = "Nord";
        s[2] = "Est";
        s[3] = "Vous";
        DataResult.setResultes(s, i);

        startActivity(new Intent(TapisJeu.this, RecapActivity.class));
    }


    /**
     * Initialisation du joueur d'interaction avec l'UI.
     */
    IPlayer creerJoueurInteract(String idd) {
        //InteractePlayer player = new InteractePlayer();
        UIPlayer player = new UIPlayer(this);
        //player.linkActivity(this);
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
        try {
            valdButton.setText("valider");
        }catch(Exception e){}

        mainJoueurT = mainJoueur;

        //maj cartes joueur
        afficherCarteJoueur(mainJoueur);
        miseAJourTapis(false);
        majGraph = true;

        for(int i = 0; i < mainJoueurUI.length ;++i){

            mainJoueurUI[i].setActive(true);
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
        try{
            valdButton.setText("valider");
        }catch (Exception e){}
        int it = -1;
        phaseJeu =true;
        afficherCarteJoueur(mainJoueur);
        miseAJourTapis(true);


        Carte[] cartt =Arrays.copyOf(mainJoueur.toArray(), mainJoueur.toArray().length, Carte[].class);
        Carte[] cart = plateau.playableCards( cartt );
        setCardPlayable(cart);

        try {
            //maj graphique
            re.postInvalidate();
        }catch (Exception e){
        }
        do {//tant que la carte n'a pas été jouée
            it = grantedExchangePlay();
            try {
                mThread.sleep(500);//attendre pour eviter trop de ressources consommées
            } catch (InterruptedException e) {}

        }
        while(-1 == it);
        afficherCarteJoueur(mainJoueur);
        phaseJeu =false;
        return it;
    }

    void deselectCartesJeu(){
        if(!phaseJeu)return;
        for(CarteUI c: mainJoueurUI){
            c.unselectJeu();
        }
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
                    c.setActive(true);

                    break;
                }
            }

            if(notPlayable) {
                try {
                    int iconColor = Color.GRAY;
                    c.getBackground().setColorFilter(iconColor, PorterDuff.Mode.MULTIPLY );
                }catch(Exception e){

                }
                c.setActive(false);
            }
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
        if(mem == -1)writeToast("Veuillez selectionner une carte.");
        return mem;
    }

    /**
     * Fonction de validation de la phaase jouer.
     * Vérifie si les cartes ont bien été selectionnées après que le bouton valider est été pressé.
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
        writeToast("Veuillez selectionner 3 cartes.");
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
            mainJoueurUI[i].setActive(false);
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

    public void writeToast(final String s){
        mThread.writeToast(s);
    }
    /**
     * Lancement du thread Jeu
     */
    private class GameThread extends Thread implements Runnable{
        @Override
        public void run() {



            lancerPartie();

        }

        public void writeToast(final String s){
            Log.d("TapisJeu", "C: Connecting...");
            runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(TapisJeu.this, s, Toast.LENGTH_LONG).show();
                }
            });
        }

        public void writeLabel(){
            Log.d("TapisJeu", "C: Connecting...");
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        labelPoints[0].setText("Ouest: " + playerss[0].getPoint() + " pts");
                        labelPoints[1].setText("Nord: " + playerss[1].getPoint() + " pts");
                        labelPoints[2].setText("Est: " + playerss[2].getPoint() + " pts");
                        labelPoints[3].setText("Sud: " + playerss[3].getPoint() + " pts");

                    }catch(Exception e){System.out.println(e.toString());}
                }
            });
        }


    }

}


