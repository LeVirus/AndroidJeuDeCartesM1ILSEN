package Game;

/* Interface entre le jeu et les diférants type de joueur tel qu'une IA ou une IHM
 * Seul les deux permières méthodes ne sont pas implémenté dans abstractPlayer
 */
public interface IPlayer
{
	//------------------------------------------------------------------Non-implémenté par virtualPlayer
	
	Carte	playCard();				//Demande de jouer une carte
	Carte[]	exchangeCards();		//Demande des cartes à échanger
	
	
	//------------------------------------------------------------------Concerne la gestion de la main
	
	void	setHand(Carte[] c);		//Remplace l'ancienne main (si existe) par une nouvelle, généralement de 13 cartes.
	void	addCard(Carte[] c);		//Ajoute les cartes passées en paramètre, cela peut être une nouvelle main ou à l'issue d'un échange
	boolean	haveCard(Carte c);		//True si le joueur à une carte semblable, False sinon.
	
	//------------------------------------------------------------------Concerne la gestion des points
	
	void	addPoint(int point);	//Ajoute des points au joueur (peuvent être négatif)
	int 	getPoint();				//Demande les points du joueur
	void	resetPoint();			//Remet à zero le compteur de point
	
	
	//------------------------------------------------------------------Autre méthodes
	
	void	setId(String id);		//Ajoute un Identifient
	String	getId();				//Récupère un Identifiant
	void	setBoard(IPlateau p);	//Ajoute le plateau de jeu
}

