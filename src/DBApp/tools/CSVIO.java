package DBApp.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class CSVIO 
{
	public static LinkedList<String[]> readCSV(String fileDir) throws IOException, FileNotFoundException
	{
		 LinkedList<String[]> ret = new LinkedList<String[]>();
		 BufferedReader br = new BufferedReader(new FileReader(fileDir));
		 String line;
		 while ((line = br.readLine()) != null)
	         ret.add(line.split(","));
		 br.close();
		 return ret;
	}
	public static void writeCSV(String fileDir, String fileName, LinkedList<String> entries) throws IOException, FileNotFoundException
	{
		 PrintWriter writer = new PrintWriter(new FileOutputStream(fileDir + fileName + ".csv", false));
		 entries.forEach(writer::println);
		 writer.close();
	}
	public static void writeToFile(String line, String fileDir) throws IOException
	{
		try(FileWriter fw = new FileWriter(fileDir, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
		{
			    out.println(line);
		}
	}
}
