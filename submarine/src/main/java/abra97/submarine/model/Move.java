package abra97.submarine.model;

public class Move extends Action {

	public Move(Direction turn, double speed) {
		super(turn, speed);
	}

	@Override
	public String toString() {
		return String.format("{\"speed\":%f,\"turn\":%f}", super.getSpeed(), super.getAngle().getAngle());
	}

	
}
