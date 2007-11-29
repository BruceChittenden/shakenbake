/**
 * Alex Meng
 * CSE326 Project 3
 * File: Coorelator.java
 * 
 * This class is designed to find a coorelation between two given files based on the frequency of words.
 * 
 * @author alexm
 */

/**
 * 
 * The Coorelator class is intended to be used to compare two text files and create a list of words and their frequency
 * in the text file. After counting, it attempts to find any coorelation between the freqency of words amongst the
 * two files.
 */
public class Correlator {

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
	 * This counts the frequency of words for each respective file then attempts to find a coorelation between
	 * the two respective files. It prints out a coorelation value that is based on LSI.
	 */
	public static void coorelate( String file1, String file2, StringCounter data, StringCounter data2  ) {
		//counts the number of words and their respective frequency.
		countWords ( file1, data );
		//counts the number of words and their respective frequency.
		countWords ( file2, data2 );
		
		StringCount[] cnt = data.GetCounts();
		StringCount[] cnt2 = data2.GetCounts();
		
		//retrieves the total number of unique words
		int total1 = cnt.length;
		int total2 = cnt2.length;
		
		double freq, freq2;
		double sum =0 ;
		for ( StringCount sc : cnt ) {
			int searchResult = binarySearch ( cnt2, sc.str, 0, cnt2.length-1 );
			if ( searchResult > -1 ) {				
				freq = sc.cnt / (double) total1;
				freq2 = cnt2[searchResult].cnt / (double) total2;
				if ( Math.abs( freq - freq2 ) <= .01 && Math.abs( freq - freq2 ) > .0001 )
					sum += Math.pow( Math.abs( freq - freq2 ), 2 );
			}
		}
		//Prints out the coorelation value which ranges [0.0, 1.0]
		System.out.println("Coorelate: " + sum  );
	}
	
	
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

	private static void countWords( String file, StringCounter SC ) {
		 try {
		      String word;
		      FileWordReader fwr = new FileWordReader(file);
		      while((word=fwr.nextWord())!=null)
		        SC.IncCount(word);
		    } catch (Throwable error) {
		      System.err.println( "Error processing \n" + file + error);
		      System.exit(1);
		    }

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
