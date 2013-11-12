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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;
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
		String hostname = "10.1.50.4";
		String username = "root";
		String password = "cs2csolutions";
		long start=System.currentTimeMillis();
		try
		{
			/* Create a connection instance */

			Connection conn = new Connection(hostname);

			/* Now connect */

			conn.connect();

			/* Authenticate.
			 * If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */

			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (isAuthenticated == false){
				throw new IOException("Authentication failed.");
			}
			/* Create a session */

			Session sess = conn.openSession();

			sess.execCommand("uname -a && date && uptime && who");

			System.out.println("Here is some information about the remote host:");

			/* 
			 * This basic example does not handle stderr, which is sometimes dangerous
			 * (please read the FAQ).
			 */

			InputStream stdout = new StreamGobbler(sess.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
  
			while (true)
			{
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}
  
			/* Show exit status, if available (otherwise "null") */
  
			System.out.println("ExitCode: " + sess.getExitStatus());
			br.close();
			sess.close();
			sess=conn.openSession();
			sess.execCommand("uname -a && date && uptime && who");
			br=new BufferedReader(new InputStreamReader(new StreamGobbler(sess.getStdout())));
			while (true)
			{
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}
			System.out.println("ExitCode: " + sess.getExitStatus());
			/* Close this session */
			br.close();
			sess.close();

			/* Close the connection */

			conn.close();

		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			System.exit(2);
		}
		System.out.println("Time: "+(System.currentTimeMillis()-start));
	}

}
