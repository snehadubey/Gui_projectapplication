/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui_components;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
//import org.bytedeco.javacv.OpenCVFrameConverter;

public class Frame_extraction {
   /* public static void main(String []args) throws IOException, Exception, InterruptedException, ExecutionException
    {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber("F:\\Gui_Project\\videos\\RAN_3806.mp4");
        frameGrabber.start();
        IplImage i;
        try {
            for(int ii=0;ii<frameGrabber.getLengthInFrames();ii++){

             OpenCVFrameConverter.ToIplImage con= new OpenCVFrameConverter.ToIplImage ();
            i = con.convertToIplImage(frameGrabber.grab());
            BufferedImage  bi = i.getBufferedImage();
            String path = "F:\\Gui_Project\\videos\\"+ii+".png";
            ImageIO.write(bi,"png", new File(path));

            }
            frameGrabber.stop();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}
