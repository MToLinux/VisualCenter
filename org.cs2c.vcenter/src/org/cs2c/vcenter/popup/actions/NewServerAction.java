/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import java.util.ArrayList;
import java.util.List;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.nginlib.config.RecDirective;
import org.cs2c.nginlib.config.RecStringParameter;
import org.cs2c.nginlib.ctl.Controller;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
	/**
	 * 
	 */
	public NewServerAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		try {
			NewServer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void NewServer() throws RemoteException{
		String sernameval = "newservername";
		String listenval = "80";		
		
		//new a server block
		String blockName = null;
		String outerBlockNames = "";
		RecConfigurator orc = null;

		orc = (RecConfigurator) this.element.getMiddlewareFactory().getConfigurator();
		
		Block newBlock = orc.newBlock();
		newBlock.setName("server");
		//make Directive : server_name
			RecDirective rdserver_name = new RecDirective();
			rdserver_name.setName("server_name");
				RecStringParameter param1 = new RecStringParameter();
				param1.setValue(sernameval);
			rdserver_name.addParameter(param1);
		newBlock.addDirective(rdserver_name);
		//make Directive : listen
			RecDirective rd = new RecDirective();
			rd.setName("listen");
				RecStringParameter param = new RecStringParameter();
				param.setValue(listenval);
			rd.addParameter(param);
		newBlock.addDirective(rd);
		
		//add the new server to conf,first do getBlocks and get datastamp
		blockName = "http";
		List<Block> list= orc.getBlocks(blockName, outerBlockNames);
		if(list.size()>0){
			orc.append(newBlock, blockName);
		}

		//TODO server_name aoto show in treeview? refresh
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
