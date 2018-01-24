/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image_display;
import static Image_display.Gui_Interface.delimeter;
import static Image_display.Gui_Interface.cols;
import static Image_display.Gui_Interface.obj_exists;
import com.mortennobel.imagescaling.ResampleOp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JViewport;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author KAVYA JANARDHANA
 */
public class Irig_slide_img extends javax.swing.JFrame {
    private IRIG_data IRIG_obj;
    private  int zoom_trigger=0;
    private int value_button=1;
    private Timer tm; 
    private int x=-1;
    private int k=0;
    private String[] Image_list;
    private List<Double> Irig_time;
    private  int pre_horizontal=-1;
    private int pre_vertical=-1;
    private int label_img_width;
    private int label_img_height; 
    private boolean time_milli=false;
    private  static BufferedImage image = null;
    private int notches=0;
    public double zoom_factor = 1.0; 
    private  double temp;
    int plus_minus=0;
    
    private JViewport jv1; 
    private JTextField col_header;
    private Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
    
    Irig_slide_img(String para_file,String Image_folder, int param_max_count) {
        super("Image Slide show");
        Irig_time = new ArrayList<Double>();
        initComponents(); 
        obj_exists=true;
         
        jv1 = new JViewport();
        col_header= new JTextField(" ");
        col_header.setBackground(Color.darkGray);
        col_header.setForeground(Color.white);
        Font font =  new Font("Calibri",Font.BOLD,14);
        col_header.setEditable(false);
        col_header.setFont(font);
        //jScrollPane1.setColumnHeaderView(jTextArea_slide);
        label_img_width=jLabel_Img.getWidth();
        label_img_height=jLabel_Img.getHeight();
        try {            
            IRIG_obj=new IRIG_data(Image_folder);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Problem  with IRIG data reading");
        }
        try{
              
            ImageIcon Next= new ImageIcon(".\\Button_Images\\NEXT.png");
            Next_jButton3.setIcon(Next);
            ImageIcon Previous= new ImageIcon(".\\Button_Images\\PREVIOUS.png");
            Previous_jButton3.setIcon(Previous);
            ImageIcon Play= new ImageIcon(".\\Button_Images\\PLAY.png");
            jButton1.setIcon(Play);
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        int i=0;
        File folder= new File(Image_folder);
        if(folder.exists()){
            File[] file_list = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".png")||name.toLowerCase().endsWith(".jpg");
                }
            });
            Image_list = new String [file_list.length];
            Arrays.sort(file_list);
            if(folder.isDirectory()){
                for(File file:file_list){
                    if(file.isFile()){
                        Image_list[i]=file.getAbsolutePath();
                        i++;
                    }
                }
            }
        }else
        {
            JOptionPane.showMessageDialog(null, "Folder does not exists");        
        }
        seek_jSlider.setVisible(false);
        seek_jSlider.setMinimum(0);
        seek_jSlider.setValue(0);
        seek_jSlider.setMaximum(Image_list.length-1);
        Parameter_display( para_file, param_max_count);
    }
    
    private void Parameter_display(String para_file,int param_max_count){

            FileReader fr_para = null;
        try {
            fr_para = new FileReader(para_file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader br_para = new BufferedReader(fr_para);
        String line_data, header;
        String [] split_data;
        String [] split_time;
        int col_number=0;  
        double   IRIG_time_calc; 
        try {
            if((header=br_para.readLine())!=null){
                int pos;
                String first_para;
                String[]  split_head;
                if((pos=header.lastIndexOf(";"))!=-1){
                //int pos2=header.indexOf(delimeter);
                first_para = header.substring(0,pos);
                split_head= header.substring(pos+1,header.length()).split(delimeter);
                col_header.setText(col_header.getText()+String.format("%-30s\t",first_para.trim()));
                }
                else{
                    split_head= header.split(delimeter);
                    col_header.setText(col_header.getText()+String.format("%-30s\t",split_head[0].trim()));
                }
            //split_head[0]=first_para;
               
                while(col_number<param_max_count&& col_number<cols.length && cols[col_number]!=-1){
                    System.out.println("cols[col_number]: "+cols[col_number]);
                    if(pos!=-1){
                        col_header.setText(col_header.getText()+String.format("%-30s\t",split_head[cols[col_number]].trim()));
                        jv1.setView(col_header);   
                        jScrollPane1.setColumnHeaderView(jv1);
                    }else{
                        col_header.setText(col_header.getText()+String.format("%-30s\t",split_head[cols[col_number]+1].trim()));
                        jv1.setView(col_header);   
                        jScrollPane1.setColumnHeaderView(jv1);
                    }
                    col_number++;
                    if(col_number==param_max_count)
                        break;
                }
                
            }
        } catch (IOException ex) {            
            Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        try {
            br_para.readLine();
            br_para.readLine();
            while((line_data =br_para.readLine())!=null){
    
                split_data = line_data.split(delimeter,-1);// trailing empty strings will not be discarded
                split_time=split_data[0].split(":"); 
                if(split_time.length==4)
                        time_milli=true;
                col_number=0;
                IRIG_time_calc=timestamp_value(split_data[0]);
                if(!Irig_time.contains(IRIG_time_calc)){
                    Irig_time.add(IRIG_time_calc);
                }
                jTextArea_slide.append(String.format("%-30s\t",split_data[0].trim()));
                while( col_number<param_max_count&&col_number<cols.length&&cols[col_number]!=-1){ 
                    jTextArea_slide.append(String.format("%-30s\t",split_data[cols[col_number]+1].trim()));                
                    col_number++;
                    if(col_number==param_max_count){
                        jTextArea_slide.append("\n");
                    }
                }
                //i++;
            }   } catch (IOException ex) {
            Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            br_para.close();
        } catch (IOException ex) {
            Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fr_para.close();
        } catch (IOException ex) {
            Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            jTextArea_slide.setCaretPosition(jTextArea_slide.getLineStartOffset(1));
        } catch (BadLocationException ex) {
            Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextArea_slide.setEditable(false);
        Irig_slide2();
    }
    
    private void Irig_slide2(){

        //jLabel_Img.setBounds(40, 30, 1000, 1000);
        tm= new Timer(1,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
             if(value_button==0){  
                try {
                    System.out.println("x value before is : "+x);
                    x++;
                    if(x>=Image_list.length){
                        x=0;
                        k=0;
                        //System.exit(0);
                    }
                    System.out.println("x value after is : "+x);
                     Display_Image(x);
                } catch (IOException ex) {
                    Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }
        });

        tm.start();

        getContentPane().setBackground(Color.ORANGE);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    } 

     private void Display_Image(int j) throws IOException {
        if(zoom_trigger==1){
              
            jScrollPane2.setViewportView(jLabel_Img);
            jScrollPane2.getViewport().setViewPosition(new Point(pre_horizontal,pre_vertical));
                        
        try {
            image = ImageIO.read(new File(Image_list[x]));
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(zoom_factor==1.0){
            ImageIcon icon= new ImageIcon(Image_list[x]);
            jLabel_Img.setIcon(icon);
            seek_jSlider.setValue(j);
            jLabel_zoomfactor.setText(String.valueOf(String.format("%.1f",zoom_factor))); 
        }
        else{
            plus_minus=0;
            resizeImage();
        }   
      
             
        }else{
            
         ImageIcon icon= new ImageIcon(Image_list[j]);
        Image pic=icon.getImage();
        Image newImg =pic.getScaledInstance(label_img_width,label_img_height,Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        jLabel_Img.setIcon(newImc);
        seek_jSlider.setValue(j);
        jLabel_zoomfactor.setText("-------");
        }
        System.out.println("for image value is: "+Image_list[x]+" "+" "+j);
        //if(j<IRIG_obj.IRIG_data_line.size())
         Double   current_IRIG_time=timestamp_value(IRIG_obj.IRIG_data_line[j]);
        jLabel_Irig_time.setText(IRIG_obj.IRIG_data_line[j]);         
        para_highlight(current_IRIG_time);
        jLabel_Img.setVisible(true);
    } 
    private Double timestamp_value(String timestamp_code){ 
        
        String time_stamp[] = timestamp_code.split(":");
        int index=1;
        if(time_stamp.length==5 || time_stamp.length==4)
            index=0;
        if(time_milli){
            double hh = TimeUnit.HOURS.toMillis(Long.parseLong(time_stamp[index]));
            double  mm  =    +TimeUnit.MINUTES.toMillis(Long.parseLong(time_stamp[index+1]));
            double  sec  =   TimeUnit.SECONDS.toMillis(Long.parseLong(time_stamp[index+2]));
            double   milli =   TimeUnit.MILLISECONDS.toMillis(Long.parseLong(time_stamp[index+3]));
            return hh+mm+sec+milli;    
        }
        else{
            double hh = TimeUnit.HOURS.toMicros(Long.parseLong(time_stamp[index]));
            double  mm  =    +TimeUnit.MINUTES.toMicros(Long.parseLong(time_stamp[index+1]));
            double  sec  =   TimeUnit.SECONDS.toMicros(Long.parseLong(time_stamp[index+2]));
            double   milli =   TimeUnit.MILLISECONDS.toMicros(Long.parseLong(time_stamp[index+3]));
            double micro=Long.parseLong(time_stamp[index+4]);
            return hh+mm+sec+milli+micro; 
        }
    }
    private void para_highlight(Double current_IRIG_time) throws IOException{
        while(k <Irig_time.size() &&(Irig_time.get(k)<=current_IRIG_time)){
            k++;
        }
        try {
            if(k!=0){
                if(k<jTextArea_slide.getLineCount()){
                    
                    if(k+14<jTextArea_slide.getLineCount()){
                        jTextArea_slide.setCaretPosition(jTextArea_slide.getLineStartOffset(k+14));// increase with noticing how many values can be displayed
                    }
                    jTextArea_slide.getHighlighter().removeAllHighlights();
                    jTextArea_slide.getHighlighter().addHighlight(jTextArea_slide.getLineStartOffset(k-1),jTextArea_slide.getLineEndOffset(k-1), painter);
                }
                else{
                    System.out.println("End of TextArea reached!!!");
                }
            }
        } catch (BadLocationException ex) {
                Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    public  void resizeImage() {

        pre_horizontal = jScrollPane2.getHorizontalScrollBar().getValue();
        pre_vertical = jScrollPane2.getVerticalScrollBar().getValue();
           
        ResampleOp  resampleOp = new ResampleOp((int)(image.getWidth()*zoom_factor), (int)(image.getHeight()*zoom_factor));
            BufferedImage resizedIcon = resampleOp.filter(image, null);
            Icon imageIcon = new ImageIcon(resizedIcon);
            jLabel_Img.setIcon(imageIcon);
            int   set_horizontal=pre_horizontal;
            int   set_vertical= pre_vertical;

        if(plus_minus == 1){
           if( notches<0 && value_button==1 ){
                set_vertical=(int) (set_vertical*1.2);
                set_horizontal=(int) (set_horizontal*1.2);
                jScrollPane2.getVerticalScrollBar().setValue(set_vertical);
                jScrollPane2.getHorizontalScrollBar().setValue(set_horizontal);
           }
           else if(notches>0 && value_button==1)
           {                
                set_vertical=(int) (set_vertical/1.2);
                set_horizontal=(int) (set_horizontal/1.2);
                jScrollPane2.getVerticalScrollBar().setValue(set_vertical);
                jScrollPane2.getHorizontalScrollBar().setValue(set_horizontal);
           }
        }
        pre_horizontal = jScrollPane2.getHorizontalScrollBar().getValue();
        pre_vertical = jScrollPane2.getVerticalScrollBar().getValue();
        jLabel_zoomfactor.setText(String.valueOf(String.format("%.1f",zoom_factor)));
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_Irig_time = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_slide = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        zoom_jButton2 = new javax.swing.JButton();
        Previous_jButton3 = new javax.swing.JButton();
        Next_jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel_Img = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_zoomfactor = new javax.swing.JLabel();
        plus = new javax.swing.JButton();
        minus = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        seek_jSlider = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel_Irig_time.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_Irig_time.setText("----------");

        jTextArea_slide.setColumns(20);
        jTextArea_slide.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextArea_slide.setRows(5);
        jScrollPane1.setViewportView(jTextArea_slide);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        zoom_jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        zoom_jButton2.setText("Activte Zoom window");
        zoom_jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoom_jButton2ActionPerformed(evt);
            }
        });

        Previous_jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Previous_jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Previous_jButton3ActionPerformed(evt);
            }
        });

        Next_jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Next_jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Next_jButton3ActionPerformed(evt);
            }
        });

        jLabel_Img.setBackground(java.awt.Color.orange);
        jLabel_Img.setText("Image Display");
        jLabel_Img.setOpaque(true);
        jScrollPane2.setViewportView(jLabel_Img);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Irig time :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Zoom Factor:");

        jLabel_zoomfactor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_zoomfactor.setText("----------");

        plus.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        plus.setText("+");
        plus.setMargin(new java.awt.Insets(2, 4, 2, 4));
        plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusActionPerformed(evt);
            }
        });

        minus.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        minus.setText("-");
        minus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        minus.setMargin(new java.awt.Insets(2, 4, 2, 4));
        minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusActionPerformed(evt);
            }
        });

        jLabel2.setText("--------");

        seek_jSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                seek_jSliderMouseDragged(evt);
            }
        });
        seek_jSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Irig_slide_img.this.mouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_zoomfactor)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Irig_time, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(seek_jSlider, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(plus, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(minus, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(zoom_jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Previous_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Next_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel_Irig_time, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seek_jSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Next_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Previous_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zoom_jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(plus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(minus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel_zoomfactor))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
           // previous = 0;
   jLabel_Img.setText(null);// donot change
     seek_jSlider.setVisible(true);
        ++value_button;
        value_button=value_button%2;
        if(value_button==0){
            try{
                 
               if(zoom_trigger==1){
                 pre_horizontal=jScrollPane2.getHorizontalScrollBar().getValue();
                 pre_vertical=jScrollPane2.getVerticalScrollBar().getValue();
                 
               }
                ImageIcon pause= new ImageIcon(".\\Button_Images\\PAUSE.png");
                jButton1.setIcon(pause);
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }else{
            try{
                ImageIcon play= new ImageIcon(".\\Button_Images\\PLAY.png");
                jButton1.setIcon(play);
             
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void Previous_jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Previous_jButton3ActionPerformed
        // TODO add your handling code here:
       if(value_button==1&&x!=-1){
            k--;
            x--;
            if(x < 0)
            {
                x = 0;
            }
        try {     
            Display_Image(x);
        } catch (IOException ex) {
            Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }//GEN-LAST:event_Previous_jButton3ActionPerformed

    private void zoom_jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoom_jButton2ActionPerformed
    
          if(x==-1){
             JOptionPane.showMessageDialog(null, "Image is not loaded to display");
        }else if(value_button==1){                
            ++zoom_trigger; 
            zoom_trigger=zoom_trigger%2;
            if(zoom_trigger==0){    
                
                zoom_jButton2.setText("Activate Zoom Window");
                if(pre_horizontal!=-1 && pre_vertical!=-1 ){
                    pre_horizontal = jScrollPane2.getHorizontalScrollBar().getValue();
                    pre_vertical = jScrollPane2.getVerticalScrollBar().getValue();
                } 
                try {    
                    Display_Image(x);
                } catch (IOException ex) {
                            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                zoom_jButton2.setText("Deactivate Zoom Window");
                    jScrollPane2.setViewportView(jLabel_Img);
                    jScrollPane2.getViewport().setViewPosition(new Point(pre_horizontal,pre_vertical));
                    try {
                        image = ImageIO.read(new File(Image_list[x]));
                    } catch (IOException ex) {
                        Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(zoom_factor==1.0){            
                            
                        ImageIcon icon= new ImageIcon(Image_list[x]);
                        jLabel_Img.setIcon(icon);
                        jLabel_zoomfactor.setText(String.valueOf(String.format("%.1f",zoom_factor))); 
                        if(pre_horizontal==-1 && pre_vertical==-1 ){
                                Rectangle bounds =  jScrollPane2.getViewport().getViewRect();
                                Dimension size =  jScrollPane2.getViewport().getViewSize();
                                pre_horizontal=(size.width-bounds.width)/2;
                                pre_vertical=(size.height-bounds.height)/2;
                                jScrollPane2.getViewport().setViewPosition(new Point((size.width-bounds.width)/2,(size.height-bounds.height)/2));
                        }
                    }
                    else{
                        plus_minus=0;
                        resizeImage();
                    }   
            }
        }
    }//GEN-LAST:event_zoom_jButton2ActionPerformed

    private void Next_jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Next_jButton3ActionPerformed
    if(value_button==1&&x!=-1){

            if(x<Image_list.length-1){
                        x++;
                try {
                    Display_Image(x);
                } catch (IOException ex) {
                    Logger.getLogger(Irig_slide_img.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }else
            {
                x  = Image_list.length - 1;
            }
            
    }
        
    }//GEN-LAST:event_Next_jButton3ActionPerformed

    private void plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusActionPerformed
        // TODO add your handling code here:
        if(zoom_trigger==1){
            notches=-1;
            temp = zoom_factor - (notches * 0.2);
            temp = Math.max(temp, 1.0);
            if (temp != zoom_factor) {
                zoom_factor = temp;
                plus_minus=1;
                resizeImage();
            }

        }
    }//GEN-LAST:event_plusActionPerformed

    private void minusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusActionPerformed
        // TODO add your handling code here:
        if(zoom_trigger==1){
            notches=1;
            temp = zoom_factor - ((notches) * 0.2);
            temp = Math.max(temp, 1.0);
            if (temp != zoom_factor) {
                zoom_factor = temp;
                plus_minus=1;
                resizeImage();
            }
        }
    }//GEN-LAST:event_minusActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
               
                obj_exists=false;
                evt.getWindow().dispose();
    }//GEN-LAST:event_formWindowClosed

    private void mouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClicked
        // TODO add your handling code here:
        if(value_button==1){
            JSlider sourceSlider =(JSlider)evt.getSource();
            BasicSliderUI ui = (BasicSliderUI) seek_jSlider.getUI();
            Point p= evt.getPoint();
            int value  = ui.valueForXPosition(p.x);
            seek_jSlider.setValue(value);
            x=seek_jSlider.getValue();
            try {
               k=0;
                Display_Image(x);             
            } catch (IOException ex) {
                Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_mouseClicked

    private void seek_jSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seek_jSliderMouseDragged
        // TODO add your handling code here:
          if(value_button==1){
            x=seek_jSlider.getValue();
            try {
                k=0;
                Display_Image(x);
            } catch (IOException ex) {
                Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_seek_jSliderMouseDragged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */ 
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Irig_slide_img.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Irig_slide_img.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Irig_slide_img.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Irig_slide_img.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             //new Irig_slide().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Next_jButton3;
    private javax.swing.JButton Previous_jButton3;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_Img;
    private javax.swing.JLabel jLabel_Irig_time;
    private javax.swing.JLabel jLabel_zoomfactor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea_slide;
    private javax.swing.JButton minus;
    private javax.swing.JButton plus;
    private javax.swing.JSlider seek_jSlider;
    private javax.swing.JButton zoom_jButton2;
    // End of variables declaration//GEN-END:variables
}

