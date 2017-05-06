package crossword;

import java.util.ArrayList;
import java.lang.StringBuilder;

public class Constraint {
	/*
	 * Contains all constraints that are created due to letters that appear
	 * around the word. Example: - - - o - If x'es are positions for a new word
	 * to insert, then there are two side constraints there. - - - d - 'a' at
	 * position 1 (position in the new word) and 'od' position: 3. x x x x - - a
	 * - - -
	 */
	private ArrayList<WordSideConstraint> wordSideConstraints = new ArrayList<WordSideConstraint>();
	private WordBeginningConstraint beginningConstraint = new WordBeginningConstraint() ;
	private WordEndConstraint endConstraint = new WordEndConstraint();
	private ArrayList<WordConstraint> pathConstraints;

	public void createConstraint(int firstLetterRow, int firstLetterColumn, int direction, int wordLength,
			char[][] occupationMap, int width, int height) {
		beginningConstraint = createBeginningConstraint(firstLetterRow, firstLetterColumn, direction, occupationMap);
		endConstraint = createEndConstraint(firstLetterRow, firstLetterColumn, direction, wordLength, occupationMap,
				width, height);
		pathConstraints = createPathAndSideConstraints(firstLetterRow, firstLetterColumn, wordLength, direction,
				occupationMap, width, height);

	}
	/* 	Contains all constraints that are created due to letters that appear around the word.
	 * 	Example:
	 *	- - - o -	If x'es are positions for a new word to insert, then there are two side constraints there.
	 *	- - - d -	'a' at position 1 (position in the new word) and 'od' position: 3.
	 *	x x x x -
	 *	- a - - -
	 */
	private ArrayList<WordConstraint> createPathAndSideConstraints(int firstLetterRow, int firstLetterColumn,
			int wordLength, int direction, char[][] occupationMap, int width, int height) {

		this.wordSideConstraints.clear();
		ArrayList<WordConstraint> wordConstraints = new ArrayList<WordConstraint>();
		
		if (direction == Constants.VERTICAL) {
			for (int letterIndex = firstLetterRow; letterIndex < (firstLetterRow + wordLength); letterIndex++) {
				// path (word) constraint -> (position, letter)
				if (occupationMap[letterIndex][firstLetterColumn] != Constants.DEFAULT_EMPTY_CELL) {
					//position of letter in the word is letterIndex - firstLetterRow
					wordConstraints.add(new WordConstraint(letterIndex - firstLetterRow, occupationMap[letterIndex][firstLetterColumn]));
				} else {
					String text = "";
					// left side
					int c = firstLetterColumn - 1;
					while (occupationMap[letterIndex][c] != Constants.DEFAULT_EMPTY_CELL) {
						text += occupationMap[letterIndex][c];
						c--;
					}
					if (!text.equals("")) {
						text = reverseString(text);
						this.wordSideConstraints
								.add(new WordSideConstraint(letterIndex - firstLetterRow, text, Constants.LEFT));
						text = "";
					}
					// right side
					c = firstLetterColumn + 1;
					while (occupationMap[letterIndex][c] != Constants.DEFAULT_EMPTY_CELL) {
						text += occupationMap[letterIndex][c];
						c++;
					}
					if (!text.equals("")) {
						this.wordSideConstraints
								.add(new WordSideConstraint(letterIndex - firstLetterRow, text, Constants.RIGHT));
					}

				}

			}
		} else {
			for (int letterIndex = firstLetterColumn; letterIndex < (firstLetterColumn + wordLength); letterIndex++) {
				// path (word) constraint -> (position, letter)
				if (occupationMap[firstLetterRow][letterIndex] != Constants.DEFAULT_EMPTY_CELL) {
					wordConstraints.add(new WordConstraint(letterIndex - firstLetterColumn, occupationMap[firstLetterRow][letterIndex]));
				} else {
					String text = "";
					// up - above the word
					int c = firstLetterRow - 1;
					while (occupationMap[c][letterIndex] != Constants.DEFAULT_EMPTY_CELL) {
						text += occupationMap[c][letterIndex];
						c--;
					}
					if (!text.equals("")) {
						text = reverseString(text);
						this.wordSideConstraints
								.add(new WordSideConstraint(letterIndex - firstLetterColumn, text, Constants.UP));
						text = "";
					}
					// down - under the word
					c = firstLetterRow + 1;
					while (occupationMap[c][letterIndex] != Constants.DEFAULT_EMPTY_CELL) {
						text += occupationMap[c][letterIndex];
						c++;
					}
					if (!text.equals("")) {
						this.wordSideConstraints
								.add(new WordSideConstraint(letterIndex - firstLetterColumn, text, Constants.DOWN));
					}

				}

			}
		}

		System.out.println("word constraints: ");
		for(int i = 0; i < wordConstraints.size(); i++) {
			System.out.println("[ " + wordConstraints.get(i).positionInWord + "] : " + wordConstraints.get(i).letter);
		}
		return wordConstraints;
	}

	private WordBeginningConstraint createBeginningConstraint(int firstLetterRow, int firstLetterColumn, int direction,
			char[][] occupationMap) {
		WordBeginningConstraint wordBeginningConstraint = new WordBeginningConstraint();
		wordBeginningConstraint.text = "";
		if (direction == Constants.VERTICAL) {
			for (int i = firstLetterRow - 1; i >= 0
					&& occupationMap[i][firstLetterColumn] != Constants.DEFAULT_EMPTY_CELL; i--) {
				wordBeginningConstraint.text += occupationMap[i][firstLetterColumn];
			}
			wordBeginningConstraint.text = reverseString(wordBeginningConstraint.text);
		} else {
			for (int i = firstLetterColumn - 1; i >= 0
					&& occupationMap[firstLetterRow][i] != Constants.DEFAULT_EMPTY_CELL; i--) {
				wordBeginningConstraint.text += occupationMap[firstLetterRow][i];
			}
			wordBeginningConstraint.text = reverseString(wordBeginningConstraint.text);
		}
		return wordBeginningConstraint;
	}

	private WordEndConstraint createEndConstraint(int firstLetterRow, int firstLetterColumn, int direction,
			int wordLength, char[][] occupationMap, int width, int height) {
		WordEndConstraint wordEndConstraint = new WordEndConstraint();
		wordEndConstraint.text = "";
		if (direction == Constants.VERTICAL) {
			for (int i = firstLetterRow + wordLength; i < height
					&& occupationMap[i][firstLetterColumn] != Constants.DEFAULT_EMPTY_CELL; i++) {
				
				wordEndConstraint.text += occupationMap[i][firstLetterColumn];
			}
		} else {
			for (int i = firstLetterColumn + wordLength; i < width
					&& occupationMap[firstLetterRow][i] != Constants.DEFAULT_EMPTY_CELL; i++) {
				wordEndConstraint.text += occupationMap[firstLetterRow][i];
			}
		}
		return wordEndConstraint;
	}

	private static String reverseString(String s) {
		if(s != null) {
			return new StringBuilder(s).reverse().toString();
		} else {
			return "";
		}
	}

	public int getBegginingConstraintLength() {
		return this.beginningConstraint.text.length();
	}

	public int getEndConstraintLength() {
		return this.endConstraint.text.length();
	}

	 public static void main(String[] args) {
		 
		 Board b = new Board(5, 6);
		 b.insertWordIntoOccupationMap("karo", 2, 1, 1);
		 b.displayOccupationMap();
		 Constraint c = new Constraint();
		 c.createConstraint(2, 1, 0, 3, b.getOccupationMap(), b.getWidth(), b.getHeight());
		 c.createConstraint(0, 1, 1, 3, b.getOccupationMap(), b.getWidth(), b.getHeight());
			System.out.println("end onstraints: ");
				System.out.println("[ " + c.endConstraint.text);
				System.out.println("[ " + c.beginningConstraint.text);
//	 System.out.println(Constraint.reverseString("karo"));
	 }
}
