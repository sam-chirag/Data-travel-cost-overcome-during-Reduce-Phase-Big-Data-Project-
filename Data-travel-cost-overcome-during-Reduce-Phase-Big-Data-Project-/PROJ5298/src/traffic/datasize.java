
package traffic;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class datasize extends ApplicationFrame {
Connection con;
       Statement st;
       ResultSet rs;
       public static ArrayList<Long>cost=new ArrayList<Long>();
       public static ArrayList<Double>ar=new ArrayList<Double>();
    public datasize(final String title) {

        super(title);

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 570));
        setContentPane(chartPanel);

    }
  
 
    private XYDataset createDataset() {
       
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/cost","root","");
            st=(Statement)con.createStatement();
            String Query="select * from powcost";
            rs=st.executeQuery(Query);
            int x=0;
          while(rs.next()){
           
                cost.add(rs.getLong(3));
                ar.add(rs.getDouble(4));
               System.out.println(rs.getLong(3));
               System.out.println(rs.getDouble(4));
                
          x++;
              }   
        }catch(Exception e){System.out.println(e);}

    
        final XYSeries series1 = new XYSeries("Joint Optimization");
        series1.add(0,3800);
       series1.add(260, cost.get(0));
        series1.add(238,cost.get(1));
        series1.add(428, cost.get(2));
        series1.add(363, cost.get(3));
        
        series1.add(1114,cost.get(4));
       // series1.add(60.0, 3.0);
      // series1.add(70.0, 3.5);
//        series1.add(80.0, 3.5);
       

        final XYSeries series2 = new XYSeries("Non Joint Optimization");
        series2.add(0.0, 5800);
        series2.add(250.0, 5700);
        series2.add(300.0, 5600);
        series2.add(400.0, 5555);
        series2.add(1120.0, 5655);
      //  series2.add(65.0, 4565);
       // series2.add(75.0, 7.5);
//        series2.add(85.0, 7);
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
          
        return dataset;
        
    }
  
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Over all Cost",      // chart title
            "Data Size",                      // x axis label
            "Operational Cost",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );


        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
         plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
//        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesLinesVisible(0, false);
//        renderer.setSeriesShapesVisible(1, false);
//        plot.setRenderer(renderer);

          final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
        
    }

  
    public static void main(final String[] args) {


        final datasize demo = new datasize("Comparison of number of task migrations when there are 7 VMs");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}