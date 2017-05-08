package crossword;

import java.util.concurrent.ThreadLocalRandom;

public class Board {
	/*
	 * occupation map contains all letters that were inserted into board. It
	 * allows to create constraints for new words.
	 */
	private static char[][] occupationMap;
	private static int width;
	private static int height;

	public Board(int w, int h) {
		width = w;
		height = h;
		occupationMap = new char[height][width];
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++)
				occupationMap[r][c] = Constants.DEFAULT_EMPTY_CELL;
		}
	}
	
//	public Board() {
//		width = Constants.WIDTH;
//		height = Constants.HEIGHT;
//		for (int r = 0; r < height; r++) {
//			for (int c = 0; c < width; c++)
//				occupationMap[r][c] = Constants.DEFAULT_EMPTY_CELL;
//		}
//	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static char[][] getOccupationMap() {
		return occupationMap;
	}

	public static void clearOccupationMap() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++)
				occupationMap[r][c] = Constants.DEFAULT_EMPTY_CELL;
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
	private static int getDistanceFromBorder(int firstLetterRow, int firstLetterColumn, int direction) {
		if (direction == Constants.HORIZONTAL)
			return width - firstLetterColumn;
		else
			return height - firstLetterRow;
	}

	public static int selectWordStartingPointAndDirection(int firstLetterRow, int firstLetterColumn, int direction) {
		int distanceFromBorder;

//		do {
//			firstLetterColumn = ThreadLocalRandom.current().nextInt(0, width);
//			firstLetterRow = ThreadLocalRandom.current().nextInt(0, height);
//			direction = ThreadLocalRandom.current().nextInt(0, 2);

			distanceFromBorder = getDistanceFromBorder(firstLetterRow, firstLetterColumn, direction);
//		} while (Constants.SHORTEST_WORD_LENGTH > distanceFromBorder);
		return distanceFromBorder;
	}

	public static int selectWordLength(int distanceFromBorder) {
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

	public static void insertWordIntoOccupationMap(String wordToInsert, int firstLetterRow, int firstLetterColumn,
			int direction) {
		if (direction == Constants.VERTICAL) {
			for (int i = 0; i < wordToInsert.length(); i++) {
				occupationMap[firstLetterRow][firstLetterColumn] = wordToInsert.charAt(i);
				firstLetterRow++;
			}
		} else {
			for (int i = 0; i < wordToInsert.length(); i++) {
				occupationMap[firstLetterRow][firstLetterColumn] = wordToInsert.charAt(i);
				firstLetterColumn++;
			}
		}
	}

	public static void displayOccupationMap() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				System.out.print(occupationMap[r][c]);
			}
			System.out.println();
		}
	}
	
	public static void main(String [] args) {
		Board b = new Board(10, 10);
		b.displayOccupationMap();
	}
}
