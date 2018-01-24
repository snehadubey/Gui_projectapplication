/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plot_data;

/**
 *
 * @author Admin
 */
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartTest extends javax.swing.JFrame {

    public ChartTest() {
        XYSeries Goals = new XYSeries("Goals Scored");
        Goals.add(3.8292995834E10, 1.0);
        Goals.add(3.8292996834E10, 2.0);
        Goals.add(3.8293004829E10, 3.0);
        Goals.add(3.8293005829E10, 6.0);
        for(int k3=0;k3<3;k3++){
            double value = (double) Goals.getX(k3);
            //Goals.update(6, 456.6);
        }
        
        String[] ss= new String[5];
        ss[0]="fd";
        ss[1]="fd";
        ss[2]="fd";
        ss[3]="fd";
        ss[4]="fd";
       List <Double> aaa;
        aaa = new ArrayList<>();
        aaa=Goals.getItems();
        System.out.println("data are: "+aaa);
        Double x1=Double.parseDouble("1.2340");
        System.out.println("item found at: "+Goals.getY(0).equals(x1));
       
        XYDataset xyDataset = new XYSeriesCollection(Goals);
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Goals Scored Over Time", "Fixture Number", "Goals",
            xyDataset, PlotOrientation.VERTICAL, true, true, false);
        XYLineAndShapeRenderer renderer=new XYLineAndShapeRenderer();
        chart.getXYPlot().setRenderer(renderer);
           renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
         DateAxis axis2 =new DateAxis();
       //axis2.setTimeZone(TimeZone.getTimeZone("GMT"));
        //axis2.setDateFormatOverride(DateFormat.getDateInstance(DateFormat.FULL));
         
         
        SimpleDateFormat sdf2= new SimpleDateFormat("HH:mm:ss.SSSSSS");
        sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf2.format(new Date().getTime());
        axis2.setDateFormatOverride(sdf2);
        axis2.getMaximumDate();
        chart.getXYPlot().setDomainAxis(axis2);
        //SymbolAxis s= new SymbolAxis("uhdfui",ss);
        //chart.getXYPlot().setDomainAxes(s);
        //System.out.println("ugfdugdf: "+chart.getPlot().toString());
        //NumberAxis domain = (NumberAxis) chart.getXYPlot().getDomainAxis();
        //domain.setVerticalTickLabels(true);
        chart.getXYPlot().getDomainAxis().setVerticalTickLabels(true);
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
