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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author Admin
 */
public class NewJFrame extends javax.swing.JFrame implements ChartMouseListener {
  Timer tm;
    private ChartPanel chartPanel;
    private Crosshair xCrosshair;
    private Crosshair y1Crosshair;
    private Crosshair y2Crosshair;
    double X_highlight=4.2345;
    double Y_highlight=7.895;
    XYPlot plot;
     XYSeries[] series1;
    CrosshairOverlay crosshairOverlay;
    /**
     * Creates new form NewJFrame
     */
      XYPointerAnnotation pointer;
    public NewJFrame() throws FileNotFoundException, IOException {
        //super(title);
       
        initComponents();
        //this.add(
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
        this.xCrosshair.setValue(3);
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
        
        this.y2Crosshair = new Crosshair(Double.NaN, Color.black, new BasicStroke(0f));
         /* y2Crosshair.setLabelGenerator(new CrosshairLabelGenerator() {
        @Override
        public String generateLabel(final Crosshair chy) {
            return String.valueOf(chy.getValue());
        }
        });*/
      
        //this.y1Crosshair.setVisible(true);
        //this.y1Crosshair.setLabelVisible(true);
        this.y2Crosshair.setLabelVisible(true);
        
        chartPanel.setSize(jPanel_graph.getWidth(),jPanel_graph.getHeight());
        chartPanel.setVisible(true);
        chartPanel.setMouseWheelEnabled(true);
        jPanel_graph.add(chartPanel,BorderLayout.CENTER);
        jPanel_graph.repaint();
        
     
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        //crosshairOverlay.addRangeCrosshair(y1Crosshair);
        crosshairOverlay.addRangeCrosshair(y2Crosshair);
        chartPanel.addOverlay(crosshairOverlay);
         jPanel_graph.add(chartPanel,BorderLayout.CENTER);
       // jPanel_graph.repaint();
        add(jPanel_graph);
       
     //dummy(4.789);
     int i=1;
      tm= new Timer(1000,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //if(plot.getDomainCrosshairValue()!=null){
                //plot.clearDomainAxes();
               // }
               setCrosshairLocation(Math.random() *2,Math.random() *4,0);
              setCrosshairLocation(Math.random() *2,Math.random() *6,1);
              series1[0].setKey(Math.random() *2);
              //setCrosshairLocation(Math.random() *2,Math.random() *2,2);
                //dummy(Math.random() *2);
               
            }
        });

        tm.start();
    }
     private void dummy(double d){
         final IntervalMarker target = new IntervalMarker(4,1);
         target.setLabel(String.valueOf(d));
          plot.setDomainCrosshairValue(d);
          ValueMarker marker = new ValueMarker(d);
          marker.setLabel("gsjdhg");
          marker.setLabelTextAnchor(TextAnchor.CENTER);
          marker.setPaint(Color.ORANGE);
          
          plot.addRangeMarker(marker);
          
          //plot.addRangeMarker(target, Layer.BACKGROUND);
         // plot.addDomainMarker(target, Layer.FOREGROUND);
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
                 
                 // domainAxis =(NumberAxis) plot.getDomainAxis();
                 //domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                 //domainAxis.setAutoRange(false);
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
                
        plot.addAnnotation(new XYShapeAnnotation(new Ellipse2D.Double(3.45-0.1,10.50-0.3,0.1+0.3,0.1+0.3)));
        
        pointer = new XYPointerAnnotation("Best Bid", 3.45, 10.50, -3.0 * Math.PI / 4.0);//- for downward direction else + for upperward direction
        pointer.setBaseRadius(35.0);
        pointer.setTipRadius(10.0);
        pointer.setFont(new Font("SansSerif", Font.PLAIN, 9));
        pointer.setPaint(Color.blue);
        pointer.setTextAnchor(TextAnchor.BASELINE_LEFT);
        plot.addAnnotation(pointer);
        

        return chart;
    }
   
    private XYDataset createDataset() throws FileNotFoundException, IOException {
        //2
      series1 = new XYSeries[3];
       for(int k=0;k<series1.length;k++){
     
         series1[k] = new XYSeries("gfh"+(k+1));
         for(int i=0;i<10;i++){
         if(k==0)
         series1[k].add(i,i*2);
           if(k==1)
               series1[k].add(i,Math.random() * 4.0);
           if(k==2)
               series1[k].add(i,Math.random() * 8.0);
           
     }
       }
       XYSeriesCollection dataset1 = new XYSeriesCollection();
      for(int i=0;i<series1.length;i++){
          dataset1.addSeries(series1[i]);
          
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
    
    
    protected void setCrosshairLocation(Double x, Double y,int i) {
        plot.removeAnnotation(pointer);
        System.out.println("2222222222222222222222");
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

    if (y != null) {
        Crosshair rangeCrosshair;
        
        List rangeCrosshairs = crosshairOverlay.getRangeCrosshairs();
        System.out.println("rangeCrosshairs.size():   "+rangeCrosshairs.size());
        if (rangeCrosshairs.isEmpty()) {
            rangeCrosshair = new Crosshair();
            rangeCrosshair.setPaint(java.awt.Color.ORANGE);
            crosshairOverlay.addRangeCrosshair(rangeCrosshair);
        }
        else {
            // We only have one at a time
           rangeCrosshair = (Crosshair) rangeCrosshairs.get(0);
       }
        rangeCrosshair.setValue(y);
    }
    
      pointer = new XYPointerAnnotation("kavya", 6, 10, 3.0 * Math.PI / 4.0);
        pointer.setBaseRadius(-50.0);
        pointer.setTipRadius(3.0);
        pointer.setFont(new Font("SansSerif", Font.PLAIN, 9));
        pointer.setPaint(Color.blue);
        pointer.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
        plot.addAnnotation(pointer);
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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new NewJFrame().setVisible(true);
                    
                } catch (IOException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_graph;
    // End of variables declaration//GEN-END:variables
}
