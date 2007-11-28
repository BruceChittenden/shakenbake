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
		int height;

		Node(String s) 
		{
			str = s;
			cnt = 1;
			left = right = null;
			height = 0;
			
			size++; // note use of inner class	
		}
	}
	
	// IncCount increments the count for a particular string
	public void IncCount(String s)
	{
		root = insert(s, root);
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
			
			root.height = Math.max(root.left.height, root.right.height) + 1;
			
			// check if we need to rotate
			if(Math.abs(root.left.height - root.right.height) >= 2)
			{
				// check if it's zig-zig or zig-zag
				if(s.compareTo(root.left.str) < 0)
				{
					// zig-zig
					Node left, middle;
					left = root.left;
					middle = root;
					
					middle.left = left.right;
					left.right = middle;
					
					root = left;
					
					// fix heights
					middle.height = Math.max(middle.left.height, middle.right.height) + 1;
					left.height = Math.max(left.left.height, left.right.height) + 1;
				
				}
				else
				{
					// zig-zag
					Node middle, left, leftRight;
					middle = root;
					left = root.left;
					leftRight = left.right;
					
					left.right = leftRight.left;
					middle.left = leftRight.right;
					leftRight.left = left;
					leftRight.right = middle;
					
					root = leftRight;
					
					//fix heights
					middle.height = Math.max(middle.left.height, middle.right.height) + 1;
					left.height = Math.max(left.left.height, left.right.height) + 1;
					leftRight.height = Math.max(leftRight.left.height, leftRight.right.height) + 1;
				}
			}
			else
				root.height = Math.max(root.left.height, root.right.height) + 1;
		}
		else if(compare > 0)
		{
			root.right = insert(s, root.right);
			
			// check if we need to rotate
			if(Math.abs(root.left.height - root.right.height) >= 2)
			{
				// check if it's zig-zig or zig-zag
				if(s.compareTo(root.right.str) > 0)
				{
					// zig-zig
					Node right, middle;
					right = root.right;
					middle = root;
					
					middle.right = right.left;
					right.left = middle;
					
					root = right;
					
					// fix heights
					middle.height = Math.max(middle.left.height, middle.right.height) + 1;
					right.height = Math.max(right.left.height, right.right.height) + 1;
				}
				else
				{
					// zig-zag
					Node middle, right, rightLeft;
					middle = root;
					right = root.right;
					rightLeft = right.left;
					
					right.left = rightLeft.right;
					middle.right = rightLeft.left;
					rightLeft.right = right;
					rightLeft.left = middle;
					
					root = rightLeft;
					
					//fix heights
					middle.height = Math.max(middle.left.height, middle.right.height) + 1;
					right.height = Math.max(right.left.height, right.right.height) + 1;
					rightLeft.height = Math.max(rightLeft.left.height, rightLeft.right.height) + 1;
				}
			}
			else
				root.height = Math.max(root.left.height, root.right.height) + 1;
		}
		else
		{
			root.cnt++;
		}
	
		return root;
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
	
	public static void main(String[] args)
	{
		System.out.println("AVL Tree Testing");
		
		HashTable table = new HashTable();
		
		table.IncCount("hello");
		table.IncCount("hello");
		
		table.IncCount("world");
		table.IncCount("world");
		table.IncCount("world");
		table.IncCount("world");
		
		table.IncCount("Happy Thanksgiving!");
		table.IncCount("Happy Thanksgiving!");
		table.IncCount("Happy Thanksgiving!");
		
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		
		table.IncCount("cool");
		
		table.IncCount("Assignment due");
		table.IncCount("Assignment due");
		
		table.IncCount("Wednesday");
		
		table.IncCount("night");
		table.IncCount("night");
		
		table.IncCount("at");
		
		table.IncCount("TWELVE!!!");
		table.IncCount("TWELVE!!!");
		table.IncCount("TWELVE!!!");
		table.IncCount("TWELVE!!!");
		table.IncCount("TWELVE!!!");
		
		StringCount[] counts = table.GetCounts();
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		
	}
}