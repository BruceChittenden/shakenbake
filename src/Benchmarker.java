
public class Benchmarker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//makes sure 3 parameters are 
		if ( args.length < 3 ) {
			printUsage();
			System.exit(1);
		}

		int numTrials = 0;

		//attempts to convert argument to integer
		try {
			numTrials=Integer.parseInt(args[0]);
		}	catch (Exception e) {
			System.err.println("The integer for the number of trials provided is not valid.");
		}

		StringCounter dstruct = null;
		String datatype="";

		if( args[1].equals("-b") ) { 		
			dstruct = new BST();
			datatype="Binary Search Tree";
		}
		else if( args[1].equals("-a") ) 	{	
			dstruct = new AvlTree();
			datatype="AVL Tree";
		}
		else if( args[1].equals("-s") ) { 
			dstruct = new SplayTree();	
			datatype="Splay Tree";
		}
		else if( args[1].equals("-h") ) { 
			dstruct = new HashTable();
			datatype="Hash table";
		}
		else {
			printUsage();
			System.exit(1);
		}

		String filename = args[2];
		double avg = performTest( numTrials, dstruct, filename );
		System.out.print("Average time for " + datatype +" with "+ numTrials +" trials: ");
		System.out.println( avg );

	}

	/**
	 * This is an internal method used to perform benchmark testing. The
	 * result is a the number of seconds to perform a task 'limit' times.
	 * 
	 * @param limit - The number of times to perform the test.
	 * @param data The data structure to be used during testing.
	 * @param file The file to be used for the method
	 * @return a double, representing the average time in seconds it took to perform a method 'limit' times.
	 */
	private static double performTest( int limit, StringCounter data, String file ){
		long timeSum=0, tempStart, tempEnd;
		final int trial = limit;
		while ( limit > 0) {		
			tempStart = System.nanoTime();
			WordCount.countWords( file, data, true, false);
			tempEnd = System.nanoTime();
			timeSum += (tempEnd - tempStart);

			limit--;
		}
		return timeSum / 1000000000.0 / trial;


	}


	/**
	 * Prints the usage of this application to standard error.
	 */
	private static void printUsage() {
		System.err.println( " java Benchmarker [# of trials] [ -b | -a | -s | -h ] <filename>" );
		System.err.println("\t # of trails Number of times to perform word counting.\n");
		System.err.println("\t-b    Use an Unbalanced BST to implement the StringCounter\n");
		System.err.println("\t-a    Use an AVL Tree\n");
		System.err.println("\t-s    Use a Splay Tree\n");
		System.err.println("\t-h    Use a Hash Table\n");
		System.err.println("\t filename The name of the file to use for counting.\n");
		System.err.println("");

	}
}
