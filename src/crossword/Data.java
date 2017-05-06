package crossword;

import java.util.ArrayList;
import java.util.HashSet;

public class Data {
	/*  Contains all words loaded from lemma.al file
	 *	Domains for variables are created upon this set.
	 */
	private ArrayList<String> dictionaryWords;
	/* 	Variable from CSP definition. Could be represented by single string, but
	 * 	this solution includes constraint kept inside Variable.
	 * 	Number of variables depends on constant defined in constant.hpp file
	 */
	private HashSet<Word> variables; 
	//	Describes board, its size and selects position, direction and word size for variable.
	private Board board;
	/*	Selects set of words from that satisfy given constraints.
	 * 	Selection is done from dictionary vector.
	 */
	private HashSet<String> getWordsSatisfyingConstraint(Constraint constraint, int wordLength) {
		HashSet<String> wordsList = null;
		for( String w : this.dictionaryWords) {
			if(checkConstraint(constraint, w, wordLength)) {
				wordsList.add(w);
			}
		}
		return wordsList;
	}
	private boolean checkConstraint(Constraint constraint, String wordToInsert, int wordLength) {
		
	}
	 
}
