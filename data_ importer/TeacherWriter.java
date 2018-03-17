import java.io.*;
import java.util.*;

/**
 * A writer that converts data regarding teachers contained in the .csv files into accepted .dat format in CPLEX. 
 * @author Derek
 *
 */
public class TeacherWriter 
{
	/**
	 * The formatted strings of each teacher tuple in CPLEX. 
	 */
	private List<String> formatted = new LinkedList<String>();
	
	/**
	 * The requirement of the .csv file is such that the first element is the name of the teacher, the second is the number of total sections of various subjects the teacher can teach, and the third is the list of courses the teacher may teach, labeled as integers determined by the secs map offered.    
	 * @param file The .csv file containing all the teacher information.  
	 * @param names The names of the teachers and their associated id.
	 * @param cous The sections of all courses properly labeled. 
	 * @throws IOException
	 */
	public TeacherWriter(File file, Map<String,Integer> names, Map<String,Integer> cous) throws IOException
	{
		BufferedReader read = new BufferedReader(new FileReader(file));
		read.readLine();
		String line;
		while((line=read.readLine())!=null)
		{
			String[] cont = line.split(",");
			StringBuilder builder = new StringBuilder();
			builder.append("<").append(names.get(cont[0])).append(",").append(cont[0]).append(",{");
			int[] courses = new int[cous.size()];
			for(int i=2; i<cont.length; i++)
				courses[cous.get(cont[i])] = 1;
			builder.append(Arrays.toString(courses).substring(1).substring(0,courses.length)).append("},").append(cont[1]).append(">");
			formatted.add(builder.toString());
		}
		read.close();
	}
	
	/**
	 * Write the processed data in the .dat format accepted by CPLEX in the file under the given file name.  
	 * @param datName The preferred name of the .dat file to be written to. 
	 * @throws FileNotFoundException
	 */
	public void write(String datName) throws FileNotFoundException
	{
		PrintWriter write = new PrintWriter(datName+".dat");
		StringBuilder builder = new StringBuilder();
		builder.append("teachers = {");
		for(String holder: formatted)
			builder.append(holder).append(",");
		builder.append("};");
		write.write(builder.toString());
		write.close();
	}
	
	public static void main(String[] args) throws IOException 
	{
		
	}

}
