package abra97.submarine.model;

public class Shoot extends Action {
	
	public Shoot(Direction angle) {
		super(angle, Torpedo.SPEED);
	}
	
	@Override
	public String toString() {
		return String.format("{\"angle\":%f}", super.getAngle().getAngle()); 
	}
	
	
}
