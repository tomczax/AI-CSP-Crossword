package crossword;

import java.util.ArrayList;
import java.util.HashSet;

public class Data {
	/*
	 * Variable from CSP definition. Could be represented by single string, but
	 * this solution includes constraint kept inside Variable. Number of
	 * variables depends on constant defined in constant.hpp file
	 */
	private HashSet<Word> variables;
	// Describes board, its size and selects position, direction and word size
	// for variable.
	private Board board;
	/*	Contains all words loaded from dictionary from lemma.al file
	 *	Domains for variables are created based on this set.
	 */
	HashSet<String> dictionaryWords = getWordsListFromFile() ;
	private HashSet<String> getWordsListFromFile() {
		WordsList.readWordsFromFile();
		return WordsList.wordsList;
	}
	/*
	 * Selects set of words from that satisfy given constraints. Selection is
	 * done from dictionary set.
	 */
	private HashSet<String> getWordsSatisfyingConstraint(Constraint constraint, int wordLength) {
		HashSet<String> wordsList = new HashSet<String>();
		for (String w : this.dictionaryWords) {
			if (checkConstraint(constraint, w, wordLength)) {
				wordsList.add(w);
			}
		}
		return wordsList;
	}

	/*
	 * Checks if a given word satisfies all four constraints (beginning, end,
	 * path, sides). \param class with all constraints for a given position and
	 * length on the board. \param word for which constraints have to be
	 * checked. \param word length (without beginning and end constraint length)
	 */
	public boolean checkConstraint(Constraint constraint, String wordToInsert, int wordLength) {
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
		for(int i = 0; i < sideConstraints.size(); i++) {
			String createdWord = "";
			//VERTICAL
			//both sides 
			if(sideConstraints.get(i).size() == 2) {
				if(sideConstraints.get(i).get(0).type == Constants.LEFT) {
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(0).id).text;
					createdWord += wordToInsert.charAt(i+beginningLength);
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(1).id).text;
				} else {
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(1).id).text;
					createdWord += wordToInsert.charAt(i+beginningLength);
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(0).id).text;
				}
				//isInDictionary == false 
				if(!isInDictionary(createdWord)) {
					return false;
				}
			// one side
			} else if( sideConstraints.get(i).size() == 1) {
				if(sideConstraints.get(i).get(0).type == Constants.LEFT) {
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(0).id).text;
					createdWord += wordToInsert.charAt(i+beginningLength);
				} else {
					createdWord += wordToInsert.charAt(i+beginningLength);
					createdWord += wordSideConstraints.get(sideConstraints.get(i).get(0).id).text;
				}
				if(!isInDictionary(createdWord)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isInDictionary(String word) {
		if( this.dictionaryWords.contains(word)) {
			return true;
		} else {
			return false;
		}
	}
}
