/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import java.util.List;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.nginlib.config.StringParameter;
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

/**
 * @author Administrator
 *
 */
public class NewServerAction implements IObjectActionDelegate {
	private TreeElement element;
	private Shell shell;
	private TreeViewer treeViewer=null;
	
	/**
	 * 
	 */
	public NewServerAction() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		try {
			NewServer();
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
	
	/**
	 * New a server block's main process which information is input by user
	 * @throws RemoteException
	 */
	private void NewServer() throws RemoteException{
		//open dialog
		serverdialog dialog = new serverdialog(shell);
	    dialog.open();
	    String sernameval = dialog.getServername();
	    String listenval = dialog.getListenParam();
		
	    if((null == sernameval)||
	    	(null == listenval)){
	    	return;
	    }
	    
		//new a server block
		String blockName = null;
		String outerBlockNames = "";
		RecConfigurator orc = null;

		orc = (RecConfigurator) this.element.getMiddlewareFactory().getConfigurator();
		
		Block newBlock = orc.newBlock();
		newBlock.setName("server");
		//make Directive : server_name
			Directive rdserver_name = orc.newDirective();
			rdserver_name.setName("server_name");
				StringParameter param1 = orc.newStringParameter();
				param1.setValue(sernameval);
			rdserver_name.addParameter(param1);
		newBlock.addDirective(rdserver_name);
		//make Directive : listen
			Directive rd = orc.newDirective();
			rd.setName("listen");
				StringParameter param = orc.newStringParameter();
				param.setValue(listenval);
			rd.addParameter(param);
		newBlock.addDirective(rd);
		
		//add the new server to conf,first do getBlocks and get datastamp
		blockName = "http";
		List<Block> list= orc.getBlocks(blockName, outerBlockNames);
		if(list.size()>0){
			orc.append(newBlock, blockName);
			
			//server_name aoto show in treeview,do refresh
			this.treeViewer.refresh(this.element);
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
		String ID = "org.cs2c.vcenter.views.MiddlewareView";
		MiddlewareView meviewer = (MiddlewareView) targetPart.getSite().getWorkbenchWindow().getActivePage().findView(ID);
		this.treeViewer = meviewer.getTreeViewer();
	}
}
