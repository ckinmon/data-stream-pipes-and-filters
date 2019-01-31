package programmingproject3;


/**
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * An object of this class is used to validate the
 * data stream as it is processed.  "Bad" data is removed
 * from the data stream, and "good" data is processed.  This
 * class uses the FileInfo class to get information about a 
 * particular record. The TSVPipe class uses this class to 
 * cleanse the data at appropriate times.
 * <br><br>
 * This class removes records that contain fields that do not
 * contain the right data type, such as a Name that contains
 * anything other than letters or white space, or longs that 
 * contain anything other than numbers. It also validates the
 * values for fields "Zip Code" "Cell Phone" "Name" and "Age".
 * "Zip Code" must be five digit format, not nine. "Cell Phone"
 * must be ten digit format, not seven. "Age" must be between 0
 * and 128. "Name" can only contain white space and letters. I chose
 * to validate values for these fields only because otherwise I would end
 * up validating values for everything humanely imaginable.
 * 
 */
public class DataCleanser {
	
	private FileInfo fileInfo;
	
	/**
	 * This constructor initializes the fileInfo reference variable 
	 * so that record information is available.
	 * <br><br>
	 * @param fileInfoParam of type FileInfo.
	 */
	DataCleanser(FileInfo fileInfoParam){
		fileInfo = fileInfoParam;
	}
	
	/**
	 * This method checks the length of the record to ensure
	 * that it matches the header record. If the record length
	 * does not match the header length, then the method returns
	 * false.
	 * @param record of type String array.
	 * @return true if valid, false otherwise.
	 */
	public boolean validateRecordLength(String[] record){
		if(record == null){
			return false;
		}
		int currentRecordLength = record.length;
		if(fileInfo.getFileHeader() == null){
			return false;
		}
		if(fileInfo.getFileHeader().length == currentRecordLength){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * This method verifies that the fields in a record match the 
	 * correct field type that is stated in the type record in the file.
	 * <br><br>
	 * This method also calls the validateLongField and validateStringField methods
	 * to check for either valid case. If a bad field is found, then the record is invalidated,
	 * and an empty String array is returned. Otherwise, the current record is returned. 
	 * @return String array. 
	 */
	public String[] validateRecord(){
		String[] currentRecord = fileInfo.getCurrentRecord();
		for(int field = 0; field < currentRecord.length; field++){
			try{
				if(!this.validateLongField(currentRecord, field) || !this.validateStringField(currentRecord, field)){
					this.printBadRecord(currentRecord);
					String[] newLine = new String[0];
					return newLine;
				}
				if(!this.validateFieldValue(currentRecord, field)){
					this.printBadRecord(currentRecord);
					String[] newLine = new String[0];
					return newLine;
				}
			}
			catch(ArrayIndexOutOfBoundsException e){
				this.printBadRecord(currentRecord);
				String[] newLine = new String[0];
				return newLine;
			}
		}
		return currentRecord;
	}
	
	/**
	 * This method displays a record that is found to be invalid.
	 * @param line of type String array.
	 */
	private void printBadRecord(String[] line){
		System.out.println("Improperly formatted record detected. Improper records are not outputted.");
		System.out.print("Bad record: ");
		for(String s : line){
			System.out.print(s + "\t");
		}
		System.out.println("");
	}
	
	/**
	 * This method checks to make sure that the fields in a record
	 * correspond with the field type String, if they are supposed to
	 * be String.
	 * @param line of type String array.
	 * @param index of type int.
	 * @return true if no bad field is found, otherwise return false.
	 */
	private boolean validateStringField(String[] line, int index){
		boolean isString = true;
		boolean validElement = true;
		String element = line[index];
		if(fileInfo.getFileType()[index].equals("String")){
			isString = element.matches("[a-zA-Z]+");
		}
		if(!isString){
			validElement = false;;
		}
		return validElement;
	}
	
	/**
	 * This method checks to make sure that the fields in a record
	 * correspond with the field type long, if they are supposed to
	 * be long.
	 * @param line of type String array.
	 * @param index of type int.
	 * @return true if no bad field is found, otherwise return false.
	 */
	private boolean validateLongField(String[] line, int index){
		String element = line[index];
		boolean validElement = true;
		if(fileInfo.getFileType()[index].equals("long")){
			try{
				Long.parseLong(element);
			}
			catch(NumberFormatException e){
				validElement = false;
			}
		}
		return validElement;
	}
	
	/**
	 * This method checks to make sure the first record in a file,
	 * the header, is in proper form and contains only character values.
	 * @return true if the record is valid, return false otherwise. 
	 */
	public boolean validateHeaderRecord(){
		boolean isValid = true;
		for(String element : fileInfo.getFileHeader()){
			isValid = element.matches("[a-zA-Z\\s]+");
			if(!isValid){
				this.printBadRecord(fileInfo.getFileHeader());
				return isValid;
			}
		}
		return isValid;
	}
	
	/**
	 * This method checks the second record in a file, the type, 
	 * to make sure all the fields are of correct form, such as
	 * a String or long.
	 * @return false if a bad field is found, otherwise return true.
	 */
	public boolean validateTypeRecord(){
		boolean isValid = true;
		for(String element : fileInfo.getFileType()){
			isValid = (element.matches("String") || element.matches("string") || element.matches("Long") || element.matches("long"));
			if(!isValid){
				this.printBadRecord(fileInfo.getFileType());
				return isValid;
			}
		}
		return isValid;
	}
	
	/**
	 * This method is a helper that validates the value for the fields
	 * age, cell phone, and zip code. 
	 * @param line of type String array.
	 * @param index of type int.
	 * @return true if the value is valid, false otherwise.
	 */
	private boolean validateFieldValue(String[] line, int index){
		boolean validElement = true;
		try{
			long longElement = Long.parseLong(line[index]);
			String stringElement = line[index];
			if(fileInfo.getFileHeader()[index].equals("Age")){
				if(longElement < 0 || longElement > 127){
					validElement = false;
				}
			}
			if(fileInfo.getFileHeader()[index].equals("Zip Code")){
				if(stringElement.length() != 5){
					validElement = false;
				}
			}
			if(fileInfo.getFileHeader()[index].equals("Cell Phone")){
				if(stringElement.length() != 10){
					validElement = false;
				}
			}
		}
		catch(NumberFormatException e){
		}
		return validElement;
	}

}
