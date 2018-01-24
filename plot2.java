import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class plot2 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		float[] time= new float[100];
        float[] value = new float[100];
	    Scanner s= new Scanner(new File("plot.txt"));
	    //List<String> names= new ArrayList<String>();
	    int i=0;
	    while(s.hasNext())
	    {
	    	time[i]=s.nextFloat();
	    	s.skip("	");
	    	//s.skip("	");
	    	//System.out.println(s.nextFloat());
	    	value[i]=s.nextFloat();
	    	i=i+1;
	    }
	    for(int j=0;j<i;j++)
	    {
	    	System.out.println(time[j]+"  "+value[j]);
	    }
	}

}
