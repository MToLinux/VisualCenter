/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import java.util.ArrayList;
import java.util.List;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.nginlib.config.RecStringParameter;
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
public class DeleteUpstreamAction implements IObjectActionDelegate {
	private TreeElement element;
	private TreeViewer treeViewer=null;
	private Shell shell;

	/**
	 * 
	 */
	public DeleteUpstreamAction() {
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
			DeleteUpstream();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void DeleteUpstream() throws RemoteException {
		//delete a Upstream block
		String outerBlockNames = null;
		String locationName = null;

		RecConfigurator orc = null;
		orc = (RecConfigurator) this.element.getMiddlewareFactory().getConfigurator();
		
		outerBlockNames = this.element.getOuterBlockNames();
		locationName = this.element.getBlocktype();
		List<Block> listlo = orc.getBlocks(locationName , outerBlockNames);
		
		if((listlo != null)&&(listlo.size()>0)){
//			System.out.println("outerBlockNames:"+outerBlockNames);	//TODO
			int index = Integer.parseInt(this.element.getBlockIndex());
			Block delBlock = listlo.get(index);
			orc.delete(delBlock, outerBlockNames);
			//aoto show in treeview,do refresh
			this.treeViewer.refresh();
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
		MiddlewareView meviewer = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
		this.treeViewer = meviewer.getTreeViewer();
		shell = targetPart.getSite().getShell();

	}

}
