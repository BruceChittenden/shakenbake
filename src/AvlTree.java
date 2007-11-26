public class AvlTree implements StringCounter
{
	Node root;
	int size;
	
	public AvlTree()
	{
		root = null;
		size = 0;
	}
	
	private class Node 
	{
		Node left, right;
		String str;
		int cnt;

		Node(String s) 
		{
			str = s;
			cnt = 1;
			left = right = null;
			size++; // note use of inner class
		}
	}
	
	// IncCount increments the count for a particular string
	public void IncCount(String s)
	{
		insert(s, root);
	}

	// recursive helper for IncCount -- NOT DONE! DOESN'T BALANCE
	public Node insert(String s, Node root)
	{
		if(root == null)
		{
			root = new Node(s);
			return root;
		}
		
		int compare = s.compareTo(root.str);
		
		if(compare < 0)
		{
			root.left = insert(s, root.left);
		}
		else if(compare > 0)
		{
			root.right = insert(s, root.right);
		}
		else
		{
			root.cnt++;
			return root;
		}
	}
	
	// GetSize returns the number of strings
	public int GetSize()
	{
		return size;
	}

	// GetCounts returns an array of all the string-count pairs
	// in the dictionary, sorted lexicographically by strings.
	// We've defined a StringCount container class
	// above to store the String-int pairs.
	public StringCount[] GetCounts()
	{
		StringCount[] counts = new StringCount[size];
		GetCounts(root, counts, 0);	
		return counts;
	}
	
	// recursive helper for GetCounts
	private int GetCounts(Node root, StringCount[] counts, int index)
	{
		if(root == null)
			return index;
		
		index = GetCounts(root.left, counts, index);
		
		counts[index] = new StringCount(root.str, root.cnt);
		index++;
		
		index = GetCounts(root.right, counts, index);
		
		return index;
	}
}