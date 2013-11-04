/**
 * 
 */
package org.cs2c.vcenter.chart;
import java.awt.*;
import java.awt.geom.Point2D;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.*;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.*;
import org.jfree.experimental.swt.*;
/**
 * @author Administrator
 *
 */
public class SystemCPUChart extends Composite{
	private DefaultValueDataset dataset=new DefaultValueDataset(0);
	/**
	 * 
	 */
	public SystemCPUChart(Composite parent, int style) {
		super(parent,style);
		DialPlot plot=new DialPlot();
		plot.setView(0, 0, 1, 1);
		plot.setDataset(dataset);
		StandardDialFrame dframe=new StandardDialFrame();
		dframe.setBackgroundPaint(Color.lightGray);
		dframe.setForegroundPaint(Color.darkGray);
		plot.setDialFrame(dframe);
//		Point2D pointA=SWTUtils.toAwtPoint(new org.eclipse.swt.graphics.Point(0,0));
//		Color colorA=SWTUtils.toAwtColor(new org.eclipse.swt.graphics.Color(this.getDisplay(), 255,255,255));
//		Point2D pointB=SWTUtils.toAwtPoint(new org.eclipse.swt.graphics.Point(100,100));
//		Color colorB=SWTUtils.toAwtColor(new org.eclipse.swt.graphics.Color(this.getDisplay(), 170,170,220));
//		GradientPaint gp=new GradientPaint(pointA,colorA,pointB,colorB);
		GradientPaint gp=new GradientPaint(new Point(),new Color(255,255,255),new Point(),new Color(170,170,220));
		DialBackground db=new DialBackground(gp);
		db.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
		plot.setBackground(db);
		DialTextAnnotation annotation=new DialTextAnnotation("Usage %");
		annotation.setRadius(0.7);
		plot.addLayer(annotation);
		DialValueIndicator dv=new DialValueIndicator();
		dv.setFont(new Font("Dialog",Font.PLAIN,10));
		dv.setOutlinePaint(Color.darkGray);
		dv.setRadius(0.6);
		dv.setAngle(-90);
		plot.addLayer(dv);
		StandardDialScale scale=new StandardDialScale();
		scale.setTickLabelOffset(0.2);
		scale.setTickLabelFont(new Font("Dialog",Font.PLAIN,14));
		scale.setLowerBound(0);
		scale.setUpperBound(100);
		scale.setTickRadius(0.9);
		scale.setStartAngle(240);
		scale.setExtent(-300);
		plot.addScale(0, scale);
		plot.addLayer(new StandardDialRange(80,100,Color.red));
		plot.addLayer(new StandardDialRange(50,80,Color.yellow));
		plot.addLayer(new StandardDialRange(0,50,Color.green));
		DialPointer needle=new DialPointer.Pointer(0);
		needle.setRadius(0.6);
		plot.addLayer(needle);
		DialCap cap=new DialCap();
		cap.setRadius(0.1);
		plot.setCap(cap);
		JFreeChart cpuChart=new JFreeChart(plot);
		cpuChart.setTitle("CPU Usage");
		this.setLayout(new FillLayout());
		ChartComposite comp=new ChartComposite(this,SWT.NONE,cpuChart,true);
	}
	public void setValue(Number data){
		this.dataset.setValue(data);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display=new Display();
		Shell shell=new Shell(display);
		shell.setSize(400, 400);
		shell.setLayout(new FillLayout());
		shell.setText("Test CPU");
		SystemCPUChart comp=new SystemCPUChart(shell,SWT.NONE);
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
	}

}
