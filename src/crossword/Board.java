package crossword;

import java.util.concurrent.ThreadLocalRandom;

public class Board {
	/*
	 * occupation map contains all letters that were inserted into board. It
	 * allows to create constraints for new words.
	 */
	private static char[][] board;

	public Board() {
		board = new char[Constants.HEIGHT][Constants.WIDTH];
		for (int r = 0; r < Constants.HEIGHT; r++) {
			for (int c = 0; c < Constants.WIDTH; c++)
				board[r][c] = Constants.DEFAULT_EMPTY_CELL;
		}
	}

	public static char[][] getBoard() {
		return board;
	}

	public static void clearOccupationMap() {
		for (int r = 0; r < Constants.HEIGHT; r++) {
			for (int c = 0; c < Constants.WIDTH; c++)
				board[r][c] = Constants.DEFAULT_EMPTY_CELL;
		}
	}
	
	public static int[] findPosition() {
		for (int r = 0; r < Constants.HEIGHT; r++) {
			for (int c = 0; c < Constants.WIDTH; c++)
				if(board[r][c] == Constants.DEFAULT_EMPTY_CELL) {
					return new int[] {r, c};
				};
		}
		return new int[] {-1, -1};
	}

	/*
	 * Distance from first_letter_position includes first letter. E.g.<pre> - -
	 * - - - If X is first_place_position placed at [2][1] position, x'es
	 * represent the rest - - X - - of word and word direction is vertical, then
	 * the distance_from_border is equal to 4. - - x - - - - x - - - - x - -
	 * </pre> \param position word first letter position on the board in letters
	 * unit. \return distance_to_border distance in letter units.
	 */
	public static int getDistanceFromBorder(int firstLetterRow, int firstLetterColumn, int direction) {
		if (direction == Constants.HORIZONTAL)
			return Constants.WIDTH - firstLetterColumn;
		else
			return Constants.HEIGHT - firstLetterRow;
	}

//	public static int selectWordStartingPointAndDirection(int firstLetterRow, int firstLetterColumn, int direction) {
//		int distanceFromBorder = getDistanceFromBorder(firstLetterRow, firstLetterColumn, direction);;
//
//		while( distanceFromBorder < Constants.SHORTEST_WORD_LENGTH)
//			firstLetterColumn = ThreadLocalRandom.current().nextInt(0, width);
//			firstLetterRow = ThreadLocalRandom.current().nextInt(0, height);
//			direction = ThreadLocalRandom.current().nextInt(0, 2);
//
//			distanceFromBorder = getDistanceFromBorder(firstLetterRow, firstLetterColumn, direction);
//		return distanceFromBorder;
//	}

	public static int selectWordLength(int distanceFromBorder) {
		if( distanceFromBorder < Constants.SHORTEST_WORD_LENGTH) {
			return 0;
		} else if( distanceFromBorder == Constants.SHORTEST_WORD_LENGTH  ) { 
			return Constants.SHORTEST_WORD_LENGTH;
		} else {
			return ThreadLocalRandom.current().nextInt(Constants.SHORTEST_WORD_LENGTH, distanceFromBorder + 1);
		}
//		int wordLength = 0;
//
//		if (Constants.SHORTEST_WORD_LENGTH < distanceFromBorder)
//			do {
//				wordLength = ThreadLocalRandom.current().nextInt(0,
//						(distanceFromBorder - Constants.SHORTEST_WORD_LENGTH + 1)) + Constants.SHORTEST_WORD_LENGTH;
//			} while (wordLength >= Constants.NUMBER_OF_RANDOM_LETTERS_IN_SET);
//		else if (Constants.SHORTEST_WORD_LENGTH == distanceFromBorder)
//			wordLength = Constants.SHORTEST_WORD_LENGTH;
//		else
//			System.out.println("Error. distance_to_border is smaller than SHORTEST_WORD_LENGTH");
//		return wordLength;
	}

	public static void insertWordIntoOccupationMap(String wordToInsert, int firstLetterRow, int firstLetterColumn,
			int direction) {
		if (direction == Constants.VERTICAL) {
			for (int i = 0; i < wordToInsert.length(); i++) {
				board[firstLetterRow][firstLetterColumn] = wordToInsert.charAt(i);
				firstLetterRow++;
			}
		} else {
			for (int i = 0; i < wordToInsert.length(); i++) {
				board[firstLetterRow][firstLetterColumn] = wordToInsert.charAt(i);
				firstLetterColumn++;
			}
		}
	}

	public static void displayBoard() {
		for (int r = 0; r < Constants.HEIGHT; r++) {
			for (int c = 0; c < Constants.WIDTH; c++) {
				System.out.print(board[r][c]);
			}
			System.out.println();
		}
	}
}
