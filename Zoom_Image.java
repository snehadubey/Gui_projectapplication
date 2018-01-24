
package Image_display;

import com.mortennobel.imagescaling.ResampleOp;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO; 
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Praveen
 */


class Zoom_Image{
       
    public double zoom = 1.0;  // zoom factor
    int value=0;
    private  static BufferedImage image = null;

    private JFrame myJFrame;
   // private JScrollPane scrollPane;
    //private JPanel panel;
    private JLabel label2;
    double temp;
    private int horizontal_pos1; 
    private int vertical_pos1;
    
    private int pre_horizontal= 0;
    private int pre_vertical=0;
    
    Zoom_Image() throws IOException{
        //myJFrame = new JFrame();
        //myJFrame.setTitle("Image Zoom In and Zoom Out");
        //myJFrame.setBounds(100, 100, 550,350);
        //label2= new JLabel("test");
        //panel = new JPanel(new BorderLayout());
       // scrollPane = new JScrollPane(label2);
        //label2.add(scrollPane, BorderLayout.CENTER);
      // myJFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
              
        
    }
    public  void display(String s2,JLabel label2,JScrollPane scrollPane,int value_button,JLabel jLabel_zoomfactor) throws IOException{

        image = ImageIO.read(new File(s2));
        ImageIcon icon= new ImageIcon(s2);
        //System.out.println("display value: "+pre_horizontal+","+pre_vertical);
        label2.setIcon(icon);
       // scrollPane.getHorizontalScrollBar().setValue(pre_horizontal);
       // scrollPane.getVerticalScrollBar().setValue(pre_vertical);
        
         // scrollPane.setViewportView(label2);
         // scrollPane.getViewport().setViewPosition(new Point(pre_horizontal,pre_vertical));
         jLabel_zoomfactor.setText("1.0");
        //myJFrame.setVisible(true);
         if(value_button==1){ 
           /* label2.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            if(e.getClickCount()==2){
            // your code here
            System.out.println("doubleclicked");
            pre_horizontal=scrollPane.getHorizontalScrollBar().getValue();
            pre_vertical=scrollPane.getVerticalScrollBar().getValue();
           //System.out.println("value: "+value);
            value=value+1;
            value=value%2;
             
            }
        }
        });
   
       */
        label2.addMouseWheelListener(new MouseWheelListener() {
            
            public void mouseWheelMoved(MouseWheelEvent e) {
           
                vertical_pos1 = scrollPane.getVerticalScrollBar().getValue();
                horizontal_pos1 = scrollPane.getHorizontalScrollBar().getValue();
                
                int notches = e.getWheelRotation();
                temp = zoom - (notches * 0.2);
                // minimum zoom factor is 1.0
                temp = Math.max(temp, 1.0);
                if (temp != zoom) {
                    zoom = temp;
                    resizeImage( notches, label2, scrollPane,jLabel_zoomfactor);
                }
                 //System.out.println("mouse listener in");
            }
            
        });
         }
        scrollPane.setPreferredSize(null); 
        scrollPane.setViewportView(label2);
       // panel.add(label2);
        label2.setVisible(true);
        //myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // myJFrame.setVisible(true);

    }
     public  void resizeImage(int notches,JLabel label2,JScrollPane scrollPane,JLabel jLabel_zoomfactor) {
            
           System.out.println("Zoom factor: "+zoom);
           ResampleOp  resampleOp = new ResampleOp((int)(image.getWidth()*zoom), (int)(image.getHeight()*zoom));
           BufferedImage resizedIcon = resampleOp.filter(image, null);
           Icon imageIcon = new ImageIcon(resizedIcon);
           label2.setIcon(imageIcon);
           jLabel_zoomfactor.setText(String.valueOf(String.format("%.1f",zoom)));
          
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
