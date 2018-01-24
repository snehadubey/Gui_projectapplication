
package Image_display;
import com.mortennobel.imagescaling.ResampleOp;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Admin
 */
public class test {
       private double zoom = 1.0;  // zoom factor
       static String names= "E:\\Gui_Project\\Images\\C1S01_2013.png";//= new String[4];
       static String array_str[];
       static int value=1;
       private  static BufferedImage image = null;

    static JFrame myJFrame;
    static JScrollPane scrollPane;
    static JPanel panel;
    static JLabel label2;
    
        int horizontal_pos1; 
    int vertical_pos1;
    
    static int pre_horizontal= 0;
    static int pre_vertical=0;
    public static void main(String args[]) throws IOException{
        
        test t1= new test();
        t1.display(names);
        System.out.println("myJFrame.setVisible(true);"+names);
       /*file_path();
        for(String s1:array_str){
            System.out.println("value: "+value);
            t1.display(s1);
            try {
                while(value==1){
                    Thread.sleep(100);
                    //System.out.println("thread sleep");  
                }
                 if(value==0){
                      t1.display(s1);
                    } 
               // continue;
                //Thread.sleep(100);
            } catch (InterruptedException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
         public static void file_path(){
    
    File file = new File("E:\\Gui_Project\\Images\\");
		File[] files = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(name.toLowerCase().endsWith(".png")){
					return true;
				} else {
					return false;
				}
			}
		});
                array_str = new String[files.length];
                int i=0;
		for(File f:files){
			
                    String str = f.getAbsolutePath();
                  
                    array_str[i]=str;
                      //System.out.println("path:  "+ i+array_str[i]);
                    i++;
                    
		}
                //System.out.println("length:  "+array_str.length);
               for(String S:array_str){
                    System.out.println("array contents:  "+S);
                }
        }
    test() throws IOException{
   
        myJFrame = new JFrame();
        myJFrame.setTitle("Image Zoom In and Zoom Out");
        myJFrame.setBounds(100, 100, 550,350);
       // myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(panel);
        myJFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
              
        label2= new JLabel("test");
        //display(names);
        //file_path();
        /*for(String s1:array_str){
            System.out.println("value: "+value);
            display(s1);
            try {
                Thread.sleep(100);
                /*try {
                while(value==1){
                Thread.sleep(100);
                //System.out.println("thread sleep");  
                }
                if(value==0){
                display(s1);
                }
                // continue;
                //Thread.sleep(100);
                } catch (InterruptedException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
    public static void display(String s2) throws IOException{
        System.out.println("correct");
        //System.out.println(Arrays.toString(inputImage_arry));
        System.out.println("display: "+s2);
        //System.out.println("s2: "+s2);
        image = ImageIO.read(new File(s2));
        ImageIcon icon= new ImageIcon(s2);
        //scrollPane.getHorizontalScrollBar().setValue(1200);
        //scrollPane.getVerticalScrollBar().setValue(800);
        
       
        label2.setIcon(icon);
        myJFrame.setVisible(true);
         System.out.println("pre_horizontal: "+pre_horizontal);
        System.out.println("pre_vertical: "+pre_vertical+"  "+value);
        /*if(value==1){
        
            panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            if(e.getClickCount()==2){
            // your code here
            System.out.println("doubleclicked");
            pre_horizontal=scrollPane.getHorizontalScrollBar().getValue();
            pre_vertical=scrollPane.getVerticalScrollBar().getValue();
             System.out.println("pre_horizontal: "+pre_horizontal);
            System.out.println("pre_vertical: "+pre_vertical);
            //pre_horizontal=912;
            //pre_vertical=1250;
           System.out.println("value: "+value);
            value=value+1;
            value=value%2;
             
            }
        }
        });
   
        panel.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
               
                horizontal_pos1 = scrollPane.getHorizontalScrollBar().getValue();
                vertical_pos1 = scrollPane.getVerticalScrollBar().getValue();
                System.out.println("horizontal_pos1: "+horizontal_pos1);
                System.out.println("vertical_pos1: "+vertical_pos1);
                int notches = e.getWheelRotation();
                double temp = zoom - (notches * 0.2);
                // minimum zoom factor is 1.0
                temp = Math.max(temp, 1.0);
                if (temp != zoom) {
                    zoom = temp;
                    resizeImage(notches);
                }
            }
        });
    }    */
      
        scrollPane.setPreferredSize(null); 
        scrollPane.setViewportView(panel);
        panel.add(label2);
        label2.setVisible(true);
        //myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.setVisible(true);
    }
     public void resizeImage(int notches) {
            System.out.println("jgfkjhgk");
           System.out.println(zoom);
           ResampleOp  resampleOp = new ResampleOp((int)(image.getWidth()*zoom), (int)(image.getHeight()*zoom));
               BufferedImage resizedIcon = resampleOp.filter(image, null);
           Icon imageIcon = new ImageIcon(resizedIcon);
           label2.setIcon(imageIcon);
           
          /*
           if(notches<0){
                int set_vertical=(int) (vertical_pos1*1.2);
                int set_horizontal=(int) (horizontal_pos1*1.2);
                scrollPane.getVerticalScrollBar().setValue(set_vertical);
                scrollPane.getHorizontalScrollBar().setValue(set_horizontal);
           }
           else if(notches>0)
           {
           
                int set_vertical=(int) (vertical_pos1/1.2);
                int set_horizontal=(int) (horizontal_pos1/1.2);
                scrollPane.getVerticalScrollBar().setValue(set_vertical);
                scrollPane.getHorizontalScrollBar().setValue(set_horizontal);
           }*/
        }
    
}
