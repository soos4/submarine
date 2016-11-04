package abra97.submarine.model;

public class Move extends Action {

	private float speed;

	public Move(float speed, Direction turn) {
		super(turn);
		this.speed = speed;
	}

	@Override
	public String toString() {
		return String.format("{\"speed\":%f,\"turn\":%f}", speed, super.getAngle().getAngle());
	}

	
}
