package programmingproject3;

/**
 * 
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * This class uses the TSVFilter and TSVPipeline classes to
 * open a file and stream its contents to a new file. The user 
 * can choose to apply a filter (select or inARow) on the stream,
 * and/or apply a calculation (compute) that sends the results
 * to the terminal. 
 * <br><br>
 * Overall, the system contains eight nodes (seven classes, and one enum), 
 * and 10 lines. (10 * 2) / 8 = 2.5.
 * <br><br>
 * Please refer to projectTestResults_MASTER_FILE.txt for test results.
 * <br><br>
 * Two important notes:  First, I tested my program on Eclipse on a Windows machine. So
 * I streamed newline characters of "\r\n" to the end of each record during testing so that
 * the output file looked pretty when I looked at it. But I switched to using just "\n" because 
 * I assume the program will be tested on Unix. I tested the program on Unix by streaming "\n" 
 * before making the switch. It worked. 
 * <br><br>
 * Second, I mistakenly tested most of my functionality and data with .txt files instead of .tsv 
 * (although it is basically the same thing). But I ran some tests on .tsv and it worked perfectly fine. 
 * <br><br>
 * <strong>Lastly, any tests that are ran with this program will create an output file in the current directory
 * of the name newFile.tsv</strong>. 
 * 
 */
public class Runner {

	/**
	 * This main method executes and demonstrates the program.
	 * @param args of type String array.
	 */
	public static void main(String[] args) {
		TSVFilter filter = new TSVFilter
				.WhichFile("testFile3.tsv")
				/* I commented out this code to show that it exists
				 * and can be used.
				 * .inARow("Cell Phone", 1)  
				 */
				.select("Name", "Frank")
				.compute("Age", Terminal.COUNT)
				.done();
		System.out.println(filter);
		new TSVPipeline(filter).stream();
	}

}
