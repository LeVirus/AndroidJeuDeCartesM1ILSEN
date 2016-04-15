package Game;
/*

	private Plateau plateau;
	private int nbMain;
	private static boolean debug = true;

	public Partie(IPlayer players[])
	public Partie()
	public Plateau getPlateau();
	public structGameStat newGame()
	private int handPlayed()
	private IPlayer newTurn(IPlayer firstPlayer)

 */


public class Partie
{
	//Plataeu de jeu
	private Plateau plateau;
	//Nombre de main passé durant une partie
	private int nbMain;
	
	private static boolean debug = false;
	
	
	public Partie(IPlayer players[])
	{
		plateau = new Plateau(players);
		for(IPlayer p : players)	p.setBoard(plateau);
	}
	
	public Partie()
	{

		plateau = new Plateau();
	}

	public Plateau getPlateau(){
		return plateau;
	}


	/* Joue jusqu'à ce qu'un joueur attaigne 100 points
	 * 
	 */
	public structGameStat newGame()
	{
		//Jouer la partie
		nbMain = 0;
		while(handPlayed()<100);
		
		//Récupération des données pour les stats
		structGameStat r = new structGameStat();
		IPlayer[] p = plateau.getPlayers();
		for(int i=0;i<4;i++)
		{
			r.point[i] = p[i].getPoint();
			r.IAName[i] = p[i].getId();
			if(debug)System.out.println("Joueur "+i+" : "+r.point[i]+" points.");
		}
		r.nbMain = nbMain;
		
		if(debug)System.out.println("Partie Terminé");
		return r;
	}
	
	/* L'enssemble des joueurs ce débarassent de leurs main
	 * Retourne les points du joueurs qui pèrd.
	 */
	private int handPlayed()
	{
		if(debug)System.out.println("\n\nNouvelle main:");
		
		//Distribution des cartes
		 plateau.resetPlayersHand();
		 
		 //Phase d'échange des cartes
		 plateau.playerExchangeCards(nbMain);
		 
		 //Récupération des donnée pour la cloche de bois
		 /*int playersPoint[] = new int[4], i=0;
		 for(IPlayer[] tp=plateau.getPlayers();i<4;i++)
			 playersPoint[i]=tp[i].getPoint();*/
		 
		 //Les joueurs jouent leurs mains
		 IPlayer p = plateau.whoStart();
		 for(int i=0;i<13;i++)
		 {
			 if(debug)System.out.println("\nNouvelle manche: "+(i+1));
			 p = newTurn(p);
		 }
		 
		 //Vérification de la cloche de bois
		 /*i=0;
		 for(IPlayer[] tp=plateau.getPlayers();i<4;i++)
			 if((tp[i].getPoint()-playersPoint[i])==26)
			 {
				 //TODO: à mettre dans le plateau
				 tp[i].addPoint(-26);
				 for(int j=0;j<4;j++)
					 if(j!=i)
						 tp[j].addPoint(26);
				 break;
				 //FIN TODO
			 }*/
		//Après chaque mains on vide le plateau et regarde si la partie est finie
		int r = plateau.whoLose().getPoint();
		plateau.resetBoard();
		nbMain++;//Pour les échanges.
		return r;
	}
	
	/* Fait jouer un tour de jeu
	 * firstPlayer: Premier joueur à devoir jouer
	 * Retourne le joueur qui à pris les cartes durant le tour.
	 */ 
	private IPlayer newTurn(IPlayer firstPlayer)
	{
		//Chaque joueur joue sa carte
		for(IPlayer currentPlayer : plateau.getOrderedPlayerOnTurn(firstPlayer))
			plateau.addCard(currentPlayer, currentPlayer.playCard());
		//On détèrmine qui à perdu
		IPlayer r = plateau.whoIsbig();
		
		//On ajoute les points du plateau au perdant
		r.addPoint(plateau.pointOnboard());
		
		plateau.clearTurn();
		return r;
	}
}

//♫♪♫♫♪♪♪♫