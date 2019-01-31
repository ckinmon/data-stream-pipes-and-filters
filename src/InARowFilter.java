package programmingproject3;

import java.io.IOException;
import java.io.FileWriter;

/**
 * 
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * An object of this class is used by the TSVPipeline class when a 
 * user wants to stream only values that are matching and adjacent to 
 * each other. This class also uses TSVFilter and FileInfo.  
 * <br><br>
 * NOTE:  This class has a method that contains a bug (processInARowFilter(), that 
 * I cannot figure out with the amount of time I have left before the assignment's deadline. 
 * I've tested over and over and cannot figure out what is wrong.  The user can
 * successfully ask to stream adjacent records in one or two consecutive matching rows,
 * but anything greater than two causes a NullPointerException. I could catch it
 * but then the data is not streamed correctly. Obviously asking for zero or below also
 * causes a problem too. I felt that making it known that a bug exists is
 * more important than trying to cover it up. 
 * <br><br>
 * It has been demonstrated in testing that this filter works for values of one and two. Please
 * refer to the testing data in the master file.
 * 
 */
public class InARowFilter {
	
	private FileInfo fileInfo;
	private TSVFilter filter;
	private FileWriter fileWriter;
	private String[][] storageArray;
	private int count;
	
	/**
	 * This constructor takes the objects from three other classes so that
	 * the data stream can be filtered.  
	 * @param newFileInfo of type FileInfo which contains the file information.
	 * @param newFilter of type TSVFilter which contains the filter information the user wants.
	 * @param newWriter of type FileWriter which contains where to stream filtered data.
	 */
	InARowFilter(FileInfo newFileInfo, TSVFilter newFilter, FileWriter newWriter){
		fileInfo = newFileInfo;
		filter = newFilter;
		fileWriter = newWriter;
		count = 0;
	}
	
	/**
	 * This method creates an array based on the number of adjacent records
	 * the user wants so that they can be stored for future comparison and
	 * then streamed. 
	 */
	public void createStorageArray(){
		int adjacencyNumber = Integer.parseInt(filter.getInARowValue());
		int nestedArrayLength = fileInfo.getFileHeader().length;
		storageArray = new String[adjacencyNumber][nestedArrayLength];
	}
	
	/**
	 * This method contains the logic of the inARow filter. This method 
	 * compares adjacent records, and calls the 
	 * updateStorage() and printInARowRecords() methods based on the comparison.
	 */
	public void processInARowFilter(){
		boolean matchedRecords = true;
		if(count < storageArray.length){
			storageArray[count] = fileInfo.getCurrentRecord();
			count++;
		}
		for(int i = 0; i < fileInfo.getFileHeader().length; i++){
			if(filter.getInARowField().equals(fileInfo.getFileHeader()[i])){
				for(int j = 0; j < storageArray.length - 1; j++){
					if(!storageArray[j][i].equals(storageArray[j + 1][i])){
						matchedRecords = false;
					}
				}
			}
		}
		if(count == storageArray.length && !matchedRecords){
			this.updateStorageArray();
			count--;
		}
		if(count == storageArray.length && matchedRecords){
			this.printInARowRecords();
			this.updateStorageArray();
			count--;
		}
	}
	
	/**
	 * This method prints records that match the user's criteria for
	 * inARow filter.
	 */
	private void printInARowRecords(){
		int recordLength = fileInfo.getCurrentRecord().length;
		for(int i = 0; i < storageArray.length; i++){
			for(int j = 0; j < storageArray[i].length; j++){
				try {
					fileWriter.write(storageArray[i][j]);
					if(j != recordLength - 1){
						fileWriter.write("\t");
					}
					else{
						fileWriter.write("\r\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method updates the storage array that holds the adjacent records.
	 */
	private void updateStorageArray(){
		for(int i = 0; i < storageArray.length - 1; i++){
			storageArray[i] = storageArray[i + 1];
		}
	}

}
