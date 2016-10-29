package abra97.submarine;

public class App 
{
    public static void main( String[] args )
    {    
        System.out.println(HttpManager.newGame());
        System.out.println(HttpManager.gameList());
        //System.out.println(HttpManager.connectGame(751685318));
    }
}
