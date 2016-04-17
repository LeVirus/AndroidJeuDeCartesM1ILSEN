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




    public CarteUI(Context c){

        super(c);
        bitmapCarte = BitmapFactory.decodeResource(getResources(), R.drawable.textcarte);
        setOnClickListener(new OnClickListener() {
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


    public boolean isMatches(Carte c){
        return c.getColor().getValue() == couleur && c.getValue() == valeur;
    }

    public boolean isSelected(){
        if( actif == false)return false;
        return selected;
    }

    public void setSelected(boolean b){
        selected = b;
    }

    public CarteUI(Context c, int coul, int val){
        super(c);
        setParam(coul, val);
    }

    public void setParam(int coul, int val){
        couleur = coul;
        valeur = val;
    }

    public void setActive(boolean b){
        actif = b;
    }

    public void erasePic(){
        actif = false;
        try {
            this.setBackground(null);
        }catch (Exception e){
        }

    }

    //"coeur", 0,"trefle", 1, "carreau", 2, "pique", 3
    BitmapDrawable confImage(){
        actif = true;
        //init des variables de texture
        int posY=2, posX=1, tailleX =85, tailleY = 128, distX = 91, distY = 134;
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
