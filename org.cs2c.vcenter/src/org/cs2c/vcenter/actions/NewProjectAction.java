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
public class NewProjectAction extends Action {
	static public final String ID="org.cs2c.vcenter.actions.NewProjectAction";
	/**
	 * 
	 */
	public NewProjectAction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 */
	public NewProjectAction(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param image
	 */
	public NewProjectAction(String text, ImageDescriptor image) {
		super(text, image);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param style
	 */
	public NewProjectAction(String text, int style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}
	public NewProjectAction(IWorkbenchWindow window){
		super("New Mid-ware...");
		this.setId(NewProjectAction.ID);
		this.setImageDescriptor(Activator.getImageDescriptor("icons/project_add.png"));
	}
	@Override
	public void run(){
		
	}
}
