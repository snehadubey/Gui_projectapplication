
package Gui_components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Praveen
 */
public class IRIG_Video {
    
  public static void main(String[] args){
        
IRIG_Video obj1=new IRIG_Video();
obj1.mergeImageAndText();
    }

    public static void mergeImageAndText(){
      try {
          String url = "F:\\Gui_Project\\Chrysanthemum.jpg";
          String text = "Hello Java Imaging!";
          File jpegFile = new File(url);
          BufferedImage im = ImageIO.read(jpegFile);
          Graphics2D g2 = im.createGraphics();
          g2.setFont(g2.getFont().deriveFont(30f));
          g2.drawString(text, 50, 50);
          g2.dispose();
          ImageIO.write(im, "png", new File("F:\\Gui_Project\\test01.png"));
        } catch (IOException ex) {
          Logger.getLogger(IRIG_Video.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
}
