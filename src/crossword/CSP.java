package crossword;

import java.util.Arrays;
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
		int wordLength = 0;		
		String word;
		int random;
		for(int i=0; i < Constants.MAX_VARIABLE_POSITION_SELECTION_NUMBER; i++) {
			firstLetterRow = ThreadLocalRandom.current().nextInt(0, Board.getHeight());
			firstLetterColumn = ThreadLocalRandom.current().nextInt(0, Board.getWidth());
			direction = ThreadLocalRandom.current().nextInt(0, 2);
			distanceFromBorder = Board.getDistanceFromBorder(firstLetterRow, firstLetterColumn, direction);
			while( distanceFromBorder < Constants.SHORTEST_WORD_LENGTH){
				firstLetterColumn = ThreadLocalRandom.current().nextInt(0, Constants.WIDTH);
				firstLetterRow = ThreadLocalRandom.current().nextInt(0, Constants.HEIGHT);
				direction = ThreadLocalRandom.current().nextInt(0, 2);

				distanceFromBorder = Board.getDistanceFromBorder(firstLetterRow, firstLetterColumn, direction);
			}
			wordLength = Board.selectWordLength(distanceFromBorder);
			System.out.println(firstLetterRow + "  " + firstLetterColumn + "  " + direction + "  " + wordLength );
			Word lastVariable = Data.getVariables().get(Data.getVariables().size()-1);
			lastVariable.setPosition(firstLetterRow, firstLetterColumn);
			lastVariable.setDirection(direction);
			lastVariable.createConstraint(wordLength, Board.getOccupationMap(), Constants.WIDTH, Constants.HEIGHT);
			HashSet<String> domain = new HashSet<String>(WordsList.wordsList);
			System.out.println(domain.size());
			String[] domainArray;
			
			while(domain.size() > 0) {
				domainArray = WordsList.wordsList.toArray(new String[WordsList.wordsList.size()]).clone();
				random = ThreadLocalRandom.current().nextInt(0, domain.size());
				word = domainArray[random];
				
				if(Data.checkConstraint(lastVariable.getConstraint(), word, wordLength)) {
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
//				System.out.println(domain);
			}
			Data.fillOccupationMap();
		}
		Data.unsetVariable();
		return false;
		
	}
	
	public static void main(String[] args) {
		new Board(Constants.WIDTH, Constants.HEIGHT);

		if(CSP.recursiveBactracking() == false) {
			System.out.println("no sultion");
		}
		Board.displayOccupationMap();

		System.out.println("Variables:");
		Data.displayVariables();
	}
}
