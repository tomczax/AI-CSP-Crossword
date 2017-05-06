package crossword;

public class SideConstraint {
	// up or down
	public int type;
	public int id;
	
	public SideConstraint(int type, int id) {
		this.type = type;
		this.id = id;
	}
}
