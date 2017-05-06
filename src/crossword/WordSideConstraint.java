package crossword;

public class WordSideConstraint {
	public int positionInWord;
	public String text;
	public	int side;
	public	WordSideConstraint(int position, String t, int s) {
		this.positionInWord = position;
		this.text = t;
		this.side = s;
	}
}
