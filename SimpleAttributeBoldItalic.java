import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class SimpleAttributeBoldItalic {
  private static EmbeddedMediaPlayerComponent mediaPlayerComponent;

public static void main(String args[]) throws BadLocationException, FileNotFoundException, IOException { {
    
	 
	JFrame frame3 = new JFrame("Simple Attributes");
    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    StyledDocument document = new DefaultStyledDocument();
    SimpleAttributeSet attributes = new SimpleAttributeSet();
    attributes.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
    attributes.addAttribute(StyleConstants.CharacterConstants.FontSize, 16);
    String[] txt=new String[20];
	int lineNo2,i=0;
	try {
		
		FileReader fr2 = new FileReader("D:\\gui_proj\\Mig_arrest\\C1S01.cih");
		
		BufferedReader br2 = new BufferedReader(fr2);
		try {
		      document.insertString(document.getLength(), "File Info:\n", attributes);
		    } catch (BadLocationException badLocationException) {
		      System.err.println("Bad insert");
		    }
		attributes.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.FALSE);
		 attributes.addAttribute(StyleConstants.CharacterConstants.FontSize, 12);
		for (lineNo2 = 1; lineNo2 < 30; lineNo2++) {
			if (lineNo2 == 16||lineNo2==17||lineNo2==18||lineNo2==19) {
				txt[i] = br2.readLine();
				//System.out.println(txt[i]);
				 try {
				      document.insertString(document.getLength(), txt[i]+"\n", attributes);
				    } catch (BadLocationException badLocationException) {
				      System.err.println("Bad insert");
				    }
				 i++;
			} else
				br2.readLine();
			
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
     JTextPane textPane = new JTextPane(document);
    textPane.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textPane);

    frame3.add(scrollPane, BorderLayout.CENTER);

    frame3.setSize(300, 150);
    frame3.setVisible(true);
    try{
        RandomAccessFile raf = new RandomAccessFile("D:\\gui_proj\\Mig_arrest\\C1S01.irg", "r");
        int Frame_no=0, count=0,seek2=8,seek3=16;
        long s1=raf.length();
        while(count<s1){
        
        Frame_no=Frame_no+1;
	    System.out.println("\nFrame no: "+Frame_no);
	    System.out.println("\ncount no: "+count);
	    raf.seek(seek2);
        seek2=seek2+20;
        byte[] dd = new byte[4];
        raf.read(dd, 0, 4);
        ByteBuffer wrapped = ByteBuffer.wrap(dd); // big-endian by default
        wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int Day_of_year = wrapped.getInt();
       // System.out.println("Day_of_year: "+Day_of_year);
        
        
        //raf.seek(12);
        byte[] hh = new byte[4];
        raf.read(hh, 3, 1);
        ByteBuffer wrapped1 = ByteBuffer.wrap(hh); // big-endian by default
        //wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int Hours = wrapped1.getInt();
       // System.out.println("\nHours: "+Hours);
        
        //raf.seek(13);
        byte[] mm = new byte[4];
        raf.read(mm, 3, 1);
        ByteBuffer wrapped2 = ByteBuffer.wrap(mm); // big-endian by default
        //wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int min = wrapped2.getInt();
       // System.out.println("\nmin: "+min);
        
        byte[] sec = new byte[4];
        //raf.seek(14);
        raf.read(sec, 3, 1);
        ByteBuffer wrapped3 = ByteBuffer.wrap(sec); // big-endian by default
        //wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int secs = wrapped3.getInt();
       // System.out.println("\nsec: "+secs);
        
     
        raf.seek(seek3);
        byte[] ms = new byte[4];
        raf.read(ms, 0, 4);
        ByteBuffer wrapped4 = ByteBuffer.wrap(ms); // big-endian by default
       wrapped4.order(ByteOrder.LITTLE_ENDIAN);
        int micro_sec = wrapped4.getInt();
        //System.out.println("Day_of_year: "+micro_sec);
        seek3=seek3+20;
        
       // System.out.println("\nmicro_sec: "+micro_sec);
        //System.out.println(BigDecimal.valueOf(sec).toPlainString());
        System.out.println(Day_of_year+"/"+Hours+":"+min+":"+secs+"."+micro_sec);
        count=count+20;
        System.out.println("length: "+document.getLength());
        document.insertString(document.getLength(), +Day_of_year+"/"+Hours+":"+min+":"+secs+"."+micro_sec+"\n", attributes);
        if(count!=88020){
        	
        	try{
        		Thread.sleep(20);
        		document.remove(111,document.getLength()-111);
        		
        	}
        	catch (InterruptedException ex){
        		Thread.currentThread().interrupt();
        	}
        }
       }
       }
        catch(FileNotFoundException f) {
        	 System.err.println("FileNotFoundException");
    	}
        catch(IOException e){
        	 System.err.println("IOException");
        }
   
  }
}
}