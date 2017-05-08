package crossword;

import java.io.BufferedReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class WordsList {
	public static HashSet<String> wordsList = new HashSet<String>();
	
	static {
		readWordsFromFile();
	}
	public static void readWordsFromFile() {
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
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.out.println("The file cannot be found");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("The file cannot be read");
		}
	}
}
