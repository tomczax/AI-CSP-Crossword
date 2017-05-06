package crossword;

import java.io.BufferedReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordsList {
	public List<String> wordsList = new ArrayList<String>();
//	public List<String> sortedWordsList = new ArrayList<String>();
	static int size;

	public WordsList() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("lemma.al"));
			String line;

			while ((line = reader.readLine()) != null) {
				String[] words = line.split(" ");
				if (words[3].equalsIgnoreCase("adv") || words[3].equalsIgnoreCase("v") || words[3].equalsIgnoreCase("a")
						|| words[3].equalsIgnoreCase("n")) {
					wordsList.add(words[2]);
				}
			}
			 size = wordsList.size();
//			sortedWordsList = wordsList;
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.out.println("The file cannot be found");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("The file cannot be read");
		}
	}

	public static void main(String [] args) {
		WordsList l = new WordsList();
		System.out.println(l.size);
		System.out.println(l.wordsList);
	}
	
}
