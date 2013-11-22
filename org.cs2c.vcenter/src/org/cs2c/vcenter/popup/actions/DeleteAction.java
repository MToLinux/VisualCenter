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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * @author Administrator
 *
 */
public class DeleteAction implements IObjectActionDelegate {
	private TreeElement element;
//	private Shell shell;
	private TreeViewer treeViewer=null;

	/**
	 * 
	 */
	public DeleteAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		try {
			DeleteServer();
			//server_name aoto show in treeview,do refresh
			this.treeViewer.refresh();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void DeleteServer() throws RemoteException {
		//delete a server block
		String outerBlockNames = "http:0";
		RecConfigurator orc = null;
		orc = (RecConfigurator) this.element.getMiddlewareFactory().getConfigurator();
		
		Block blServer = getServerBlock(orc);
		
		if(blServer!=null){
			orc.delete(blServer, outerBlockNames);
		}
	}
	
	private Block getServerBlock(RecConfigurator orc) throws RemoteException{
		String blockName = null;
		String outerBlockNames = "http:0";
		List<Block> list= null;
		String diServer_name = this.element.getName();
		
		blockName = "server";
		list= orc.getBlocks(blockName, outerBlockNames);
		
		for(int i = 0;i<list.size();i++){
			List<Directive> listdire = new ArrayList<Directive>();
			Block tembl = list.get(i);
			listdire = tembl.getDirectives();
			for(int j = 0;j<listdire.size(); j++){
				if(listdire.get(j).getName().equals("server_name")){
					RecStringParameter rsp = (RecStringParameter)listdire.get(j).getParameters().get(0);
					if(diServer_name.equals(rsp.getValue())){
						return tembl;
					}
				}
			}
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
//		shell = targetPart.getSite().getShell();
//		MiddlewareView meviewer = (MiddlewareView) targetPart.getSite().getWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
		MiddlewareView meviewer = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
		this.treeViewer = meviewer.getTreeViewer();
	}
}
