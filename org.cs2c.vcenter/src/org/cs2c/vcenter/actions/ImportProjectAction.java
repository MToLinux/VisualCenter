/**
 * 
 */
package org.cs2c.vcenter.actions;

import java.util.List;

import org.cs2c.vcenter.Activator;
import org.cs2c.vcenter.dialog.Importmiddleware;
import org.cs2c.vcenter.metadata.HostInfo;
import org.cs2c.vcenter.metadata.HostManager;
import org.cs2c.vcenter.views.MiddlewareView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
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
	}

	/**
	 * @param text
	 */
	public ImportProjectAction(String text) {
		super(text);
	}

	/**
	 * @param text
	 * @param image
	 */
	public ImportProjectAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	/**
	 * @param text
	 * @param style
	 */
	public ImportProjectAction(String text, int style) {
		super(text, style);
	}
	public ImportProjectAction(IWorkbenchWindow window){
		super("Import...");
		this.setId(ImportProjectAction.ID);
		this.setImageDescriptor(Activator.getImageDescriptor("icons/project_import.png"));
		this.window = window;
	}
	@Override
	public void run(){
		try {
			//  get return list
			HostManager objDOMParser;
				objDOMParser = HostManager.getInstance();
		    List<String> list = objDOMParser.getMainHostInfo();
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
		    HostInfo objHostInfo = objDOMParser.getHostInfo(selectitem);
		    
			// get middleware instance based on user input
			AuthInfo authInfo=MiddlewareFactory.newAuthInfo();
			authInfo.setHost(objHostInfo.getHostName());
			authInfo.setUsername(objHostInfo.getUserName());
			authInfo.setPassword(objHostInfo.getPassWord());
	
			MiddlewareFactory middle;

			middle = MiddlewareFactory.getInstance(authInfo, objHostInfo.getHome());
			IWorkbenchPage page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			MiddlewareView view=(MiddlewareView)page.findView(MiddlewareView.ID);
			view.addProject(selectitem, middle);
		} catch (RemoteException e) {
			e.printStackTrace();
			MessageDialog.openInformation(window.getShell(), "RemoteException", e.getMessage());
			return;
		} catch (Exception e1) {
			MessageDialog.openInformation(window.getShell(), "Exception", e1.getMessage());
			e1.printStackTrace();
		}
	}
}
