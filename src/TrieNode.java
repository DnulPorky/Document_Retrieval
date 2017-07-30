/**
 * Class that represents a node in my trie-like dictionary structure.
 * Contains a map to its childrens, a boolean that signals end of word, 
 * and if so, the pages that contains that word
 * @author JetLi
 *
 */
public class TrieNode {
	MyMap children;
	boolean endOfWord;
	int[] pages;
	
	public TrieNode(){
		children = new MyMap();
		endOfWord = false;
		pages = new int[1];
	}
}
