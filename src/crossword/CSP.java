package crossword;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class CSP {
	public static boolean recursiveBactracking() {
		//success, the solution has been found
		if(!Data.selectUnassignedVariable()) {
			return true;
		}
		int firstLetterRow;
		int firstLetterColumn;
		int direction;
		int distanceFromBorder;
		int wordLength;		
		String word;
		int random;
		for(int i=0; i < Constants.MAX_VARIABLE_POSITION_SELECTION_NUMBER; i++) {
			firstLetterRow = ThreadLocalRandom.current().nextInt(0, Board.getHeight());
			firstLetterColumn = ThreadLocalRandom.current().nextInt(0, Board.getWidth());
			direction = ThreadLocalRandom.current().nextInt(0, 2);
			distanceFromBorder = Board.selectWordStartingPointAndDirection(firstLetterRow, firstLetterColumn, direction);
			wordLength = Board.selectWordLength(distanceFromBorder);
			
			Word lastVariable = Data.getVariables().get(Data.getVariables().size()-1);
			lastVariable.setPosition(firstLetterRow, firstLetterColumn);
			lastVariable.setDirection(direction);
			lastVariable.createConstraint(wordLength, Board.getOccupationMap(), Constants.WIDTH, Constants.HEIGHT);
			HashSet<String> domain = WordsList.wordsList;
			String[] domainArray = WordsList.wordsList.toArray(new String[WordsList.wordsList.size()]);
			
			while(domain.size() > 0) {
				random = ThreadLocalRandom.current().nextInt(0, domain.size());
				word = domainArray[random];
				
				if(Data.checkConstraint(lastVariable.getConstraint(), word, wordLength)) {
					System.out.println("wesz³o");
					Data.insertWord(word);
					Data.removeFromDictionaryWords(word);
					System.out.println(word);
					Board.displayOccupationMap();
					
					if(recursiveBactracking()) {
						return true;
					}
					System.out.println("Selected word:" + word + " did not match" );
					lastVariable.eraseWord();
					Data.addWordToDictionaryWord(word);
				}
				domain.remove(word);
			}
			Data.fillOccupationMap();
		}
		Data.unsetVariable();
		return false;
		
	}
	
	public static void main(String[] args) {
		new Board(Constants.WIDTH, Constants.HEIGHT);
		
		
		
		Board.displayOccupationMap();
		if(CSP.recursiveBactracking() == false) {
			System.out.println("so sultion");
		}
		Board.displayOccupationMap();

		System.out.println("Variables:");
		Data.displayVariables();
	}
}
