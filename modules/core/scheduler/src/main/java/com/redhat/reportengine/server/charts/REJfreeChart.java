/**
 * 
 */
package com.redhat.reportengine.server.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 27, 2014
 */
public class REJfreeChart {
	
	public static JFreeChart getPieChart(String title, DefaultPieDataset dataset){
		 JFreeChart chart = ChartFactory.createPieChart(
		            title,  // chart title
		            dataset,             // data
		            true,               // include legend
		            true,
		            false
		        );
		 		chart.setBorderVisible(false);
		 		chart.setBackgroundPaint(Color.WHITE);
		 		PiePlot plot = (PiePlot) chart.getPlot();
		 		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		        plot.setNoDataMessage("No data available");
		        plot.setCircular(false);
		        plot.setLabelGap(0.02);
		        PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
		                "{0} ({1})", new DecimalFormat("0"), new DecimalFormat("0"));
		        //plot.setLabelGenerator(generator);
		        plot.setLegendLabelGenerator(generator);
		        return chart;
	}
	
	public static JFreeChart getLineChart(String title, XYDataset dataset){
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
	            	title,  // title
	            "Time",             // x-axis label
	            "Usage",   // y-axis label
	            dataset,            // data
	            true,               // create legend?
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );

		chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        //plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        
       // XYItemRenderer r = plot.getRenderer();
        /*if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(true);
            renderer.setDefaultShapesFilled(true);
        }
        */
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return chart;
        
	}
	
	

}
