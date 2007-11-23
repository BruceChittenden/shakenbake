public class HashTable implements StringCounter
{
	private HashEntry[] table;
	private int tableSize;
	private int numEntries;
	
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
	
	// constructor to initialize the table
	public HashTable()
	{
		numEntries = 0;
		tableSize = 10007;
		table = new HashEntry[tableSize];
	}
	
	// return true if num is prime, otherwise false
	private boolean isPrime(int num)
	{
		if(num % 2 == 0 && num != 2)
			return false;
		
		int mid = (int)Math.sqrt(num);
		
		for(int i = 3; i < mid; i++)
			if(num%i == 0)
				return false;
		
		return true;
	}
	
	// returns the next prime number after num
	private int nextPrime(int num)
	{
		int prime = num+1;
		for(; !isPrime(prime); prime++);
		
		return prime;
	}
	
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
	
	private HashEntry contains(String s)
	{
		for(int i = 0; i < tableSize; i++)
			if(table[i] != null && table[i].key.compareTo(s) == 0)
				return table[i];
		
		return null;
	}
	
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
	
	// IncCount increments the count for a particular string
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

	// GetSize returns the number of strings
	public int GetSize() 
	{
		return numEntries;
	}

	// GetCounts returns an array of all the string-count pairs
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
	
	public static void main(String[] args)
	{
		System.out.println("HashTable Testing");
		
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