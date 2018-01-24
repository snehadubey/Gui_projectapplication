import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class SplitPaneDividerDemo extends JPanel  {

    private JSplitPane splitPane;
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	
	 public static void main(final String[] args) {
         NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
         Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new SplitPaneDividerDemo(args);
    
                }
            });
	 }
	 public SplitPaneDividerDemo(String[] args){
        super(new BorderLayout());

        Font font = new Font("Serif", Font.ITALIC, 24);
//++++++++++++++++++
        JFrame frame1 = new JFrame("vlcj ");

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        frame1.setContentPane(mediaPlayerComponent);

        frame1.setLocation(50, 100);
        frame1.setSize(600, 600);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);   
        mediaPlayerComponent.getMediaPlayer().playMedia("D:\\gui_proj\\algo_FL_24mm.avi");
       //+++++++++++++++++++++++++++++++++++++++++++++ 
        JFrame frame2 = new JFrame("vlcj ");

        mediaPlayerComponent= new EmbeddedMediaPlayerComponent();

        frame2.setContentPane(mediaPlayerComponent);

        frame2.setLocation(650, 100);
        frame2.setSize(600, 600);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia("D:\\gui_proj\\Wildlife.wmv");
        //ImageIcon icon = createImageIcon("images/Cat.gif");
        //SizeDisplayer sd1 = new SizeDisplayer("left", icon);
        //sd1.setMinimumSize(new Dimension(30,30));
       // sd1.setFont(font);
        
       // icon = createImageIcon("images/Dog.gif");
       // SizeDisplayer sd2 = new SizeDisplayer("right", icon);
       // sd2.setMinimumSize(new Dimension(60,60));
       // sd2.setFont(font);
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,frame1, frame2);
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,frame1, frame2);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        add(splitPane, BorderLayout.CENTER);
        //add(createControlPanel(), BorderLayout.PAGE_END);
    }

}