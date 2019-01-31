package programmingproject3;


/**
 * @author Caleb Kinmon (UNI: cgk2128) <br><br>
 * 
 * This class allows the user to apply a filter to a stream
 * of data.  The user must create a TSVFilter object, then
 * they have the option of selecting a field in a file and a specific
 * value to look for.  If the field exists, then only those values within
 * the stream of data that match the selected value are sent to
 * a new file.  This selection filter is used by the TSVPipeline
 * class. <br><br>
 * This class was extended such that a terminal was added. The terminal
 * allows the user to apply a second filter to the data stream that is then
 * displayed to the user's screen, if the data meets the user's stated criteria.
 * <br><br>
 * This class was extended once again such that an inARow filter was added.  Now
 * the user can filter the stream of data such that it only displays consecutive records
 * that match the user's stated criteria.
 */
public class TSVFilter {
	
	private final String selectedField;
	private final String selectedValue;
	private final String fileName;
	private final Terminal terminal;
	private final String terminalField;
	private final String inARowField;
	private final String inARowValue;
	
	/**
	 * There is only one constructor which is necessary to create
	 * a filter. The constructor accepts an object that contains
	 * the different possible filter variations of the file. <br><br>
	 * @param file object of subclass type WhichFile.
	 */
	private TSVFilter(WhichFile file){
		this.selectedField = file.selectedField;
		this.selectedValue = file.selectedValue;
		this.fileName = file.fileName;
		this.terminal = file.terminal;
		this.terminalField = file.terminalField;
		this.inARowField = file.inARowField;
		this.inARowValue = file.inARowValue;
	}
	
	/**
	 * This method allows the Filter object to display important data about its makeup.
	 */
	public String toString(){
		return "Filter Object Information: \n" + "File Name: " + fileName + "\tSelection: " + selectedField + "\tData: " 
				+ selectedValue + "\tTerminal: " + terminal + "\nTerminal Field: " + terminalField + 
				"\tinARowField : " + inARowField + "\tinARowValue: " + inARowValue + ".\n";
	}
	
	/**
	 * This method returns the value of the file name.
	 * @return fileName of type String.
	 */
	public String getFileName(){
		return fileName;
	}

	/**
	 * This method returns the value of the selection filter's value.
	 * @return selectedValue of type String.
	 */
	public String getSelection(){
		return selectedValue;
	}
	
	/**
	 * This method returns the value of the selected field.
	 * @return selectedField of type String.
	 */
	public String getField(){
		return selectedField;
	}
	
	/**
	 * This method returns the value of the chosen terminal.
	 * @return terminal of type Terminal.
	 */
	public Terminal getTerminal(){
		return terminal;
	}
	
	/**
	 * This method returns the value of the terminal field.
	 * @return terminalField of type String.
	 */
	public String getTerminalField(){
		return terminalField;
	}
	
	/**
	 * This method returns the value of the inARow filter field.
	 * @return inARowField of type String.
	 */
	public String getInARowField(){
		return inARowField;
	}
	
	/**
	 * This method returns the value of the inARow filter value.
	 * @return inARowValue of type String.
	 */
	public String getInARowValue(){
		return inARowValue;
	}
	
	/**
	 * 
	 * @author Caleb Kinmon (UNI:  cgk2128) 
	 * <br><br>
	 * An object of this class is used by the parent TSVFilter class
	 * to determine the type of filter to apply to the data stream.
	 * <br><br>
	 * This class was extended to include the terminal filter.
	 * <br><br>
	 * This class was extended again to include the inARow filter. 
	 */
	public static class WhichFile{
		private String selectedField;
		private String terminalField;
		private Terminal terminal;
		private String selectedValue;
		private String fileName;
		private String inARowField;
		private String inARowValue;
		
		/**
		 * This class contains only one constructor, which accepts a file
		 * name from the user. It then initializes the fields of the object to null,
		 * such that a NULL Object pattern can be used in the pipeline to help
		 * make decisions about the filter. 
		 * @param fileName of type String.
		 */
		WhichFile(String fileName){
			this.fileName = fileName;
			this.selectedField = null;
			this.selectedValue = null;
			this.terminal = null;
			this.terminalField = null;
			this.inARowField = null;
			this.inARowValue = null;
		}
		
		/**
		 * This method will build a filter based on the user choosing a field and
		 * a value to look for that is in that field.
		 * @param field of type String.
		 * @param selectedValue of type String.
		 * @return WhichFile object.  
		 */
		public WhichFile select(String field, String selectedValue){
			this.selectedField = field;
			this.selectedValue = selectedValue;
			return this;
		}
		
		/**
		 * This method will build a filter based on the user choosing a field and
		 * a value to look for that is in that field.
		 * @param field of type String.
		 * @param value of type long.
		 * @return WhichFile object.  
		 */		
		public WhichFile select(String field, long value){
			this.selectedField = field;
			this.selectedValue = Long.toString(value);
			return this;
		}
		
		/**
		 * This method will build a terminal filter based on the user choosing a field and
		 * a terminal operation.
		 * @param newTerminalField of type String.
		 * @param newTerminal of type Terminal.
		 * @return WhichFile object.  
		 */
		public WhichFile compute(String newTerminalField, Terminal newTerminal){
			this.terminalField = newTerminalField;
			this.terminal = newTerminal;
			return this;
		}
		
		/**
		 * This method will build an inARow filter based on the user choosing a field and
		 * a value to look for that is in that field.
		 * @param field of type String.
		 * @param value of type long.
		 * @return WhichFile object.  
		 */
		public WhichFile inARow(String field, int value){
			this.inARowField = field;
			this.inARowValue = Integer.toString(value);
			return this;
		}
		
		/**
		 * This method is required when creating a Filter object, such that
		 * it returns a WhichFile object to the TSVFilter constructor, which
		 * contains all the relevant data about the filter.
		 * @return TSVFilter object.  
		 */
		public TSVFilter done(){
			return new TSVFilter(this);
		}
	}
}
