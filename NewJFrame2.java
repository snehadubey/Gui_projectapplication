/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plot_data;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.axis.AxisLabelLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author Admin
 */
public class NewJFrame2 extends javax.swing.JFrame implements ChartMouseListener {
  Timer tm;
    private ChartPanel chartPanel;
    private Crosshair xCrosshair;
    private Crosshair y1Crosshair;
    private Crosshair y2Crosshair;
    int col_number=4;
     XYSeries[] series = new XYSeries[col_number];
    // XYSeriesCollection dataset1;
     DefaultXYDataset dataset1;
    double X_highlight=4.2345;
    double Y_highlight=7.895;
    XYPlot plot;
    Double[] irig_time= new Double[50];
    static   int s1=0;
     List<String> titles;
    CrosshairOverlay crosshairOverlay;
    /**
     * Creates new form NewJFrame2
     */
      XYPointerAnnotation pointer;
       private List<String> line_data;
    public NewJFrame2() throws FileNotFoundException, IOException {
        //super(title);
       
        initComponents();
        
        createContent();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
         
        //createContent();
        //graph();
    }
    
     private void createContent() throws FileNotFoundException, IOException {//1
        JFreeChart chart = createChart(createDataset());
        this.chartPanel = new ChartPanel(chart);
        //this.chartPanel.addChartMouseListener(this);
        crosshairOverlay = new CrosshairOverlay();
 
        this.xCrosshair = new Crosshair(Double.NaN, Color.WHITE, new BasicStroke(0.0f));
        this.xCrosshair.setLabelBackgroundPaint(new Color(1f, 1f, 1f, 0.9f));
        this.xCrosshair.setLabelPaint(Color.BLACK);
        this.xCrosshair.setLabelOutlineVisible(true);
        this.xCrosshair.setLabelFont(xCrosshair.getLabelFont().deriveFont(11f));
      //this.xCrosshair.setValue(200);
        this.xCrosshair.setLabelXOffset(30.43);
        this.xCrosshair.setLabelVisible(true);
  
        //this.y1Crosshair = new Crosshair(Double.NaN, Color.black, new BasicStroke(0f));
        //this.y1Crosshair.setLabelXOffset(10);
        //this.y1Crosshair.setLabelBackgroundPaint(new Color(1f, 1f, 1f, 0.9f));
        //this.y1Crosshair.setLabelOutlineVisible(true);
        //this.y1Crosshair.setLabelPaint(Color.GRAY);
        //this.y1Crosshair.setLabelFont(y1Crosshair.getLabelFont().deriveFont(11f));
        //this.y1Crosshair.setValue(18);
        
        //this.y1Crosshair.setLabelGenerator(new CrosshairLabelGenerator() {
        //@Override
        //public String generateLabel(final Crosshair ch) {
            //return String.valueOf(ch.getValue());
       // }
        //});
        
        //this.y2Crosshair = new Crosshair(Double.NaN, Color.black, new BasicStroke(0f));
         /* y2Crosshair.setLabelGenerator(new CrosshairLabelGenerator() {
        @Override
        public String generateLabel(final Crosshair chy) {
            return String.valueOf(chy.getValue());
        }
        });*/
      
        //this.y1Crosshair.setVisible(true);
        //this.y1Crosshair.setLabelVisible(true);
        //this.y2Crosshair.setLabelVisible(true);
        
        chartPanel.setSize(jPanel_graph.getWidth(),jPanel_graph.getHeight());
        chartPanel.setVisible(true);
        chartPanel.setMouseWheelEnabled(true);
        jPanel_graph.add(chartPanel,BorderLayout.CENTER);
        jPanel_graph.repaint(); 
        
     
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        //crosshairOverlay.addRangeCrosshair(y1Crosshair);
        //crosshairOverlay.addRangeCrosshair(y2Crosshair);
        chartPanel.addOverlay(crosshairOverlay);
         jPanel_graph.add(chartPanel,BorderLayout.CENTER);
       // jPanel_graph.repaint();
        add(jPanel_graph);
       

 
      tm= new Timer(1000,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(s1<irig_time.length){
                    //System.out.println("s1"+s1+"\t"+irig_time[s1]);
                      plot.clearAnnotations();
                    setCrosshairLocation(irig_time[s1],0);
              
                     for(int k=0;k<4;k++){
          
           for(int s2=0;s2<series[k].getItemCount();s2++){
         
               if(s1==s2){
                   
               //System.out.println("series "+k+" :"+series[k].getY(s2));//getDataItem(i));
              // System.out.println("series222222 "+i+" :"+series[i].getDataItem(s2));
        
      XYPointerAnnotation pointer = new XYPointerAnnotation(String.valueOf(series[k].getY(s2)),irig_time[s1], (double) series[k].getY(s2), -3.0 * Math.PI / 4.0);
                    
        pointer.setBaseRadius(50.0);
        pointer.setTipRadius(5.0);
        pointer.setFont(new Font("SansSerif", Font.PLAIN, 9));
        pointer.setPaint(Color.blue);
        if(k==0)
            pointer.setTextAnchor(TextAnchor.CENTER);
        if(k==1)
             pointer.setTextAnchor(TextAnchor.BASELINE_LEFT);
        if(k==2)
             pointer.setTextAnchor(TextAnchor.HALF_ASCENT_CENTER);
        if(k==3)
             pointer.setTextAnchor(TextAnchor.TOP_RIGHT);
        plot.addAnnotation(pointer);
        
        
        String y_value=titles.get(k);
        //System.out.println("    "+y_value);
        String y_value_after=y_value+" : "+String.valueOf(series[k].getY(s2));
        //System.out.println("    "+y_value_after);
        int pos=y_value_after.lastIndexOf(" : ")+1;
        y_value_after=y_value+y_value_after.substring(pos,y_value_after.length());  
        //System.out.println("    "+y_value_after);
       // System.out.println("    "+y_value+"  "+y_value_after);
       // plot.getLegendItems().get(k).setSeriesKey(series[k].getY(s2));
                 series[k].setKey(y_value_after);
                 
               }
     
                     }
           }
                        s1++;
               }
            };
        });

        tm.start();
    }

     private JFreeChart createChart(XYDataset dataset) {//3
        JFreeChart chart = ChartFactory.createXYLineChart("Crosshair Demo", 
                "X", "Y", dataset,PlotOrientation.VERTICAL, true, true, false);
         XYLineAndShapeRenderer renderer0=new XYLineAndShapeRenderer();
		plot= chart.getXYPlot();
                
                //final Marker start = new ValueMarker(4.5);
                //start.setPaint(Color.red);
                //start.setLabel("current value");
                //start.setLabelBackgroundColor(Color.yellow);
               //start.setLabelTextAnchor(TextAnchor.CENTER);
               //plot.addRangeMarker(start);
               
                 //Stroke scrosshair= new BasicStroke(1);
                 //Color ccrosshair =new Color(0xFFFF00);
                 //plot.setDomainCrosshairPaint(ccrosshair);
                // plot.setDomainCrosshairValue(4.789); 
                // plot.setDomainCrosshairLockedOnData(true);
                // plot.setDomainCrosshairVisible(true); 
                 
                  NumberAxis domainAxis =(NumberAxis) plot.getDomainAxis();
                 //domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                // domainAxis.setAutoRange(true);
                 //domainAxis.setLabel("kavya");
                 //domainAxis.setLabelLocation(AxisLabelLocation.MIDDLE);
                 //domainAxis.setLabelAngle(50);//.setLowerMargin(20.0);
                 
                 XYLineAndShapeRenderer renderer5=new XYLineAndShapeRenderer();
                plot.setRenderer(renderer5);
                 //renderer5.setLegendItemToolTipGenerator(new StandardXYSeriesLabelGenerator("Legend {1}"));
                renderer5.setBaseShapesVisible(true);
                renderer5.setBaseShapesFilled(true);
                //renderer5.setBaseItemLabelGenerator(new  StandardXYItemLabelGenerator()); 
               //renderer5.isItemLabelVisible(0, 2);
               //XYItemRenderer res= plot.getRenderer();
 
                renderer5.setBaseItemLabelsVisible(true);
                 //plot.setRenderer(0, renderer5);
		//plot.setDataset(1, dataset);
               // plot.setDataset(2, dataset);
               
               //XYItemLabelGenerator itemlabelgen= new StandardXYItemLabelGenerator();
               //plot.getRenderer().setBaseItemLabelGenerator(itemlabelgen);
               //plot.getRenderer().setBaseSeriesVisible(true);
               //plot.getRenderer().setSeriesVisible(1, true);
               
               //final AbstractXYItemRenderer xyRenderer = (AbstractXYItemRenderer) 
               chart.getXYPlot().getRenderer();

                //final ItemLabelPosition p1 = new ItemLabelPosition(ItemLabelAnchor.CENTER,TextAnchor.BOTTOM_LEFT, TextAnchor.TOP_CENTER, - Math.PI / 4); 
                //xyRenderer.setItemLabelAnchorOffset(3.5); 
                //xyRenderer.setSeriesItemLabelPaint(1, Color.BLUE);
               // xyRenderer.setSeriesPositiveItemLabelPosition(1, p1);



                //final ItemLabelPosition p2 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.BOTTOM_CENTER, TextAnchor.BOTTOM_CENTER, - Math.PI / 4);
                //xyRenderer.setItemLabelAnchorOffset(-8.5); 
                //xyRenderer.setSeriesItemLabelPaint(0, Color.RED); 
                //xyRenderer.setSeriesPositiveItemLabelPosition(0, p2);
                
        //plot.addAnnotation(new XYShapeAnnotation(new Ellipse2D.Double(200-0.1,10000-0.3,0.1+0.3,0.1+0.3)));
        pointer = new XYPointerAnnotation("Best Bid", 13.5,2000, -3.0 * Math.PI / 4.0);//- for downward direction else + for upperward direction
        pointer.setBaseRadius(35.0);
        pointer.setTipRadius(3.0);
        pointer.setFont(new Font("SansSerif", Font.PLAIN, 9));
        pointer.setPaint(Color.blue);
        pointer.setTextAnchor(TextAnchor.BASELINE_LEFT);
       plot.clearAnnotations();
        
       
       String [] line_array= new String[line_data.size()];
      for(int k2=0;k2<line_data.size();k2++){
          line_array[k2]=line_data.get(k2);
      }
          
       ValueAxis xAxis = new SymbolAxis("x", line_array);
      ValueAxis yAxis = new NumberAxis("Symbol");
      XYItemRenderer renderer = new XYLineAndShapeRenderer();
      plot = new XYPlot(dataset1, xAxis, yAxis, renderer);

      JFreeChart chart2 = new JFreeChart("Symbol Axis Demo", new Font("Tahoma", 0, 18), plot, true);
      JFrame frame = new JFrame("XY Plot Demo");
      frame.setContentPane(new ChartPanel(chart2));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      frame.pack();
      frame.setVisible(true);
      
      chart2.getXYPlot().getDomainAxis().setVerticalTickLabels(true);
        return chart;
    }
   
    private XYDataset createDataset() throws FileNotFoundException, IOException {
        //2
        
        FileReader fr_para = null;
          //Displays the title of parameter data in Text Area
          fr_para = new FileReader("F:\\Gui_Project\\dummy2.txt");
        BufferedReader br_para = new BufferedReader(fr_para);
        String header,line, x_label,y_label0,y_label1,y_label2;
        String [] split_data;
       // int col_number=4;  
            br_para.readLine();
            br_para.readLine();
        int j=0;
       XYPointerAnnotation[] pointer = new XYPointerAnnotation[col_number];
 //XYSeries[] series = new XYSeries[col_number];
            if((header=br_para.readLine())!=null){
                String[] split_head = header.split(",");
                x_label=split_head[0];
                y_label0=split_head[1];
                y_label1=split_head[2];
                y_label2=split_head[3];
                
                
                
                
                
               // System.out.println("x_label: "+x_label);
               // System.out.println("y_label0: "+y_label0);
                //System.out.println("y_label1: "+y_label1);
                //System.out.println("y_label2: "+y_label2);
                while( j<col_number){
                    
                    
                    
                     series[j] = new XYSeries(split_head[j+1]);
                     
       
                   // System.out.println("data  id:  "+split_head[j+1].trim()+"\t");
                    //jTextArea_slide.append(String.format("%-30s\t",split_head[cols[col_number]].trim()));
                    j++;
                }
                
            } 
          //System.out.println("series. length: "+series.length);
      
         br_para.readLine();
             int k=0; 
                line_data = new ArrayList<String>();
               
               
            while((line =br_para.readLine())!=null && k<irig_time.length){
                //jTextArea_slide.append("\n");
                //col_number=0;// to keep track of next line columns
                 
                split_data = line.split(",");
                line_data.add(split_data[0]);
              int pos=line.lastIndexOf(":")+1;  
                        double time= Double.parseDouble(split_data[0].substring(pos,split_data[0].length()));
             
              irig_time[k]=time;
              k++; 
         for(int i=0;i<col_number;i++){
                //System.out.println("Float====>"+Float.parseFloat(split_data[0].substring(pos,split_data[0].length())));
               // System.out.println("\n\nfloat====>"+time);
                                          
        //plot.addAnnotation(new XYShapeAnnotation(new Ellipse2D.Double(200-0.1,10000-0.3,0.1+0.3,0.1+0.3)));
       
                       // System.out.println("irig time"+time+"\t"+split_data[i+1]);
                        series[i].add(time,Double.parseDouble(split_data[i+1]));
                         //System.out.println("System.out.println: "+series[0].getX(0)+","+series[0].getY(0)); 
               //series[i].add(i,Math.random() * 4.0);
               //series[i].add(i,Math.random() * 8.0);
           
     }
      //System.out.println("System.out.println: "+series[0].getX(0));      
       }
              br_para.close();
       // dataset1 = new XYSeriesCollection();
       dataset1= new DefaultXYDataset();
      for(int i=0;i<col_number;i++){
         // dataset1.addSeries(series[i]);
          dataset1.addSeries(series[i].getKey(), series[i].toArray());
          //dataset1.getSeries().getYValue(SOMEBITS, i).getEndYValue(series[i], i);
      }
      
       titles = new ArrayList<String>();
                 for(int i=0;i<dataset1.getSeriesCount();i++){
                
                titles.add(String.valueOf(series[i].getKey()));
                
                }
        
     /*final XYSeries series1 = new XYSeries("rfjyfjy");
           XYSeriesCollection dataset1 = new XYSeriesCollection();
        float[] time1= new float[100];
         FileReader fr_para = null;// to display parameters in textarea
    BufferedReader br_para;
        fr_para = new FileReader("plot2.txt");
        br_para = new BufferedReader(fr_para);
        //Scanner s1= new Scanner(new File("plot2.txt"));
        //Scanner s2= new Scanner(new File("plot3.txt"));
        //List<String> names= new ArrayList<String>();
        int i=0;
        String line;
        while((line=br_para.readLine())!=null)
        {
            String[] split_head = line.split("\t");
              //String series[]=new String[split_head.length-1];
            //String dataset[] =new String[split_head.length-1];
            //series[i]="series"+i;
            //dataset[i]="dataset"+i;
            
            
            System.out.println(split_head[0]+"\t"+ split_head[1]);
            series1.add(Double.parseDouble(split_head[i]),Double.parseDouble(split_head[1]));
            //System.out.println(series1[i].getItems());
            //series1[i].add(Double.parseDouble(split_head[0]),Double.parseDouble( split_head[1]));
           // series.add(Double.parseDouble(split_head[0]), Double.parseDouble( split_head[2]));
            //series.add(time1[i], s1.nextFloat());
            i=i+1;
            
            
        }
        dataset1.addSeries(series1);*/
     
     System.out.println("data: "+line_data);
        return  dataset1; 
    }
    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        // ignore
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, 
                RectangleEdge.RIGHT);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 1, x);//0 for series 0
        this.xCrosshair.setValue(x);
        this.y2Crosshair.setLabelXOffset(10);
        this.y2Crosshair.setValue(y);
    }
    
    
    protected void setCrosshairLocation(Double x, int i) {
        plot.removeAnnotation(pointer);
        Crosshair domainCrosshair;
        List domainCrosshairs = crosshairOverlay.getDomainCrosshairs();
        if (domainCrosshairs.isEmpty()&& x!=null) {
            domainCrosshair = new Crosshair();
            domainCrosshair.setPaint(java.awt.Color.CYAN);
            crosshairOverlay.addDomainCrosshair(domainCrosshair);
        }
        else {
        // We only have one at a time
            domainCrosshair = (Crosshair) domainCrosshairs.get(0);
        }
        domainCrosshair.setValue(x);
        //XYPointerAnnotation[] pointer =new XYPointerAnnotation[col_number];  
}
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_graph = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel_graphLayout = new javax.swing.GroupLayout(jPanel_graph);
        jPanel_graph.setLayout(jPanel_graphLayout);
        jPanel_graphLayout.setHorizontalGroup(
            jPanel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );
        jPanel_graphLayout.setVerticalGroup(
            jPanel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 477, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_graph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_graph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(NewJFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new NewJFrame2().setVisible(true);
                    
                } catch (IOException ex) {
                    Logger.getLogger(NewJFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_graph;
    // End of variables declaration//GEN-END:variables
}
