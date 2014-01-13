/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import java.util.List;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.vcenter.views.MiddlewareView;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Administrator
 *
 */
public class DeleteLocationAction implements IObjectActionDelegate {
	private TreeElement element;
	private Shell shell;
	private TreeViewer treeViewer=null;
	/**
	 * 
	 */
	public DeleteLocationAction() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		try {
			// make sure
			boolean retValue = MessageDialog.openQuestion(
					this.shell, "Warn", "do you really want to delete?");
			if(!retValue){
				return;
			}
			
			DeleteLocation();
		} catch (RemoteException e) {
			//RemoteException message
			if(null != e.getMessage()){
				MessageDialog.openInformation(shell, "RemoteException", e.getMessage());
			}else{
				e.printStackTrace();
			}

		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Delete target Location node and block
	 * @throws RemoteException
	 */
	private void DeleteLocation() throws RemoteException {
		String outerBlockNames = null;
		String locationName = null;

		RecConfigurator orc = null;
		orc = (RecConfigurator) this.element.getMiddlewareFactory().getConfigurator();
		
		outerBlockNames = this.element.getOuterBlockNames();
		locationName = this.element.getBlocktype();
		List<Block> listlo = orc.getBlocks(locationName , outerBlockNames);
		if((listlo != null)&&(listlo.size()>0)){
//			System.out.println("listlo.size():"+listlo.size());
			int index = Integer.parseInt(this.element.getBlockIndex());
			Block delBlock = listlo.get(index);
			orc.delete(delBlock, outerBlockNames);
			//aoto show in treeview,do refresh
			this.treeViewer.refresh(this.element.getParent());
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
