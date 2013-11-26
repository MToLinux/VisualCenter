/**
 * 
 */
package org.cs2c.vcenter.actions;

import java.util.ArrayList;
import java.util.List;

import org.cs2c.vcenter.Activator;
import org.cs2c.vcenter.dialog.Importmiddleware;
import org.cs2c.vcenter.dialog.deploydialog;
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
	private IWorkbenchWindow window; 
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
		this.setImageDescriptor(Activator.getImageDescriptor("icons/project_import.png"));
		this.window = window;
	}
	@Override
	public void run(){
		//  get liu qin return list 1.2.3
	    List<String> list = new ArrayList<String>();
	    list.add("Nginx 10.1.50.4");
		// call Import Project Dialog
		// open dialog
		Importmiddleware dialog = new Importmiddleware(window.getShell());
		dialog.init(list);
	    dialog.open();
	    String selectitem = dialog.getServername();
	    if((null == selectitem)||("" == selectitem.trim())){
	    	return;
	    }

		// gethostinfo
		
		// get middleware instance based on user input
		AuthInfo authInfo=MiddlewareFactory.newAuthInfo();
		authInfo.setHost("10.1.50.4");
		authInfo.setUsername("root");
		authInfo.setPassword("cs2csolutions");
		MiddlewareFactory middle;
		try {
			middle = MiddlewareFactory.getInstance(authInfo, "/usr/local/nginx/");
		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		}
		
		IWorkbenchPage page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		MiddlewareView view=(MiddlewareView)page.findView(MiddlewareView.ID);
		view.addProject("Nginx 10.1.50.4", middle);
		view.addProject("Nginx C", middle);
	}
	
	
}
