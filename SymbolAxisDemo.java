/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plot_data;

import java.awt.Font;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;

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
public class SymbolAxisDemo {
   public static void main(String[] args) {
      DefaultXYDataset dataset = new DefaultXYDataset();
      
      
        DefaultXYDataset result = new DefaultXYDataset();
     
        
        XYSeries Goals = new XYSeries("Goals_name");
        Goals.add(1, 1.2340);
        Goals.add(2, 3.0);
        Goals.add(3, 2.0);
        Goals.add(4, 0.0);
        Goals.add(5, 3.0);
         result.addSeries("Values 1", Goals.toArray());
         
      //dataset.addSeries("Values 1",new double[][]{{1, 2, 3}, {2, 4, 1}});      
      //dataset.addSeries("Values 2",new double[][]{{4, 5, 6}, {0, 3, 2}});      
      //dataset.addSeries("Values 3",new double[][]{{1.5, 3.5, 5.5}, {0.5, 3.5, 2.5}});      
      ValueAxis xAxis = new NumberAxis("x");
      xAxis.setLabelAngle(Math.PI/4.0);
      ValueAxis yAxis = new SymbolAxis("Symbol", new String[]{"One","Two","Three"});
      XYItemRenderer renderer = new XYLineAndShapeRenderer();
      XYPlot plot = new XYPlot(result, xAxis, yAxis, renderer);
      JFreeChart chart = new JFreeChart("Symbol Axis Demo", new Font("Tahoma", 0, 18), plot, true);
      JFrame frame = new JFrame("XY Plot Demo");
      frame.setContentPane(new ChartPanel(chart));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      frame.pack();
      frame.setVisible(true);
   }
}