/**
 * Alex Meng
 * CSE326 Project 3
 * File: WordCount.java
 * 
 * This file is a driver program which counts the frequency
 * of words in a given file. 
 * 
 */


/**
 * The WordCount class is designed to count the frequency of each word
 * in a given file.
 */
public class WordCount {

	/**
	 * This is method takes the given file name, data structure, and booleans to
	 * open the file, store the frequency information in the data structure
	 * and uses the booleans to determine if it needs to print out
	 * the words in descending frequency order and secondarily by lexiographical order.
	 * 
	 * @param file The name of the file to read.
	 * @param SC The type of data structure to use.
	 * @param freq To determine if it needs to print out the word/frequency pairs.
	 * @param unique To determine if it needs to print out how many unique words are in the file.
	 */
  public static void countWords(String file, StringCounter SC, boolean freq, boolean unique) {    
    try {
      String word;
      FileWordReader fwr = new FileWordReader(file);
      while((word=fwr.nextWord())!=null)
        SC.IncCount(word);
    } catch (Throwable error) {
      System.err.println( "Error processing \n" + file + error);
      System.exit(1);
    }

    // Use insertion sort to sort the BST by number of occurrences.
    // Note that we rely on GetCounts being sorted by name.  This
    // is an example of bad coupling!  Unfortunately that's what
    // you get using code from a "history professor".

    StringCount[] cnt = SC.GetCounts();
    if (cnt != null && freq) {
    	
//    	//create a mergesort
//      for (int i = 1; i < cnt.length; i++) {
//        StringCount x = cnt[i];
//        int j;
//        for (j = i-1; j >= 0; j--) {
//          if (cnt[j].cnt >= x.cnt)
//            break;
//          cnt[j+1] = cnt[j];
//        }
//        cnt[j+1] = x;
//      }
      cnt = mergeSort ( cnt );
      for (int i = 0; i < cnt.length; i++)
        System.out.println(cnt[i].cnt + " \t" + cnt[i].str);
    } else if ( freq )
      System.out.println("No words found!");
    
    if ( unique ) 
    	System.out.println( "Total number of unique words: " + cnt.length );
    
  }

  private static StringCount[] mergeSort( StringCount[] list ) {
	  if ( list.length <=1 )
		  return list;
	  int mid = list.length / 2;
	  StringCount[] left = new StringCount[ mid ];
	  StringCount[] right = new StringCount[ list.length - mid ];

	  for (int i=0; i < list.length; i++) {
		  if ( i < mid  )
			  left[i] = list[i];
		  else 
			  right[i - mid] = list[i];
	  }
	  
	  left = mergeSort( left );
	  right = mergeSort( right );
	  return merge( left, right );
  }
  private static StringCount[] merge( StringCount[] left, StringCount[] right ) {
	  StringCount[] list = new StringCount[ left.length  + right.length ];
	  int listCounter = 0;
	  int i=0,j = 0;
	  while ( i < left.length && j < right.length ) {
		  if ( left[i].cnt > right[j].cnt ) {
			  list[ listCounter ] = left[i];
		  	  i++;
		  }  else {
			  list[ listCounter ] = right[j];
		  	  j++;
		  }
		  
		  listCounter++;
			
	  }
		  if ( i != left.length ) {
			  for ( ; i < left.length; i++) {
				  list[ listCounter ] = left[ i ];
				  listCounter++;
			  }
		  }
		  
		  if ( j != right.length ) {
			  for ( ; j < right.length; j++) {
				  list[ listCounter ] = right[ j ];
				  listCounter++;
			  }
		  
		  }
		 return list;
  }
  
  public static void main(String[] args) {
    
	StringCounter SC = null;
    
    boolean freq = false;
    boolean unique = false;

    int i = 0;

    //Checks to make sure at least a data structure and file is specified. 
    if (args.length < 2) {
      printUsage();
      System.exit(1);
    }  	
    
    if( args[0].equals("-b") ) 		
		SC = new BST();
	else if( args[0].equals("-a") ) 		
		SC = new AvlTree();
	else if( args[0].equals("-s") ) 
		SC = new SplayTree();		
	else if( args[0].equals("-h") ) 
		SC = new HashTable();
	else {
		printUsage();
		return;
	}
    
    i++;

    if ( args[i].equals("-frequency") ) {
    	i++;
    	freq = true;
    }
    
    if (args[i].equals("-num_unique")  ) {
    	i++;
    	unique = true;
    }
    
    //counts the number of words in the given file.
    countWords( args[i], SC, freq, unique );

  }
	/**
	 * Prints the usage of this application to standard error.
	 */
	private static void printUsage() {
		System.err.println( " java WordCount [ -b | -a | -s | -h ] [ -frequency | -num_unique ] <filename>" );
		System.err.println("\t-b    Use an Unbalanced BST to implement the StringCounter\n");
		System.err.println("\t-a    Use an AVL Tree\n");
		System.err.println("\t-s    Use a Splay Tree\n");
		System.err.println("\t-frequency     Print all the word/frequency pairs, ordered by frequency, and then by the words in lexicographic order\n");
		System.err.println("\t-num_unique     Print the number of unique words in the document.\n");
		System.err.println("");
		
	}
}
