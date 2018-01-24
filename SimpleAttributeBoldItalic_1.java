
package Gui_components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class SimpleAttributeBoldItalic_1 {
  
  static int value_button=1;

public static void main(String args[]) throws BadLocationException, FileNotFoundException, IOException { {
    
    StyledDocument document = new DefaultStyledDocument();
    SimpleAttributeSet attributes = new SimpleAttributeSet();
    JButton pauseButton;

    JButton rewindButton;

    JButton skipButton; 
    
    JPanel contentPane = new JPanel();
           contentPane.setLayout(new BorderLayout());
           JPanel controlsPane = new JPanel();
           controlsPane.setBackground(Color.DARK_GRAY);
           pauseButton = new JButton("Pause");
           controlsPane.add(pauseButton);
           rewindButton = new JButton("Rewind");
           controlsPane.add(rewindButton);
           skipButton = new JButton("Skip");
           controlsPane.add(skipButton);
           contentPane.add(controlsPane, BorderLayout.PAGE_START);

           // to display the controls in different frame
           JFrame frame4 = new JFrame("My First Media Player");
            frame4.setBounds(50, 600, 300, 80);
             frame4.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame4.add(contentPane, BorderLayout.SOUTH);
            frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame4.setVisible(true);
            value_button=50;
           pauseButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                ++value_button;
                value_button=value_button%2;
               // System.out.println("value_button:  :"+value_button%2);
               }
           });
    frame4.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {value_button=90;
                System.out.println("Closed"+value_button);
                
                e.getWindow().dispose();
            }
        });
           rewindButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   //mediaPlayerComponent1.getMediaPlayer().skip(39000);
                   //mediaPlayerComponent2.getMediaPlayer().skip(-5);
               }
           });

           skipButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   //mediaPlayerComponent1.getMediaPlayer().skip(5);
                   //mediaPlayerComponent2.getMediaPlayer().skip(5);
               }
           });
	JFrame frame3 = new JFrame("Simple Attributes");
    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
  
    attributes.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
    attributes.addAttribute(StyleConstants.CharacterConstants.FontSize, 16);
    String[] txt=new String[20];
	int lineNo2,i=0;
	try {
		
		FileReader fr2 = new FileReader("F:\\Gui_Project\\videos\\C1S01.cih");
		
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
           Scanner s1 = null;
                   try {
                       s1 = new Scanner(new File("F:\\Gui_Project\\data_file.txt"));
       
        int Frame_no=0, count=0,seek2=8,seek3=16;
        
        
       
         String str="";
        while(s1.hasNextLine()&&count<1000){
      
              
           System.out.println("value_button:====== "+value_button);
       
	    System.out.println("\ncount no: "+count);
	   

        //count=count+20;
       
       // System.out.println("length: "+document.getLength());
        if(value_button==0){
              str=s1.nextLine();
            Frame_no=Frame_no+1;
            document.insertString(document.getLength(),str, attributes);
        
	    System.out.println("\nFrame no: "+Frame_no);
        }
        //else 
        //   break;
        if(count!=88020){
        	
        	try{
        		Thread.sleep(300);
                        if(value_button==0){
        		document.remove(111,document.getLength()-111);
                        }
                        
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