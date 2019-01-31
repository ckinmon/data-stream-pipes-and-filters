package programmingproject3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * An object of this class can process and stream data from a file
 * to a new file.  This class uses the TSVFilter, Terminal, TerminalFilter,
 * InARowFilter classes.  This class was created, and the methods from Step One,
 * including Stream and its helper methods, were added. 
 * <br><br>
 * This class requires an object of TSVFilter, to open a file, apply a filter,
 * and stream the data to a new file. 
 * <br><br>
 * This class was expanded in Step Three to account for the Terminal functionality of
 * TSVFilter. 
 * <br><br>
 * This class was expanded in Step Four to account for the InARow filter, and the FIRSTDIFF
 * functionality.
 * <br><br>
 * NOTE:  This class contains more than seven methods, and the method streamRecord() goes deeper
 * than three abstractions. I admittedly didn't have time to refactor the streamRecord() method, 
 * and since the class already has too many methods, I left it alone.  
 */
public class TSVPipeline{
	
	private String[] tempArray;	
	private File file;
	private Scanner scanner;
	private FileWriter fileWriter;
	private FileInfo fileInfo;
	private TSVFilter filter;
	private Terminal terminal;
	private DataCleanser cleaner;
	private InARowFilter rowFilter;
	
	/**
	 * This constructor takes a TSVFilter object and initializes
	 * the filter, fileInfo, cleaner, and terminal reference variables.
	 * @param newFilter of type TSVFilter.
	 */
	TSVPipeline(TSVFilter newFilter){
		filter = newFilter;
		fileInfo = new FileInfo();
		cleaner = new DataCleanser(fileInfo);
		terminal = filter.getTerminal();
		try{
			String filePath = filter.getFileName();
			file = new File(filePath);
			String newFilePath = "newFile.tsv";
			fileWriter = new FileWriter(newFilePath);
		}
		catch(FileNotFoundException e){
			System.out.println("Error: file not found!");
		} 
		catch (IOException e) {
			System.out.println("Error: new file could not be created.");
		}
	}
	
	/**
	 * This method applies the filter from the select method in the TSVFilter
	 * to the data stream and returns true or false based on if the record
	 * abides by the user's defined criteria.
	 * @return true if the record is valid, false otherwise.
	 */
	private boolean filterSelectedRecord(){
		boolean validField = false;
		String[] tempRecord = fileInfo.getCurrentRecord();
		String selectionField = filter.getField();
		String selection = filter.getSelection();
		if(selectionField == null){
			validField = true;
			return validField;
		}
		for(int i = 0; i < fileInfo.getFileHeader().length; i++){
			if(selectionField.equals(fileInfo.getFileHeader()[i])){
				if(selection.equals(tempRecord[i])){
					validField = true;
				}
			}
		}
		return validField;
	}
	
	/**
	 * This method performs the terminal operation defined by the user. 
	 */
	private void terminalCompute(){
		if(terminal != null){
			String terminalField = filter.getTerminalField();
			terminal.compute(fileInfo, terminalField);
		}
	}
	
	/**
	 * This method displays the filtered stream to the terminal
	 * based on if it is valid.
	 */
	private void displayTerminal(){
		TerminalFilter terminalFilter = terminal.terminalFilter;
		if(terminal == Terminal.FIRSTDIFF && terminal != null){
			terminalFilter.getFirstDiff();
		}
		if(terminal != null && terminal != Terminal.FIRSTDIFF){
			System.out.println("Terminal Value: " + terminalFilter.getValue());
		}
	}
	
	/**
	 * This method streams the file header if it is valid.
	 */
	private void streamFileHeader(){
		this.readLine();
		fileInfo.setFileHeader(tempArray);
		this.writeLine();
		if(!cleaner.validateHeaderRecord()){
			tempArray = null;
			fileInfo.setFileHeader(tempArray);
		}
		
	}
	
	/**
	 * This method streams the file type record if it
	 * is valid.
	 */
	private void streamFileType(){
		this.readLine();
		fileInfo.setFileType(tempArray);
		this.writeLine();
		if(!cleaner.validateTypeRecord()){
			tempArray = null;
			fileInfo.setFileType(tempArray);
		}
	}
	
	/**
	 * This method contains the logic of the streaming process, after the header
	 * and type records have been validated and streamed.  It actually goes
	 * deeper than the three abstractions that we ideally want, but I 
	 * didn't have time to refactor, and this class already contains too many
	 * methods. With that being said, despite it being four abstractions deep,
	 * it is still relatively easy to follow along with the logic flow.
	 */
	private void streamRecord(){
		if(filter.getInARowField() != null){
			rowFilter = new InARowFilter(fileInfo, filter, fileWriter);
			rowFilter.createStorageArray();
		}
		while(scanner.hasNextLine()){
			this.readLine();
			tempArray = cleaner.validateRecord();
			if(cleaner.validateRecordLength(tempArray)){
				if(filterSelectedRecord()){
					if(rowFilter != null){
						rowFilter.processInARowFilter();
						this.terminalCompute();
					}
					else{
						this.terminalCompute();
						this.writeLine();
					}
				}
			}
		}
		if(terminal != null){
			this.displayTerminal();
		}
	}
	
	/**
	 * This method streams the file. It calls the stream header and type methods,
	 * and then calls the stream record method. This method performs all the various
	 * tasks of the TSVPipeline class. 
	 */
	public void stream(){
		try {
			scanner = new Scanner(file);
			this.streamFileHeader();
			this.streamFileType();
			if(cleaner.validateRecordLength(tempArray)){
				this.streamRecord();
			}
			else{
				System.out.println("Fatal Error: File was not streamed. ");
			}
			scanner.close();
			try {
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
		}
	}
	
	/**
	 * This method reads the current record from the line and
	 * temporarily stores it. 
	 */
	private void readLine(){
		String line;
		line = scanner.nextLine();
		tempArray = line.split("\t");
		fileInfo.setCurrentRecord(tempArray);
	}
	
	/**
	 * This method writes the current record to the new file.
	 */
	private void writeLine(){
		tempArray = fileInfo.getCurrentRecord();
		int recordLength = tempArray.length;
		for(int i = 0; i < recordLength; i++){
			try {
				fileWriter.write(tempArray[i]);
				if(i != recordLength - 1){
					fileWriter.write("\t");
				}
				else{
					fileWriter.write("\n");
				}
			} catch (IOException e) {
				System.out.println("Failed to write to file.");
				e.printStackTrace();
			}
		}
	}
}
