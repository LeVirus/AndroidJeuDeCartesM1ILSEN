package Player;
import java.util.ArrayList;

import Game.Carte;
import Game.IPlayer;
import Game.color;

public class IAPlayer extends abstractPlayer implements IPlayer 
{
	
	public IAPlayer()
	{
		carteEnMain = new ArrayList<Carte>();
		points = 0;
	}

	public IAPlayer(String name)
	{
		carteEnMain = new ArrayList<Carte>();
		points = 0;
		id = name;
	}
	
	public Carte playCard()
	{
		if(debug)
		{
			System.out.println("\n\nJoueur: "+id+"\nCartes en main:");
			afficheCartes(carteEnMain.toArray(new Carte[carteEnMain.size()]));
			System.out.println("\nCartes jouables:");
			afficheCartes(plateau.playableCards(carteEnMain.toArray(new Carte[carteEnMain.size()])));
		}
		int p = plateau.getPositionOnTurn();
		if(p==0)	return playCardFirst();
		if(p==3)	return playCardFourth();
					return playCardSecondThird();
}
	
	
	
/*Fonction qui a pour but d'échanger les 3 plus mauvaise carte en début de jeu*/
	public Carte[] exchangeCards()
	{
		Carte[] Echange = new Carte[3]; // déclaration du tableau de 3 carte qui sera renvoyé
		Carte carteEnMainTab[]=carteEnMain.toArray(new Carte[carteEnMain.size()]);
		//Carte[] PlayableC = plateau.playableCards(carteEnMainTab);
		
		if(DameDePique(carteEnMainTab) != null)// Si DdP priorité à échanger celle ci sinon échanger 3 + fort
		{
			Echange[0] = DameDePique(carteEnMainTab);
			DeleteCard(Echange[0]);
			Carte carteEnMainTab2[]=carteEnMain.toArray(new Carte[carteEnMain.size()]);
			Echange[1] = HigherCard(carteEnMainTab2);
			DeleteCard(Echange[1]);
			Carte carteEnMainTab3[]=carteEnMain.toArray(new Carte[carteEnMain.size()]);
			Echange[2] = HigherCard(carteEnMainTab3);
			DeleteCard(Echange[2]);
		}
		else
		{
			Echange[0] = HigherCard(carteEnMainTab);
			DeleteCard(Echange[0]);
			Carte carteEnMainTab2[]=carteEnMain.toArray(new Carte[carteEnMain.size()]);
			Echange[1] = HigherCard(carteEnMainTab2);
			DeleteCard(Echange[1]);
			Carte carteEnMainTab3[]=carteEnMain.toArray(new Carte[carteEnMain.size()]);
			Echange[2] = HigherCard(carteEnMainTab3);
			DeleteCard(Echange[2]);
		}
		return Echange;
	}
	
	/*Fonction jouer au premier tour retourne la plus petite carte de la main*/
	public Carte playCardFirst()
	{
		Carte carteEnMainTab[]=carteEnMain.toArray(new Carte[carteEnMain.size()]);
		Carte[] PlayableC = plateau.playableCards(carteEnMainTab);
		Carte r = LowerCard(PlayableC);
		DeleteCard(r);
		return r;
		
	}
	
	/*Fonction jouer au second ou au troisième tour*/
	public Carte playCardSecondThird()
	{
		Carte r = null;
		Carte[] PlayedCard =  plateau.getPlayCards();
		Carte FirstPlayedCard = compareBestCard(PlayedCard);
		//Carte FirstPlayedCard = new Carte(8,color.trefle);
		Carte carteEnMainTab[]=carteEnMain.toArray(new Carte[carteEnMain.size()]);
		Carte[] PlayableC = plateau.playableCards(carteEnMainTab);
	    
		if(SameColor(FirstPlayedCard ,carteEnMainTab) == true)// Si meme couleur présente dans la main que la 1ere carte jouée
		{
			if((r=InfCloserCard(FirstPlayedCard, PlayableC))!=null);
			else if((r=SupCloserCard(FirstPlayedCard, PlayableC)) !=null); // sinon jouer plus gros
		}
		else // Si pas la meme couleur présente dans la main
		{
			if((r=DameDePique(PlayableC)) != null); // Jouer en priorité Dame de pique si présente
			else if((r=HigherCoeur(PlayableC)) !=null); // Jouer en priorité gros coeur si présent
			else if ((r=HigherCard(PlayableC)) !=null);
		}
		DeleteCard(r);
		return r;

	}
	
	
	public Carte playCardFourth()
	{
		
		Carte r = null;
		Carte[] PlayedCard =  plateau.getPlayCards();	
		Carte FirstPlayedCard = compareBestCard(PlayedCard);
		//Carte FirstPlayedCard = new Carte(2,color.trefle);
		Carte carteEnMainTab[]=carteEnMain.toArray(new Carte[carteEnMain.size()]);
		Carte[] PlayableC = plateau.playableCards(carteEnMainTab);
		
	
		if(SameColor(FirstPlayedCard ,PlayableC) == true)// Si meme couleur présente dans la main que la 1ere carte jouée
		{
			if((r=InfCloserCard(FirstPlayedCard, PlayableC))!=null);
			else if((r=HigherCardSameColor(FirstPlayedCard, PlayableC)) !=null); // sinon jouer plus gros
		}
		else // Si pas la meme couleur présente dans la main
		{
			if((r=DameDePique(PlayableC)) != null); // Jouer en priorité Dame de pique si présente
			else if((r=HigherCoeur(PlayableC)) !=null); // Jouer en priorité gros coeur si présent
			else if ((r=HigherCard(PlayableC)) !=null);
		}
		
		DeleteCard(r);
		return r;
		
	}
	
	/*Compare la main à la carte selectionnée et retourne toutes les cartes de la meme couleur*/
	public boolean SameColor(Carte FirstPlayedCard, Carte[] PlayableC)
	{
		for(Carte c : PlayableC )
			if(c.getColor() == FirstPlayedCard.getColor())
				return true;
		return false;
					
	}
	
	/* Retourne la carte la plus grosse des cartes reçus*/
	public Carte HigherCard(Carte[] PlayableC)
	{
		Carte Higher=null;
		int i=0;
		for(Carte c : PlayableC )
			if(c.getValue() > i)
			{
				i=c.getValue();
				Higher=new Carte(c.getValue(),c.getColor());
			}
		return Higher;
	}
	
	/* Retourne la carte la plus petite des cartes reçus*/
	public Carte LowerCard(Carte[] PlayableC) 
	{
		Carte Lower=null;
		int i=30;
		for(Carte c : PlayableC )
			if(c.getValue() < i)
			{
				i=c.getValue();
				Lower=new Carte(c.getValue(),c.getColor());
			}	
		if (i!=30)
			return Lower;
		else
			return null;
	}
	
	/* Retourne la carte de valeur la plus grosse tout en étant inferieur à la carte comparée*/
	public Carte InfCloserCard(Carte FirstPlayedCard,Carte[] PlayableC)
	{
		Carte Higher=null;
		int i=0;
		for(Carte c : PlayableC )
			if(c.getValue() > i && c.getValue()< FirstPlayedCard.getValue() && c.getColor() == FirstPlayedCard.getColor())
			{
				i=c.getValue();
				Higher=new Carte(c.getValue(),c.getColor());
			}	
		
		if (i!=0)
			return Higher;
		else
		return null;
	}
	
	
	/* Retourne la carte de valeur la plus petite tout en étant supérieur à la carte comparée*/
	public Carte SupCloserCard(Carte FirstPlayedCard,Carte[] PlayableC)
	{
		Carte Higher=null;
		int i=20;
		for(Carte c : PlayableC )
			if(c.getValue() < i && c.getValue()> FirstPlayedCard.getValue() && c.getColor() == FirstPlayedCard.getColor())
			{
				i=c.getValue();
				Higher=new Carte(c.getValue(),c.getColor());
			}	
		if (i!=20)
			return Higher;
		else
		return null;
	}
	
	/* Retourne la carte de valeur la plus forte de la meme couleur que la carte comparée*/
	public Carte HigherCardSameColor(Carte FirstPlayedCard,Carte[] PlayableC)
	{
		Carte Higher=null;
		int i=0;
		for(Carte c : PlayableC )
			if(c.getValue() > i && c.getValue()> FirstPlayedCard.getValue() && c.getColor() == FirstPlayedCard.getColor())
			{
				i=c.getValue();
				Higher=new Carte(c.getValue(),c.getColor());
			}	
		if (i!=0)
			return Higher;
		else
		return null;
	}
	
	
	/*Retourne Dame de Pique si présente*/
	public Carte DameDePique(Carte[] PlayableC)
	{
		for(Carte c : PlayableC )
			if(c.getValue() == 12 && c.getColor() == color.pique)
				return c;
		return null;
	}
	
	/*Retourne la carte Coeur de valeur la plus élevé*/
	public Carte HigherCoeur(Carte[] PlayableC)
	{
		Carte Higher=null;
		int i=0;
		for(Carte c : PlayableC )
			if(c.getValue() > i && c.getColor() == color.coeur)
			{
				i=c.getValue();
				Higher=new Carte(c.getValue(),c.getColor());
			}	
		if (i!=0)
			return Higher;
		else
		return null;
	}
	
	/*Supprime de la main la carte passée en argument*/
	public void DeleteCard(Carte DelCard)
	{
		if(DelCard==null)	return;
		int index=0;
		for(int i = 0; i < this.carteEnMain.size(); i++)
	    	if(this.carteEnMain.get(i).isSame(DelCard))
	    	{
	    		index=i;
	    		break;
	    	}

		this.carteEnMain.remove(index);
	}
	
	public Carte compareBestCard(Carte[] c)
	{
		if(debug)	System.out.println("comapreBestCard: Longuer tab entré: "+c.length);
		Carte r=c[0];
		for(int i=1;i<c.length;i++)
			if(r.isSameColor(c[i]) && r.getValue()<c[i].getValue())
				r=c[i];
		return r;
	}
}
