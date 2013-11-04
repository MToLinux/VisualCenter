// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.cs2c.vcenter.chart;

import java.awt.*;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.*;
import org.jfree.ui.*;

public class XYSplineRendererDemo1a extends ApplicationFrame
{

	public XYSplineRendererDemo1a(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		getContentPane().add(jpanel);
	}

	public static JPanel createDemoPanel()
	{
		NumberAxis numberaxis = new NumberAxis("X");
		numberaxis.setAutoRangeIncludesZero(false);
		NumberAxis numberaxis1 = new NumberAxis("Y");
		numberaxis1.setAutoRangeIncludesZero(false);
		XYSplineRenderer xysplinerenderer = new XYSplineRenderer();
		XYPlot xyplot = new XYPlot(createSampleData(), numberaxis, numberaxis1, xysplinerenderer);
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
		JFreeChart jfreechart = new JFreeChart("XYSplineRenderer", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
		ChartUtilities.applyCurrentTheme(jfreechart);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		return chartpanel;
	}

	private static XYDataset createSampleData()
	{
		XYSeries xyseries = new XYSeries("Series 1");
		xyseries.add(2D, 56.270000000000003D);
		xyseries.add(3D, 41.32D);
		xyseries.add(4D, 31.449999999999999D);
		xyseries.add(5D, 30.050000000000001D);
		xyseries.add(6D, 24.690000000000001D);
		xyseries.add(7D, 19.780000000000001D);
		xyseries.add(8D, 20.940000000000001D);
		xyseries.add(9D, 16.73D);
		xyseries.add(10D, 14.210000000000001D);
		xyseries.add(11D, 12.44D);
		XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
		XYSeries xyseries1 = new XYSeries("Series 2");
		xyseries1.add(11D, 56.270000000000003D);
		xyseries1.add(10D, 41.32D);
		xyseries1.add(9D, 31.449999999999999D);
		xyseries1.add(8D, 30.050000000000001D);
		xyseries1.add(7D, 24.690000000000001D);
		xyseries1.add(6D, 19.780000000000001D);
		xyseries1.add(5D, 20.940000000000001D);
		xyseries1.add(4D, 16.73D);
		xyseries1.add(3D, 14.210000000000001D);
		xyseries1.add(2D, 12.44D);
		xyseriescollection.addSeries(xyseries1);
		return xyseriescollection;
	}

	public static void main(String args[])
	{
		XYSplineRendererDemo1a xysplinerendererdemo1a = new XYSplineRendererDemo1a("JFreeChart: XYSplineRendererDemo1a.java");
		xysplinerendererdemo1a.pack();
		RefineryUtilities.centerFrameOnScreen(xysplinerendererdemo1a);
		xysplinerendererdemo1a.setVisible(true);
	}
}
