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
    //private int position;
    private boolean actif = false;
    static Bitmap bitmapCarte = null;
    //public Canvas canvas = null; // tmp
    //Paint paint = null;
    boolean selected = false;
    CarteUI memCart = this;


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
                if( actif == false)return;
                selected = !selected;
                System.out.print(selected+"select\n");
                if(selected)
                    memCart.getBackground().setColorFilter(Color.argb(100, 255, 255, 255), PorterDuff.Mode.DST_IN);
                else
                    memCart.getBackground().setColorFilter(Color.argb(255, 255, 255, 255), PorterDuff.Mode.DST_IN);
            }
        });

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
    BitmapDrawable confImage(){
        actif = true;
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
            this.setBackground(drawable);
        }
        catch (Exception e){
        }

        return drawable;


    }

}
