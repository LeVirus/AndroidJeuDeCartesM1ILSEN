package ceri.androiddamepic;

/**
 * Created by Mad on 22/05/2016.
 */
public class DataResult
{
    //Nom et scores acocié
    private static String[] noms = {"Vous","Est","Nord","Ouest"};
    private static int[] scores  = {-1,-1,-1,-1};

    //--------------SET

    public static boolean setResultes(String[] pNoms, int[] pScores)
    {
        if(pNoms.length!=4 || pScores.length!=4)  return false;
        noms=pNoms;
        scores=pScores;
        return true;
    }

    public static boolean setScores(int[] pScores)
    {
        if(pScores.length!=4)  return false;
        scores=pScores;
        return true;
    }

    public static boolean setNames(String[] pNoms)
    {
        if(pNoms.length!=4)  return false;
        noms=pNoms;
        return true;
    }

    //--------------GET

    public static String[] getNames()
    {
        return noms;
    }

    public static int[] getScores()
    {
        return scores;
    }
}
