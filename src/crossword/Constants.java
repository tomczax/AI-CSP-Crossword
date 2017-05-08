package crossword;

public class Constants {
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int DEFAULT_EMPTY_CELL = '-';
	/*	When randomly choosing word_length, condition will be checked,
	 *	so that no word shorter than this variable will be selected.
	 */
	public static final int SHORTEST_WORD_LENGTH = 3;
	/*  Defines how many letters there are in a set when creating word.
	 * 	With combinations of those letters word has to be created plus
	 * 	letters on the board can be used
	 */
	public static final int NUMBER_OF_RANDOM_LETTERS_IN_SET = 20;
	/*	Describes maximal number of the position selection repetition
	 *	for single Variable when the deeper recursion has no way to go
	 *	due to empty domain = too strong constraint.
	 */
	public static final int MAX_VARIABLE_POSITION_SELECTION_NUMBER = 20;
	/* How many words should be inserted into the crossword */
	public static final int NUMBER_OF_WORDS_TO_INSERT = 2;
	public static final int HEIGHT = 5;
	public static final int WIDTH =6;
	
}
