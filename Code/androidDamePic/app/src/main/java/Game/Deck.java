package Game;

import java.util.Arrays;
import java.util.Collections;

public class Deck
{
	//Cr�er un paquet de 52 cartes m�langer
		static Carte[] createDeck()
		{
			Carte[] r = new Carte[52];int i=0;
			color cc[]={color.coeur, color.pique, color.carreau, color.trefle};
			for(color c : cc)
				for(int j=2;j<15;j++)
				{
					r[i]=new Carte(j,c);
					i++;
				}
			Collections.shuffle(Arrays.asList(r));
			return r;
		}
		
		//Retourne 4 mains de 13 cartes
		static Carte[][] createFourPlayerHand()
		{
			Carte[] r[] = new Carte[4][13], d=Deck.createDeck();
			for(int i=0;i<52;i++)	r[i/13][i%13]=d[i];
			return r;
		}
		
		//Retourn Les 13 cartes d'une couleur.
		static Carte[] creatColoredCardSet(color c)
		{
			Carte[] r = new Carte[13];
			for(int i=0;i<13;i++)	r[i] = new Carte(i,c);
			return r;
		}



}
