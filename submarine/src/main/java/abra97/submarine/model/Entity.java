package abra97.submarine.model;

public abstract class Entity {

	private ObjectType type;
	protected int id;
	private Position position;
	private Team owner;
	private double velocity;
	private Direction angle;

	public Entity(ObjectType type, int id, Position position, Team owner, double velocity, Direction angle) {
		super();
		this.type = type;
		this.id = id;
		this.position = position;
		this.owner = owner;
		this.velocity = velocity;
		this.angle = angle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Entity [type=" + type + ", id=" + id + ", position=" + position + ", owner=" + owner + ", velocity="
				+ velocity + ", angle=" + angle + "]";
	}

}
