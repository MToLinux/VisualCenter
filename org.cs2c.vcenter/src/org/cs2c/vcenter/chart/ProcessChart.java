/**
 * 
 */
package org.cs2c.vcenter.chart;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

/**
 * @author Administrator
 *
 */
public class ProcessChart extends Composite {
	private DefaultCategoryDataset dataset=new DefaultCategoryDataset();
	/**
	 * @param parent
	 * @param style
	 */
	public ProcessChart(Composite parent, int style, String title) {
		super(parent, style);
		JFreeChart jfreechart = ChartFactory.createBarChart3D(title, "PIDs", "Usage", this.dataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		categoryplot.setOutlineVisible(false);
		categoryplot.setDomainGridlinesVisible(true);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
		categoryaxis.setCategoryMargin(0.1D);
		BarRenderer3D barrenderer3d = (BarRenderer3D)categoryplot.getRenderer();
		barrenderer3d.setDrawBarOutline(false);
		this.setLayout(new FillLayout());
		ChartComposite comp=new ChartComposite(this,SWT.NONE,jfreechart,true);
	}
	public void setDataset(Map<String,Number> dataset){
		Set<String> keys=dataset.keySet();
		for(String key: keys){
			this.dataset.addValue(dataset.get(key), key, "PID");
		}
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
		ProcessChart comp=new ProcessChart(shell,SWT.NONE,"CPU");
		Map<String,Number> dataset=new HashMap<String,Number>();
		dataset.put("2231", 10);
		dataset.put("2232", 12);
		dataset.put("2233", 7);
		dataset.put("2234", 14);
		comp.setDataset(dataset);
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}

	}

}
