/**
 * 
 */
package org.cs2c.vcenter.chart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.util.Rotation;

/**
 * @author Administrator
 *
 */
public class MemoryChart extends Composite {
	private DefaultPieDataset dataset=new DefaultPieDataset();
	/**
	 * @param parent
	 * @param style
	 */
	public MemoryChart(Composite parent, int style) {
		super(parent, style);
		this.dataset.setValue("Used", 10);
		this.dataset.setValue("Buffer", 10);
		this.dataset.setValue("Cached", 10);
		this.dataset.setValue("Free", 70);
		JFreeChart chart=ChartFactory.createPieChart3D("Memory Usage", this.dataset,true,true,false);
		PiePlot3D plot=(PiePlot3D)chart.getPlot();
		plot.setDarkerSides(true);
		plot.setStartAngle(-120);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		plot.setNoDataMessage("No data.");
		this.setLayout(new FillLayout());
		ChartComposite comp=new ChartComposite(this,SWT.NONE,chart,true);
	}
	public void setValue(Number used, Number buffer, Number cached, Number free){
		this.dataset.setValue("Used", used);
		this.dataset.setValue("Buffer", buffer);
		this.dataset.setValue("Cached", cached);
		this.dataset.setValue("Free", free);
	}
	
	public static void main(String[] args) {
		Display display=new Display();
		Shell shell=new Shell(display);
		shell.setSize(400, 400);
		shell.setLayout(new FillLayout());
		shell.setText("Test CPU");
		MemoryChart comp=new MemoryChart(shell,SWT.NONE);
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
	}
	
}
