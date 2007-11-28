
public class Coorelator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Check basic argument validity and print usage information.
		if(args.length < 1) {
			printUsage();
			return;
		}

		int i=0;

		if( args[i].equals("-b") ) {
			i++;
		 //	runner = new RandomMazeRunner();
		}

		if( args[i].equals("-a") ) {
			i++;
			//runner = new BFSMazeRunner();
		}
		
		if( args[i].equals("-s") ) {
			i++;
		//	runner = new DFSMazeRunner();
		}

		if( args[i].equals("-h") ) {
			i++;
			if ( args[i].equals("-bin")) {
				i++;
			//	runner = new BestFirstMazeRunner(0,-1);
			
			}
			else {
				printUsage();
				return;
			}
				
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
