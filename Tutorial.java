
//package tutorial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Tutorial {

    private final JFrame frame;
    
    //private final JFrame frame2;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    private final JButton pauseButton;

    private final JButton rewindButton;

    private final JButton skipButton;

    public static void main(final String[] args) {
    	 NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
         Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Tutorial(args);
    
                }
            });
            
        }

    public Tutorial(String[] args) {
        frame = new JFrame("My First Media Player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });

        
        
      JPanel contentPane = new JPanel();
      contentPane.setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
      // contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
        
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia("D:\\gui_proj\\algo_FL_24mm.avi");
        
        JPanel controlsPane = new JPanel();
        controlsPane.setBackground(Color.cyan);
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
                mediaPlayerComponent.getMediaPlayer().pause();
            }
        });

        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.getMediaPlayer().skip(-10000);
            }
        });

        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.getMediaPlayer().skip(10000);
            }
        });
       // frame2 = new JFrame("My First Media Player");
        //frame2.setBounds(50, 600, 300, 80);
        //frame2.setSize(200, 200);
        //frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
       frame.add(controlsPane, BorderLayout.SOUTH);
        // frame.add(rewindButton,BorderLayout.CENTER);
        // frame.add(skipButton,BorderLayout.CENTER);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     //frame.setVisible(true);

       
    }
}