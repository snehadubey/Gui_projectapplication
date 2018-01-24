
package Gui_components;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author KAVYA JANARDHANA
 */
public class Camera_IRIG_Info {
     
    static int value_button=1;
    StyledDocument camera_IRIG_doc = new DefaultStyledDocument();
    SimpleAttributeSet attributes=new SimpleAttributeSet();
    JFrame frame3 = new JFrame("camera_IRIG_doc");
    
    public void Camera_Info() throws BadLocationException{
    
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame3.setLocationRelativeTo(null);
        attributes.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
        attributes.addAttribute(StyleConstants.CharacterConstants.FontSize, 16);
        String[] txt=new String[20];
	int lineNo2,i=0;
	try {
		
		FileReader fr2 = new FileReader("F:\\Gui_Project\\videos\\C1S01.cih");
		
		BufferedReader br2 = new BufferedReader(fr2);
                camera_IRIG_doc.insertString(camera_IRIG_doc.getLength(), "File Info:\n", attributes);
		attributes.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.FALSE);
		 attributes.addAttribute(StyleConstants.CharacterConstants.FontSize, 12);
		for (lineNo2 = 1; lineNo2 < 30; lineNo2++) {
			if (lineNo2 == 16||lineNo2==17||lineNo2==18||lineNo2==19) {
				txt[i] = br2.readLine();
                            //System.out.println(txt[i]);
                            camera_IRIG_doc.insertString(camera_IRIG_doc.getLength(), txt[i]+"\n", attributes);
				 i++;
			} else
				br2.readLine();
			
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
    JTextPane textPane = new JTextPane(camera_IRIG_doc);
    textPane.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textPane);

    
    frame3.add(scrollPane, BorderLayout.CENTER);
    frame3.setSize(1250, 150);
    frame3.setVisible(true);
    }
    public void create_data_file() throws FileNotFoundException, IOException, BadLocationException{
    // RandomAccessFile raf = new RandomAccessFile("F:\\Gui_Project\\videos\\C1S01.irg", "r");
      //int Frame_no=0, count=0,seek2=8,seek3=16;
       // long irig_length=raf.length();
       
        RandomAccessFile raf = new RandomAccessFile("F:\\Gui_Project\\videos\\C1S01.irg", "r");
        int Frame_no=0, count=0,seek2=8,seek3=16;
        long irig_length=raf.length();
        System.out.println(irig_length+":s1");
        PrintWriter writer = new PrintWriter("data_file.txt","UTF-8");
        
        //Holds the binary data
        byte[] dd = new byte[4];
        byte[] hh = new byte[4];
        byte[] mm = new byte[4];
        byte[] sec = new byte[4];
        byte[] ms = new byte[4];
        
        int Day_of_year,Hours,min,secs,micro_sec;//holds the decimal data of binary values
        
        while(count<irig_length){
     
            Frame_no=Frame_no+1;
	    System.out.println("\nFrame no: "+Frame_no);
	    //System.out.println("\ncount no: "+count);
	    raf.seek(seek2);
            seek2=seek2+20;
        
     
            raf.read(dd, 0, 4);
            ByteBuffer wrapped = ByteBuffer.wrap(dd); // big-endian by default
            wrapped.order(ByteOrder.LITTLE_ENDIAN);
            Day_of_year = wrapped.getInt();
            //System.out.println("Day_of_year: "+Day_of_year);
        
        
            //raf.seek(12);
            raf.read(hh, 3, 1);
            ByteBuffer wrapped1 = ByteBuffer.wrap(hh); // big-endian by default
            //wrapped.order(ByteOrder.LITTLE_ENDIAN);
            Hours = wrapped1.getInt();
           // System.out.println("\nHours: "+Hours);
        
            
            //raf.seek(13);
            raf.read(mm, 3, 1);
            ByteBuffer wrapped2 = ByteBuffer.wrap(mm); // big-endian by default
            //wrapped.order(ByteOrder.LITTLE_ENDIAN);
            min = wrapped2.getInt();
            //System.out.println("\nmin: "+min);
        
      
            //raf.seek(14);
            raf.read(sec, 3, 1);
            ByteBuffer wrapped3 = ByteBuffer.wrap(sec); // big-endian by default
            //wrapped.order(ByteOrder.LITTLE_ENDIAN);
            secs = wrapped3.getInt();
            // System.out.println("\nsec: "+secs);
        
     
            raf.seek(seek3);
       
            raf.read(ms, 0, 4);
            ByteBuffer wrapped4 = ByteBuffer.wrap(ms); // big-endian by default
            wrapped4.order(ByteOrder.LITTLE_ENDIAN);
            micro_sec = wrapped4.getInt();
            //System.out.println("Day_of_year: "+micro_sec);
            seek3=seek3+20;
            // System.out.println("\nmicro_sec: "+micro_sec);
             //System.out.println(BigDecimal.valueOf(sec).toPlainString());
            
            //System.out.println(Day_of_year+"/"+Hours+":"+min+":"+secs+"."+micro_sec);
            writer.println(Day_of_year+"/"+Hours+":"+min+":"+secs+"."+micro_sec);
            count=count+20;
            System.out.println("count value:" +count);
       }
       writer.close();
       IRIG_Info(irig_length);
    }
       
        
        //return irig_length;
   
    
    public void IRIG_Info(long irig_length) throws BadLocationException {
        
       // camera_IRIG_doc.insertString(camera_IRIG_doc.getLength(),"KAVYA", attributes);
       
       int irig_frame_no_count=(int) ((irig_length/20)+2);
       Scanner s1 = null;
        int Frame_no=0;
        try{
        s1 = new Scanner(new File("F:\\Gui_Project\\data_file.txt"));
        String str="";
        while(s1.hasNextLine()&&Frame_no<irig_length){
      
              
           // System.out.println("value_button:====== "+value_button);
       
	    //System.out.println("\ncount no: "+count);
	       
       // System.out.println("length: "+document.getLength());
        //if(value_button==0){
              str=s1.nextLine();
            Frame_no=Frame_no+1;
            camera_IRIG_doc.insertString(camera_IRIG_doc.getLength(),str+"\n", attributes);
        
	    System.out.println("\nFrame no: "+Frame_no);
        //}
        //else 
        //   break;
       
        if(Frame_no!=irig_frame_no_count){
        	
        	try{
        		Thread.sleep(300);
                        //if(value_button==0){
        		camera_IRIG_doc.remove(111,camera_IRIG_doc.getLength()-111);
                       // }
        	}
        	catch (InterruptedException ex){
        		Thread.currentThread().interrupt();
        	}
        }
       
        }
        s1.close();
    
    }
        catch(FileNotFoundException f) {
        	 System.err.println("FileNotFoundException");
    	}
          }
    
}
class dummy_main
{
    public static void main(String args[]) throws IOException, BadLocationException{
    
        Camera_IRIG_Info obj1= new Camera_IRIG_Info();
        obj1.Camera_Info();
        obj1.create_data_file();
    }

}
