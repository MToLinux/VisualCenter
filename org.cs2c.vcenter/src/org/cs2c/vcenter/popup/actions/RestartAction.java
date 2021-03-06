/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.ctl.Controller;
import org.cs2c.vcenter.views.MiddlewareView;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * @author Administrator
 *
 */
public class RestartAction implements IObjectActionDelegate {
	private Shell shell;
	private TreeElement element;
	private MiddlewareView meview = null;

	/**
	 * 
	 */
	public RestartAction() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		try {
			RestartNginx();
			showMessage("The server nginx is already restart.");
		} catch (RemoteException e) {
			openMessageDialog(e.getMessage());
			e.printStackTrace();
		}catch (Exception ex) {
			//RemoteException message
			if(null != ex.getMessage()){
				openMessageDialog(ex.getMessage());
			}else{
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Restart Nginx server by call the function restart() which belong to the Nginxlib's Controller
	 * @throws RemoteException
	 */
	private void RestartNginx() throws RemoteException{
		Controller reccontro=this.element.getMiddlewareFactory().getController();
		if(reccontro==null)
		{
			throw new RemoteException("reccontro=null");
		}
		else
		{
			reccontro.restart();
		}
	}
	/**
	 * open and show MessageDialog
	 * @param mess the message
	 */
	private void openMessageDialog(String mess){
		MessageDialog.openInformation(shell, "RemoteException", mess);
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
		this.meview = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);

	}
	
	private void showMessage(String message) {
		this.meview.showMessage(message);
	}

}
