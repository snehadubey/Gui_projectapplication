/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plot_data;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Praveen
 */
public class plot_vertical extends javax.swing.JFrame {


    public plot_vertical() {
        
        DefaultXYDataset result = new DefaultXYDataset();
     
        
        XYSeries Goals = new XYSeries("Goals_name");
        Goals.add(3.8292995834E10, 1.2340);
        Goals.add(3.8292996834E10, 3.0);
        Goals.add(3.829501585E10, 2.0);
        Goals.add(3.8295072851E10, 0.0);
        Goals.add(3.8295082852E10, 3.0);
           result.addSeries(getTitle(), Goals.toArray());
         
        String[] ss= new String[5];
        ss[0]="";
        ss[1]="fd";
        ss[2]="fgfd";
        //ss[3]="fd";
        //ss[4]="fd";
       List <Double> aaa;
        aaa = new ArrayList<>();
        aaa=Goals.getItems();
        System.out.println("data are: "+aaa);
        Double x1=Double.parseDouble("1.2340");
        System.out.println("item found at: "+Goals.getY(0).equals(x1));
       
        ValueAxis xaxis = new NumberAxis("x");
        
        ValueAxis yaxis = new SymbolAxis("Symbol", new String[]{"one","two","three"});
        XYDataset xyDataset = new XYSeriesCollection(Goals);
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Goals Scored Over Time", "Fixture Number", "Goals",
            xyDataset, PlotOrientation.VERTICAL, true, true, false);
        
        
        //XYItemRenderer renderer1= new XYLineAndShapeRenderer();
       
        XYStepRenderer render = new XYStepRenderer();
        render.setBaseShapesVisible(true);
        render.setSeriesStroke(0, new BasicStroke(2.0f));
        render.setSeriesStroke(1, new BasicStroke(2.0f));
        render.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        render.setDefaultEntityRadius(6);
        chart.getXYPlot().setRenderer(render);
        
         XYPlot plot = new XYPlot(result,xaxis,yaxis,render);
        
        
        SymbolAxis s= new SymbolAxis("Goals",ss);
        s.setTickUnit(new NumberTickUnit(1));
       s.setRange(0,4);
       s.setLabelAngle(45);
        chart.getXYPlot().setRangeAxis(s);
       // chart.getXYPlot().setRangeAxis(s);
        System.out.println("ugfdugdf: "+chart.getPlot().toString());
        //NumberAxis domain = (NumberAxis) chart.getXYPlot().getDomainAxis();
        //domain.setVerticalTickLabels(true);
        //chart.getXYPlot().getDomainAxis().setVerticalTickLabels(true);
         ValueAxis xAxis = new NumberAxis("x");
        ValueAxis yAxis = new SymbolAxis("Symbol", new String[]{"One","Two","Three","Four","Five"});
      XYItemRenderer renderer = new XYLineAndShapeRenderer();
      XYPlot plot2 = new XYPlot(result, xAxis, yAxis, renderer);
         JFreeChart chart2 = new JFreeChart("Symbol Axis Demo", new Font("Tahoma", 0, 18), plot, true);
      JFrame frame = new JFrame("XY Plot Demo");
      frame.setContentPane(new ChartPanel(chart));
      frame.setVisible(true);
        ChartPanel cp = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(320, 240);
            }
        };
        cp.setMouseWheelEnabled(true);
        add(cp);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChartTest().setVisible(true);
            }
        });
    }
}

