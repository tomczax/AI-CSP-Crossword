package crossword;

public class WordEndConstraint {
	public String text;

	public int checkConstraint(String wordToInsert) {
		if (text.isEmpty()) {
			return 0;
		}

		if (wordToInsert.length() <= text.length()) {
			return -1;
		}

		int insertAtIndex = wordToInsert.length() - text.length();
		for (int i = 0; i < text.length(); i++, insertAtIndex++) {
			if (wordToInsert.charAt(insertAtIndex) != text.charAt(i)) {
				return -1;
			}

		}

		return text.length();
	}

}
