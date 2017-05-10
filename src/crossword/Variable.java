package crossword;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.HashSet;

//Represents Variable in the CSP problem.
public class Variable {
	// The value of the variable
	private String value;
	private int firstLetterRow;
	private int firstLetterColumn;
	/*
	 * Direction is randomly selected by Board class.
	 */
	private int direction;
	/*
	 * Class of all four constraints(beginning, end, path, side)
	 */
	private Constraint constraint = new Constraint();
	// Class contains string and score for this particular string.
//	ArrayList<Domain> domain = new ArrayList<Domain>();

	public Variable() {
		this.direction = 0;
		this.value = "";
	}
	
	public int getLength() { return this.value.length(); }
	public int[] getPosition() { return new int[]{this.firstLetterRow, this.firstLetterColumn}; }
	public String getWord() { return this.value; }
	public int getDirection() { return this.direction; } 
//	public ArrayList<Domain> getDomain() { return this.domain; }
	
	public void setPosition(int row, int column) { this.firstLetterRow = row; this.firstLetterColumn = column; }
	public void setDirection(int direction) { this.direction = direction; }
	public void assignWord(String wordToAssign) { this.value = wordToAssign; }
	public boolean isAssigned() {
		if(value.length() == 0)
			return true;
		return false;
	}
	public void eraseWord() { this.value = ""; }
	public void createConstraint(int wordLength, char [][] occupationMap) {
		this.constraint.createConstraint(this.firstLetterRow, this.firstLetterColumn, this.direction, wordLength, occupationMap);
	}
	public Constraint getConstraint() { return this.constraint; }
	public int getBegginingConstraintLength() { return this.constraint.getBegginingConstraintLength(); }
	public int getEndConstraintLength() { return this.constraint.getEndConstraintLength(); }
//	public void setDomain(ArrayList<String> words) { 
//		for(String w : words) {
//			this.domain.add((new Domain(w, 0)));
//		}
//	}
//	public boolean isDomainEmpty() {
//		return this.domain.isEmpty();
//	}
//	public String getDomainLastElement() {
//		this.score = this.domain.get(this.domain.size()-1).score;
//		return this.domain.get(this.domain.size()-1).word;
//	}
//	public void removeDomainLastElement() {
//		this.domain.remove(this.domain.size()-1);
//	}
//	public void gradeDomain() {
//		for(Domain d : this.domain) {
//			d.gradeWord();
//		}
//	}
//	public void sortDomain() {
//		Collections.sort(this.domain, new DomainComparator());
//	}	
}
