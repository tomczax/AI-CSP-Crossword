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
		int wordLength = 0;		
		String word;
		int random;
//		for(int i=0; i < Constants.MAX_VARIABLE_POSITION_SELECTION_NUMBER; i++) {
			if( Board.findPosition()[0] == -1 || Board.findPosition()[1] == -1 ) {
				System.out.println(" no more empty spaces");
				return false;
			}
			firstLetterRow = Board.findPosition()[0];
			firstLetterColumn = Board.findPosition()[1];
//			firstLetterRow = ThreadLocalRandom.current().nextInt(0, Constants.HEIGHT);
//			firstLetterColumn = ThreadLocalRandom.current().nextInt(0, Constants.WIDTH);
			direction = ThreadLocalRandom.current().nextInt(0, 2);
			distanceFromBorder = Board.getDistanceFromBorder(firstLetterRow, firstLetterColumn, direction);
//			while( distanceFromBorder < Constants.SHORTEST_WORD_LENGTH){
//				firstLetterColumn = ThreadLocalRandom.current().nextInt(0, Constants.WIDTH);
//				firstLetterRow = ThreadLocalRandom.current().nextInt(0, Constants.HEIGHT);
//				direction = ThreadLocalRandom.current().nextInt(0, 2);
//
//				distanceFromBorder = Board.getDistanceFromBorder(firstLetterRow, firstLetterColumn, direction);
//			}
			wordLength = Board.selectWordLength(distanceFromBorder);
			System.out.println(firstLetterRow + "  " + firstLetterColumn + "  " + direction + "  " + wordLength );
			Variable lastVariable = Data.getVariables().get(Data.getVariables().size()-1);
			lastVariable.setPosition(firstLetterRow, firstLetterColumn);
			lastVariable.setDirection(direction);
			lastVariable.createConstraint(wordLength, Board.getBoard());
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
					Board.displayBoard();
					
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
//		}
		Data.unsetVariable();
		return false;
		
	}
	
	public static void main(String[] args) {
		new Board();
		
		  if(CSP.recursiveBactracking() == false) {
		 
			System.out.println("no sultion");
		}
		System.out.println("FINAL BOARD: ");
		Board.displayBoard();

//		System.out.println("Variables:");
//		Data.displayVariables();
//		 Board b = new Board();
//		 Board.insertWordIntoOccupationMap("mess", 0, 0, 0);
////		 Board.insertWordIntoOccupationMap("karolina", 4, 0, 0);	 
//		 Board.displayBoard();
//		 Constraint c = new Constraint();
//		 c.createConstraint(0, 4, 1, 6, Board.getBoard());
////		 c.createConstraint(0, 1, 1, 3, Board.getBoard());
//		 System.out.println("end onstraints: ");
//		 System.out.println("[" + c.endConstraint.text + "]");
//		 System.out.println("start onstraints: ");
//		 System.out.println("[" + c.beginningConstraint.text + "]");
//		
	}
}
