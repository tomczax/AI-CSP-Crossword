package crossword;

public class Constants {
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int DEFAULT_EMPTY_CELL = '0';
	/*	When randomly choosing word_length, condition will be checked,
	 *	so that no word shorter than this variable will be selected.
	 */
	public static final int SHORTEST_WORD_LENGTH = 4;
	/*  Defines how many letters there are in a set when creating word.
	 * 	With combinations of those letters word has to be created plus
	 * 	letters on the board can be used
	 */
	public static final int NUMBER_OF_RANDOM_LETTERS_IN_SET = 20;
}
