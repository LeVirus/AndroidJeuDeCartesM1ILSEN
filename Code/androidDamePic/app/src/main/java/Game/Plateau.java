package Game;

import java.util.ArrayList;

import Player.IAPlayer;

public class Plateau implements Game.IPlateau
{
	//Les quatres joueurs présents
	private IPlayer[] players;
	//Stock les cartes en jeu et qui les a joué ce tour-ci
	private ArrayList<IPlayer> playerForCard;
	private ArrayList<Carte> cardOnBoard;
	
	//Position dans le tour de 0 à 3
	private int position;
	//True si un coeur a été joué, false sinon
	private boolean coeurPlayed;
	
	private static boolean debug = false;

	public Plateau(IPlayer[] p)
	{
		players = p;
		cardOnBoard = new ArrayList<Carte>();
		playerForCard = new ArrayList<IPlayer>();
		this.resetBoard();
	}
	
	public Plateau()
	{
		players = new IPlayer[4];
		for(int i=0;i<4;i++)
		{
			players[i] = new IAPlayer();
			players[i].setId("Player"+i);
		}
		cardOnBoard = new ArrayList<Carte>();
		playerForCard = new ArrayList<IPlayer>();
		this.resetBoard();
	}
	
	//----------------------------------------------Méthodes pour les IPlayer
	


	public int getPositionOnTurn()
	{
		return position;
	}
	
	public Carte[] getPlayCards()
	{
		return cardOnBoard.toArray(new Carte[cardOnBoard.size()]);
	}
	
	//Retourne les cartes jouables parmis celles passé en paramètres.
	public Carte[] playableCards(Carte[] c)
	{
		if(debug)System.out.println("Appel de playableCarte.");
		ArrayList<Carte> filtreCarte = new ArrayList<Carte>(), r = new ArrayList<Carte>();
		
		//Si on est le tout premier de la partie à jouer
		if(cardOnBoard.size()==0&&itIsCard(new Carte(2, color.trefle),c))
			{r.add(new Carte(2, color.trefle));return r.toArray(new Carte[r.size()]);}
		
		//Si on est premier pour ce tour (sans le coeur)
		if(position==0)
		{
			color[] tco = {color.pique, color.carreau, color.trefle};
			for(color co : tco )
				for(Carte ca : Deck.creatColoredCardSet(co))
					filtreCarte.add(ca);
			if(coeurPlayed)
				for(Carte ca : Deck.creatColoredCardSet(color.coeur))
					filtreCarte.add(ca);
		}
		
		//Si on est 2eme, 3eme ou 4eme (sans le coeur)
		if(position!=0)
		{
			for(Carte ca : Deck.creatColoredCardSet(cardOnBoard.get(0).getColor()))
				filtreCarte.add(ca);
			//Si on ne peut rien jouer on coupe
			if(filtreCarte.size()==0)
				for(Carte ca : Deck.createDeck())
					filtreCarte.add(ca);
		}
		
		//On applique le filtre que l'on a créé.
		for(Carte ca : c)
			for(Carte fca : filtreCarte)
				if(ca.isSame(fca))
				{r.add(ca);break;}
		
		if(r.size()==0)	return c;
		
		return r.toArray(new Carte[r.size()]);
	}
	
	//Retourne True si la carte c est dans le tableau de carte d.
	boolean itIsCard(Carte c, Carte[] d)
	{
		for(Carte e : d) if(e.isSame(c)) return true;
		return false;
	}
	
	
	//----------------------------------------------Méthodes pour le jeu
	
	//Retourne le joueur qui à posé la plus grosse carte
	public IPlayer whoIsbig()
	{
		Carte bc=cardOnBoard.get(0);int n=0;
		for(int i=0;i<4;i++)	if(cardOnBoard.get(i).compareValue(bc)==1)	{bc=cardOnBoard.get(i);n=i;}

		return playerForCard.get(n);
	}
	
	//Retourn le joueur qui à le plus de points
	public IPlayer whoLose()
	{
		IPlayer r=null;
		for(int i=0,t=0;i<4;i++)
			if(t<players[i].getPoint())
				{r=players[i];t=r.getPoint();}
		return r;
	}
	
	//Retourne le joueur qui a un 2 de trèfle
	public IPlayer whoStart()
	{
		for(IPlayer p : players)
			if(p.haveCard(new Carte(2, color.trefle)))
				return p;
		return null;
	}
	
	//retourne le total de point en jeu.
	public int pointOnboard()
	{
		int r = 0;
		for(Carte c : cardOnBoard)	r+=c.getPoint();
		if(debug)System.out.println("Retoure: pointOnBoard: "+r);
		return r;
	}
	
	//Te met le terrain à zero entre deux parties
	public void resetBoard()
	{
		coeurPlayed=false;
		this.clearTurn(); 
	}
	
	public void clearTurn()
	{
		cardOnBoard.clear();
		playerForCard.clear();
		position=0;
	}
	
	
	//Ajoute une carte et le joueur qui lui est acocié.
	public void addCard(IPlayer p, Carte c)
	{
		cardOnBoard.add(c);
		playerForCard.add(p);
		position++;
		if(debug)System.out.println("Carte joué: "+c.toString());
		
	}
	
	
	//Retourne la liste des joueur en considérant le premier (jouer dans le sens des aiguilles d'une montre)
	public IPlayer[] getOrderedPlayerOnTurn(IPlayer firstPlayer)
	{
		IPlayer[] r = new IPlayer[4];
		if(debug)System.out.println("Nombre de joueurs: "+players.length);
		for(int i=0,fp = getPlayerPosition(firstPlayer);i<4;i++)
			r[i] = players[((i+fp)%4)];
		return r;
	}
	
	//Retourn la position adsolut d'un joueur
	public int getPlayerPosition(IPlayer p)
	{
		for(int i=0;i<4;i++)
		{
			if(p==players[i])
				return i;
		}
		return -1;
	}
	
	//
	public void resetPlayersHand()
	{
		Carte[][] c = Deck.createFourPlayerHand();
		for(int i=0;i<4;i++){
			players[i].setHand(c[i]);
		}
	}
	
	//Demande aux joueur d'échanger leurs cartes
	//int nTour: numéor du tour de 0 à inf.
	public void playerExchangeCards(int nTour)
	{
		//Récupération des chartes à échanger
		Carte[][] c = new Carte[4][];
		if(nTour%4==3)return;

			for(int i=0;i<4;i++)
				c[i] = players[i].exchangeCards();
		
		//Déstribution des cartes à échanger
		switch (nTour%4)
		{
			case 0 :	for(int i=0;i<4;i++)	players[i].addCard(c[(i+1)%4]);	break;	//à Droite
			case 1 :	for(int i=0;i<4;i++)	players[i].addCard(c[(i+3)%4]);	break;	//à Gauche
			case 2 :	for(int i=0;i<4;i++)	players[i].addCard(c[(i+2)%4]);	break;	//En Face
		}
	}
	
	public IPlayer[] getPlayers()
	{
		return players;
	}
}
