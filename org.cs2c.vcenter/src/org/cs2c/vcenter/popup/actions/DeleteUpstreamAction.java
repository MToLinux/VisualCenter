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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
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
			DeleteUpstream();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void DeleteUpstream() throws RemoteException {
		//delete a Upstream block
		String outerBlockNames = "http:0";
		RecConfigurator orc = null;
		orc = (RecConfigurator) this.element.getMiddlewareFactory().getConfigurator();
		
		Block blServer = getUpstreamBlock(orc);
		
		if(blServer!=null){
			orc.delete(blServer, outerBlockNames);
			this.treeViewer.refresh();
		}
	}
	
	private Block getUpstreamBlock(RecConfigurator orc) throws RemoteException{
		String blockName = null;
		String outerBlockNames = "http:0";
		List<Block> list= null;
		
		blockName = "upstream "+this.element.getName();
		list= orc.getBlocks(blockName, outerBlockNames);
		if((list != null)&&(list.size()>0)){
			Block tembl = list.get(0);
			return tembl;
		}
		
		return null;
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
