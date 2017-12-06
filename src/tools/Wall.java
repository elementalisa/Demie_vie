package tools;

public class Wall {
	
	private WallPosition position;
	
	public Wall(WallPosition position){
		this.position = position;
	}
	
	public WallPosition getPosition() {
		return position;
	}

	public void setPosition(WallPosition position) {
		this.position = position;
	}

	
}
