// AvlTree
// Eriel Thomas
// This class is an AVL Tree that implements the StringCounter interface.

public class AvlTree implements StringCounter
{
	// Root of the tree
	Node root;
	
	// Number of entries in the tree
	int size;
	
	// AvlTree
	// initializes the root of the tree to null and the size of the tree to 0
	public AvlTree()
	{
		root = null;
		size = 0;
	}
	
	// inner Node class
	private class Node 
	{
		// left and right children
		Node left, right;
		
		// the string key and the count of the number of occurences of that string
		String str;
		int cnt;
		
		// the height of the tree from that node down
		int height;

		// Node
		// initializes the node and increment the size of the tree
		Node(String s) 
		{
			str = s;
			cnt = 1;
			left = right = null;
			height = 0;
			
			size++; // note use of inner class	
		}
	}
	
	// IncCount 
	// increments the count for a particular string
	public void IncCount(String s)
	{
		root = insert(s, root);
	}

	// height
	// a helper function to return the height of a node
	public int height(Node node)
	{
		if(node == null)
			return -1;
		else
			return node.height;
	}
	
	// insert
	// recursive helper for IncCount
	// takes a string s to insert and a root to start at
	// returns the new root after insertion of the string and balancing
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
			
			root.height = Math.max(height(root.left), height(root.right)) + 1;
			
			// check if we need to rotate
			if(Math.abs(height(root.left) - height(root.right)) >= 2)
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
					middle.height = Math.max(height(middle.left), height(middle.right)) + 1;
					left.height = Math.max(height(left.left), height(left.right)) + 1;
				
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
					middle.height = Math.max(height(middle.left), height(middle.right)) + 1;
					left.height = Math.max(height(left.left), height(left.right)) + 1;
					leftRight.height = Math.max(height(leftRight.left), height(leftRight.right)) + 1;
				}
			}
			else
				root.height = Math.max(height(root.left), height(root.right)) + 1;
		}
		else if(compare > 0)
		{
			root.right = insert(s, root.right);
			
			// check if we need to rotate
			if(Math.abs(height(root.left) - height(root.right)) >= 2)
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
					middle.height = Math.max(height(middle.left), height(middle.right)) + 1;
					right.height = Math.max(height(right.left), height(right.right)) + 1;
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
					middle.height = Math.max(height(middle.left), height(middle.right)) + 1;
					right.height = Math.max(height(right.left), height(right.right)) + 1;
					rightLeft.height = Math.max(height(rightLeft.left), height(rightLeft.right)) + 1;
				}
			}
			else
				root.height = Math.max(height(root.left), height(root.right)) + 1;
		}
		else
		{
			root.cnt++;
		}
	
		return root;
	}
	
	// GetSize 
	// returns the number of strings
	public int GetSize()
	{
		return size;
	}

	// GetCounts 
	// returns an array of all the string-count pairs
	// in the dictionary, sorted lexicographically by strings.
	// We've defined a StringCount container class
	// above to store the String-int pairs.
	public StringCount[] GetCounts()
	{
		StringCount[] counts = new StringCount[size];
		GetCounts(root, counts, 0);	
		return counts;
	}
	
	// GetCounts
	// recursive helper for GetCounts
	// traverses the tree and adds the strings and counts to the array of StringCount starting at index
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
	
	// main
	// a unit test to make sure that the AVL tree functions properly
	public static void main(String[] args)
	{
		System.out.println("AVL Tree Testing");
		
		AvlTree table = new AvlTree();
		
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