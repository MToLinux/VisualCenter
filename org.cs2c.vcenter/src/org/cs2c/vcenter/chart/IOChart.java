/**
 * 
 */
package org.cs2c.vcenter.chart;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.RectangleInsets;

/**
 * @author Administrator
 *
 */
public class IOChart extends Composite{
	private Map<String,TimeSeries> seriesMap=new HashMap<String,TimeSeries>();
	private TimeSeriesCollection collection=new TimeSeriesCollection();
	/**
	 * 
	 */
	public IOChart(Composite parent, int style, String title) {
		super(parent,style);
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, "Time", "Kbps", this.collection,true,true,false);
		XYPlot xyplot = jfreechart.getXYPlot();
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
		xyplot.setDomainPannable(true);
		xyplot.setRangePannable(false);
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairVisible(true);
		DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
		dateaxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
		ChartUtilities.applyCurrentTheme(jfreechart);
		this.setLayout(new FillLayout());
		ChartComposite comp=new ChartComposite(this,SWT.NONE,jfreechart,true);
	}
	public void addDataset(Map<String,Number> dataset){
		Date date=new Date(System.currentTimeMillis());
		Set<String> keys=dataset.keySet();
		for(String key: keys){
			TimeSeries series=this.seriesMap.get(key);
			if(series==null){
				series=new TimeSeries(key);
				this.seriesMap.put(key, series);
				this.collection.addSeries(series);
			}
			series.add(new Millisecond(date), dataset.get(key));
		}
	}
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args){
		Display display=new Display();
		Shell shell=new Shell(display);
		shell.setSize(400, 400);
		shell.setLayout(new FillLayout());
		shell.setText("Test CPU");
		IOChart comp=new IOChart(shell,SWT.NONE,"Read");
		Map<String,Number> dataset=new HashMap<String,Number>();
		dataset.put("sda1", 10012);
		dataset.put("sda2", 12);
		comp.addDataset(dataset);
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}

	}

}
