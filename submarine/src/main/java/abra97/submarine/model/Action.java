package abra97.submarine.model;

public class Action {

	private Direction angle;
	private double speed;

	public Action(Direction angle, double speed) {
		this.angle = angle;
		this.speed = speed;
	}

	public Direction getAngle() {
		return angle;
	}
	
	public double getSpeed() {
		return speed;
	}
	
}
