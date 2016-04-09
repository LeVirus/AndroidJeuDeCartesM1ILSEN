package Game;

public interface IPlateau
{
	public int getPositionOnTurn();				//Retourne la position du joueur dans le tour, (0 - 3)
	public Carte[] playableCards(Carte[] c);	//Retourne les cartes jouable parmi les cartes pass� en param�tre
	public Carte[] getPlayCards();
}
