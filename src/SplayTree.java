// SplayTree
// Eriel Thomas
// This class is a Splay Tree that implements the StringCounter interface.

public class SplayTree implements StringCounter
{
	
	// Root of the tree
	private Node root;
	
	// Number of entries in the tree
	private int size;
	
	// counter to help with the splaying
	private int counter;
	
	// SplayTree
	// initializes the root of the tree to null and the size of the tree to 0
	public SplayTree()
	{
		root = null;
		size = 0;
		counter = 0;
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
		counter = 0;
	}
	
	// insert
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
					
					rightLeft.left = top;
					rightLeft.right = right;
					
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
		System.out.println("Splay Tree Testing");
		
		SplayTree table = new SplayTree();
		
		table.IncCount("hello");
		table.IncCount("hello");
		
		table.IncCount("world");
		table.IncCount("world");
		table.IncCount("world");
		table.IncCount("world");
		
		StringCount[] counts = table.GetCounts();
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		System.out.println();
		
		table.IncCount("Happy Thanksgiving!");
		table.IncCount("Happy Thanksgiving!");
		table.IncCount("Happy Thanksgiving!");
		
		counts = table.GetCounts();
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		System.out.println();
		
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		table.IncCount("Food");
		
		counts = table.GetCounts();
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		System.out.println();
		
		table.IncCount("cool");
		
		// BREAKS HERE!!!
		
		counts = table.GetCounts();
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		System.out.println();
		
		table.IncCount("Assignment due");
		table.IncCount("Assignment due");
		
		counts = table.GetCounts();
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		System.out.println();
		
		table.IncCount("Wednesday");
		
		counts = table.GetCounts();
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		System.out.println();
		
		table.IncCount("night");
		table.IncCount("night");
		
		table.IncCount("at");
		
		counts = table.GetCounts();
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		System.out.println();
		
		table.IncCount("TWELVE!!!");
		table.IncCount("TWELVE!!!");
		table.IncCount("TWELVE!!!");
		table.IncCount("TWELVE!!!");
		table.IncCount("TWELVE!!!");
		
		counts = table.GetCounts();
		String output = "";
		
		for(int i = 0; i<counts.length; i++)
		{
			if(counts[i] != null)
			{
				System.out.print("[" + counts[i].str +"," + counts[i].cnt + "], ");
				output += "[" + counts[i].str +"," + counts[i].cnt + "], ";
			}
			else
				System.out.print("NULL!!!!! " + i);
		}
		
		System.out.println();
		
		if(output.compareTo("[Assignment due,2], [Food,7], [Happy Thanksgiving!,3], [TWELVE!!!,5], [Wednesday,1], [at,1], [cool,1], [hello,2], [night,2], [world,4], ") == 0)
			System.out.println("Success! Output is correct.");
		else
			System.out.println("Failure! The output wasn't correct. Output was: \"" + output +"\"");
	}
}