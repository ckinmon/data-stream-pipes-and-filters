package programmingproject3;

/**
 * 
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * An object of this class is used by the enum Terminal 
 * and by the TSVPipeline.  This class performs various
 * calculations that are ultimately displayed to the terminal,
 * based on the stream that the user has chosen to filter.
 * <br><br>
 * This method contains nine methods, instead of less than or equal to seven.
 * The problem occured when I expanded this class to include the FIRSTDIFF 
 * functionality. I didn't have time to refactor. 
 */
public class TerminalFilter {
	
	/**
	 * This method returns the value sought after a calculation.
	 * @return temp of type String.
	 */
	public String getValue(){
		return temp;
	}
	
	/**
	 * This method increases the count variable by one,
	 * and then updates the temp variable after converting count
	 * to type String. I chose type String so that I could have 
	 * a single return function (getValue()), that returns type String,
	 * and not a return function for each type of variable. 
	 */
	public void increaseCount(){
		count += 1;
		temp = String.valueOf(count);
	}
	
	/**
	 * This method finds the minimum value from the data stream.
	 * @param record of type String array.
	 */
	public void calculateMin(String record){
		if(min == null){
			min = record;
			temp = min;
		}
		else{
			int value = min.compareTo(record);
			if(value > 0){
				min = record;
				temp = min;
			}
		}
	}
	
	/**
	 * This method finds the maximum value from the data stream.
	 * @param record of type String array.
	 */
	public void calculateMax(String record){
		if(max == null){
			max = record;
			temp = max;
		}
		else{
			int value = max.compareTo(record);
			if(value < 0){
				max = record;
				temp = max;
			}
		}
	}
	
	/**
	 * This method compares all the various records.  It stores the first
	 * record, and then compares all consecutive records. If a non matching
	 * record is found, then value is updated to false.
	 * @param record of type String array.
	 */
	public void compareRecord(String record){
		if(currentRecord == null){
			currentRecord = record;
			isSame = true;
			temp = "true";
		}
		else{
			if(!currentRecord.equals(record)){
			isSame = false;
			}
		}
		if(isSame == false){
			temp = "false";
		}
	}
	
	/**
	 * This method calculates the sum of long values from the data stream, if
	 * they are valid.
	 * @param record of type String array.
	 */
	public void calculateSum(String record){
		try{
			sum += Long.parseLong(record);
		}
		catch(NumberFormatException e){
			System.out.println("Error: Cannot take the sum of a non-number.");
		}
		temp = String.valueOf(sum);
	}
	
	/**
	 * This method finds the first differential.  
	 * @param element of type String that contains the field.
	 * @param record of type String array that contains current record.
	 */
	public void findFirstDiff(String element, String[] record){
		if(temp == null){
			temp = element;
			markedRecord = null;
			count++;
		}
		else
			if(!temp.equals(element) && markedRecord == null){
				temp = element;
				markedRecord = record;
				count++;
			}
			else{
				if(temp.equals(element) && count > 1){
					repetitionsCount++;
				}
			}
		}
	
	/**
	 * This method returns the first differential value and the record. 
	 */
	public void getFirstDiff(){
		if(markedRecord != null){
			for(String element : markedRecord){
				System.out.print(element + "\t");
			}
			System.out.print(repetitionsCount);
			System.out.println("");	
		}
		else{
			System.out.println("No valid record found for FIRSTDIFF.");
		}
	}
	
	/**
	 * This method is used by the all the Terminal methods to locate the field in
	 * the header array that the user desires to filter to the terminal.  If the field
	 * is found, then it is returned so that the terminal computation can be called to 
	 * process it.
	 * <br><br>
	 * @param fileInfo of type FileInfo which contains file header and type information.
	 * @param terminalField of type String which contains the field.  
	 * @return identifiedRecord of type String which contains the correct record. 
	 */
	public String findTerminalFieldInHeaderArray(FileInfo fileInfo, String terminalField){
		String[] fileHeader = fileInfo.getFileHeader();
		String[] currentRecord = fileInfo.getCurrentRecord();
		String identifiedRecord = null;
		int fileLength = fileHeader.length;
		for(int i = 0; i < fileLength; i++){
			if(terminalField.equals(fileHeader[i])){
				if(!currentRecord[i].equals("")){
					identifiedRecord = currentRecord[i];
				}
			}
		}
		return identifiedRecord;
	}

		private String[] markedRecord;
		private String max;
		private String min;
		private int count;
		private String temp;
		private String currentRecord;
		private boolean isSame;
		private long sum;
		private int repetitionsCount;
}
