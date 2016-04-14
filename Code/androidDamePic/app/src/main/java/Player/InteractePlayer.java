package Player;

import java.util.ArrayList;

import Game.Carte;
import Game.IPlayer;
import ceri.androiddamepic.TapisJeu;

public class InteractePlayer extends abstractPlayer implements IPlayer
{
	TapisJeu tapisAndroidP = null;
	public InteractePlayer()
	{
		carteEnMain = new ArrayList<Carte>();
		points = 0;
	}
	
	public Carte playCard()
	{
		/*System.out.println("Cartes en main:");
		afficheCartes(carteEnMain.toArray(new Carte[carteEnMain.size()]));
		System.out.println("\n\nCartes jouable:");
		Carte[] tmp = plateau.playableCards(carteEnMain.toArray(new Carte[carteEnMain.size()]));
		afficheCartes(tmp);*/
		//Scanner in = new Scanner(System.in);
//		int i = in.nextInt();
		//in.close();
		int c = tapisAndroidP.playCard(carteEnMain);
		Carte r = carteEnMain.get(c);
		carteEnMain.remove(c);
		return r;
	}
	
	
	public Carte[] exchangeCards()
	{
		Carte[] r = new Carte[3];


		//int t[] = new int[3];
		int t[] = tapisAndroidP.exchangeCardsPlayer(carteEnMain);
		//System.out.println("Chosiir les 3 cartes  changer.");
		//System.out.println("Cartes en main:");
		//Scanner in = new Scanner(System.in);

		for(int i=0;i<3;i++)
		{
			//System.out.print("Carte numro: "+i);
			//t[i]=in.nextInt();
			r[i]=carteEnMain.get(t[i]);
		}



		//in.close();
		carteEnMain.remove(t);
		return r;
	}

	public void linkActivity(TapisJeu tapisAndroid){
		tapisAndroidP = tapisAndroid;
	}
}
