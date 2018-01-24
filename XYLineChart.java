package Plot_data;

//package com.bethecoder.tutorials.jfree_charts.tests;
//import net.sf.jasperreports.engine.JRChart;
//import net.sf.jasperreports.engine.fill.JRFillChart;
//import net.sf.jasperreports.engine.fill.JRBaseFiller;
//import net.sf.jasperreports.engine.fill.JRFillChartDataset;
//import net.sf.jasperreports.engine.JRAbstractChartCustomizer;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.XYItemLabelGenerator;
//import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.*;
import org.jfree.ui.TextAnchor;
public class XYLineChart extends JPanel {
	
	/* private JRFillChart fillChart;
	     @Override
	     public void init(JRBaseFiller chartFiller, JRFillChart chart)
	     {
	         super.init(chartFiller, chart);
	         this.fillChart = chart;
	     }
	     @Override
	     public void customize(JFreeChart chart, JRChart jasperChart)
	     {
	         XYItemRenderer renderer = chart.getXYPlot().getRenderer();
	         renderer.setBaseItemLabelsVisible(true);
	         renderer.setBaseItemLabelGenerator((XYItemLabelGenerator)((JRFillChartDataset) fillChart.getDataset()).getLabelGenerator());
	     }*/
public void paintComponent(Graphics g)
{
	super.paintComponent(g);
	Graphics2D g2=(Graphics2D) g;
	g2.setColor(Color.yellow);
	g2.fillOval(10, 10, 40, 40);
	g2.drawString(("rr,"), 20, 35);
	//g2.dr
        
}

	public static void main(String arg[]) throws FileNotFoundException, InterruptedException {

            // Prepare the data set
            XYSeries xySeries1 = new XYSeries("Number & Square Chart1");
            XYSeries xySeries2 = new XYSeries("Number & Square Chart2");
            XYSeries xySeries3 = new XYSeries("Number & Square Chart3");
            float[] time1= new float[100];
            float[] value1 = new float[100];
            float[] value2 = new float[100];
	    float[] value3 = new float[100];
            Scanner s1= new Scanner(new File("plot2.txt"));
	    //Scanner s2= new Scanner(new File("plot3.txt"));
	    //List<String> names= new ArrayList<String>();
	    int i=0;
	    while(s1.hasNext())
	    {
	    	time1[i]=s1.nextFloat();
	    	//s1.skip("	");
	    	//s.skip("	");
	    	//System.out.println(s1.nextFloat());
	    	value1[i]=s1.nextFloat();
                value2[i]=s1.nextFloat();
                value3[i]=s1.nextFloat();
                xySeries1.add(time1[i], value1[i]);
                xySeries2.add(time1[i], value2[i]);
                xySeries3.add(time1[i], value3[i]);
	    	i=i+1;
	    	
	    }
            /*int k=0;
	    while(s2.hasNext())
	    {
	    	time2[k]=s2.nextFloat();
	    	s2.skip("	");
	    	//s.skip("	");
	    	//System.out.println(s.nextFloat());
	    	value2[k]=s2.nextFloat();
	    	k=k+1;
	    	
	    }*/
	  	XYDataset xyDataset1 = new XYSeriesCollection(xySeries1);
	  	XYDataset xyDataset2 = new XYSeriesCollection(xySeries2);
                XYDataset xyDataset3 = new XYSeriesCollection(xySeries3);
	 
	    System.out.println(xySeries1.getItems());
	  
		//Create the chart
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Plotting points", "x label", "y label", xyDataset1,
				PlotOrientation.VERTICAL, true, true, false);
		
		chart.setBackgroundPaint(Color.pink);
		chart.getTitle().setPaint(Color.red);
		//CategoryPlot p=chart.getCategoryPlot();
		//p.setRangeGridlinePaint(Color.BLUE);
		//Render the frame
		
		ChartFrame chartFrame = new ChartFrame("XYLine Chart", chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(600, 600);
		
		XYPlot plot= chart.getXYPlot();
		plot.setDataset(0, xyDataset1);
		plot.setDataset(1, xyDataset2);
                plot.setDataset(2, xyDataset3);
		
                XYLineAndShapeRenderer renderer0=new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer renderer1=new XYLineAndShapeRenderer();
                XYLineAndShapeRenderer renderer2=new XYLineAndShapeRenderer();
		plot.setRenderer(0, renderer0);
		plot.setRenderer(1, renderer1);
                plot.setRenderer(2, renderer2);
                
                final Marker start = new ValueMarker(4.5);
                start.setPaint(Color.red);
                start.setLabel("current value");
                start.setLabelBackgroundColor(Color.yellow);
                start.setLabelTextAnchor(TextAnchor.CENTER);
                plot.addRangeMarker(start);
                
                
                
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.yellow);
		//plot.getRendererForDataset(plot.getDataset(1)).setSeriesPaint(1, Color.blue);

		//renderer0.setSeriesPaint(0, Color.cyan);
		//renderer0.setSeriesStroke(0, new BasicStroke(4.0f)); //thickness of line
		System.out.println("plot  :  "+plot.getDomainAxis());
		
		
		//final XYPlot p= chart.getXYPlot();
		//System.out.println("\nplot  :  "+p.getDomainAxis());
		//xySeries1
		//System.out.println(xySeries2.getItems());
		//lineRender lf=new lineRender
		

    //JFreeChart chart = new JFreeChart("Cos(x) and Cos^2(x) versus x", parent);
    ChartPanel myChart = new ChartPanel(chart);
    myChart.setChart(chart);
    myChart.setMouseWheelEnabled(true);
	}
}