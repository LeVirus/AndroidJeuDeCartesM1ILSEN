package Player;

import java.util.ArrayList;

import Game.Carte;
import Game.IPlateau;

public abstract class abstractPlayer
{
	protected IPlateau plateau;
	protected ArrayList<Carte> carteEnMain;
	protected int points;
	protected String id;
	
	protected static boolean debug = true;
	
	/* CONSTRUCTEUR
	  Ne surtout pas oublier d'ajouter les lignes suivante des le construteurs des classe filles:
	  	carteEnMain = new ArrayList<Carte>();
		points = 0;
	 */
	
	//Ajoute le plateau de jeu
	public void setBoard(IPlateau p)
	{
		plateau = p;
	}

	//------------------------------------------------------------------Gestion de la main du joueur
	public void setHand(Carte c[])
	{
		carteEnMain.clear();
		for(Carte d : c)	carteEnMain.add(d);
	}
	
	public void addCard(Carte c[])
	{
		for(Carte d : c)	carteEnMain.add(d);
	}
	
	public boolean haveCard(Carte c)
	{
		for(Carte c2 : carteEnMain)	if(c.isSame(c2)) return true;
		return false;
	}
	
	//------------------------------------------------------------------Gestion des points du joueur
	
	public void 	addPoint(int point)	{points += point;}
	public int 		getPoint()			{return points;}
	public void 	resetPoint()		{points = 0;}
	
	//------------------------------------------------------------------Gestion des identifiants
	
	public void setId(String name){this.id=name;}
	public String getId(){return id;}

	//------------------------------------------------------------------Tri de la main
	
	
	
	/*Permet de trier la main pour la rendre plus humainement lisible
	 * hand: Main du joueur � trier
	 * return: main tri�
	 */
	protected ArrayList<Carte> sortHand(ArrayList<Carte> hand)
	{
		//On s�pare les dif�rantes couleur
		ArrayList<Carte> r = new ArrayList<Carte>();
		ArrayList<ArrayList<Carte>> t = new ArrayList<ArrayList<Carte>>();
		for(int i=0;i<4;i++)
		{
			t.add(new ArrayList<Carte>());
			for(Carte c  : hand)	if(i==c.getColor().getValue())	t.get(i).add(c);
		}
		//Trie par valeur
		for(int i=0;i<4;i++)	r.addAll(sortCardArrayList(t.get(i)));
		return r;
	}
	
	//Tri les cartes par valeur, � n'utiliser que pour des cartes de m�me couleur. Ne pas appeler.
	protected ArrayList<Carte> sortCardArrayList(ArrayList<Carte> l)
	{
		System.out.println("Tri");
		for(Carte c : l)	System.out.println(c.toString());
		ArrayList<Carte> r = new ArrayList<Carte>();
		for(int j=0;l.size()!=0;j=0)
		{
			for(int i=0;i<l.size();i++)	if(l.get(j).compareValue(l.get(i))==1)	j=i;
			r.add(l.get(j));
			l.remove(j);
		}
		return r;
	}
	
	protected void afficheCartes(Carte[] h)
	{
		if(debug)for(int i=0;i<h.length;i++)	System.out.println((i==1)?"Carte en main\n":""+i+" - "+h[i].toString() );
	}
}


