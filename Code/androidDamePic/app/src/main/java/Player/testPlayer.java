package Player;

import java.util.ArrayList;
import Game.Carte;
import Game.IPlayer;

public class testPlayer extends abstractPlayer implements IPlayer
{
	public testPlayer()
	{
		carteEnMain = new ArrayList<Carte>();
		points = 0;
	}
	
	public Carte playCard()
	{
		Carte tmp = carteEnMain.get(0);
		carteEnMain.remove(0);
		return tmp;
	}
	
	public Carte[] exchangeCards()
	{
		Carte[] c ={carteEnMain.get(1),carteEnMain.get(2),carteEnMain.get(3)};
		for(int i=0;i<3;i++)	carteEnMain.remove(1);
		return c;
	}
	
}
