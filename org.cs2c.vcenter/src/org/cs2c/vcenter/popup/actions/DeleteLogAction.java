/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.log.Logger;
import org.cs2c.vcenter.views.models.LogInstanceElement;
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

/**
 * @author Administrator
 *
 */
public class DeleteLogAction implements IObjectActionDelegate {
	private TreeElement element;
//	private Shell shell;
	private TreeViewer treeViewer=null;
	/**
	 * 
	 */
	public DeleteLogAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		Shell shell=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		boolean ok=MessageDialog.openConfirm(shell, "Delete Confirm", "Do you really delete it from remote file system?");
		if(ok){
			Logger logger=this.element.getMiddlewareFactory().getLogger();
			try {
				logger.delete(((LogInstanceElement)this.element).getLog().getName());
			} catch (RemoteException e) {
				MessageDialog.openError(shell, "Delete Error", e.getMessage());
			}
		}
		//aoto show in treeview,do refresh
		this.treeViewer.refresh();
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
		MiddlewareView meviewer = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
		this.treeViewer = meviewer.getTreeViewer();
	}

}
