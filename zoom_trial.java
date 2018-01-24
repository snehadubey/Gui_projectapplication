/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image_display;

import com.mortennobel.imagescaling.ResampleOp;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Admin
 */
public class zoom_trial {

    
    public static double zoom = 1.0;  // zoom factor
    
    static int value=1;
    private  static BufferedImage image = null;

   // static JFrame myJFrame;
    //static JScrollPane scrollPane;
    //static JPanel panel;
   // static JLabel label2;
    
        static int horizontal_pos1; 
    static int vertical_pos1;
    
    static int pre_horizontal= 0;
    static int pre_vertical=0;
    
    zoom_trial() throws IOException{
   
       // myJFrame = new JFrame();
       // myJFrame.setTitle("Image Zoom In and Zoom Out");
        //myJFrame.setBounds(100, 100, 550,350);

       // panel = new JPanel(new BorderLayout());
       // scrollPane = new JScrollPane(panel);
       // myJFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
              
      //  label2= new JLabel("test");
    }
    public  void display(String s2,JLabel label2,JScrollPane scrollPane) throws IOException{
          
        
//System.out.println(Arrays.toString(inputImage_arry));
        //System.out.println("display: "+s2);
        image = ImageIO.read(new File(s2));
        ImageIcon icon= new ImageIcon(s2);
        scrollPane.getHorizontalScrollBar().setValue(pre_horizontal);
        scrollPane.getVerticalScrollBar().setValue(pre_vertical);
        
       
        label2.setIcon(icon);
       // myJFrame.setVisible(true);
        //System.out.println("pre_horizontal: "+pre_horizontal);
        //System.out.println("pre_vertical: "+pre_vertical+"  "+value);
        //if(Irig_slide.value_button==1){
        
            label2.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            if(e.getClickCount()==2){
            // your code here
            System.out.println("doubleclicked");
            pre_horizontal=scrollPane.getHorizontalScrollBar().getValue();
            pre_vertical=scrollPane.getVerticalScrollBar().getValue();
            //System.out.println("pre_horizontal: "+pre_horizontal);
            //System.out.println("pre_vertical: "+pre_vertical);
            //pre_horizontal=912;
            //pre_vertical=1250;
           //System.out.println("value: "+value);
            value=value+1;
            value=value%2;
             
            }
        }
        });
   
        label2.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
               System.out.println("zoom value "+zoom);
                horizontal_pos1 = scrollPane.getHorizontalScrollBar().getValue();
                vertical_pos1 = scrollPane.getVerticalScrollBar().getValue();
                //System.out.println("horizontal_pos1: "+horizontal_pos1);
                //System.out.println("vertical_pos1: "+vertical_pos1);
                int notches = e.getWheelRotation();
                double temp = zoom - (notches * 0.2);
                // minimum zoom factor is 1.0
                temp = Math.max(temp, 1.0);
                if (temp != zoom) {
                    zoom = temp;
                    resizeImage(notches, label2, scrollPane); 
                }
            }
        });
   // }    
      
        scrollPane.setPreferredSize(null); 
        scrollPane.setViewportView(label2);
        //panel.add(label2);
        label2.setVisible(true);
        //myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //myJFrame.setVisible(true);
    }
    public  void resizeImage(int notches,JLabel label2,JScrollPane scrollPane) {
            
           System.out.println("Zoom factor: "+zoom);
           ResampleOp  resampleOp = new ResampleOp((int)(image.getWidth()*zoom), (int)(image.getHeight()*zoom));
               BufferedImage resizedIcon = resampleOp.filter(image, null);
           Icon imageIcon = new ImageIcon(resizedIcon);
           label2.setIcon(imageIcon);
           
          
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
           }
        }
    
}