/**
 * Class that represents one of the fields of a node in dictionary(trie) structure
 * @author JetLi
 *
 */
public class MyMap {
	
	private MyMapEntry[] entries;

	public MyMap() {
		entries = new MyMapEntry[1];
		entries[0] = new MyMapEntry();
	}
	/**
	 * Adding new record in this structure
	 * @param ch - a letter from a word in the dictionary
	 * @param pages - corresponding node for the letter
	 */
	public void put(char ch, TrieNode pages) {
		int capacity = entries.length;
		if(entries[0].getTrNode() == null){
			entries[0].set(ch, pages);
		}
		else{
			MyMapEntry[] newEntries = new MyMapEntry[capacity + 1];
			System.arraycopy(entries, 0, newEntries, 0, capacity);
				entries = newEntries;
				entries[capacity] = new MyMapEntry();
				entries[capacity].set(ch, pages);
			
		}
	}
	
	/**
	 * 
	 * @param ch - letter to be searched
	 * @return - the node corresponding to the searched letter
	 */
	public TrieNode get(char ch){
		for(int i = 0; i < entries.length; i++){
			if(ch == entries[i].getCh()){
				return entries[i].getTrNode();
			}
		}
		return null;
	}
}
