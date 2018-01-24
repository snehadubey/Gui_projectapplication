// jcommon is require for jfreechart usage
package Gui_components;

import com.sun.jna.Native;// present in jna jar file
import com.sun.jna.NativeLibrary;// present in jna jar file
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author KAVYA J
 */
public class video_play {
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent1;
      //private final EmbeddedMediaPlayerComponent mediaPlayerComponent2;
      //private final JFrame frame4; //for controls to display in different frame
    private final JButton startButton;
    
    private final JButton pauseButton;

    private final JButton rewindButton;

    private final JButton skipButton;
      
    private final JFrame frame = new JFrame("vlcj ");
    
public video_play() {
       
   
    
    	   mediaPlayerComponent1 = new EmbeddedMediaPlayerComponent();
           frame.setContentPane(mediaPlayerComponent1);
           frame.setLocation(50, 100);
           frame.setSize(600, 600);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setVisible(true);   
           mediaPlayerComponent1.getMediaPlayer().prepareMedia("F:\\C1S01.avi");
          
           //To control the video display++++++++++++++++++++++++++++++++
           JPanel contentPane = new JPanel();
           contentPane.setLayout(new BorderLayout());
           JPanel controlsPane = new JPanel();
           controlsPane.setBackground(Color.DARK_GRAY);
           pauseButton = new JButton("Pause");
           controlsPane.add(pauseButton);
           startButton= new JButton("play");
           controlsPane.add(startButton);
           rewindButton = new JButton("Rewind");
           controlsPane.add(rewindButton);
           skipButton = new JButton("Skip");
           controlsPane.add(skipButton);
           contentPane.add(controlsPane, BorderLayout.PAGE_START);

           
               SimpleAttributeSet attributes = new SimpleAttributeSet();
           startButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
               
                   mediaPlayerComponent1.getMediaPlayer().play();           
                   System.out.println("time: " +mediaPlayerComponent1.getMediaPlayer().getLength());
                  System.out.println("time: " +mediaPlayerComponent1.getMediaPlayer().getTime());
                  float dur_in_sec = (mediaPlayerComponent1.getMediaPlayer().getLength()/1000);
                  System.out.println("dur_in_sec: " +dur_in_sec);
                    System.out.println("getFps: " +mediaPlayerComponent1.getMediaPlayer().getFps()); 
                    float total_frames = (mediaPlayerComponent1.getMediaPlayer().getFps()*mediaPlayerComponent1.getMediaPlayer().getLength()/1000);
                   System.out.println("total_frames: " +total_frames);
                   float current_frame =(mediaPlayerComponent1.getMediaPlayer().getPosition()*total_frames);
            	    System.out.println("current_frame: " +current_frame);
                   mediaPlayerComponent1.getMediaPlayer().pause();
               }
           });
           
           pauseButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                //mediaPlayerComponent1.getMediaPlayer().getLength();
            	  System.out.println("time: " +mediaPlayerComponent1.getMediaPlayer().getMediaDetails());
                  System.out.println("time: " +mediaPlayerComponent1.getMediaPlayer().getTime());
                  float dur_in_sec = (mediaPlayerComponent1.getMediaPlayer().getLength()/1000);
                  System.out.println("dur_in_sec: " +dur_in_sec);
                    System.out.println("getFps: " +mediaPlayerComponent1.getMediaPlayer().getFps()); 
                    float total_frames = (mediaPlayerComponent1.getMediaPlayer().getFps()*mediaPlayerComponent1.getMediaPlayer().getLength()/1000);
                   System.out.println("total_frames: " +total_frames);
                   float current_frame =(mediaPlayerComponent1.getMediaPlayer().getPosition()*total_frames);
            	    System.out.println("current_frame: " +current_frame);
                   mediaPlayerComponent1.getMediaPlayer().pause();
                   //mediaPlayerComponent2.getMediaPlayer().pause();
               }
           });

           rewindButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e){
                   mediaPlayerComponent1.getMediaPlayer().skip(-1);
               }
           });

           skipButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   mediaPlayerComponent1.getMediaPlayer().skip(1);
               }
           });
        frame.add(controlsPane, BorderLayout.SOUTH);//  for displaying controls pane with in same frame
                
 }
      public static void main(final String[] args) throws InterruptedException, FileNotFoundException {
      
      System.out.println("hello");
      //load the native library of vlc from directory where VLC is installed
      NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
      Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
            try {
				SwingUtilities.invokeAndWait(new Runnable() {
				    @Override
				    public void run() {
				        new video_play();
   
				    }
				});
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
}
      
