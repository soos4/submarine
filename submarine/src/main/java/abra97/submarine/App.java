package abra97.submarine;

import abra97.submarine.model.Entity;
import abra97.submarine.model.Game;
import abra97.submarine.model.GameStatus;
import abra97.submarine.model.Submarine;
import abra97.submarine.model.Torpedo;

public class App 
{
    public static void main( String[] args )
    {    
        //System.out.println(HttpManager.newGame());
        //System.out.println(HttpManager.gameList());
        //System.out.println(HttpManager.connectGame(751685318));
        //MyJson.xyJsonParser();
    	//Game g = new Game();
    	//System.out.println(g.getGameID());
    	
    	//Entity e = new Submarine(1, null, null, 0, null);
    	Game g = new Game();
    	System.out.println(Torpedo.SPEED + " - " + Torpedo.EXPLOSION_RADIUS + " - " + Torpedo.RANGE);
    	do {
    		g.update();
    		try {
				Thread.sleep(g.getRoundLength());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (g.getStatus() == GameStatus.RUNNING);
    	
    }
}
