package Image_display;

/**
 *
 * @author KAVYA JANARDHANA
 */
import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import com.mortennobel.imagescaling.ResampleOp;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JViewport;

public class ImageZoom {

    
    private int actual_width;
    private int actual_height;
    private int set_width;
    private int set_height;
    int horizontal_pos1; 
    int vertical_pos1;
    double temp;
    
    
    
    
    private JFrame frmImageZoomIn;
    private static String inputImage;// give image path here
    private JLabel label = null; 
    private double zoom = 1.0;  // zoom factor
    private BufferedImage image = null;
    JScrollPane scrollPane = new JScrollPane();
        //frmImageZoomIn.getContentPane().add(scrollPane, BorderLayout.CENTER);
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ImageZoom window = new ImageZoom("F:\\Gui_Project\\Images\\aaa.jpg");
                    window.frmImageZoomIn.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     * @throws IOException 
     */
    public ImageZoom(String zoom_Image) throws IOException {
       
        initialize(zoom_Image);
         frmImageZoomIn.setVisible(true);
        System.out.println("Zooming called!!!!");
        
    }

    /**
     * Initialize the contents of the frame.
     * @throws IOException 
     */
    private void initialize(String zoom_Image) throws IOException {
        
        inputImage= zoom_Image; // give image path here
        frmImageZoomIn = new JFrame();
        frmImageZoomIn.setTitle("Image Zoom In and Zoom Out");
        frmImageZoomIn.setBounds(100, 100, 450, 300);
        //frmImageZoomIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //JScrollPane scrollPane = new JScrollPane();
        frmImageZoomIn.getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setLocation(500, 500);
        scrollPane.setToolTipText(inputImage);
        
        image = ImageIO.read(new File(inputImage));
        
        actual_width=(int)(image.getWidth());
        actual_height=(int)(image.getHeight());
        System.out.println("Actual Image Width X Height: "+actual_width+"X"+actual_height);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // display image as icon 
        Icon imageIcon = new ImageIcon(inputImage);
        label = new JLabel( imageIcon );
    
        //panel.add(label, BorderLayout.CENTER);
        
         
        panel.addMouseWheelListener(new MouseWheelListener() {
            
            public void mouseWheelMoved(MouseWheelEvent e) {
                final JViewport viewport = scrollPane.getViewport();
                vertical_pos1 = scrollPane.getVerticalScrollBar().getValue();
                horizontal_pos1 = scrollPane.getHorizontalScrollBar().getValue();
                System.out.println("horizontal_pos1: "+horizontal_pos1);
                System.out.println("vertical_pos1: "+vertical_pos1);
                System.out.println();
                
                int notches = e.getWheelRotation();
                System.out.println("notches value: "+notches);
                temp = zoom - (notches * 0.2);
                System.out.println("zoom value: "+zoom);
                System.out.println("temp value: "+temp);
                // minimum zoom factor is 1.0
                temp = Math.max(temp, 1.0);
                if (temp != zoom) {
                    zoom = temp;
                    resizeImage();
                }
                 System.out.println("mouse listener in");
            }
            
        });
        
        System.out.println("mouse listener out");
        scrollPane.setViewportView(label);
            scrollPane.getHorizontalScrollBar().setValue(250);
        scrollPane.getVerticalScrollBar().setValue(250);
    }
    
    public void resizeImage() {

        double zoom_previous=zoom;
        System.out.println("Zoom factor: "+zoom);

        int zoom_width=(int)(image.getWidth()*(zoom));
        int zoom_height=(int)(image.getHeight()*(zoom));
           
        set_width=actual_width+(zoom_width-actual_width);
        set_height=actual_height+(zoom_height-actual_height);
          
        System.out.println("Zoomed Width X Height:  "+zoom_width+"X"+zoom_height);
        System.out.println("Zooming calculated: "+set_width+"X"+set_height);
        ResampleOp  resampleOp = new ResampleOp((int)(image.getWidth()*zoom), (int)(image.getHeight()*zoom));
        BufferedImage resizedIcon = resampleOp.filter(image, null);
        Icon imageIcon = new ImageIcon(resizedIcon);
        label.setIcon(imageIcon);
           
        int vertical_pos2 = scrollPane.getVerticalScrollBar().getValue();
        int horizontal_pos2 = scrollPane.getHorizontalScrollBar().getValue();
        
        System.out.println("horizontal_pos2: "+horizontal_pos2);
        System.out.println("vertical_pos2: "+vertical_pos2+"\n");
        
        int set_vertical=(int) ((vertical_pos2+(vertical_pos2-vertical_pos1))*1.2);
        int set_horizontal=(int) ((horizontal_pos2+(horizontal_pos2-horizontal_pos1))*1.2);
        
        System.out.println();
        scrollPane.getVerticalScrollBar().setValue(set_vertical);
        scrollPane.getHorizontalScrollBar().setValue(set_horizontal);
        //frmImageZoomIn.setBounds(100, 100, (int)(450*1.2), (int)(300*1.2));
        System.out.println("set_horizontal: "+set_horizontal);
        System.out.println("set_vertical: "+set_vertical+"\n");
 
        }
}
