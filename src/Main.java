import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * Class Main
 * 
 * @author JetLi
 *
 */
public class Main {

	/**
	 * 
	 * Main method
	 * 
	 * @param args
	 *            Arguments received by program
	 */
	public static void main(String[] args) {
		Dictionary dict = new Dictionary("dict.txt");
		System.out.println("We have " + dict.getnumEntries() + " dictionary entries.");
		System.out.println("You may search the dictionary for one or more words.");
		System.out.println(
				"Your input should be an expression E like: (w), (w1 and w2), (w1 or w2), (w1 or (w2 and w3)) ");
		System.out.println("where w, w1, w2, w3 are words from dictionary. ");
		System.out.println(
				"Also, your input may be like (E1 and E2), or like (E1 or E2), where E1 and E2 are expressions as the ones above.");
		System.out.println("For example you may insert: ((ana and gigi) or (dan and (ionel and vasile))) ,");
		System.out.println(
				"which is an expression of type (E1 or E2), E1 = (ana and gigi), E2 = (dan and (ionel and vasile))");
		System.out.println("Please insert your input or type 'quit' to quit the program:");
		BufferedReader in = null;
		
		/**
		 * Reads input from keyboard line by line and sends those to be processed until 'quit' is typed.
		 */
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			in.hashCode();
			String line = in.readLine();
			while (!line.equals("quit")) {
				String[] words = dict.wordExtractor(line);
				if (words.length == 1) {
					int[] result = dict.searchWord(words[0]);
					for (int page : result) {
						System.out.print(page + " ");
					}
				} else {
					if (words.length == 3) {
						int[] result = dict.searchWords(words[0], words[1], words[2]);
						for (int page : result) {
							System.out.print(page + " ");
						}
					} else {
						dict.extractExpresion(line);
						int[] result = dict.processExpresions();
						for (int page : result) {
							System.out.print(page + " ");
						}

					}
				}
				dict.result1 = new int[1];
				dict.expressionNr = 0;
				dict.expressions = new String[100];
				System.out.println();
				line = in.readLine();
			}

		} catch (IOException e) {
			System.out.println("Catch block called");
			e.printStackTrace();
		}catch (NullPointerException e) {
			System.out.println("Catch null pointer  block called");
			e.printStackTrace();
		}	
		System.out.println("  [[[[[[[[[ [[[[[ Outside try catch");
	}
}
