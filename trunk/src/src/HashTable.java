// HashTable
// Eriel Thomas
// This class is a Hash Table that implements the StringCounter interface.

public class HashTable implements StringCounter
{
	// the array of HashEntry that serves as our table
	private HashEntry[] table;
	
	// the size of the table
	private int tableSize;
	
	// the number of entries in the table
	private int numEntries;
	
	// HashEntry
	// a class to store the keys and counts in the table
	private static class HashEntry
	{
		public String key;
		public int value;
		
		public HashEntry(String key)
		{
			value = 1;
			this.key = key;
		}
	}
	
	// HashTable
	// constructor to initialize the table
	public HashTable()
	{
		numEntries = 0;
		tableSize = 10007;
		table = new HashEntry[tableSize];
	}
	
	
	// Prime finding functions from Peter
	/**
	 * Internal method to find a prime number at least as large as n.
	 * @param n the starting number (must be positive).
	 * @return a prime number larger than or equal to n.
	 */
	private static int nextPrime( int n )
	{
		if( n % 2 == 0 )
			n++;

		for( ; !isPrime( n ); n += 2 )
			;

		return n;
	}

	/**
	 * Internal method to test if a number is prime.
	 * Not an efficient algorithm.
	 * @param n the number to test.
	 * @return the result of the test.
	 */
	private static boolean isPrime( int n )
	{
		if( n == 2 || n == 3 )
			return true;

		if( n == 1 || n % 2 == 0 )
			return false;

		for( int i = 3; i * i <= n; i += 2 )
			if( n % i == 0 )
				return false;

		return true;
	}
	
	// hash
	// a hash routine for string objects
	// it takes a string to hash, the tableSize, and returns the hash value
	private int hash(String key, int tableSize)
	{
		int hashVal = 0;
		
		for(int i = 0; i < key.length(); i++)
			hashVal = 37 * hashVal + key.charAt(i);
		
		hashVal %= tableSize;
		
		if(hashVal < 0)
			hashVal += tableSize;
		
		return hashVal;
	}
	
	// rehashTable
	// rehashes the table by doubling the size
	private void rehashTable()
	{
		int newSize = nextPrime(tableSize*2);
		
		HashEntry[] temp = new HashEntry[newSize];
		HashEntry[] swap = table;
		
		table = temp;
		temp = swap;
		
		for(int i = 0; i < tableSize; i++)
		{
			HashEntry entry = temp[i];
			
			if(entry != null)
			{
				int index = insert(entry.key);
				table[index].value = entry.value;
			}
		}
		
		tableSize = newSize;
	}
	
	// contains
	// returns an entry if the string is contained in the table
	// otherwise, null is returned
	private HashEntry contains(String s)
	{
		for(int i = 0; i < tableSize; i++)
			if(table[i] != null && table[i].key.compareTo(s) == 0)
				return table[i];
		
		return null;
	}
	
	// insert
	// inserts string s into the table
	private int insert(String s)
	{
		int hash = hash(s,tableSize);
		
		if(table[hash] == null)
			table[hash] = new HashEntry(s);
		else
		{
			for(int i = hash+1; i != hash; i++)
			{
				if(i == tableSize)
					i=0;
				
				if(table[i] == null)
				{
					table[i] = new HashEntry(s);
					hash = i;
					break;
				}
			}
		}
		
		return hash;
	}
	
	// IncCount 
	// increments the count for a particular string
	public void IncCount(String s) 
	{
		if(numEntries+1 >= tableSize)
			rehashTable();
		
		HashEntry entry = contains(s);
		
		if(entry == null)
		{
			insert(s);
			numEntries++;
		}
		else
			entry.value++;
	}

	// GetSize 
	// returns the number of strings
	public int GetSize() 
	{
		return numEntries;
	}

	// GetCounts 
	// returns an array of all the string-count pairs
	// in the dictionary, sorted lexicographically by strings.
	// We've defined a StringCount container class
	// above to store the String-int pairs.
	public StringCount[] GetCounts() 
	{
		StringCount[] counts = new StringCount[numEntries];
		int entries = 0;
		
		for(int i = 0; i < tableSize; i++)
		{
			if(table[i] != null)
			{
				insertionSort(new StringCount(table[i].key, table[i].value), counts, entries);
				entries++;
			}
		}
		
		return counts;
	}
	
	// insertionSort
	// inserts the string count s into the proper place in the array
	public void insertionSort(StringCount s, StringCount[] array, int entries)
	{
		int insert = 0;
		
		for(int i = 0; i < entries; i++, insert++)
		{
			if(s.str.compareTo(array[i].str) <= 0)
				break;
		}
		
		if(insert == entries)
			array[insert] = s;
		else
		{
			for(int i = entries; i > insert; i--)
				array[i] = array[i-1];
			
			array[insert] = s;
		}
	}
	
	// main
	// a unit test to make sure that the AVL tree functions properly
	public static void main(String[] args)
	{
		System.out.println("Hash Table Testing");
		
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