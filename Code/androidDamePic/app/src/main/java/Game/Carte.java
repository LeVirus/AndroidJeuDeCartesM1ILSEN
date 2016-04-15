package Game;

public class Carte
{
	private color c;	//Couleur
	private int v;		//Valeur
	
	public Carte(int valeur, color couleur){c=couleur;v=valeur;}
	
	//Get - Set
	public color getColor(){return c;}
	public boolean isColor(color c){return (this.c.isSame(c));}
	public int getValue(){return v;}
	public boolean isValue(int v){return (this.v==v);}
	
	//------------------------------------------------------------------METHODES
	
	//Retourne true si mme couleur, false sinon.
	public boolean isSameColor(Carte card){return (card.getColor().isSame(c));}
	
	//Retourne 0 si gale | 1 si this > card et -1 si this < card
	public int compareValue(Carte card){return (v==card.getValue())?0:(v>card.getValue())?1:-1;}
	
	//Retourne true si les cartes sont identiques, false sinon.
	public boolean isSame(Carte c){return (c.isColor(this.c)&&c.isValue(v));}
	
	//Retourne le teste de la carte
	public String toString()
	{
		String r="";
		if(v>=2&&v<=10)	r=Integer.toString(v);
		else if(v==11)	r="Valet";
		else if(v==12)	r="Dame";
		else if(v==13)	r="Roi";
		else if(v==14)	r="As";
		return r+" de "+c.getName();
	}
	
	//Retourne les points que valent la carte a la dame de pique
	public int getPoint()
	{
		return (this.c.isSame(color.coeur))?1:(this.c.isSame(color.pique)&&v==12)?13:0;
	}
	
}
