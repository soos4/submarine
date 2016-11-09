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
    	if (args.length != 0) {
    		HttpManager.setDefaultURL(args[0]);
    	}
    	
    	Game g = new Game();
    	System.out.println(Torpedo.SPEED + " - " + Torpedo.EXPLOSION_RADIUS + " - " + Torpedo.RANGE);
    	do {
    		g.update();
    		try {
				Thread.sleep(g.getRoundLength());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (g.getStatus() == GameStatus.RUNNING);
    	
    }
}
