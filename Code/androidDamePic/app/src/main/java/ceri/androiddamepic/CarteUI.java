package ceri.androiddamepic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageButton;

import Game.Carte;

/**
 * Classe représentant une carte et permettant son affichage sur android.
 * Created by cyril on 10/04/16.
 */
public class CarteUI extends ImageButton {
    private int couleur;
    private int valeur;
    public static boolean sonactif;

    //private int position;
    private boolean actif = false;
    static Bitmap bitmapCarte = null;
    //public Canvas canvas = null; // tmp
    //Paint paint = null;
    boolean selected = false;
    CarteUI memCart = this;
    TapisJeu tapisJeu = null;


    /**
     * Constructeur de la classe.
     * Récupération de la texture(commune a toute les cartes UI).
     *
     */
    public CarteUI(Context c){

        super(c);
        bitmapCarte = BitmapFactory.decodeResource(getResources(), R.drawable.textcarte);
        setOnClickListener(new OnClickListener() {
            /**
             * Création du recepteur d'évenement du bouton.
             * Sert a selectionner ou déselectionner le bouton.
             * Un filtre de transparence sera appliqué au bouton selectionné.
             */
            @Override
            public void onClick(View v) {
                try {
                    if (actif == false) return;
                    if(sonactif)soundPlayer.playSong(R.raw.carte_play, tapisJeu);
                    selected = !selected;
                    if (selected) {//modifier la transparence
                        tapisJeu.deselectCartesJeu();
                        selected = true;
                        memCart.getBackground().setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.DST_IN);

                    } else {
                        memCart.getBackground().setColorFilter(Color.argb(255, 255, 255, 255), PorterDuff.Mode.DST_IN);

                        //int iconColor = android.graphics.Color.RED;
                        //memCart.getBackground().setColorFilter(iconColor, PorterDuff.Mode.MULTIPLY );
                    }
                }catch (Exception e){}
            }
        });

    }

    public void linkTapisJeu(TapisJeu tj){
        tapisJeu = tj;
    }

    /**
     * Constructeur de Carte UI avec initialisation des paramètres.
     */
    public CarteUI(Context c, int coul, int val){
        super(c);
        setParam(coul, val);
    }

    /**
     * Comparaison de 2 cartes.
     * La carteUI est comparé à celle envoyé en paramètre(Game)
     * Renvoie true si les 2 cartes sont identiques false sinon.
     */
    public boolean isMatches(Carte c){
        return c.getColor().getValue() == couleur && c.getValue() == valeur;
    }

    /**
     * Renvoie true si la carte est selectionnée false sinon.
     */
    public boolean isSelected(){
        if( actif == false)return false;
        return selected;
    }

    /**
     * Modifie le bouléen qui détermine si la carte est sélectionnée.
     *
     */
    public void setSelected(boolean b){
        selected = b;
    }


    public void unselectJeu(){
        if( actif == false)return;
        selected = false;
        try {
            this.getBackground().setColorFilter(Color.argb(255, 255, 255, 255), PorterDuff.Mode.DST_IN);
        }catch (Exception e){}
    }

    /**
     * Modifications des paramètres valeurs de la cartes.
     */
    public void setParam(int coul, int val){
        couleur = coul;
        valeur = val;
    }

    /**
     * Modifie le bouléen qui détermine si la carte est active.
     */
    public void setActive(boolean b){
        actif = b;
    }

    /**
     * Efface l'arriere plan de la carte et la désactive.
     */
    public void erasePic(){
        actif = false;
        try {
            this.setBackground(null);
        }catch (Exception e){
        }

    }

    /**
     * Modifie l'arrière plan de la carte en fonction des paramètres valeurs de celle ci.
     * Le bitmap représentant l'image est renvoyé.
     */
    //"coeur", 0,"trefle", 1, "carreau", 2, "pique", 3
    void confImage(){
        /*actif = true;
        //init des variables de texture
        int posY=2, posX=1, tailleX =85, tailleY = 128, distX = 91, distY = 134;
        //détermination de la position et de la taille du sprite a partir de la texture
        switch(couleur){
            case 0://coeur
                //pas de modif a faire
                break;
            case 1://trefle
                posY += 2 * distY;
                break;
            case 2://carreau
                posY += 1 * distY;

                break;
            case 3://pique
                posY += 3 * distY;

                break;
        }

      if(valeur == 14){
          posX += distX;
      }
      else{
          posX += distX * valeur;
      }

        //application du bitmap ainsi récupéré.
        Bitmap b = bitmapCarte.createBitmap(bitmapCarte, posX, posY, tailleX, tailleY);

        Bitmap mutableBitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), mutableBitmap);
        try {

            //this.setBackground(drawable);
        }
        catch (Exception e){
        }*/
        int mem = 0;


        if(couleur == 0){//coeur
            switch(valeur){
                case 2:
                    mem = R.drawable.deuxc;
                    break;
                case 3:
                    mem = R.drawable.troisc;
                    break;
                case 4:
                    mem = R.drawable.quatrec;
                    break;
                case 5:
                    mem = R.drawable.cinqc;
                    break;
                case 6:
                    mem = R.drawable.sixc;
                    break;
                case 7:
                    mem = R.drawable.septc;
                    break;
                case 8:
                    mem = R.drawable.huitc;
                    break;
                case 9:
                    mem = R.drawable.neufc;
                    break;
                case 10:
                    mem = R.drawable.dixc;
                    break;
                case 11:
                    mem = R.drawable.valetc;
                    break;
                case 12:
                    mem = R.drawable.reinec;
                    break;
                case 13:
                    mem = R.drawable.roic;
                    break;
                case 14:
                    mem = R.drawable.asc;
                    break;

            }
        }
        else if(couleur == 1){//trefle
            switch(valeur){
                case 2:
                    mem = R.drawable.deuxt;
                    break;
                case 3:
                    mem = R.drawable.troist;
                    break;
                case 4:
                    mem = R.drawable.quatret;
                    break;
                case 5:
                    mem = R.drawable.cinqt;
                    break;
                case 6:
                    mem = R.drawable.sixt;
                    break;
                case 7:
                    mem = R.drawable.septt;
                    break;
                case 8:
                    mem = R.drawable.huitt;
                    break;
                case 9:
                    mem = R.drawable.neuft;
                    break;
                case 10:
                    mem = R.drawable.dixt;
                    break;
                case 11:
                    mem = R.drawable.valett;
                    break;
                case 12:
                    mem = R.drawable.reinet;
                    break;
                case 13:
                    mem = R.drawable.roit;
                    break;
                case 14:
                    mem = R.drawable.ast;
                    break;

            }
        }
        else if(couleur == 2){//carreau
            switch(valeur){
                case 2:
                    mem = R.drawable.deuxcar;
                    break;
                case 3:
                    mem = R.drawable.troiscar;
                    break;
                case 4:
                    mem = R.drawable.quatrecar;
                    break;
                case 5:
                    mem = R.drawable.cinqcar;
                    break;
                case 6:
                    mem = R.drawable.sixcar;
                    break;
                case 7:
                    mem = R.drawable.septcar;
                    break;
                case 8:
                    mem = R.drawable.huitcar;
                    break;
                case 9:
                    mem = R.drawable.neufcar;
                    break;
                case 10:
                    mem = R.drawable.dixcar;
                    break;
                case 11:
                    mem = R.drawable.valetcar;
                    break;
                case 12:
                    mem = R.drawable.reinecar;
                    break;
                case 13:
                    mem = R.drawable.roicar;
                    break;
                case 14:
                    mem = R.drawable.ascar;
                    break;

            }
        }
        else if(couleur == 3){//pique
            switch(valeur){
                case 2:
                    mem = R.drawable.deuxp;
                    break;
                case 3:
                    mem = R.drawable.troisp;
                    break;
                case 4:
                    mem = R.drawable.quatrep;
                    break;
                case 5:
                    mem = R.drawable.cinqp;
                    break;
                case 6:
                    mem = R.drawable.sixp;
                    break;
                case 7:
                    mem = R.drawable.septp;
                    break;
                case 8:
                    mem = R.drawable.huitp;
                    break;
                case 9:
                    mem = R.drawable.neufp;
                    break;
                case 10:
                    mem = R.drawable.dixp;
                    break;
                case 11:
                    mem = R.drawable.valetp;
                    break;
                case 12:
                    mem = R.drawable.reinep;
                    break;
                case 13:
                    mem = R.drawable.roip;
                    break;
                case 14:
                    mem = R.drawable.asp;
                    break;

            }
        }


        Bitmap bm = BitmapFactory.decodeResource(getResources(), mem);

        Bitmap mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), mutableBitmap);
        try {

            this.setBackground(drawable);
        }
        catch (Exception e){
        }

       // return drawable;
        //return this.getDrawable();


    }

}
