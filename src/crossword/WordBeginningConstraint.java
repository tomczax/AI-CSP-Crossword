package crossword;

public class WordBeginningConstraint {
	public String text;

	public int checkConstraint(String wordToInsert) {
		if (text.isEmpty()) {
			return 0;
		}
		if (wordToInsert.length() <= text.length()) {
			return -1;
		}
		for (int i = 0; i < text.length(); i++) {
			if (wordToInsert.charAt(i) != text.charAt(i)) {
				return -1;
			}
		}

		return text.length();
	}
}
