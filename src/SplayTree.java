public class SplayTree implements StringCounter
{
	Node root;
	int size;
	
	public SplayTree()
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
		root = insert(s, root);
		counter = 0;
	}
	
	// counter to help with the splaying
	private static int counter = 0;
	
	// recursive helper for IncCount
	public Node insert(String s, Node root)
	{
		if(root == null)
		{
			root = new Node(s);
			counter++;
			return root;
		}
		
		int compare = s.compareTo(root.str);
		
		if(compare < 0)
		{
			root.left = insert(s, root.left);
			
			// check if we need to do a rotation
			if(counter == 2)
			{
				// do a zig-zig or zig-zag rotation
				counter = 0;
				
				if(s.compareTo(root.left.str) < 0)
				{
					// do a zig-zig
					Node top, left, leftLeft;
					
					top = root;
					left = root.left;
					leftLeft = left.left;
					
					top.left = left.right;
					
					left.left = leftLeft.right;
					left.right = top;
					
					leftLeft.right = left;
					
					root = leftLeft;
				}
				else
				{
					// do a zig-zag
					Node top, left, leftRight;
					
					top = root;
					left = root.left;
					leftRight = left.right;
					
					top.left = leftRight.right;
					
					left.right = leftRight.left;
					
					leftRight.left = left;
					leftRight.right = top;
					
					root = leftRight;
				}
				
			}
			else if(root == this.root)
			{
				// do a zig rotation
				Node top, left;
				
				top = root;
				left = root.left;
				
				top.left = left.right;
				
				left.right = top;
				
				root = left;
			}
			
			counter++;
			return root;
		}
		else if(compare > 0)
		{
			root.right = insert(s, root.right);
			
			// check if we need to do a rotation
			if(counter == 2)
			{
				// do a zig-zig or zig-zag rotation
				counter = 0;
				
				if(s.compareTo(root.right.str) > 0)
				{
					// do a zig-zig
					Node top, right, rightRight;
					
					top = root;
					right = root.right;
					rightRight = right.right;
					
					top.right = right.left;
					
					right.right = rightRight.left;
					right.left = top;
					
					rightRight.left = right;
					
					root = rightRight;
				}
				else
				{
					// do a zig-zag
					Node top, right, rightLeft;
					
					top = root;
					right = root.right;
					rightLeft = right.left;
					
					top.right = rightLeft.left;
					
					right.left = rightLeft.right;
					
					rightLeft.left = right;
					rightLeft.right = top;
					
					root = rightLeft;
				}
				
			}
			else if(root == this.root)
			{
				// do a zig rotation
				Node top, right;
				
				top = root;
				right = root.right;
				
				top.right = right.left;
				
				right.left = top;
				
				root = right;
			}
			
			counter++;
			return root;
		}
		else
		{
			root.cnt++;
			counter++;
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
	
	public static void main(String[] args)
	{
		System.out.println("Splay Tree Testing");
		
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