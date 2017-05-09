package crossword;

import java.util.ArrayList;
import java.util.HashSet;

public class Data {
	/*
	 * Variable from CSP definition. Could be represented by single string, but
	 * this solution includes constraint kept inside Variable. Number of
	 */
	// Array list, bo chce zeby byla kolejnosc dodania
	private static ArrayList<Word> variables = new ArrayList<Word>();

	public static ArrayList<Word> getVariables() {
		return variables;
	}

	public static void addWordToDictionaryWord(String word) {
		dictionaryWords.add(word);
	}

	public static void unsetVariable() {
		variables.remove(variables.size() - 1);
	}

	// // Describes board, its size and selects position, direction and word
	// size
	// // for variable.
	// private Board board;
	/*
	 * Contains all words loaded from dictionary from lemma.al file Domains for
	 * variables are created based on this set.
	 */
	public static HashSet<String> dictionaryWords = getWordsListFromFile();

	private static HashSet<String> getWordsListFromFile() {
		WordsList.readWordsFromFile();
		return WordsList.wordsList;
	}

	public static void removeFromDictionaryWords(String word) {
		dictionaryWords.remove(word);
	}

	public static HashSet<String> getWordsSatisfyingConstraint(Constraint constraint, int wordLength) {
		HashSet<String> satisfying = new HashSet<String>();
		// sprawdz czy nie zmienia si ekolejnosc i sie nie psuje wyszukiwanie
		for (String w : dictionaryWords) {
			if (checkConstraint(constraint, w, wordLength)) {
				satisfying.add(w);
			}
		}
		return satisfying;
	}

	public static boolean selectUnassignedVariable() {
		if (variables.size() < Constants.NUMBER_OF_WORDS_TO_INSERT) {
			variables.add(new Word());
			return true;
		} else {
			return false;
		}

	}

	public static void insertWord(String word) {
		Word lastVariable = variables.get(variables.size() - 1);
		int beginningConstraintLength = lastVariable.getBegginingConstraintLength();
		int endConstraintLength = lastVariable.getEndConstraintLength();
		int wordLengthWithoutConstraints = word.length() - beginningConstraintLength - endConstraintLength;

		// Only part of the word has to be inserted if there are beginning or
		// end constraint. It means that for e.g. there is line D I S s o l V E
		// where D I S is word_beginning constraint and V E is
		// word_end_constraint. Only s o l has to be inserted, because the rest
		// is already on the board.
		// word_to_insert = dissolve, therefore we look for sol. wbc_length = 3
		// word_length_without_constraints = 3 ( length of 's o l').
		String wordWithoutConstraints = word.substring(beginningConstraintLength,
				beginningConstraintLength + wordLengthWithoutConstraints);
		Board.insertWordIntoOccupationMap(wordWithoutConstraints, lastVariable.getPosition()[0],
				lastVariable.getPosition()[1], lastVariable.getDirection());

		// Variable is equal to the whole word: dissolve.
		lastVariable.assignWord(word);
	}

	public static void fillOccupationMap() {
		Board.clearOccupationMap();
		int beginningConstraintLength;
		int endConstraintLength;
		int wordLengthWithoutConstraints;
		String wordWithoutConstraints = null;
		for (Word v : variables) {
			beginningConstraintLength = v.getBegginingConstraintLength();
			endConstraintLength = v.getEndConstraintLength();
			wordLengthWithoutConstraints = v.getWord().length() - beginningConstraintLength - endConstraintLength;
			wordWithoutConstraints = v.getWord().substring(beginningConstraintLength,
					beginningConstraintLength + wordLengthWithoutConstraints);
			Board.insertWordIntoOccupationMap(wordWithoutConstraints, v.getPosition()[0], v.getPosition()[1],
					v.getDirection());
		}
	}

	public static void displayVariables() {
		for (Word w : variables) {
			System.out.println(w.getWord());
		}
	}

	/*
	 * Removes all strings from the list that cannot be found in dictionary.
	 * is_this_in_dictionary() function is used to search for words. \param
	 * vector with set of strings \return vector with set of words
	 */
	public static HashSet<String> removeNotFoundWords(HashSet<String> domain) {
		for (String s : domain) {
			if (!isInDictionary(s)) {
				domain.remove(s);
			}
		}
		return domain;
	}

	/*
	 * Checks if a given word satisfies all four constraints (beginning, end,
	 * path, sides). \param class with all constraints for a given position and
	 * length on the board. \param word for which constraints have to be
	 * checked. \param word length (without beginning and end constraint length)
	 */
	public static boolean checkConstraint(Constraint constraint, String wordToInsert, int wordLength) {
//		if(wordLength != wordToInsert.length()) {
//			return false;
//		}
		int beginningLength;
		if ((beginningLength = constraint.checkBeginningConstraint(wordToInsert)) == -1) {
			return false;
		}
		int endLength;
		if ((endLength = constraint.checkEndConstraint(wordToInsert)) == -1) {
			return false;
		}
		// ???????????????????????????????????
		if (wordToInsert.length() != (beginningLength + wordLength + endLength)) {
			return false;
		}
		// lastIndex = wordToInsert.length()-beginningLength?
		String wordWithoutBeginningConstraint = wordToInsert.substring(beginningLength,
				wordToInsert.length() - endLength);
		if (constraint.checkPathConstraint(wordWithoutBeginningConstraint) == false) {
			return false;
		}
		ArrayList<WordSideConstraint> wordSideConstraints = constraint.wordSideConstraints;
		// dla kazdego znaku w word_length
		ArrayList<ArrayList<SideConstraint>> sideConstraints = new ArrayList<ArrayList<SideConstraint>>();

		for (int i = 0; i < wordSideConstraints.size(); i++) {
			sideConstraints.get(wordSideConstraints.get(i).positionInWord)
					.add(new SideConstraint(wordSideConstraints.get(i).side, i));
		}
		for (int i = 0; i < sideConstraints.size(); i++) {
			String createdWord = "";
			// VERTICAL
			// both sides
			if (sideConstraints.get(i).size() == 2) {
				if (sideConstraints.get(i).get(0).type == Constants.LEFT) {
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(0).id).text;
					createdWord += wordToInsert.charAt(i + beginningLength);
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(1).id).text;
				} else {
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(1).id).text;
					createdWord += wordToInsert.charAt(i + beginningLength);
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(0).id).text;
				}
				// isInDictionary == false
				if (!isInDictionary(createdWord)) {
					return false;
				}
				// one side
			} else if (sideConstraints.get(i).size() == 1) {
				if (sideConstraints.get(i).get(0).type == Constants.LEFT) {
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(0).id).text;
					createdWord += wordToInsert.charAt(i + beginningLength);
				} else {
					createdWord += wordToInsert.charAt(i + beginningLength);
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(0).id).text;
				}
				if (!isInDictionary(createdWord)) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean isInDictionary(String word) {
		if (dictionaryWords.contains(word)) {
			return true;
		} else {
			return false;
		}
	}
}
