package abra97.submarine.model;

public class Direction {
	
	public static final Direction EAST = new Direction(0);
	public static final Direction NORTH = new Direction(90);
	public static final Direction WEST = new Direction(180);
	public static final Direction SOUTH = new Direction(270);
	
	
	private float angle;

	public Direction(float angle) {
		super();
		this.angle = angle;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
}
