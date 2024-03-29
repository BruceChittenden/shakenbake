/**
 * Alex Meng
 * CSE326 Project 3
 * File: Correlator.java
 * 
 * This class is designed to find a correlation between two given files based on the frequency of words.
 * 
 * @author alexm
 */

/**
 * 
 * The Correlator class is intended to be used to compare two text files and create a list of words and their frequency
 * in the text file. After counting, it attempts to find any correlation between the frequency of words amongst the
 * two files.
 */
public class Correlator {

	private static final double FREQ_MIN = 0.0001;
	private static final double FREQ_MAX = 0.01;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		StringCounter data;
		StringCounter data2;
		
		// Check basic argument validity and print usage information.
		if(args.length < 3) {
			printUsage();
			return;
		}
		String filename1 = args[1];
		String filename2 = args[2];

		if( args[0].equals("-b") ) {		
			data = new BST();
			data2 = new BST();
		}

		else if( args[0].equals("-a") ) {
			
			data = new AvlTree();
			data2 = new AvlTree();
		}
		
		else if( args[0].equals("-s") ) {
			data = new SplayTree();
			data2 = new SplayTree();
			
		}
		else if( args[0].equals("-h") ) {
			data = new SplayTree();
			data2 = new SplayTree();
		}
		else {
			printUsage();
			return;
		}
		coorelate(filename1, filename2, data, data2);
	}

	/**
	 * This counts the frequency of words for each respective file then attempts to find a correlation between
	 * the two respective files. It prints out a correlation value that is based on LSI.
	 */
	public static void coorelate( String file1, String file2, StringCounter data, StringCounter data2  ) {


		
		
		//counts the number of words and their respective frequency.
		int size = countWords ( file1, data );
		//counts the number of words and their respective frequency.
		int size2 = countWords ( file2, data2 );
		
		StringCount[] cnt = data.GetCounts();
		StringCount[] cnt2 = data2.GetCounts();
				
		double sum = 0 ;
		int searchResult;
		for ( StringCount sc : cnt ) {
			//searches the second list for the current string
			searchResult = binarySearch ( cnt2, sc.str, 0, cnt2.length -1 );
			
			if ( searchResult > -1 ) {				
		
				double frequency = ((double)sc.cnt) / size;
				double frequency2 = ((double)cnt2[ searchResult ].cnt ) / size2;
				
				double difference =  Math.abs( frequency - frequency2 );
				if ( frequency >= FREQ_MIN && frequency <= FREQ_MAX && frequency2 >= FREQ_MIN && frequency2 <= FREQ_MAX ) {
					sum +=  Math.pow(  difference , 2.0 );
				}
			}
		}
		
		System.out.println( sum );
		
			
	}
	
	/**
	 * This is an internal method used to perform a binary search on a list based
	 * on the given key and indicies.
	 * 
	 * @param list The list to perform the search on
	 * @param key The key to look for in the list
	 * @param low The starting index to begin looking
	 * @param high The ending index to stop looking
	 * @return A positive integer, representing the index of the founded key. A negative integer if the key
	 * is not existent in the list.
	 */
	private static int binarySearch(StringCount[] list, String key, int low, int high) {
		while (low <= high) {
			int mid = (low + high) / 2;
			String midVal = list[mid].str;

			if ( midVal.compareTo( key ) < 0) {
				low = mid + 1;
			} else if ( midVal.compareTo( key ) > 0 ) {
				high = mid - 1;
			} else {
				return mid; // key found
			}
		}
		return -(low + 1); // key not found.
	}

	/**
	 * This is an internal method used to count the frequency of words
	 * in a given file and stores them in a StringCounter ADT and returns
	 * the number of words in the given file
	 * 
	 * @param file The given filename to open and perform the read on.
	 * @param SC The ADT used to hold the counting of words.
	 * @return an integer representing the number of words in the given file.
	 */
	public static int countWords( String file, StringCounter SC ) {
		int numWords = 0;
		 try {
		      String word;
		      FileWordReader fwr = new FileWordReader(file);
		      while((word=fwr.nextWord())!=null) {
		        SC.IncCount(word);
		        numWords++;
		      }
		    } catch (Throwable error) {
		      System.err.println( "Error processing \n" + file + error);
		      System.exit(1);
		    }
		 return numWords;

	}
	
	/**
	 * Prints the Usage to standard error.
	 */
	private static void printUsage() {
		System.err.println( "Usage: java Correlator [ -b | -a | -s | -h ] <filename1> <filename2>" );
		System.err.println("\t-b -- Use an Unbalanced BST in the backend\n");
		System.err.println("\t-a -- Use an AVL Tree in the backend\n");
		System.err.println("\t-s -- Use an Splay Tree in the backend \n");
		System.err.println("\t-h -- Use a Hashtable in the backend\n");
		System.err.println("");
		
	}
}
