package Gui_components;


import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;

public class Frames_extraction {
    
    public static void main(String args[]) throws FrameGrabber.Exception, IOException{
        
    FFmpegFrameGrabber g = new FFmpegFrameGrabber("C:\\Users\\rana\\Desktop\\Gui_Project\\C1S01_timestamp.avi");
    g.start();

    for (int i = 0 ; i < 5 ; i++) {
        ImageIO.write(g.grab().getBufferedImage(), "avi", new File("C:\\Users\\rana\\Desktop\\Gui_Project\\videos" + (i+1) + ".avi"));
    }

    g.stop();

}
}
