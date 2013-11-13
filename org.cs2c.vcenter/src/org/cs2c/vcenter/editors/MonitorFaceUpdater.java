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
		while(!this.stopped){
			this.monitor.getSite().getShell().getDisplay().syncExec(new Runnable(){
				@Override
				public void run(){
					monitor.update();
				}
			});
			try {
				Thread.yield();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
