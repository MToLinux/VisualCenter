// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.cs2c.vcenter.chart;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class TimeSeriesDemo1 extends ApplicationFrame
{

	public TimeSeriesDemo1(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static JFreeChart createChart(XYDataset xydataset)
	{
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Legal & General Unit Trust Prices", "Date", "Price Per Unit", xydataset, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		xyplot.setDomainPannable(true);
		xyplot.setRangePannable(false);
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairVisible(true);
		org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		if (xyitemrenderer instanceof XYLineAndShapeRenderer)
		{
			XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyitemrenderer;
			xylineandshaperenderer.setBaseShapesVisible(false);
		}
		DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
		dateaxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
		return jfreechart;
	}

	private static XYDataset createDataset()
	{
		TimeSeries timeseries = new TimeSeries("L&G European Index Trust");
		timeseries.add(new Month(2, 2001), 181.80000000000001D);
		timeseries.add(new Month(3, 2001), 167.30000000000001D);
		timeseries.add(new Month(4, 2001), 153.80000000000001D);
		timeseries.add(new Month(5, 2001), 167.59999999999999D);
		timeseries.add(new Month(6, 2001), 158.80000000000001D);
		timeseries.add(new Month(7, 2001), 148.30000000000001D);
		timeseries.add(new Month(8, 2001), 153.90000000000001D);
		timeseries.add(new Month(9, 2001), 142.69999999999999D);
		timeseries.add(new Month(10, 2001), 123.2D);
		timeseries.add(new Month(11, 2001), 131.80000000000001D);
		timeseries.add(new Month(12, 2001), 139.59999999999999D);
		timeseries.add(new Month(1, 2002), 142.90000000000001D);
		timeseries.add(new Month(2, 2002), 138.69999999999999D);
		timeseries.add(new Month(3, 2002), 137.30000000000001D);
		timeseries.add(new Month(4, 2002), 143.90000000000001D);
		timeseries.add(new Month(5, 2002), 139.80000000000001D);
		timeseries.add(new Month(6, 2002), 137D);
		timeseries.add(new Month(7, 2002), 132.80000000000001D);
		timeseries.add(new Month(8, 2002), 110.3D);
		timeseries.add(new Month(9, 2002), 110.5D);
		timeseries.add(new Month(10, 2002), 94.109999999999999D);
		timeseries.add(new Month(11, 2002), 102.5D);
		timeseries.add(new Month(12, 2002), 112.3D);
		timeseries.add(new Month(1, 2003), 104D);
		timeseries.add(new Month(2, 2003), 98.530000000000001D);
		timeseries.add(new Month(3, 2003), 97.150000000000006D);
		timeseries.add(new Month(4, 2003), 94.900000000000006D);
		timeseries.add(new Month(5, 2003), 107.8D);
		timeseries.add(new Month(6, 2003), 113.7D);
		timeseries.add(new Month(7, 2003), 112.5D);
		timeseries.add(new Month(8, 2003), 118.59999999999999D);
		timeseries.add(new Month(9, 2003), 123.8D);
		timeseries.add(new Month(10, 2003), 117.2D);
		timeseries.add(new Month(11, 2003), 123D);
		timeseries.add(new Month(12, 2003), 127D);
		timeseries.add(new Month(1, 2004), 132.69999999999999D);
		timeseries.add(new Month(2, 2004), 132.40000000000001D);
		timeseries.add(new Month(3, 2004), 131.69999999999999D);
		timeseries.add(new Month(4, 2004), 128D);
		timeseries.add(new Month(5, 2004), 131.80000000000001D);
		timeseries.add(new Month(6, 2004), 127.40000000000001D);
		timeseries.add(new Month(7, 2004), 133.5D);
		timeseries.add(new Month(8, 2004), 126D);
		timeseries.add(new Month(9, 2004), 129.5D);
		timeseries.add(new Month(10, 2004), 135.30000000000001D);
		timeseries.add(new Month(11, 2004), 138D);
		timeseries.add(new Month(12, 2004), 141.30000000000001D);
		timeseries.add(new Month(1, 2005), 148.80000000000001D);
		timeseries.add(new Month(2, 2005), 147.09999999999999D);
		timeseries.add(new Month(3, 2005), 150.69999999999999D);
		timeseries.add(new Month(4, 2005), 150D);
		timeseries.add(new Month(5, 2005), 145.69999999999999D);
		timeseries.add(new Month(6, 2005), 152D);
		timeseries.add(new Month(7, 2005), 157.19999999999999D);
		timeseries.add(new Month(8, 2005), 167D);
		timeseries.add(new Month(9, 2005), 165D);
		timeseries.add(new Month(10, 2005), 171.59999999999999D);
		timeseries.add(new Month(11, 2005), 166.19999999999999D);
		timeseries.add(new Month(12, 2005), 174.30000000000001D);
		timeseries.add(new Month(1, 2006), 183.80000000000001D);
		timeseries.add(new Month(2, 2006), 187D);
		timeseries.add(new Month(3, 2006), 191.30000000000001D);
		timeseries.add(new Month(4, 2006), 202.5D);
		timeseries.add(new Month(5, 2006), 200.59999999999999D);
		timeseries.add(new Month(6, 2006), 187.30000000000001D);
		timeseries.add(new Month(7, 2006), 192.19999999999999D);
		timeseries.add(new Month(8, 2006), 190.80000000000001D);
		timeseries.add(new Month(9, 2006), 194.69999999999999D);
		timeseries.add(new Month(10, 2006), 201.30000000000001D);
		timeseries.add(new Month(11, 2006), 205.09999999999999D);
		timeseries.add(new Month(12, 2006), 206.69999999999999D);
		timeseries.add(new Month(1, 2007), 216.80000000000001D);
		timeseries.add(new Month(2, 2007), 218D);
		timeseries.add(new Month(3, 2007), 215.40000000000001D);
		timeseries.add(new Month(4, 2007), 223D);
		timeseries.add(new Month(5, 2007), 235.09999999999999D);
		timeseries.add(new Month(6, 2007), 242D);
		timeseries.add(new Month(7, 2007), 237.80000000000001D);
		TimeSeries timeseries1 = new TimeSeries("L&G UK Index Trust");
		timeseries1.add(new Month(2, 2001), 129.59999999999999D);
		timeseries1.add(new Month(3, 2001), 123.2D);
		timeseries1.add(new Month(4, 2001), 117.2D);
		timeseries1.add(new Month(5, 2001), 124.09999999999999D);
		timeseries1.add(new Month(6, 2001), 122.59999999999999D);
		timeseries1.add(new Month(7, 2001), 119.2D);
		timeseries1.add(new Month(8, 2001), 116.5D);
		timeseries1.add(new Month(9, 2001), 112.7D);
		timeseries1.add(new Month(10, 2001), 101.5D);
		timeseries1.add(new Month(11, 2001), 106.09999999999999D);
		timeseries1.add(new Month(12, 2001), 110.3D);
		timeseries1.add(new Month(1, 2002), 111.7D);
		timeseries1.add(new Month(2, 2002), 111D);
		timeseries1.add(new Month(3, 2002), 109.59999999999999D);
		timeseries1.add(new Month(4, 2002), 113.2D);
		timeseries1.add(new Month(5, 2002), 111.59999999999999D);
		timeseries1.add(new Month(6, 2002), 108.8D);
		timeseries1.add(new Month(7, 2002), 101.59999999999999D);
		timeseries1.add(new Month(8, 2002), 90.950000000000003D);
		timeseries1.add(new Month(9, 2002), 91.019999999999996D);
		timeseries1.add(new Month(10, 2002), 82.370000000000005D);
		timeseries1.add(new Month(11, 2002), 86.319999999999993D);
		timeseries1.add(new Month(12, 2002), 91D);
		timeseries1.add(new Month(1, 2003), 86D);
		timeseries1.add(new Month(2, 2003), 80.040000000000006D);
		timeseries1.add(new Month(3, 2003), 80.400000000000006D);
		timeseries1.add(new Month(4, 2003), 80.280000000000001D);
		timeseries1.add(new Month(5, 2003), 86.420000000000002D);
		timeseries1.add(new Month(6, 2003), 91.400000000000006D);
		timeseries1.add(new Month(7, 2003), 90.519999999999996D);
		timeseries1.add(new Month(8, 2003), 93.109999999999999D);
		timeseries1.add(new Month(9, 2003), 96.799999999999997D);
		timeseries1.add(new Month(10, 2003), 94.780000000000001D);
		timeseries1.add(new Month(11, 2003), 99.560000000000002D);
		timeseries1.add(new Month(12, 2003), 100.8D);
		timeseries1.add(new Month(1, 2004), 103.40000000000001D);
		timeseries1.add(new Month(2, 2004), 102.09999999999999D);
		timeseries1.add(new Month(3, 2004), 105.3D);
		timeseries1.add(new Month(4, 2004), 103.7D);
		timeseries1.add(new Month(5, 2004), 105.2D);
		timeseries1.add(new Month(6, 2004), 103.7D);
		timeseries1.add(new Month(7, 2004), 105.7D);
		timeseries1.add(new Month(8, 2004), 103.59999999999999D);
		timeseries1.add(new Month(9, 2004), 106.09999999999999D);
		timeseries1.add(new Month(10, 2004), 109.3D);
		timeseries1.add(new Month(11, 2004), 110.3D);
		timeseries1.add(new Month(12, 2004), 112.59999999999999D);
		timeseries1.add(new Month(1, 2005), 116D);
		timeseries1.add(new Month(2, 2005), 117.3D);
		timeseries1.add(new Month(3, 2005), 120.09999999999999D);
		timeseries1.add(new Month(4, 2005), 119.3D);
		timeseries1.add(new Month(5, 2005), 116.2D);
		timeseries1.add(new Month(6, 2005), 120.8D);
		timeseries1.add(new Month(7, 2005), 125.2D);
		timeseries1.add(new Month(8, 2005), 127.7D);
		timeseries1.add(new Month(9, 2005), 130.80000000000001D);
		timeseries1.add(new Month(10, 2005), 131D);
		timeseries1.add(new Month(11, 2005), 135.30000000000001D);
		timeseries1.add(new Month(12, 2005), 141.19999999999999D);
		timeseries1.add(new Month(1, 2006), 144.69999999999999D);
		timeseries1.add(new Month(2, 2006), 146.40000000000001D);
		timeseries1.add(new Month(3, 2006), 151.90000000000001D);
		timeseries1.add(new Month(4, 2006), 153.5D);
		timeseries1.add(new Month(5, 2006), 144.5D);
		timeseries1.add(new Month(6, 2006), 150.09999999999999D);
		timeseries1.add(new Month(7, 2006), 148.69999999999999D);
		timeseries1.add(new Month(8, 2006), 150.09999999999999D);
		timeseries1.add(new Month(9, 2006), 151.59999999999999D);
		timeseries1.add(new Month(10, 2006), 153.40000000000001D);
		timeseries1.add(new Month(11, 2006), 158.30000000000001D);
		timeseries1.add(new Month(12, 2006), 157.59999999999999D);
		timeseries1.add(new Month(1, 2007), 163.90000000000001D);
		timeseries1.add(new Month(2, 2007), 163.80000000000001D);
		timeseries1.add(new Month(3, 2007), 162D);
		timeseries1.add(new Month(4, 2007), 167.09999999999999D);
		timeseries1.add(new Month(5, 2007), 170D);
		timeseries1.add(new Month(6, 2007), 175.69999999999999D);
		timeseries1.add(new Month(7, 2007), 171.90000000000001D);
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		timeseriescollection.addSeries(timeseries1);
		return timeseriescollection;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[])
	{
		TimeSeriesDemo1 timeseriesdemo1 = new TimeSeriesDemo1("Time Series Demo 1");
		timeseriesdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(timeseriesdemo1);
		timeseriesdemo1.setVisible(true);
	}
}
