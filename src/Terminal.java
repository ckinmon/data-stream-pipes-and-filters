package programmingproject3;

/**
 * 
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * This Terminal enum is used by the TSVPipeline and TSVFilter classes
 * to perform various calculations on the data stream and send the 
 * results to the terminal. The Terminal enum uses the TerminalFilter to
 * call on various calculations.  This enum is made up of an abstract 
 * method, called compute(), which performs different calculations based on
 * the Terminal.USER'S_CHOICE. 
 *
 */
public enum Terminal {
	
	/**
	 * This enum keyword calls the methods that count a specified field in a data stream.
	 */
	COUNT{
		public void compute(FileInfo fileInfo, String terminalField){
			String identifiedField = terminalFilter.findTerminalFieldInHeaderArray(fileInfo, terminalField);
			if(identifiedField != null){
				terminalFilter.increaseCount();
			}
		}
	},
	
	/**
	 * This enum keyword calls the methods that compare the records in a data stream.
	 */
	ALLSAME{
		public void compute(FileInfo fileInfo, String terminalField){
			String identifiedField = terminalFilter.findTerminalFieldInHeaderArray(fileInfo, terminalField);
			if(identifiedField != null){
				terminalFilter.compareRecord(identifiedField);
			}
		}
	},
	
	/**
	 * This enum keyword calls the methods that finds the minimum value in a data stream.
	 */
	MIN{
		public void compute(FileInfo fileInfo, String terminalField){
			String identifiedField = terminalFilter.findTerminalFieldInHeaderArray(fileInfo, terminalField);
			if(identifiedField != null){
				terminalFilter.calculateMin(identifiedField);
			}
		}
	},
	
	/**
	 * This enum keyword calls the methods that finds the maximum value in a data stream.
	 */
	MAX{
		public void compute(FileInfo fileInfo, String terminalField){
			String identifiedField = terminalFilter.findTerminalFieldInHeaderArray(fileInfo, terminalField);
			if(identifiedField != null){
				terminalFilter.calculateMax(identifiedField);
			}
		}
	},
	
	/**
	 * This enum keyword calls the methods that finds the sum of a specified value in a data stream.
	 */
	SUM{
		public void compute(FileInfo fileInfo, String terminalField){
			String identifiedField = terminalFilter.findTerminalFieldInHeaderArray(fileInfo, terminalField);
			if(identifiedField != null){
				terminalFilter.calculateSum(identifiedField);
			}
		}
	},
	
	/**
	 * This enum keyword calls the methods that finds the first differential in a data stream.
	 */
	FIRSTDIFF{
		public void compute(FileInfo fileInfo, String terminalField){
			String identifiedField = terminalFilter.findTerminalFieldInHeaderArray(fileInfo, terminalField);
			if(identifiedField != null){
				terminalFilter.findFirstDiff(identifiedField, fileInfo.getCurrentRecord());
			}
		}
	};
	
	/**
	 * This constructor creates an object of the TerminalFilter
	 * that the compute() methods use. 
	 */
	Terminal(){
		terminalFilter = new TerminalFilter();
	}
	
	/**
	 * This abstract method is defined such that the Terminal.KEYWORDS can 
	 * call and define it in various ways.
	 * @param fileInfo of type FileInfo which contains file information.
	 * @param terminalField of type String. 
	 */
	public abstract void compute(FileInfo fileInfo, String terminalField);
	TerminalFilter terminalFilter;
}
