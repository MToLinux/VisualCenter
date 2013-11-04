/**
 * 
 */
package org.cs2c.vcenter.editors;

import org.cs2c.vcenter.chart.MemoryChart;
import org.cs2c.vcenter.chart.SwapChart;
import org.cs2c.vcenter.chart.SystemCPUChart;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;

/**
 * @author Administrator
 *
 */
public class MonitorFace extends EditorPart {
	public static final String ID="org.cs2c.vcenter.editors.MonitorFace";
	/**
	 * 
	 */
	public MonitorFace() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		TabItem tbtmSystem = new TabItem(tabFolder, SWT.NONE);
		tbtmSystem.setText("System");
		TabItem tbtmNginx = new TabItem(tabFolder, SWT.NONE);
		tbtmNginx.setText("Nginx");
		
		Composite compositeSystem = new Composite(tabFolder, SWT.NONE);
		compositeSystem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compositeSystem.setLayout(new GridLayout(2, true));
		tbtmSystem.setControl(compositeSystem);
		
		Composite compositeNginx = new Composite(tabFolder, SWT.NONE);
		compositeNginx.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compositeNginx.setLayout(new GridLayout(2, true));
		tbtmNginx.setControl(compositeNginx);
		
		Group grpCpu = new Group(compositeSystem, SWT.NONE);
		grpCpu.setText("CPU");
		grpCpu.setLayout(new GridLayout(2,false));
		grpCpu.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group grpMem = new Group(compositeSystem, SWT.NONE);
		grpMem.setLayout(new GridLayout(1,false));
		grpMem.setLayoutData(new GridData(GridData.FILL_BOTH));
		grpMem.setText("Memory");
		Group grpIO = new Group(compositeSystem, SWT.NONE);
		grpIO.setText("IO");
		Group grpNetwork = new Group(compositeSystem, SWT.NONE);
		grpNetwork.setText("Network");
		
		Group grpConn = new Group(compositeNginx, SWT.NONE);
		grpConn.setText("Connection");
		Group grpReq = new Group(compositeNginx, SWT.NONE);
		grpReq.setText("Request");
		Group grpHeader = new Group(compositeNginx, SWT.NONE);
		grpHeader.setText("Header");
		Group grpProc = new Group(compositeNginx, SWT.NONE);
		grpProc.setText("Process");
		
		SystemCPUChart cpuChart=new SystemCPUChart(grpCpu,SWT.NONE);
		GridData gridData=new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan=2;
		cpuChart.setLayoutData(gridData);
		new Label(grpCpu,SWT.NONE).setText("Running Tasks:");
		Label runningTasksLab=new Label(grpCpu,SWT.NONE);
		new Label(grpCpu,SWT.NONE).setText("Blocking Tasks:");
		Label blockingTasksLab=new Label(grpCpu,SWT.NONE);
		new Label(grpCpu,SWT.NONE).setText("Context Switch:");
		Label contextSwitchLab=new Label(grpCpu,SWT.NONE);
		new Label(grpCpu,SWT.NONE).setText("Interrupt Count:");
		Label interruptCountLab=new Label(grpCpu,SWT.NONE);
		
		MemoryChart memChart=new MemoryChart(grpMem,SWT.NONE);
		memChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		SwapChart swapChart=new SwapChart(grpMem,SWT.NONE);
		swapChart.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
