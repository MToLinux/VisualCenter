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
	private Shell shell;
	private TreeViewer treeViewer=null;

	/**
	 * 
	 */
	public DeleteAction() {
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
			DeleteServer();
		} catch (RemoteException e) {
			//RemoteException message
			if(null != e.getMessage()){
				MessageDialog.openInformation(shell, "RemoteException", e.getMessage());
			}else{
				e.printStackTrace();
			}
		}catch (Exception ex) {
			MessageDialog.openInformation(shell, "Exception", ex.getMessage());
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
			//TODO
//			System.out.println("blServer:"+blServer.getName()+"："+blServer.toString());	//TODO
//			System.out.println("outerBlockNames:"+outerBlockNames);	//TODO

			orc.delete(blServer, outerBlockNames);
			//server_name aoto show in treeview,do refresh
			this.treeViewer.refresh(this.element.getParent());
		}
	}
	
	private Block getServerBlock(RecConfigurator orc) throws RemoteException{
		String blockName = null;
		String outerBlockNames = "http:0";
		List<Block> list= null;
		
		blockName = this.element.getBlocktype();
		list= orc.getBlocks(blockName, outerBlockNames);
		
		if((list != null)&&(list.size()>0)){
//			System.out.println("outerBlockNames:"+outerBlockNames);	//TODO
			int index = Integer.parseInt(this.element.getBlockIndex());
			Block delBlock = list.get(index);
			return delBlock;
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
		shell = targetPart.getSite().getShell();
//		MiddlewareView meviewer = (MiddlewareView) targetPart.getSite().getWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
		MiddlewareView meviewer = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
		this.treeViewer = meviewer.getTreeViewer();
	}
}
