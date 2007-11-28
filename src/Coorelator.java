
public class Coorelator {

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

	private static void coorelate( String file1, String file2, StringCounter data, StringCounter data2  ) {
		WordCount.countWords( file1, data, false, false );
		WordCount.countWords( file2, data2, false, false );

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
