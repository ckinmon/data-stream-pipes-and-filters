package programmingproject3;

/**
 * 
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * This class is an Information Holder.  An object of 
 * this class contains valuable information about the 
 * file that is being streamed, such as the header fields
 * the type fields, and the current record being processed, and 
 * allows access to this information.
 */
public class FileInfo {
	
	private String[] fileHeaderArray;
	private String[] fileTypeArray;
	private String[] fileCurrentRecordArray;
	
	/**
	 * This method stores the header record of a file.
	 * @param headerArray of type String array.
	 */
	public void setFileHeader(String[] headerArray){
		fileHeaderArray = headerArray;
	}
	
	/**
	 * This method stores the type record of a file.
	 * @param typeArray of type String array.
	 */
	public void setFileType(String[] typeArray){
		fileTypeArray = typeArray;
	}
	
	/**
	 * This method stores the current record being streamed.
	 * @param currentRecord of type String array.
	 */
	public void setCurrentRecord(String[] currentRecord){
		fileCurrentRecordArray = currentRecord;
	}
	
	/**
	 * This method returns the file header.
	 * @return fileHeaderArray of type String array.
	 */
	public String[] getFileHeader(){
		return fileHeaderArray;
	}

	/**
	 * This method returns the type header.
	 * @return fileTypeArray of type String array.
	 */
	public String[] getFileType(){
		return fileTypeArray;
	}
	
	/**
	 * This method returns the current record.
	 * @return fileCurrentRecordArray of type String array.
	 */
	public String[] getCurrentRecord(){
		return fileCurrentRecordArray;
	}

}
