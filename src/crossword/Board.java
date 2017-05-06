package crossword;

import java.util.concurrent.ThreadLocalRandom;

public class Board {
	/*
	 * occupation map contains all letters that were inserted into board. It
	 * allows to create constraints for new words.
	 */
	private char[][] occupationMap;
	private int width;
	private int height;
	

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.occupationMap = new char[height][width];
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++)
				this.occupationMap[r][c] = Constants.DEFAULT_EMPTY_CELL;
		}
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public char[][] getOccupationMap() {
		return this.occupationMap;
	}

	public void clearOccupationMap() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++)
				this.occupationMap[r][c] = Constants.DEFAULT_EMPTY_CELL;
		}
	}

	/*
	 * Distance from first_letter_position includes first letter. E.g.<pre> - -
	 * - - - If X is first_place_position placed at [2][1] position, x'es
	 * represent the rest - - X - - of word and word direction is vertical, then
	 * the distance_from_border is equal to 4. - - x - - - - x - - - - x - -
	 * </pre> \param position word first letter position on the board in letters
	 * unit. \return distance_to_border distance in letter units.
	 */
	private int getDistanceFromBorder(int firstLetterRow, int firstLetterColumn, int direction) {
		if (direction == Constants.HORIZONTAL)
			return this.width - firstLetterColumn;
		else
			return this.height - firstLetterRow;
	}

	public int selectWordStartingPointAndDirection(int firstLetterRow, int firstLetterColumn, int direction) {
		int distanceFromBorder;

		do {
			firstLetterColumn = ThreadLocalRandom.current().nextInt(0, this.width);
			firstLetterRow = ThreadLocalRandom.current().nextInt(0, this.height);
			direction = ThreadLocalRandom.current().nextInt(0, 2);

			distanceFromBorder = getDistanceFromBorder(firstLetterRow, firstLetterColumn, direction);
		} while (Constants.SHORTEST_WORD_LENGTH > distanceFromBorder);
		return distanceFromBorder;
	}

	public int selectWordLength(int distanceFromBorder) {
		int wordLength = 0;

		if (Constants.SHORTEST_WORD_LENGTH < distanceFromBorder)
			do {
				wordLength = ThreadLocalRandom.current().nextInt(0,
						(distanceFromBorder - Constants.SHORTEST_WORD_LENGTH + 1)) + Constants.SHORTEST_WORD_LENGTH;
			} while (wordLength >= Constants.NUMBER_OF_RANDOM_LETTERS_IN_SET);
		else if (Constants.SHORTEST_WORD_LENGTH == distanceFromBorder)
			wordLength = Constants.SHORTEST_WORD_LENGTH;
		else
			System.out.println("Error. distance_to_border is smaller than SHORTEST_WORD_LENGTH");
		return wordLength;
	}

	public void insertWordIntoOccupationMap(String wordToInsert, int firstLetterRow, int firstLetterColumn,
			int direction) {
		if (direction == Constants.VERTICAL) {
			for (int i = 0; i < wordToInsert.length(); i++) {
				this.occupationMap[firstLetterRow][firstLetterColumn] = wordToInsert.charAt(i);
				firstLetterRow++;
			}
		} else {
			for (int i = 0; i < wordToInsert.length(); i++) {
				this.occupationMap[firstLetterRow][firstLetterColumn] = wordToInsert.charAt(i);
				firstLetterColumn++;
			}
		}
	}

	public void displayOccupationMap() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				System.out.print(this.occupationMap[r][c]);
			} System.out.println();
		}
	}

//	 public static void main(String[] args) {
//		 Board b = new Board(6, 5);
//		 b.displayOccupationMap();
//		 b.insertWordIntoOccupationMap("karo", 1, 2, 1);
//		 b.displayOccupationMap();
//		 b.clearOccupationMap();
//		 b.displayOccupationMap();
//	
//	 }
}
