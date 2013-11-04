/**
 * 
 */
package org.cs2c.vcenter.actions;

import org.cs2c.vcenter.Activator;
import org.cs2c.vcenter.views.MiddlewareView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.cs2c.nginlib.*;
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
		// call ImportProject Dialog
		// get middleware instance based on user input
		MiddlewareFactory middle=null;
		IWorkbenchPage page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		MiddlewareView view=(MiddlewareView)page.findView(MiddlewareView.ID);
		view.addProject("Nginx B", middle);
		view.addProject("Nginx C", middle);
	}
}
