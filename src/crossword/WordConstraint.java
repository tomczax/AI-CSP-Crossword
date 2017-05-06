package crossword;

public class WordConstraint {
	public int positionInWord;
	public char letter;

	public WordConstraint(int position, char letter) {
		this.positionInWord = position;
		this.letter = letter;
	}

	public boolean checkConstraint(String word) {
		if (word.length() <= this.positionInWord)
			return false;
		if (word.charAt(this.positionInWord) != this.letter)
			return false;
		return true;
	}
}
