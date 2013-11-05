/**
 * 
 */
package org.cs2c.vcenter.editors;

/**
 * @author Administrator
 *
 */
public class MonitorFaceUpdater implements Runnable {
	private MonitorFace monitor;
	private boolean stopped=false;
	/**
	 * 
	 */
	public MonitorFaceUpdater(MonitorFace monitor) {
		this.monitor=monitor;
	}
	public void stop(){
		this.stopped=true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.monitor.update();
	}

}
