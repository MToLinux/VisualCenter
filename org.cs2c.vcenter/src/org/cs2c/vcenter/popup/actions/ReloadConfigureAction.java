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
public class ReloadConfigureAction implements IObjectActionDelegate {
	private TreeElement element;
	private Shell shell;
	private MiddlewareView meview = null;

	/**
	 * 
	 */
	public ReloadConfigureAction() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		try {
			ReloadConf();
			showMessage("The server nginx configure is already Reloaded.");

		}catch (Exception e) {
			openMessageDialog(e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * open and show MessageDialog
	 * @param mess the message
	 */
	private void openMessageDialog(String mess){
		MessageDialog.openInformation(shell, "Exception", mess);
	}
	/**
	 * Reload the Nginx configure file to make it take effect.
	 * this function call the function restart() which belong to the Nginxlib's Controller
	 * @throws RemoteException
	 */
	private void ReloadConf() throws RemoteException{
		Controller reccontro=this.element.getMiddlewareFactory().getController();
		if(reccontro==null)
		{
			throw new RemoteException("reccontro=null");
		}
		else
		{
			reccontro.reload();
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
		this.meview = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
	}
	/**
	 * open and show MessageDialog
	 * @param mess the message
	 */
	private void showMessage(String message) {
		this.meview.showMessage(message);
	}

}
