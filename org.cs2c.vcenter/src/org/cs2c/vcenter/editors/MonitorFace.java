/**
 * 
 */
package org.cs2c.vcenter.editors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.monitor.CPUStatus;
import org.cs2c.nginlib.monitor.Device;
import org.cs2c.nginlib.monitor.IOStatus;
import org.cs2c.nginlib.monitor.MemoryStatus;
import org.cs2c.nginlib.monitor.Monitor;
import org.cs2c.nginlib.monitor.NetworkStatus;
import org.cs2c.nginlib.monitor.NginxStatus;
import org.cs2c.nginlib.monitor.RecProcessStatus;
import org.cs2c.vcenter.chart.IOChart;
import org.cs2c.vcenter.chart.MemoryChart;
import org.cs2c.vcenter.chart.ProcessChart;
import org.cs2c.vcenter.chart.SwapChart;
import org.cs2c.vcenter.chart.SystemCPUChart;
import org.cs2c.vcenter.views.models.ProjectElement;
import org.cs2c.vcenter.views.models.TreeElement;
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
	private MonitorFaceUpdater updater=new MonitorFaceUpdater(this);
	private TreeElement input;
	private SystemCPUChart cpuChart;
	private Label runningTasksLab;
	private Label blockingTasksLab;
	private Label contextSwitchLab;
	private Label interruptCountLab;
	private MemoryChart memChart;
	private SwapChart swapChart;
	private IOChart readChart;
	private IOChart writeChart;
	private IOChart networkChart;
	private IOChart connChart;
	private IOChart reqChart;
	private IOChart headerChart;
	private ProcessChart procCpuChart;
	private ProcessChart procMemChart;
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
		this.input=(TreeElement)input;
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
		grpIO.setLayout(new GridLayout(1,false));
		grpIO.setLayoutData(new GridData(GridData.FILL_BOTH));
		grpIO.setText("IO");
		Group grpNetwork = new Group(compositeSystem, SWT.NONE);
		grpNetwork.setLayoutData(new GridData(GridData.FILL_BOTH));
		grpNetwork.setLayout(new FillLayout());
		grpNetwork.setText("Network");
		
		Group grpConn = new Group(compositeNginx, SWT.NONE);
		grpConn.setLayoutData(new GridData(GridData.FILL_BOTH));
		grpConn.setLayout(new FillLayout());
		grpConn.setText("Connection");
		Group grpReq = new Group(compositeNginx, SWT.NONE);
		grpReq.setLayoutData(new GridData(GridData.FILL_BOTH));
		grpReq.setLayout(new FillLayout());
		grpReq.setText("Request");
		Group grpHeader = new Group(compositeNginx, SWT.NONE);
		grpHeader.setLayoutData(new GridData(GridData.FILL_BOTH));
		grpHeader.setLayout(new FillLayout());
		grpHeader.setText("Header");
		Group grpProc = new Group(compositeNginx, SWT.NONE);
		grpProc.setLayoutData(new GridData(GridData.FILL_BOTH));
		grpProc.setLayout(new GridLayout(1,false));
		grpProc.setText("Process");
		
		this.cpuChart=new SystemCPUChart(grpCpu,SWT.NONE);
		GridData gridData=new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan=2;
		cpuChart.setLayoutData(gridData);
		new Label(grpCpu,SWT.NONE).setText("Running Tasks:");
		this.runningTasksLab=new Label(grpCpu,SWT.NONE);
		new Label(grpCpu,SWT.NONE).setText("Blocking Tasks:");
		this.blockingTasksLab=new Label(grpCpu,SWT.NONE);
		new Label(grpCpu,SWT.NONE).setText("Context Switch:");
		this.contextSwitchLab=new Label(grpCpu,SWT.NONE);
		new Label(grpCpu,SWT.NONE).setText("Interrupt Count:");
		this.interruptCountLab=new Label(grpCpu,SWT.NONE);
		
		this.memChart=new MemoryChart(grpMem,SWT.NONE);
		memChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.swapChart=new SwapChart(grpMem,SWT.NONE);
		swapChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		this.readChart=new IOChart(grpIO,SWT.NONE,"IO Reading");
		this.writeChart=new IOChart(grpIO,SWT.NONE,"IO Writing");
		readChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		writeChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		this.networkChart=new IOChart(grpNetwork,SWT.NONE,"Network in and out");
		
		this.connChart=new IOChart(grpConn,SWT.NONE,"Nginx Connections");
		
		this.reqChart=new IOChart(grpReq,SWT.NONE,"Requests");
		
		this.headerChart=new IOChart(grpHeader,SWT.NONE,"Header reading and writing");
		
		this.procCpuChart=new ProcessChart(grpProc,SWT.NONE,"CPU Usage");
		procCpuChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.procMemChart=new ProcessChart(grpProc,SWT.NONE,"Memory Usage");
		procMemChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Thread thread=new Thread(this.updater);
		thread.start();
	}
	public void update(){
/*		Monitor monitor=this.input.getMiddlewareFactory().getMonitor();
		ProjectElement projectElement=(ProjectElement)this.input;
		try {
			CPUStatus cpuStatus=monitor.getCPUStatus();
			MemoryStatus memStatus=monitor.getMemoryStatus();
			IOStatus ioStatus=monitor.getIOStatus();
			NetworkStatus networkStatus=monitor.getNetworkStatus();
			this.cpuChart.setValue((cpuStatus.getSystemPercent()+cpuStatus.getUserPercent())*100);
			this.runningTasksLab.setText(cpuStatus.getRunningNum()+"");
			this.blockingTasksLab.setText(cpuStatus.getBlockingNum()+"");
			this.contextSwitchLab.setText(cpuStatus.getContextSwitchNum()+"");
			this.interruptCountLab.setText(cpuStatus.getInterruptNum()+"");
			this.memChart.setValue(memStatus.getUsed(), memStatus.getBuffers(), memStatus.getCached(), memStatus.getFree());
			//TODO lack total swap.
			Map<String,Number> datasetRead=new HashMap<String,Number>();
			Map<String,Number> datasetWrite=new HashMap<String,Number>();
			List<Device> devices=ioStatus.getDevices();
			for(Device device: devices){
				datasetRead.put(device.getName(), device.getBlockReadPerSec());
				datasetWrite.put(device.getName(), device.getBlockWritenPerSec());
			}
			this.readChart.addDataset(datasetRead);
			this.writeChart.addDataset(datasetWrite);
			Map<String,Number> datasetNetwork=new HashMap<String,Number>();
			datasetNetwork.put("Input", networkStatus.getInputKbPerSec());
			datasetNetwork.put("Output", networkStatus.getOutputPerSec());
			this.networkChart.addDataset(datasetNetwork);
			
			NginxStatus nginxStatus=monitor.getNginxStatus(projectElement.getStatusPath(), projectElement.getManagerUsername(), projectElement.getManagerPassword());
			Map<String,Number> datasetConn=new HashMap<String,Number>();
			datasetConn.put("Active", nginxStatus.getActiveConnections());
			datasetConn.put("KeepAlive", nginxStatus.getKeepAliveConnections());
			this.connChart.addDataset(datasetConn);
			Map<String,Number> datasetReq=new HashMap<String,Number>();
			datasetReq.put("Accepted", nginxStatus.getServerAccepts());
			datasetReq.put("Handled", nginxStatus.getServerHandled());
			datasetReq.put("Requests", nginxStatus.getServerRequests());
			this.reqChart.addDataset(datasetReq);
			Map<String,Number> datasetHeader=new HashMap<String,Number>();
			datasetHeader.put("Readings", nginxStatus.getNginxReading());
			datasetHeader.put("Writing", nginxStatus.getNginxWriting());
			this.headerChart.addDataset(datasetHeader);
			Map<String,Number> datasetProcCpu=new HashMap<String,Number>();
			Map<String,Number> datasetProcMem=new HashMap<String,Number>();
			List<RecProcessStatus> psList=nginxStatus.getNginxPSList();
			for(RecProcessStatus process: psList){
				datasetProcCpu.put(process.getProcessID()+"", process.getProcessCPU());
				datasetProcMem.put(process.getProcessID()+"", process.getProcessMem());
			}
			this.procCpuChart.setDataset(datasetProcCpu);
			this.procMemChart.setDataset(datasetProcMem);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		*/
		this.cpuChart.setValue(30);
		this.runningTasksLab.setText("1");
		this.runningTasksLab.pack(true);
		this.blockingTasksLab.setText("2");
		this.contextSwitchLab.setText("3");
		this.interruptCountLab.setText("4");
	}
	@Override
	public void dispose(){
		super.dispose();
		this.updater.stop();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
