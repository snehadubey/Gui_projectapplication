
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
//import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.Frame;
//import java.awt.Graphics;
//import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.nio.file.Files;
//import java.nio.file.Paths; 

import javax.swing.JButton;
//import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
import javax.swing.JTextPane;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
//import javax.swing.Timer;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class VideoExample {

      private final EmbeddedMediaPlayerComponent mediaPlayerComponent1;
      //private final EmbeddedMediaPlayerComponent mediaPlayerComponent2;
      //private final JFrame frame4; //for controls to display in different frame
      private final JButton pauseButton;

      private final JButton rewindButton;

      private final JButton skipButton;

      public static void main(final String[] args) throws InterruptedException, FileNotFoundException {
      NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
      Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new VideoExample(args);
    
                }
            });
            XYSeries xySeries1 = new XYSeries("Number & Square Chart1");
    		XYSeries xySeries2 = new XYSeries("Number & Square Chart2");
    		float[] time1= new float[100];
            float[] value1 = new float[100];
            float[] time2= new float[100];
            float[] value2 = new float[100];
    	    Scanner s1= new Scanner(new File("plot2.txt"));
    	    Scanner s2= new Scanner(new File("plot3.txt"));
    	    //List<String> names= new ArrayList<String>();
    	    int i=0,k=0;
    	    while(s1.hasNext())
    	    {
    	    	time1[i]=s1.nextFloat();
    	    	s1.skip("	");
    	    	//s.skip("	");
    	    	//System.out.println(s.nextFloat());
    	    	value1[i]=s1.nextFloat();
    	    	i=i+1;
    	    	
    	    }
    	    while(s2.hasNext())
    	    {
    	    	time2[k]=s2.nextFloat();
    	    	s2.skip("	");
    	    	//s.skip("	");
    	    	//System.out.println(s.nextFloat());
    	    	value2[k]=s2.nextFloat();
    	    	k=k+1;
    	    	
    	    }
    	  	XYDataset xyDataset1 = new XYSeriesCollection(xySeries1);
    	  	XYDataset xyDataset2 = new XYSeriesCollection(xySeries2);
    	    for(int j=0;j<i;j++)
    	    {
    	    	
    	    	xySeries1.add(time1[j], value1[j]);
    	    	Thread.sleep(20);
    	    	System.getProperty("java.class.path");
    	    	
    	    }
    	    for(int j=0;j<k;j++)
    	    {
    	    	
    	    	xySeries2.add(time2[j], value2[j]);
    	    	Thread.sleep(20);
    	    	System.getProperty("java.class.path");
    	    	
    	    }
    		//Create the chart
    		JFreeChart chart = ChartFactory.createXYLineChart(
    				"Plotting points", "x label", "y label", xyDataset2,
    				PlotOrientation.VERTICAL, true, true, false);
    		
    		chart.setBackgroundPaint(Color.orange);
    		chart.getTitle().setPaint(Color.BLUE);
    		//CategoryPlot p=chart.getCategoryPlot();
    		//p.setRangeGridlinePaint(Color.BLUE);
    		//Render the frame
    		
    		ChartFrame chartFrame = new ChartFrame("XYLine Chart", chart);
    		chartFrame.setVisible(true);
    		chartFrame.setSize(600, 600);
    		chartFrame.setLocation(650, 100);
    		XYPlot plot= chart.getXYPlot();
    		plot.setDataset(0, xyDataset1);
    		plot.setDataset(1, xyDataset2);
    		
    		XYLineAndShapeRenderer renderer0=new XYLineAndShapeRenderer();
    		XYLineAndShapeRenderer renderer1=new XYLineAndShapeRenderer();
    		plot.setRenderer(0, renderer0);
    		plot.setRenderer(1, renderer1);
    		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.RED);
    		plot.getRendererForDataset(plot.getDataset(1)).setSeriesPaint(0, Color.BLUE);

    		//renderer0.setSeriesPaint(0, Color.cyan);
    		//renderer0.setSeriesStroke(0, new BasicStroke(4.0f)); //thickness of line
    		
       }

       private VideoExample(String[] args) {
       
    	   //Video 1 display +++++++++++++++++++++++++++++++++++++++++++
    	   JFrame frame = new JFrame("vlcj ");

           mediaPlayerComponent1 = new EmbeddedMediaPlayerComponent();
           frame.setContentPane(mediaPlayerComponent1);
           frame.setLocation(50, 100);
           frame.setSize(600, 600);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setVisible(true);   
           //System.out.println("time is: "+mediaPlayerComponent1.getMediaPlayer().getTime());
           mediaPlayerComponent1.getMediaPlayer().playMedia("D:\\gui_proj\\algo_FL_24mm.avi");
          // System.out.println("time is: "+mediaPlayerComponent1.getMediaPlayer().getTime());
          // mediaPlayerComponent1.getMediaPlayer().pause();
          
    		/*
            //video 2 display +++++++++++++++++++++++++++++++++++++++++++
           JFrame frame2 = new JFrame("vlcj ");
           mediaPlayerComponent2= new EmbeddedMediaPlayerComponent();
    	   frame2.setContentPane(mediaPlayerComponent2);
    	   frame2.setLocation(650, 100);
    	   frame2.setSize(600, 600);
           frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame2.setVisible(true);
           mediaPlayerComponent2.getMediaPlayer().playMedia("D:\\gui_proj\\Wildlife.wmv");
      	*/
            //To control the video display++++++++++++++++++++++++++++++++
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

           pauseButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
            	   System.out.println("time is: "+mediaPlayerComponent1.getMediaPlayer().getTime());
            	   mediaPlayerComponent1.getMediaPlayer().pause();
                   //mediaPlayerComponent2.getMediaPlayer().pause();
               }
           });

           rewindButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   mediaPlayerComponent1.getMediaPlayer().skip(-5);
                   //mediaPlayerComponent2.getMediaPlayer().skip(-5);
               }
           });

           skipButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   mediaPlayerComponent1.getMediaPlayer().skip(5);
                   //mediaPlayerComponent2.getMediaPlayer().skip(5);
               }
           });
         /* // to display the controls in different frame
           //frame4 = new JFrame("My First Media Player");
         //  frame4.setBounds(50, 600, 300, 80);
          // frame4.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
          // frame4.add(contentPane, BorderLayout.SOUTH);
          // frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          // frame4.setVisible(true);
           */
           
           frame.add(controlsPane, BorderLayout.SOUTH);//  for displaying controls pane with in same frame
           
           
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
        	   // System.out.println("\nFrame no: "+Frame_no);
        	   // System.out.println("\ncount no: "+count);
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
                //System.out.println(Day_of_year+"/"+Hours+":"+min+":"+secs+"."+micro_sec);
                count=count+20;
                //System.out.println("length: "+document.getLength());
                try {
					document.insertString(document.getLength(), +Day_of_year+"/"+Hours+":"+min+":"+secs+"."+micro_sec+"\n", attributes);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
            //if(count!=88020){
                	
                	//try{
                		//Thread.sleep(20);
                		//try {
							//document.remove(111,document.getLength()-111);
						//} catch (BadLocationException e) {
						//	e.printStackTrace();
						//}
                		
                	//}
                	//catch (InterruptedException ex){
                	//	Thread.currentThread().interrupt();
                	//}
                }
               //}
               }
                catch(FileNotFoundException f) {
                	 System.err.println("FileNotFoundException");
            	}
                catch(IOException e){
                	 System.err.println("IOException");
                }
       }
    }      
      
