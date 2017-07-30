/**
 * Class representing an element of MyMap, 
 * and containing one char from a word in the dictionary and 
 * its corresponding node in the the trie-like(dictionary) structure
 * @author JetLi
 *
 */
public class MyMapEntry {
		private char ch;
		TrieNode trNode;

		public MyMapEntry(){
			ch = '\0';
			trNode = null;
		}
		
		public char getCh() {
			return ch;
		}
		public TrieNode getTrNode() {
			return trNode;
		}
		
		public void set(char ch, TrieNode trNode){
			this.ch = ch;
			this.trNode = trNode;
		}
}
