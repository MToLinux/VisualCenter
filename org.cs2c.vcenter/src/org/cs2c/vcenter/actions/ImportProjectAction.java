/**
 * 
 */
package org.cs2c.vcenter.actions;

import org.cs2c.vcenter.Activator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
/**
 * @author Administrator
 *
 */
public class ImportProjectAction extends Action {
	static public final String ID="org.cs2c.vcenter.actions.ImportProjectAction";
	/**
	 * 
	 */
	public ImportProjectAction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 */
	public ImportProjectAction(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param image
	 */
	public ImportProjectAction(String text, ImageDescriptor image) {
		super(text, image);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param style
	 */
	public ImportProjectAction(String text, int style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}
	public ImportProjectAction(IWorkbenchWindow window){
		super("Import...");
		this.setId(ImportProjectAction.ID);
		this.setImageDescriptor(Activator.getImageDescriptor("icons/alt_window_16.gif"));
	}
	@Override
	public void run(){
		
	}
}
