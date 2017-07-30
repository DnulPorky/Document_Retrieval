import java.io.*;
import java.lang.Character.Subset;
import java.util.Arrays;
import java.util.Scanner;
/**
 * 
 * Class that defines type dictionary
 * 
 * @author JetLi
 *
 */
public class Dictionary {

	/**
	 * 
	 * Number of entries from dictionary
	 * 
	 */
	private int numEntries = 0;
	/**
	 * The structure that stores the dictionary, similar to a trie in many ways. 
	 */
	private Trie myDictionary = new Trie();
	/**
	 * An array of expressions contained by an input line
	 */
	String[] expressions = new String[100];
	/**
	 * The numbers of expressions contained by a statement(line);
	 */
	int expressionNr = 0;
	/**
	 * An int array representing the result of a subexpression(E1/E2) from an expression of type (E1 and/or E2) 
	 */
	int[] result1 = new int[1];

	/**
	 * Breaks a string into an array of words
	 * @param str - the string to be split
	 * @return - an array of words from a string
	 */
	public String[] wordExtractor(String str) {
		char[] charArray = new char[str.length()];
		int index = 0;
		for (char c : str.toCharArray()) {
			if (c != '(' && c != ')') {
				charArray[index++] = c;
			}
		}
		char[] newCharArray = new char[index];
		for (int i = 0; i < index; i++) {
			newCharArray[i] = charArray[i];
		}
		String str1 = new String(newCharArray);

		String[] words = str1.split("[^a-zA-Z]+");
		return words;
	}

	
	/**
	 * Breaks an input line into subexpressions in order to be processed
	 * @param input - the line to be split
	 */
	public void extractExpresion(String input) {
		int index = 0;
		int start = 0;
		int end = 0;

		while (input.charAt(index++) != ')') {
			end++;
			if (input.charAt(index - 1) == '(') {
				start = index;
			}
		}
		expressions[expressionNr++] = input.substring(start, end);
		if (expressions[expressionNr - 1].charAt(0) == ' ') {
			expressions[expressionNr - 1] = input.substring(start + 1, end);
		}
		if (start > 0 && end < input.length() && index < input.length()) {
			extractExpresion(input.substring(0, start - 1).concat(input.substring(end + 1)));
		}
		String[] newStr = new String[expressionNr];
		System.arraycopy(expressions, 0, newStr, 0, expressionNr);
		expressions = newStr;
	}

	/**
	 * Process the array of expressions contained by a line and returns the final result
	 * @return - an int array representing the final result
	 */
	public int[] processExpresions() {
		int[] result = new int[1];
		for (int i = 0; i < expressions.length; i++) {
			String[] words = expressions[i].split("\\s");
			if (words.length == 3 && i == 0) {
				result = searchWords(words[0], words[1], words[2]);
			} else {
				if (words.length == 2) {
					for (int j = 0; j < 2; j++) {
						if (words[j].equals("or") && j == 0) {
							result = reunion(searchWord(words[1]), result);
						} else {
							if (words[j].equals("and") && j == 0) {
								result = intersection(searchWord(words[1]), result);
							} else {
								if (words[j].equals("or") && j == 1) {
									result = reunion(searchWord(words[0]), result);
								} else {
									if (words[j].equals("and") && j == 1) {
										result = intersection(searchWord(words[0]), result);
									}
								}
							}
						}
					}
				} else {
					if (words.length == 3) {
						String[] newExpresions = new String[expressions.length - i];
						System.arraycopy(expressions, i, newExpresions, 0, expressions.length - i);
						expressions = newExpresions;

						result1 = result;
						result = processExpresions();
					}
					if (words.length == 1 && words[0].equals("or")) {
						result = reunion(result1, result);
					}
					if (words.length == 1 && words[0].equals("and")) {
						result = intersection(result1, result);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param a1 - int array
	 * @param a2 - int array
	 * @return - common elements contained by both arrays
	 */
	public int[] intersection(int[] a1, int[] a2) {
		if (a1[0] == 0) {
			return new int[1];
		}
		if (a2[0] == 0) {
			return new int[1];
		}
		int[] tempResult = new int[a1.length + a2.length];
		int index = 0;
		for (int i = 0; i < a1.length; i++) {
			for (int j = 0; j < a2.length; j++) {
				if (a1[i] == a2[j]) {
					tempResult[index++] = a1[i];
				}
			}
		}
		int[] result = new int[index];
		if (index == 0) {
			return new int[1];
		}
		System.arraycopy(tempResult, 0, result, 0, index);
		return result;
	}

	/**
	 * 
	 * @param a1 - int array
	 * @param a2 - int array
	 * @return - all elements contained by both arrays 
	 */
	public int[] reunion(int[] a1, int[] a2) {
		if (a1[0] == 0) {
			return a2;
		}
		if (a2[0] == 0) {
			return a1;
		}
		if (a1[0] == 0 && a2[0] == 0) {
			return new int[1];
		}
		int[] tempResult = new int[a1.length + a2.length];
		System.arraycopy(a1, 0, tempResult, 0, a1.length);
		int index = a1.length;
		int j = 0;
		for (int i = 0; i < a2.length; i++) {
			int flag = 0;
			for (j = 0; j < a1.length; j++) {
				if (tempResult[j] == a2[i]) {
					flag = 1;
				}
			}
			if (flag == 0) {
				tempResult[index++] = a2[i];
			}
		}
		int[] result = new int[index];
		System.arraycopy(tempResult, 0, result, 0, index);
		return result;
	}

	/**
	 * Search dictionary for a word
	 * @param word - word to be searched
	 * @return - int array of pages that contains searched word 
	 */
	public int[] searchWord(String word) {
		return myDictionary.searchPages(word);
	}

	/**
	 * Search 2 words in the dictionary and 
	 * sends the results to proper operation (intersection/reunion) and returns the result of these operation
	 * @param word1 - word to be searched
	 * @param operation - operation to be done('or' - reunion/'and' - intersection)
	 * @param word2 - word to be searched
	 * @return result of operation
	 */
	public int[] searchWords(String word1, String operation, String word2) {
		if (operation.compareTo("or") == 0) {
			return reunion(myDictionary.searchPages(word1), myDictionary.searchPages(word2));
		}
		if (operation.compareTo("and") == 0) {
			return intersection(myDictionary.searchPages(word1), myDictionary.searchPages(word2));
		}
		return new int[1];
	}
	
	/**
	 * Takes a line read from dict.txt file, 
	 * extracts the word and corresponding pages and 
	 * send those to be inserted into dictionary trie-like structure
	 * @param str - line from dict.txt
	 */
	public void processLine(String str) {
		String[] partial = str.split("\\s");
		String tempWord = "";
		int[] tempPages = new int[partial.length - 1];
		int index = 0;
		for (int i = 0; i < partial.length; i++) {
			if (i == 0) {
				tempWord = partial[i];
			} else {
				tempPages[index++] = Integer.valueOf(partial[i]);
			}
		}
		myDictionary.insert(tempWord, tempPages);
	}

	/**
	 * 
	 * Constructor for dictionary. Receives the name of the text
	 * file(dictionary) as param and reads the text file line by line
	 * 
	 * @param inputFile
	 *            Name of the text file(dictionary)
	 */
	public Dictionary(String inputFile) {
		try {
			File dictFile = new File(inputFile);
			Scanner reader = new Scanner(dictFile);
			String strLine;

			while (reader.hasNextLine()) {
				strLine = reader.nextLine(); // current line from dictionary
				// System.out.println(strLine);
				numEntries++;
				String[] part = strLine.split("\\s");
				for (String str : part) {
					processLine(strLine);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * 
	 * Method that returns the number of entries from dictionary
	 * 
	 * @return Number of entries from dictionary
	 */
	public int getnumEntries() {
		return numEntries;
	}
}
