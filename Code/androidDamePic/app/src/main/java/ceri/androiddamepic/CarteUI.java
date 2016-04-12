package ceri.androiddamepic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageButton;

/**
 * Created by cyril on 10/04/16.
 */
public class CarteUI extends ImageButton {
    private int couleur;
    private int valeur;
    private int position;
    private boolean actif = false;
    Bitmap bitmapCarte = null;
    public Canvas canvas = null; // tmp
    Paint paint = null;




    public CarteUI(Context c){

        super(c);
        bitmapCarte = BitmapFactory.decodeResource(getResources(), R.drawable.textcarte);
    }

    public void test(){
        Bitmap b = bitmapCarte.createBitmap(bitmapCarte, 0, 0, 32, 32);

        Bitmap mutableBitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        //canvas = new Canvas(mutableBitmap);
        //paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //canvas.clipRect(0, 0, 32, 32);
        //canvas.drawRect(0, 0, 32, 32, paint);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), mutableBitmap);
        this.setBackground(drawable);
    }

    public CarteUI(Context c, int coul, int val){
        super(c);
        setParam(coul, val);
    }

    public void setParam(int coul, int val){
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
        //canvas = new Canvas(mutableBitmap);
        //paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //canvas.clipRect(0, 0, 32, 32);
        //canvas.drawRect(0, 0, 32, 32, paint);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), mutableBitmap);
        this.setBackground(drawable);





    }

}
