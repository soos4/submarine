package abra97.submarine.model;

public class Move {

	private float speed;
	private Direction turn;

	public Move(float speed, Direction turn) {
		super();
		this.speed = speed;
		this.turn = turn;
	}

	@Override
	public String toString() {
		return String.format("{\n\t\"speed\": %f\n\t\"turn\": %f\n}", speed, turn.getAngle());
	}

}
