/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import org.cs2c.nginlib.AuthInfo;
import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.ctl.Controller;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Administrator
 *
 */
public class RestartAction implements IObjectActionDelegate {
	private Shell shell;
	private TreeElement element;
	/**
	 * 
	 */
	public RestartAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		try {
			RestartNginx();
		} catch (RemoteException e) {
			openMessageDialog(e.getMessage());
			e.printStackTrace();
		}catch (Exception ex) {
			openMessageDialog(ex.getMessage());
			ex.printStackTrace();
		}
	}
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
	private void openMessageDialog(String mess){
		MessageDialog.openInformation(shell, "Exception", mess);
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
		// TODO Auto-generated method stub
		shell = targetPart.getSite().getShell();
	}

}
