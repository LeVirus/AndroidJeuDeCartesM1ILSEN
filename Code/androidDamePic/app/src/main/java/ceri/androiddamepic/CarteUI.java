package ceri.androiddamepic;

import android.content.Context;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.widget.ImageButton;

/**
 * Created by cyril on 10/04/16.
 */
public class CarteUI extends ImageButton{
    private int couleur;
    private int valeur;
    private int position;
    private boolean actif = false;



    public CarteUI(Context c){
        super(c);
    }

    public CarteUI(Context c, int coul, int val, Texture texture_up, Texture texture_down, Texture background){
        super(c);
        couleur = coul;
        valeur = val;
    }

    //"coeur", 0,"trefle", 1, "carreau", 2, "pique", 3
    void confImage(){
        //init des variables de texture
        int posY=1, posX=1, tailleX =85, tailleY = 129, distX = 91, distY = 134;
        switch(couleur){
            case 0://coeur
                //pas de modif a faire
                break;
            case 1://trefle
                posY += 2 * distY;//134 est la distance entre 2 carte en hauteur
                break;
            case 2://carreau
                posY += 1 * distY;

                break;
            case 3://pique
                posY += 3 * distY;

                break;
        }

      if(valeur == 14){
          posX += distX;//91 est la distance entre 2 carte en largeur
      }
      else{
          posX += distX * valeur;
      }
        new DrawableWrapper();
this.setBackground();
    }

}
