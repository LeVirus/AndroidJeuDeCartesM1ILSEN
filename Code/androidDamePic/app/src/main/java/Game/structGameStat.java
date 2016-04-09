package Game;

public class structGameStat
{
	//Chaque tableau à une corespondance par i.
	public String[] IAName;
	public int[] point;
	
	public int nbMain;	//Nombre de main joué
	
	public structGameStat()
	{
		IAName = new String[4];
		point = new int[4];
		nbMain=-1;
	}
	
	public void setAll(String[] IAName, int[] point, int nbMain)
	{
		this.IAName = IAName;
		this.point = point;
		this.nbMain=nbMain;
	}
}
