/**
 * 
 */
package org.cs2c.vcenter.actions;

import org.cs2c.vcenter.Activator;
import org.cs2c.vcenter.dialog.HostsManagerDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
/**
 * @author Administrator
 *
 */
public class HostManagerAction extends Action {
	static public final String ID="org.cs2c.vcenter.actions.HostManagerAction";
	/**
	 * 
	 */
	private IWorkbenchWindow window;//abliuqin
	
	public HostManagerAction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 */
	public HostManagerAction(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param image
	 */
	public HostManagerAction(String text, ImageDescriptor image) {
		super(text, image);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param style
	 */
	public HostManagerAction(String text, int style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}
	public HostManagerAction(IWorkbenchWindow window){
		super("Hosts...");
		this.window=window;
		this.setId(HostManagerAction.ID);
		this.setImageDescriptor(Activator.getImageDescriptor("icons/hosts.png"));
	}
	@Override
	public void run(){
		HostsManagerDialog dg  =   new  HostsManagerDialog(window.getShell());
		dg.open();
	}
}
