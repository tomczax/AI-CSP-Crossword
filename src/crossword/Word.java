package crossword;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.HashSet;

//Represents Variable in the CSP problem.
public class Word {
	// There are two directions in a crossword. HORIZONTAL and VERTICAL.

	// The value of the variable
	private String word;
	private int firstLetterRow;
	private int firstLetterColumn;
	/*
	 * Score for current variable(word) value - UNSIGNED
	 */
	private int score;
	/*
	 * Direction is randomly selected by Crossword_board class.
	 */
	private int direction;
	/*
	 * Class of all four constraints(beginning, end, path, side)
	 */
	private Constraint constraint;
	// Class contains string and points for this particular string.
	ArrayList<Domain> domain;

	public Word() {
		this.direction = 0;
		this.word = "";
	}
	
	public int getLength() { return this.word.length(); }
//	public int getPosition() { return this.firstLetterPosition; }
	public String getWord() { return this.word; }
	public int getDirection() { return this.direction; }
	public int getScore() { return this.score; }
	public ArrayList<Domain> getDomain() { return this.domain; }
	
	public void setPosition(int row, int column) { this.firstLetterRow = row; this.firstLetterColumn = column; }
	public void setDirection(int direction) { this.direction = direction; }
	public void assignWord(String wordToAssign) { this.word = wordToAssign; }
	public boolean isAssigned() {
		if(word.length() == 0)
			return true;
		return false;
	}
	public void eraseWord() { this.word = ""; }
	public void createConstraint(int wordLength, char [][] occupationMap, int width, int height) {
		this.constraint.createConstraint(this.firstLetterRow, this.firstLetterColumn, this.direction, wordLength, occupationMap, width, height);
	}
	public Constraint getConstraint() { return this.constraint; }
	public int getBegginingConstraintLength() { return this.constraint.getBegginingConstraintLength(); }
	public int getEndConstraintLength() { return this.constraint.getEndConstraintLength(); }
	public void setDomain(ArrayList<String> words) { 
		for(String w : words) {
			this.domain.add((new Domain(w, 0)));
		}
	}
	public boolean isDomainEmpty() {
		return this.domain.isEmpty();
	}
	public String getDomainLastElement() {
		this.score = this.domain.get(this.domain.size()-1).score;
		return this.domain.get(this.domain.size()-1).word;
	}
	public void removeDomainLastElement() {
		this.domain.remove(this.domain.size()-1);
	}
	public void gradeDomain() {
		for(Domain d : this.domain) {
			d.gradeWord();
		}
	}
	public void sortDomain() {
		Collections.sort(this.domain, new DomainComparator());
	}	

	public static void main(String [] args) {
		Word w = new Word();
		System.out.println(w.word.length());
	}
}
