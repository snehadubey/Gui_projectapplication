/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plot_data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;

public class CrosshairOverlayDemo1 extends JFrame implements ChartMouseListener {

    private ChartPanel chartPanel;

    private Crosshair xCrosshair;

    private Crosshair yCrosshair;
    CrosshairOverlay crosshairOverlay;

    public CrosshairOverlayDemo1(String title) {
        super(title);
        add(createContent());
       
    }

    private JPanel createContent() {//1
        JFreeChart chart = createChart(createDataset());
        
        this.chartPanel = new ChartPanel(chart);
        this.chartPanel.addChartMouseListener(this);
        crosshairOverlay = new CrosshairOverlay();
       // this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        //this.xCrosshair.setLabelVisible(true);
        //this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        //this.yCrosshair.setLabelVisible(true);
        
        
        this.xCrosshair = new Crosshair(4.346, Color.WHITE, new BasicStroke(0.8f));
        xCrosshair.setLabelBackgroundPaint(new Color(1f, 1f, 1f, 0.7f));
        xCrosshair.setLabelPaint(Color.GRAY);
        xCrosshair.setLabelOutlineVisible(false);
 
        xCrosshair.setLabelFont(xCrosshair.getLabelFont().deriveFont(1f));
       // xCrosshair.setLabelBackgroundPaint(new Color(1f, 1f, 1f, 0f));
        this.xCrosshair.setLabelVisible(true);

        

        this.yCrosshair = new Crosshair(7.623, Color.black, new BasicStroke(0.8f));
        yCrosshair.setLabelBackgroundPaint(new Color(1f, 1f, 1f, 0.7f));
        yCrosshair.setLabelPaint(Color.GRAY);
        yCrosshair.setLabelOutlineVisible(false);
        yCrosshair.setLabelFont(yCrosshair.getLabelFont().deriveFont(11f));
        /*yCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {
        @Override
        public String generateLabel(final Crosshair ch) {
            return String.valueOf(7.623)+"dBc/Hz";
        }
        });
        this.yCrosshair.setLabelVisible(true);*/
        
        
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        chartPanel.addOverlay(crosshairOverlay);
        return chartPanel;
    }

    private JFreeChart createChart(XYDataset dataset) {//3
        JFreeChart chart = ChartFactory.createXYLineChart("Crosshair Demo", 
                "X", "Y", dataset);
        
        return chart;
    }

    private XYDataset createDataset() {//2
        XYSeries series = new XYSeries("S1");
        for (int x = 0; x < 10; x++) {
            series.add(x, x + Math.random() * 4.0);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return dataset;
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
                RectangleEdge.TOP);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);
    }
    
    protected void setCrosshairLocation(double x, Double y) {
    Crosshair domainCrosshair;
    List domainCrosshairs = crosshairOverlay.getDomainCrosshairs();
    if (domainCrosshairs.isEmpty()) {
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
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CrosshairOverlayDemo1 app = new CrosshairOverlayDemo1(
                        "JFreeChart: CrosshairOverlayDemo1.java");
                app.pack();
                app.setVisible(true);
            }
        });
    }

}
