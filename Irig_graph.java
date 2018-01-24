/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image_display;

import static Image_display.Gui_Interface.cols;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
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
public class Irig_graph extends javax.swing.JFrame {

    private IRIG_data IRIG_obj;
    private int zoom_trigger=0;
    private int value_button=1;
    Timer tm;
    private int x=-1;//holds the image index to display
    private int k=0;// keep the index for track the highlighting
    private String[] Image_list; //Path of images in folder
    List<Double> txt_Irig;
    private int pre_horizontal=-1;
    private int pre_vertical=-1;
    private XYLineAndShapeRenderer renderer;
    private CrosshairOverlay crosshairOverlay;
    private XYPlot plot;
    private int col_length= cols.length;
    private XYSeries[] series = new XYSeries[col_length];
    private  XYSeriesCollection dataset1;
    private List<String> titles;
    private String para_file= null;
    private Double cross_set = null;
    private int label_img_width;
    private int label_img_height;
    private int notches=0;
    private double zoom_factor = 1.0; 
    private double temp;
    private int set_horizontal; 
    private int set_vertical;
    private  static BufferedImage image = null;
    private JFrame jframe;
    private Double pre_Irig;
    boolean series_data2 = false;
    private String pre_data[];
    Crosshair xCrosshair;
      DateAxis axis=null;
    Irig_graph(String para_file,String Image_folder, int param_max_count) {
     
        super("Image Slide show");
        initComponents();
        x=-1;
        k=0;
        obj_exists=true;
        //System.out.println("Irig graph's zoom factor : "+zoom_factor);  
        label_img_width=jLabel_Img.getWidth();
        label_img_height=jLabel_Img.getHeight();
        this.para_file=para_file;
        txt_Irig = new ArrayList<>();
        try {
            // to create and initialize the Array named IRIG_data_line which holds the IRIG time from .irg file
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
            jButton1.setIcon(Play);
        
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
        try {
            createContent();
            
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
       // ImageIcon icon= new ImageIcon(Image_list[0]);
       // pre_horizontal=icon.getIconWidth()/2;
        //pre_vertical=icon.getIconHeight()/2;
        //icon.getIconHeight();
        this.setVisible(true);
        Irig_slide2();
        //Parameter_display( para_file, param_max_count);
    }
     
    private void createContent() throws FileNotFoundException, IOException {//1
        ChartPanel chartPanel;
        //JFreeChart chart = createChart(createDataset());
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
       // System.out.println(axis.getTimeZone().getDisplayName());
        //add(jPanel_graph);
    }
    
     private XYDataset createDataset(){
        int i=0;
        FileReader fr_para = null;
        try {
            fr_para = new FileReader(para_file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Displays the title of parameter data in Text Area
        BufferedReader br_para = new BufferedReader(fr_para);
        String line, header;
        String [] split_data;
        int col_number=0;  
        try {
            br_para.readLine();
            br_para.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if((header=br_para.readLine())!=null){
                String[] split_head = header.split("\t");
                while(col_number<col_length && cols[col_number]!=-1){        
                    series[col_number] = new XYSeries(split_head[cols[col_number]+1]);
                    col_number++;                   
                }
            }
        } catch (IOException ex) {            
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Displays the parameter values in Text Area
        try {
            br_para.readLine();
            while((line =br_para.readLine())!=null){
                col_number=0;// to keep track of next line columns
                split_data = line.split("\t");
                int pos=line.lastIndexOf(":")+1;  
                double  time= Double.parseDouble(split_data[0].substring(pos,split_data[0].length())); 
                txt_Irig.add(i,time);
                while(col_number<col_length&&cols[col_number]!=-1){ // To extract the all parameter in line
                    series[col_number].add(time,Double.parseDouble(split_data[cols[col_number]+1]));                                       

                    col_number++;
                }
                i++;
            }   
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            br_para.close();
            fr_para.close();
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }      
        dataset1 = new XYSeriesCollection();
        for(int j=0;j<col_length;j++){      
            dataset1.addSeries(series[j]);          
        }
        titles = new ArrayList<String>();
        for(int j=0;j<dataset1.getSeriesCount();j++){
                titles.add(String.valueOf(series[j].getKey()));
        }
        return  dataset1; 
    }
     
      private XYDataset createDataset2(){
        FileReader fr_para = null;
        try {
            fr_para = new FileReader(para_file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Displays the title of parameter data in Text Area
        BufferedReader br_para = new BufferedReader(fr_para);
        String line, header;
        String [] split_data;
        int col_number=0;  
        try {
            if((header=br_para.readLine())!=null){
                    
            int pos=header.lastIndexOf(";")+1;
            int pos2=header.indexOf("\t");
            String first_para = header.substring(pos,pos2);
            String[]  split_head= header.split("\t");
                 split_head[0]=first_para;
                while(col_number<col_length && cols[col_number]!=-1){        
                    series[col_number] = new XYSeries(split_head[cols[col_number]+1]);
                    col_number++;                   
                }
            }
        } catch (IOException ex) {            
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Displays the parameter values in Text Area
        try {
            br_para.readLine();
            br_para.readLine();
            while((line =br_para.readLine())!=null){
                col_number=0;// to keep track of next line columns
                
                split_data = line.split("\t",-1);// trailing empty strings will not be discarded
               
                String time[] = split_data[0].split(":");
               double hh = TimeUnit.HOURS.toMicros(Long.parseLong(time[1]));
                double  mm  =    +TimeUnit.MINUTES.toMicros(Long.parseLong(time[2]));
                double  sec  =   TimeUnit.SECONDS.toMicros(Long.parseLong(time[3]));
                double   milli =   TimeUnit.MILLISECONDS.toMicros(Long.parseLong(time[4]));
                double micro=Long.parseLong(time[5]);
               double xx= TimeUnit.MICROSECONDS.toMicros(Long.parseLong(time[5]));
                System.out.println(hh+":"+mm+":"+sec+":"+milli+":"+micro+"\nms:"+xx);
                 
                double   IRIG_time_calc =   hh+mm+sec+milli+micro;
                System.out.println("IRIG_time_calc: "+IRIG_time_calc);
                Timestamp ts= new Timestamp((long) IRIG_time_calc);
                System.out.println("Timestamp: "+ts);
                // txt_Irig.add(i,IRIG_time_calc);
                
                int pos=line.lastIndexOf(":")+1;  
                //double  time= Double.parseDouble(split_data[0].substring(pos,split_data[0].length())); 
                while(col_number<col_length&&cols[col_number]!=-1){ // To extract the all parameter in line
                   if(!split_data[cols[col_number]+1].equals("")){
                        if(!txt_Irig.contains(IRIG_time_calc))
                            txt_Irig.add(IRIG_time_calc);
                        series[col_number].add(IRIG_time_calc,Double.parseDouble(split_data[cols[col_number]+1]));
                   }
                    //txt_Irig.add(i,time);
                    col_number++;
                }
            }   
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            br_para.close();
            fr_para.close();
        } catch (IOException ex) {
            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
        }      
        dataset1 = new XYSeriesCollection();
        for(int j=0;j<col_length;j++){      
            dataset1.addSeries(series[j]);     
        }
        titles = new ArrayList<String>();
        for(int j=0;j<dataset1.getSeriesCount();j++){    
            titles.add(String.valueOf(series[j].getKey()));
            series[j].setKey(series[j].getKey()+"\t");
        }
        System.out.println("data@ size "+txt_Irig.size());
        
        //for(int j=0;j<txt_Irig.size();j++)
            // System.out.println("data@ "+j+" is "+txt_Irig.get(j));
        return  dataset1; 
    }
      
    private JFreeChart createChart(XYDataset dataset) {//3
        JFreeChart chart = ChartFactory.createXYLineChart("Irig time vs Parameters", 
        "Irig Time------->", "Parameter------>", dataset,PlotOrientation.VERTICAL, true, true, false);
        plot= chart.getXYPlot();
        renderer=new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);
      /*Calendar cal= Calendar.getInstance();
       
        DateFormat sdf= new SimpleDateFormat("HH:mm:ss.SSSSSS");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        axis= new DateAxis();
       // cal.setTime(axis);
       //String s3 = sdf.format(axis);
       System.out.println("default timezone: "+ cal.getTime()+" "+cal.getTimeZone()+" "+cal);
       // axis.setTimeZone(TimeZone.getTimeZone("UTC"));
    
        //axis.setDateFormatOverride(sdf);
        plot.setDomainAxis(axis);
        
        */
        
        
       /*DateAxis axis2 =new DateAxis();
       axis2.setTimeZone(TimeZone.getTimeZone("UTC"));
        //axis2.setDateFormatOverride(DateFormat.getDateInstance(DateFormat.FULL));
         
         
        SimpleDateFormat sdf2= new SimpleDateFormat("HH:mm:ss:SSSSSS");
        sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf2.format(new Date().getTime());
        axis2.setDateFormatOverride(sdf2);
        axis2.getMaximumDate();
        plot.setDomainAxis(axis2);*/
       
        //Date date = (Date)sdf2.format(axis2);
         plot.getDomainAxis().setTickLabelsVisible(false);
         renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        
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
                    x++;
                    if(x>=Image_list.length){
                        x=0;
                        k=0;
                        //System.exit(0);
                    }
                    Display_Image(x);
                    //chartPanel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
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
                //System.out.println("Zoom factor: "+zoom_factor);
                jLabel_Img.setIcon(icon);
                jLabel_zoomfactor.setText(String.valueOf(String.format("%.1f",zoom_factor))); 
            }
            else{
              resizeImage();
            }   
      
        }else{
         
            ImageIcon icon= new ImageIcon(Image_list[j]);
            Image pic=icon.getImage();
            Image newImg =pic.getScaledInstance(label_img_width,label_img_height,Image.SCALE_SMOOTH);
            ImageIcon newImc = new ImageIcon(newImg);
            jLabel_Img.setIcon(newImc);
            jLabel_zoomfactor.setText("-------");
        }
        //if(j<IRIG_obj.IRIG_data_line.size())
        String line=IRIG_obj.IRIG_data_line[j];
        int pos=line.lastIndexOf(":")+1;
        Double IRIG_time=Double.parseDouble(line.substring(pos,line.length()));
        
        
        jLabel_Irig_time.setText(line);  
        series_data3(IRIG_time);
        jLabel_Img.setVisible(true);
    } 

    private void series_data(Double IRIG_time){
        System.out.println("k & size value is: "+k+"  "+txt_Irig.get(k));
        while(k>=0 && k < txt_Irig.size() &&(txt_Irig.get(k)<=IRIG_time)){ 
            k++;
        }
       
        if(k-1>=0 && k < txt_Irig.size()){
        cross_set=txt_Irig.get(k-1);
        setCrosshairLocation(cross_set);
        }
        System.out.println("cross hair: "+cross_set);
        if(k-1>=0 && k < txt_Irig.size()){
            for(int series_no=0;series_no<col_length;series_no++){      
                String y_value=titles.get(series_no);
                String y_value_after=y_value+" : "+String.valueOf(series[series_no].getY(k-1));
                System.out.println("series[series_no].getY(k-1)"+series[series_no].getX(k-1));
                System.out.println("series[series_no].getY(k-1)"+series[series_no].getY(k-1));
                int pos2=y_value_after.lastIndexOf(" : ")+1;
                y_value_after=y_value+" "+y_value_after.substring(pos2,y_value_after.length())+"        ";  
                // plot.getLegendItems().get(k).setSeriesKey(series[k].getY(s2));
                series[series_no].setKey(y_value_after);
                renderer.setSeriesVisibleInLegend(series_no, true);
            }
        }
    }
    private void series_data3(Double IRIG_time){   
        
        
        series_data2=true;
        for(int g=0;g<dataset1.getSeriesCount();g++){
            series[g].setKey(titles.get(g)+"\t");
        }
        
        while(k>=0 && k < txt_Irig.size() &&(txt_Irig.get(k)<=IRIG_time)){ 
            k++;
        }
        
        int index=k-1;
        if(index>=0 && index < txt_Irig.size()){
        cross_set=txt_Irig.get(index);
        }
        
        int pos_data;
        for(int series_no=0;series_no<col_length;series_no++){      
            
            if((cross_set!=null)&&(pos_data=series[series_no].indexOf(cross_set))>=0){
                
                String y_value=titles.get(series_no);
                String y_value_after=y_value+" : "+String.valueOf(series[series_no].getY(pos_data));
                int pos2=y_value_after.lastIndexOf(" : ")+1;
                y_value_after=y_value+" "+y_value_after.substring(pos2,y_value_after.length())+"        ";  
                series[series_no].setKey(y_value_after);
                //plot.getLegendItems().get(series_no).setSeriesKey(series[k].getY(y_value_after));
                renderer.setSeriesVisibleInLegend(series_no, true);
                setCrosshairLocation(cross_set);
        
            }
               
        }

    }
    
    private void series_data2(Double IRIG_time){
      //  series_data2 =true;
    for(int g=0;g<dataset1.getSeriesCount();g++){
         //if(!plot.getDomainAxis().getLabel().equals("Irig Time ------>"))
            series[g].setKey(titles.get(g));
    }
    //plot.getDomainAxis().setLabel("Irig Time ------>");
    while(k < txt_Irig.size() &&(txt_Irig.get(k)<=IRIG_time)){ 
            k++;
        }   
        if(k-1!=-1 && k < txt_Irig.size()){
        cross_set=txt_Irig.get(k-1);
        System.out.println("cross_set: "+cross_set);
        //setCrosshairLocation(cross_set);
        }
         
       // System.out.println("cross_set: "+cross_set+"k: "+k);
       if(k-1!=-1 && k < txt_Irig.size()){
            //for(int series_no=0;series_no<col_length;series_no++){      
                int pos_data=0, series_no=0 ,item_count; 
                item_count = series[series_no].getItemCount();
                while(pos_data < item_count && series_no<dataset1.getSeriesCount()){
                    if(!series[series_no].getX(pos_data).equals(cross_set)){
                        pos_data++;
                            pre_Irig=xCrosshair.getValue();
                            //System.out.println("xCrosshair: "+pre_Irig);
                      
                    }else if(series[series_no].getX(pos_data).equals(cross_set))
                    {
                        //pre_Irig = plot.getDomainAxis().getLabel();
                            
                        //System.out.println("series_no data "+series[series_no].getX(pos_data)+" cross_set :"+cross_set);
                            String y_value=titles.get(series_no);
                            String y_value_after=y_value+" : "+String.valueOf(series[series_no].getY(pos_data));
                            int pos2=y_value_after.lastIndexOf(" : ")+1;
                            y_value_after=y_value+" "+y_value_after.substring(pos2,y_value_after.length())+"        ";  
                            // plot.getLegendItems().get(k).setSeriesKey(series[k].getY(s2));
                            series[series_no].setKey(y_value_after);
                            
                            pre_data[series_no]=y_value_after;
                            
                            renderer.setSeriesVisibleInLegend(series_no, true);
                            setCrosshairLocation(cross_set);
                            break;
                    }
                    if(pos_data==item_count ){

                        //System.out.println("pos_data==item_count");
                        pos_data=0;
                        if(series_no<dataset1.getSeriesCount()){
                            series_no++;
                            if(series_no!=dataset1.getSeriesCount())
                                item_count = series[series_no].getItemCount();
                            //System.out.println("series_no :"+series_no);
                        }
                        
                    }    
               
                }
               /* if(pos_data<series[series_no].getItemCount()){
                    String y_value=titles.get(series_no);
                    String y_value_after=y_value+" : "+String.valueOf(series[series_no].getY(pos_data));
                    int pos2=y_value_after.lastIndexOf(" : ")+1;
                    y_value_after=y_value+" "+y_value_after.substring(pos2,y_value_after.length())+"        ";  
                    // plot.getLegendItems().get(k).setSeriesKey(series[k].getY(s2));
                    series[series_no].setKey(y_value_after);
                    renderer.setSeriesVisibleInLegend(series_no, true);
                    setCrosshairLocation(cross_set);
                }*/
            //}
            
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
            // We only have one at a time
            domainCrosshair = (Crosshair) domainCrosshairs.get(0);
            domainCrosshair.setValue(IRIG_time);
            //xCrosshair.setValue(IRIG_time);
        }
        String xlabel="Irig Time ------>"+String.valueOf(IRIG_time);
        plot.getDomainAxis().setLabel(xlabel);
        System.out.println("crosshair set at position: "+cross_set);
        //series[0].setKey(series[0].getKey()+"hedihcfi");
        //renderer.setSeriesVisibleInLegend(0, true);                   
    }
    public  void resizeImage() {

        pre_horizontal = jScrollPane2.getHorizontalScrollBar().getValue();
        pre_vertical = jScrollPane2.getVerticalScrollBar().getValue();
        
        ResampleOp  resampleOp = new ResampleOp((int)(image.getWidth()*zoom_factor), (int)(image.getHeight()*zoom_factor));
        BufferedImage resizedIcon = resampleOp.filter(image, null);
        Icon imageIcon = new ImageIcon(resizedIcon);
        jLabel_Img.setIcon(imageIcon);
           
        System.out.println("zoom_factor: "+zoom_factor);
           System.out.println("resizeImage :"+pre_horizontal+" "+pre_vertical);
            int   set_horizontal=pre_horizontal;
            int   set_vertical= pre_vertical;
        
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
         pre_horizontal = jScrollPane2.getHorizontalScrollBar().getValue();
           pre_vertical = jScrollPane2.getVerticalScrollBar().getValue();
           System.out.println("resizeImage :"+pre_horizontal+" "+pre_vertical);
        jLabel_zoomfactor.setText("resizeImage "+ String.valueOf(String.format("%.1f",zoom_factor)));
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
        jButton1 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel_Irig_time.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_Irig_time.setText("----------");

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
        jLabel1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Zoom Factor:");

        jLabel_zoomfactor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_zoomfactor.setText("----------");

        javax.swing.GroupLayout jPanel_graphLayout = new javax.swing.GroupLayout(jPanel_graph);
        jPanel_graph.setLayout(jPanel_graphLayout);
        jPanel_graphLayout.setHorizontalGroup(
            jPanel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );
        jPanel_graphLayout.setVerticalGroup(
            jPanel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(zoom_jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Previous_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Next_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel_graph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_zoomfactor))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_Irig_time, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Irig_time, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_graph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Next_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Previous_jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(zoom_jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel_zoomfactor))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.flush();
        System.out.println(txt_Irig.indexOf(15.050851));
        jLabel_Img.setText(null);// it is required
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
                  //obj1.zoom=1.0;
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
                if(series_data2){
                    
                    String xlabel=plot.getDomainAxis().getLabel();
                    int pos=xlabel.lastIndexOf(">")+1;
                    Double xlabel_value=null;
                    if(pos<xlabel.length()){
                        xlabel_value=Double.parseDouble(xlabel.substring(pos,xlabel.length()));
                        String line=IRIG_obj.IRIG_data_line[x];
                        int pos2=line.lastIndexOf(":")+1;
                        Double IRIG_time=Double.parseDouble(line.substring(pos2,line.length()));
                        if(xlabel_value>IRIG_time){
                            
                             for(int g=0;g<dataset1.getSeriesCount();g++){
                                //if(!plot.getDomainAxis().getLabel().equals("Irig Time ------>"))
                                series[g].setKey(titles.get(g));
                            }   
                            plot.getDomainAxis().setLabel("Irig Time ------>");
                            crosshairOverlay.clearDomainCrosshairs();
                            System.out.println(crosshairOverlay.getDomainCrosshairs().isEmpty());
                            }
                        }
                    }
            } catch (IOException ex) {
                Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             else{
                        //zoom_factor=1.0;
                        zoom_jButton2.setText("Deactivate Zoom Window");
                        System.out.println("Activated state");
                        //zoom_trigger=1;
                        jScrollPane2.setViewportView(jLabel_Img);
                        jScrollPane2.getViewport().setViewPosition(new Point(pre_horizontal,pre_vertical));
 
                        //jScrollPane2.getHorizontalScrollBar().setValue(pre_horizontal2);
                        //jScrollPane2.getVerticalScrollBar().setValue(pre_vertical2);
                          
                        try {
                            image = ImageIO.read(new File(Image_list[x]));
                        } catch (IOException ex) {
                            Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
                        }

                 if(zoom_factor==1.0){            
                        
                        ImageIcon icon= new ImageIcon(Image_list[x]);
                        System.out.println("image set in zoom button");
                        jLabel_Img.setIcon(icon);
                         jLabel_zoomfactor.setText(String.valueOf(String.format("%.1f",zoom_factor))); 
                         
                         
                          if(pre_horizontal==-1 && pre_vertical==-1 ){
                            Rectangle bounds =  jScrollPane2.getViewport().getViewRect();
                            Dimension size =  jScrollPane2.getViewport().getViewSize();
                             pre_horizontal=(size.width-bounds.width)/2;
                             pre_vertical=(size.height-bounds.height)/2;
                            jScrollPane2.getViewport().setViewPosition(new Point((size.width-bounds.width)/2,(size.height-bounds.height)/2));
                        }
                        //else{
                            
                           
                           // jScrollPane2.getViewport().setViewPosition(new Point(pre_horizontal2,pre_vertical2));
                        //}  
                       System.out.println("image set in zoom button: "+pre_horizontal+"=="+pre_vertical);     
                      }
                      else{
                        
                            resizeImage();
                        }
                       //pre_horizontal2 = jScrollPane2.getHorizontalScrollBar().getValue();
                        //pre_vertical2 = jScrollPane2.getVerticalScrollBar().getValue();         
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
                    Logger.getLogger(Irig_graph.class.getName()).log(Level.SEVERE, null, ex);
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
                resizeImage();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
         obj_exists=false;
         evt.getWindow().dispose();
    }//GEN-LAST:event_formWindowClosed

    private void jLabel1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1AncestorAdded

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
            java.util.logging.Logger.getLogger(Irig_graph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Irig_graph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Irig_graph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Irig_graph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JButton Previous_jButton3;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JButton zoom_jButton2;
    // End of variables declaration//GEN-END:variables
}

