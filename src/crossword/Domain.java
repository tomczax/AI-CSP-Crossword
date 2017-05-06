package crossword;
import java.util.HashMap;
import java.util.Map;

public class Domain {
	
	public String word;
	public int score;
	
	public Domain(String w, int s) {
		this.word = w;
		this.score = s;
	}
	
	public void addWord(String w) {
		this.word = w;
	}
	
	public void gradeWord() {
		Map<Character, Integer> grades = new HashMap<>();
		grades.put('a', 1);
		grades.put('b', 3);
		grades.put('c', 3);
		grades.put('d', 2);
		grades.put('e', 1);
		grades.put('f', 4);
		grades.put('g', 2);
		grades.put('h', 4);
		grades.put('i', 1);
		grades.put('j', 8);
		grades.put('k', 5);
		grades.put('l', 1);
		grades.put('m', 3);
		grades.put('n', 1);
		grades.put('o', 1);
		grades.put('p', 3);
		grades.put('q', 10);
		grades.put('r', 1);
		grades.put('s', 1);
		grades.put('t', 1);
		grades.put('u', 1);
		grades.put('v', 4);
		grades.put('w', 4);
		grades.put('x', 8);
		grades.put('y', 4);
		grades.put('z', 10);
		
		for ( int i=0; i<this.word.length(); i++) {
			this.score += grades.get(word.charAt(i));
		}
		
	}

}
