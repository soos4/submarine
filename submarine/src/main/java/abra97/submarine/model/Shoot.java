package abra97.submarine.model;

public class Shoot {

	private Direction angle;

	public Shoot(Direction angle) {
		super();
		this.angle = angle;
	}
	
	@Override
	public String toString() {
		return String.format("{\n\t\"angle\": %f\n}", angle.getAngle()); 
	}
	
	
}
