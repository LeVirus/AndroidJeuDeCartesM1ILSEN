package Game;

public enum color
{
	coeur("coeur", 0), trefle("trefle", 1), carreau("carreau", 2), pique("pique", 3);
	
	private String n;
	private int v;
	
	color(String name, int valeur){n=name;v=valeur;}
	public String getName(){return n;}
	public int getValue(){return v;}
	public boolean isSame(color c){return (c.getValue()==v);}
}
