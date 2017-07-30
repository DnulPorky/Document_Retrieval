import java.util.*;
import javax.xml.stream.events.EndDocument;

/**
 * Class representing a trie-like structure that stores the dictionary
 * @author JetLi
 *
 */
public class Trie {
	private final TrieNode root;
	
	/**
	 * Constructor for this structure that creates the root, first node
	 */
	public Trie(){
		root = new TrieNode();
	}
	
	/**
	 * Inserts word in the dictionary
	 * @param word - to be inserted
	 * @param pages - pages containing inserted word
	 */
	public void insert(String word, int[] pages){
		TrieNode current = root;
		for(int i = 0; i < word.length(); i++){
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			if(node == null){
				node = new TrieNode();
				current.children.put(ch, node);
			}
			current = node;
		}
		current.endOfWord = true;
		current.pages = pages;
	}
	
	/**
	 * Search the dictionary for a word 
	 * @param word - to be searched
	 * @return - boolean true if word was found in the dictionary
	 */
	public boolean search(String word){
		TrieNode current = root;
		for(int i = 0; i < word.length(); i++){
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			if(node == null){
				return false;
			}
			current = node;
		}
		return current.endOfWord;
	}
	
	/**
	 * Search the dictionary for a word and if found, returns its coresponding pages
	 * @param word - to be searched
	 * @return - word's corresponding pages or 0 if word wasn't found 
	 */
	public int[] searchPages(String word){
		TrieNode current = root;
		boolean wordFound;
		for(int i = 0; i < word.length(); i++){
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			if(node == null){
				return new int[1];
			}
			current = node;
		}
			return current.pages;
	}
}

