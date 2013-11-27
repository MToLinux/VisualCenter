/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import java.io.File;
import java.io.IOException;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.nginlib.ctl.Controller;
import org.cs2c.vcenter.dialog.deploydialog;
import org.cs2c.vcenter.dialog.serverdialog;
import org.cs2c.vcenter.views.MiddlewareView;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.trilead.ssh2.SCPClient;

/**
 * @author Administrator
 *
 */
public class DeployAction implements IObjectActionDelegate {
	private TreeElement element;
	private Shell shell;
	private TreeViewer treeViewer=null;
	
	/**
	 * 
	 */
	public DeployAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		try {
			DeployToServer();
			MessageDialog.openInformation(shell, "Information", "Deploy OK");
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void DeployToServer() throws RemoteException, IOException{
		// TODO Auto-generated method stub
		//check server block weather has the root directive
		//if had,set dialog txt and 
		
		//open dialog
		deploydialog dialog = new deploydialog(shell);
	    dialog.open();
	    String LocalFilename = dialog.getLocalFilename();
	    String RemotePath = dialog.getRemotePath();
		
	    if((null == LocalFilename)||
	    	(null == RemotePath)){
	    	return;
	    }
	    
	    WritetoRemote(LocalFilename,RemotePath);
	}
	
	/**
	 * Write the Remote nginx.conf file which is select.
	 * @throws RemoteException 
	 * @throws IOException 
	 * */
	private void WritetoRemote(String LocalFilename,String RemotePath) throws IOException, RemoteException{        
		System.out.println("LocalFilename:"+LocalFilename);

		Controller orc = null;
		orc = this.element.getMiddlewareFactory().getController();
		File zipfile = new File(LocalFilename);
		if (null != zipfile){
//			System.out.println("RemotePath:"+RemotePath);
			orc.deploy(zipfile, RemotePath);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection ss=(IStructuredSelection)selection;
		this.element=(TreeElement)ss.getFirstElement();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		MiddlewareView meviewer = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
		this.treeViewer = meviewer.getTreeViewer();
	}

}
