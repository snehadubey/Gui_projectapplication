
package Image_display;
import static Image_display.Gui_Interface.cols;
import static Image_display.Gui_Interface.delimeter;
import static Image_display.Gui_Interface.obj_exists;
import com.mortennobel.imagescaling.ResampleOp;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicSliderUI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
/**
 *
 * @author KAVYA JANARDHANA
 */
public class Irig_graph_Img extends javax.swing.JFrame {

    private IRIG_data IRIG_obj;
    private int zoom_trigger=0;
    private int value_button=1;
    private Timer tm;
    private int x=-1;
    private int k=0;
    private String[] Image_list;
    private List<Double> Irig_time;
    private List<String> timestamp;
    private int pre_horizontal=-1;
    private int pre_vertical=-1;
    private String para_file= null;
    private int label_img_width;
    private int label_img_height;
    private int notches=0;
    private double zoom_factor = 1.0; 
    private double temp;
    private  static BufferedImage image = null;
    private int plus_minus =0; 
    
    private XYLineAndShapeRenderer renderer;
    private CrosshairOverlay crosshairOverlay;
    private XYPlot plot;
    private int col_length= cols.length;
    private XYSeries[] series = new XYSeries[col_length];
    private  XYSeriesCollection dataset1;
    private List<String> parameter_name; 
    private Double cross_set = null;
    private int test_time=1;//not required
    private boolean time_milli=false;
    
    Irig_graph_Img(String para_file,String Image_folder, int param_max_count) {
     
        super("Image Slide show");
        initComponents();
        x=-1;
        k=0;
        obj_exists=true;
        label_img_width=jLabel_Img.getWidth();
        label_img_height=jLabel_Img.getHeight();
        this.para_file=para_file;
        Irig_time = new ArrayList<>();
        timestamp = new ArrayList<>();
        try {            
            IRIG_obj=new IRIG_data(Image_folder);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Problem  with IRIG data reading");
        }
        System.out.flush();
        try{ 
            
            ImageIcon Next= new ImageIcon(".\\Button_Images\\NEXT.png");
            Next_jButton3.setIcon(Next);
            ImageIcon Previous= new ImageIcon(".\\Button_Images\\PREVIOUS.png");
            Previous_jButton3.setIcon(Previous);
            ImageIcon Play= new ImageIcon(".\\Button_Images\\PLAY.png");
            Play_Pause_jButton.setIcon(Play);
        
        }
        catch(Exception ex){
                System.out.println(ex);
        }

        int i=0;
        File folder= new File(Image_folder);
        if(folder.exists()){
            File[] file_list = folder.listFiles(new FilenameFilter() {
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

        jSlider_Img.setVisible(true);
        jSlider_Img.setMinimum(0);
        jSlider_Img.setValue(0);
        jSlider_Img.setMaximum(Image_list.length-1);
        this.pack();
        try {
            createContent();
            
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(true);
        Irig_slide2();
    }
    
    private void createContent() throws FileNotFoundException, IOException {//1
        ChartPanel chartPanel;
        JFreeChart chart = createChart(createDataset2());
        chartPanel = new ChartPanel(chart);
        chart.setBackgroundPaint(Color.pink);
        chart.getTitle().setPaint(Color.red);
        //this.chartPanel.addChartMouseListener(this);
        crosshairOverlay = new CrosshairOverlay();        
        //xCrosshair = new Crosshair(Double.NaN, Color.black, new BasicStroke(0.0f));
        chartPanel.setSize(jPanel_graph.getWidth(), jPanel_graph.getHeight());
        chartPanel.setVisible(true);
        chartPanel.setMouseWheelEnabled(true);
        jPanel_graph.add(chartPanel, BorderLayout.CENTER);
        jPanel_graph.repaint();
        //crosshairOverlay.addDomainCrosshair(xCrosshair);
        chartPanel.addOverlay(crosshairOverlay);
        jPanel_graph.add(chartPanel, BorderLayout.CENTER);
        jPanel_graph.repaint();
    }
    
    private XYDataset createDataset2(){

        FileReader fr_para = null;
        try {
            fr_para = new FileReader(para_file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br_para = new BufferedReader(fr_para);
        String line, header;
        String [] split_data;
        int col_number=0;
        double   IRIG_time_calc; 
        try {
            if((header=br_para.readLine())!=null){
                    
                int pos;
                String[]  split_head;
                pos=header.lastIndexOf(";");
                if(pos!=-1){
                    split_head= header.substring(pos+1,header.length()).split(delimeter);
                }
                else{
                    split_head= header.split(delimeter);
                }
                while(col_number<col_length && cols[col_number]!=-1){        
                    System.out.println("cols[col_number]: "+cols[col_number]);
                    if(pos!=-1)
                        series[col_number] = new XYSeries(split_head[cols[col_number]]);
                    else 
                        series[col_number] = new XYSeries(split_head[cols[col_number]+1]);
                    col_number++;                   
                }
            }
            br_para.readLine();
            br_para.readLine();
          
            while((line =br_para.readLine())!=null){
                split_data = line.split(delimeter,-1);// trailing empty strings will not be discarded
                if(test_time==1){
                    String time_stamp[] = split_data[0].split(":");
                    if(time_stamp.length==4)
                        time_milli=true;
                }    
                col_number=0;
                IRIG_time_calc=timestamp_value(split_data[0]);

                //System.out.println("col_length.length: "+col_length);
                while(col_number<col_length&&cols[col_number]!=-1){
                  // System.out.println("col_number: "+col_number);
                    if(!split_data[cols[col_number]+1].equals("")){
                        if(!Irig_time.contains(IRIG_time_calc)){
                            Irig_time.add(IRIG_time_calc);
                            timestamp.add(split_data[0]);    
                        }
                        series[col_number].add(IRIG_time_calc,Double.parseDouble(split_data[cols[col_number]+1]));
                    }
                   col_number++;
                }
            }   
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {   
                br_para.close();
            if(fr_para!=null)
                fr_para.close();
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
        }      
        dataset1 = new XYSeriesCollection();
        for(int j=0;j<col_length;j++){      
            dataset1.addSeries(series[j]);     
        }
        parameter_name = new ArrayList<String>();
        for(int j=0;j<dataset1.getSeriesCount();j++){    
            parameter_name.add(String.valueOf(series[j].getKey()));
            series[j].setKey(series[j].getKey()+"\t");
        }
       /* // displays the data os series in dataset
        for(int j2=0;j2<dataset1.getSeriesCount();j2++){    
            for(int j1=0;j1<series[j2].getMaximumItemCount();j1++){
                System.out.println("series[j2] data "+j2+" "+series[j2].getItems());
            }
        }*/
       //for(int j=0;j<Irig_time.size();j++)
            //System.out.println("data@ "+j+" is "+Irig_time.get(j)+"==="+timestamp.get(j));
        return  dataset1; 
    } 
      
    private JFreeChart createChart(XYDataset dataset) {//3
        
        JFreeChart chart = ChartFactory.createXYLineChart("Irig time vs Parameters", 
        "Irig Time------->", "Parameter------>", dataset,PlotOrientation.VERTICAL, true, true, false);
        plot= chart.getXYPlot();
        renderer=new XYLineAndShapeRenderer();
        plot.setRenderer(renderer); 

        renderer.setBaseShapesVisible(false);
        renderer.setBaseShapesFilled(false);
        plot.getDomainAxis().setTickLabelsVisible(false);
        if(time_milli){
            plot.getDomainAxis().setTickLabelsVisible(true);
            chart.getXYPlot().getDomainAxis().setVerticalTickLabels(true);
            DateAxis date_axis =new DateAxis();
            SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss:SSS");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            sdf.format(new Date().getTime());
            date_axis.setDateFormatOverride(sdf);
            //date_axis.getMaximumDate();
            plot.setDomainAxis(date_axis);
        }
   
        chart.getXYPlot().getDomainAxis().setVerticalTickLabels(true);
        LegendTitle legend = chart.getLegend();
        Font labelfont = new Font("Arial",Font.BOLD, 12);
        legend.setItemFont(labelfont);
        chart.getLegend().setFrame(BlockBorder.NONE);
        legend.setPosition(RectangleEdge.BOTTOM);    
        for(int j=0;j<dataset1.getSeriesCount();j++){
            plot.getRenderer().setSeriesStroke(j, new BasicStroke(2));               
        }
        chart.getXYPlot().getRenderer();
        return chart;
    }
    
    

    private void Irig_slide2(){

        tm= new Timer(1,new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            if(value_button==0){  
                try {
                    System.out.println("before pre increment x value is : "+x);
                    x++;
                    if(x>=Image_list.length){
                        x=0;
                        k=0;
                        //System.exit(0);
                    }
                    System.out.println("x value is : "+x);
                    Display_Image(x);
                    //chartPanel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(zoom_factor==1.0){
                ImageIcon icon= new ImageIcon(Image_list[x]);
                jLabel_Img.setIcon(icon);
                jSlider_Img.setValue(j);
                jLabel_zoomfactor.setText(String.valueOf(String.format("%.1f",zoom_factor))); 
            }
            else{
                plus_minus = 0;
              resizeImage();
            }   
      
        }else{
         
            ImageIcon icon= new ImageIcon(Image_list[j]);
            Image pic=icon.getImage();
            Image newImg =pic.getScaledInstance(label_img_width,label_img_height,Image.SCALE_SMOOTH);
            ImageIcon newImc = new ImageIcon(newImg);
            jLabel_Img.setIcon(newImc);
            jSlider_Img.setValue(j);
            jLabel_zoomfactor.setText("-------");
        }
        System.out.println("for image value is: "+Image_list[x]+" "+jSlider_Img.getValue()+" "+j);
        Double   IRIG_time=timestamp_value(IRIG_obj.IRIG_data_line[j]);
        jLabel_Irig_time.setText(IRIG_obj.IRIG_data_line[j]);  
        series_data3(IRIG_time);
        jLabel_Img.setVisible(true);
    } 
 
    private void series_data(Double IRIG_time){
        
        while(k>=0 && k < Irig_time.size() &&(Irig_time.get(k)<=IRIG_time)){ 
            k++;
        }
       
        if(k-1>=0 && k < Irig_time.size()){
        cross_set=Irig_time.get(k-1);
        setCrosshairLocation(cross_set);
        }
        if(k-1>=0 && k < Irig_time.size()){
            for(int series_no=0;series_no<col_length;series_no++){      
                String y_value=parameter_name.get(series_no);
                String y_value_after=y_value+" : "+String.valueOf(series[series_no].getY(k-1));
                int pos2=y_value_after.lastIndexOf(" : ")+1;
                y_value_after=y_value+" "+y_value_after.substring(pos2,y_value_after.length())+"        ";  
                series[series_no].setKey(y_value_after);
                renderer.setSeriesVisibleInLegend(series_no, true);
            }
        }
    }
    private void series_data3(Double IRIG_time){   
        
       // series_data2=true;
        for(int g=0;g<dataset1.getSeriesCount();g++){
            series[g].setKey(parameter_name.get(g)+"\t");
        }
        while(k>=0 && k < Irig_time.size() &&(Irig_time.get(k)<=IRIG_time)){ 
            k++;
        }
        int index=k-1;
        if(index>=0 && index < Irig_time.size()){
        cross_set=Irig_time.get(index);
        }
        int pos_data;
        for(int series_no=0;series_no<col_length;series_no++){      
            
            if((cross_set!=null)&&(pos_data=series[series_no].indexOf(cross_set))>=0){
                
                String y_value=parameter_name.get(series_no);
                String y_value_after=y_value+" : "+String.valueOf(series[series_no].getY(pos_data));
                int pos2=y_value_after.lastIndexOf(" : ")+1;
                y_value_after=y_value+" "+y_value_after.substring(pos2,y_value_after.length())+"        ";  
                series[series_no].setKey(y_value_after);
                //plot.getLegendItems().get(series_no).setSeriesKey(series[k].getY(y_value_after));
                renderer.setSeriesVisibleInLegend(series_no, true);
                setCrosshairLocation(cross_set);
                String xlabel="Irig Time ------>"+timestamp.get(index);
                plot.getDomainAxis().setLabel(xlabel);
            }     
        }
    }
    

    protected void setCrosshairLocation(Double IRIG_time) {
       
        Crosshair domainCrosshair;
        List domainCrosshairs = crosshairOverlay.getDomainCrosshairs();
        if (domainCrosshairs.isEmpty()&& IRIG_time!=null) {
            
            domainCrosshair = new Crosshair();
            domainCrosshair.setPaint(java.awt.Color.BLACK);
            crosshairOverlay.addDomainCrosshair(domainCrosshair);
            domainCrosshair.setValue(IRIG_time);
        }
        else {
            domainCrosshair = (Crosshair) domainCrosshairs.get(0);
            domainCrosshair.setValue(IRIG_time);
        }                  
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
        if(notches<0 && value_button==1 ){
            set_vertical=(int) (set_vertical*1.2);
            set_horizontal=(int) (set_horizontal*1.2);
            jScrollPane2.getVerticalScrollBar().setValue(set_vertical);
            jScrollPane2.getHorizontalScrollBar().setValue(set_horizontal);
        }
        else if(notches>0 && value_button==1 )
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
        Play_Pause_jButton = new javax.swing.JButton();
        zoom_jButton2 = new javax.swing.JButton();
        Previous_jButton3 = new javax.swing.JButton();
        Next_jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel_Img = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_zoomfactor = new javax.swing.JLabel();
        jPanel_graph = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSlider_Img = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel_Irig_time.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_Irig_time.setText("----------");

        Play_Pause_jButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Play_Pause_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Play_Pause_jButtonActionPerformed(evt);
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

        jLabel_Img.setBackground(new java.awt.Color(153, 0, 204));
        jLabel_Img.setText("Image Display");
        jLabel_Img.setOpaque(true);
        jScrollPane2.setViewportView(jLabel_Img);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Irig time :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Zoom Factor:");

        jLabel_zoomfactor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_zoomfactor.setText("----------");

        jPanel_graph.setPreferredSize(new java.awt.Dimension(507, 473));

        javax.swing.GroupLayout jPanel_graphLayout = new javax.swing.GroupLayout(jPanel_graph);
        jPanel_graph.setLayout(jPanel_graphLayout);
        jPanel_graphLayout.setHorizontalGroup(
            jPanel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );
        jPanel_graphLayout.setVerticalGroup(
            jPanel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton2.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jButton2.setText("+");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButton2.setPreferredSize(new java.awt.Dimension(30, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jButton3.setText("-");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("--------");

        jSlider_Img.setBackground(new java.awt.Color(204, 204, 204));
        jSlider_Img.setForeground(new java.awt.Color(0, 26, 40));
        jSlider_Img.setMaximum(0);
        jSlider_Img.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jSlider_Img.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jSlider_ImgMouseDragged(evt);
            }
        });
        jSlider_Img.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Irig_graph_Img.this.mouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel_Irig_time, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSlider_Img, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129)
                                .addComponent(zoom_jButton2)
                                .addGap(7, 7, 7)
                                .addComponent(Previous_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(Play_Pause_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(Next_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_zoomfactor)))
                        .addGap(474, 474, 474))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_graph, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1))
                    .addComponent(jLabel_Irig_time, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_graph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(jSlider_Img, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(zoom_jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Previous_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Play_Pause_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Next_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_zoomfactor)
                    .addComponent(jLabel3))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Play_Pause_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Play_Pause_jButtonActionPerformed
        // TODO add your handling code here:
        System.out.flush();
        jLabel_Img.setText(null);// donot change
        ++value_button;
        jSlider_Img.setVisible(true);
        value_button=value_button%2;
        if(value_button==0){
            
            try{
                 
                if(zoom_trigger==1){
                    pre_horizontal=jScrollPane2.getHorizontalScrollBar().getValue();
                    pre_vertical=jScrollPane2.getVerticalScrollBar().getValue();
                }
                ImageIcon pause= new ImageIcon(".\\Button_Images\\PAUSE.png");
                Play_Pause_jButton.setIcon(pause);
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }else{            
            
            try{
                ImageIcon play= new ImageIcon(".\\Button_Images\\PLAY.png");
                Play_Pause_jButton.setIcon(play);
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_Play_Pause_jButtonActionPerformed

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
                String xlabel=plot.getDomainAxis().getLabel();
                int pos=xlabel.lastIndexOf(">")+1;
                Double xlabel_value=null;
                Double IRIG_time=null;
                if(pos<xlabel.length()){         
                    xlabel_value = timestamp_value(xlabel.substring(pos,xlabel.length()));
                    IRIG_time =timestamp_value(IRIG_obj.IRIG_data_line[x]);
                }
                if(xlabel_value>IRIG_time){
                            
                        for(int g=0;g<dataset1.getSeriesCount();g++){                               
                            series[g].setKey(parameter_name.get(g));
                        }   
                        plot.getDomainAxis().setLabel("Irig Time ------>");
                        crosshairOverlay.clearDomainCrosshairs();
                }
            } catch (IOException ex) {
                Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
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

                try {            
                    Display_Image(x);
                } catch (IOException ex) {
                    Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                
                zoom_jButton2.setText("Deactivate Zoom Window");
                jScrollPane2.setViewportView(jLabel_Img);
                jScrollPane2.getViewport().setViewPosition(new Point(pre_horizontal,pre_vertical));
                          
                try {
                    image = ImageIO.read(new File(Image_list[x]));
                } catch (IOException ex) {
                    Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
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
                        plus_minus = 0;
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
                    List domainCrosshairs = crosshairOverlay.getDomainCrosshairs();
                    Crosshair domainCrosshair;
                    if (domainCrosshairs.isEmpty()) {
            
                        domainCrosshair = new Crosshair();
                        domainCrosshair.setPaint(java.awt.Color.BLACK);
                        crosshairOverlay.addDomainCrosshair(domainCrosshair);
                    }
                    Display_Image(x);
                    
                } catch (IOException ex) {
                    Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
                }            
            }else
            {
                x  = Image_list.length - 1;
            }            
        }        
    }//GEN-LAST:event_Next_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(zoom_trigger==1){
            notches=-1;
            temp = zoom_factor - (notches * 0.2);
            temp = Math.max(temp, 1.0);
            if (temp != zoom_factor) {
                zoom_factor = temp;
                plus_minus = 1;
                resizeImage();
            }    
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(zoom_trigger==1){
            notches=1;
            temp = zoom_factor - ((notches) * 0.2);
            temp = Math.max(temp, 1.0);
            if (temp != zoom_factor) {
                zoom_factor = temp;
                plus_minus = 1;
                resizeImage();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
         obj_exists=false;
         evt.getWindow().dispose();
    }//GEN-LAST:event_formWindowClosed

    private void mouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClicked
        // TODO add your handling code here:
        if(value_button==1){
            JSlider sourceSlider =(JSlider)evt.getSource();
            BasicSliderUI ui = (BasicSliderUI) jSlider_Img.getUI();
            Point p= evt.getPoint();
            int value  = ui.valueForXPosition(p.x);
            jSlider_Img.setValue(value);
            x=jSlider_Img.getValue();
            try {
                k=0;
                Display_Image(x);
                String xlabel=plot.getDomainAxis().getLabel();
                int pos=xlabel.lastIndexOf(">")+1;
                Double xlabel_value=null;
                Double IRIG_time=null;
                if(pos<xlabel.length()){
                    xlabel_value = timestamp_value(xlabel.substring(pos,xlabel.length()));
                    IRIG_time =timestamp_value(IRIG_obj.IRIG_data_line[x]);
                }
                if(xlabel_value>IRIG_time){

                    for(int g=0;g<dataset1.getSeriesCount();g++){
                        series[g].setKey(parameter_name.get(g));
                    }
                    plot.getDomainAxis().setLabel("Irig Time ------>");
                    crosshairOverlay.clearDomainCrosshairs();
                    //System.out.println(crosshairOverlay.getDomainCrosshairs().isEmpty());
                }
            } catch (IOException ex) {
                Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_mouseClicked

    private void jSlider_ImgMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_ImgMouseDragged
        // TODO add your handling code here:
        if(value_button==1){
            x=jSlider_Img.getValue();
            try {
                k=0;
                Display_Image(x);
                String xlabel=plot.getDomainAxis().getLabel();
                int pos=xlabel.lastIndexOf(">")+1;
                Double xlabel_value=null;
                Double IRIG_time=null;
                if(pos<xlabel.length()){
                    xlabel_value = timestamp_value(xlabel.substring(pos,xlabel.length()));
                    IRIG_time =timestamp_value(IRIG_obj.IRIG_data_line[x]);
                }
                if(xlabel_value>IRIG_time){

                    for(int g=0;g<dataset1.getSeriesCount();g++){
                        series[g].setKey(parameter_name.get(g));
                    }
                    plot.getDomainAxis().setLabel("Irig Time ------>");
                    crosshairOverlay.clearDomainCrosshairs();
                }
            } catch (IOException ex) {
                Logger.getLogger(Irig_graph_Img.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jSlider_ImgMouseDragged

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
            java.util.logging.Logger.getLogger(Irig_graph_Img.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Irig_graph_Img.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Irig_graph_Img.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Irig_graph_Img.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private javax.swing.JButton Play_Pause_jButton;
    private javax.swing.JButton Previous_jButton3;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_Img;
    private javax.swing.JLabel jLabel_Irig_time;
    private javax.swing.JLabel jLabel_zoomfactor;
    private javax.swing.JPanel jPanel_graph;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider_Img;
    private javax.swing.JButton zoom_jButton2;
    // End of variables declaration//GEN-END:variables
}

