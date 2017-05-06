package crossword;

public class WordSideConstraint {
	private int positionInWord;
	private String text;
	private	int side;
	public	WordSideConstraint(int position, String t, int s) {
		this.positionInWord = position;
		this.text = t;
		this.side = s;
	}
}
