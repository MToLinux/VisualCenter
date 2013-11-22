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
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
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
public class NewUpstreamAction implements IObjectActionDelegate {
	private TreeElement element;
	private Shell shell;
	private TreeViewer treeViewer=null;
	
	/**
	 * 
	 */
	public NewUpstreamAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		try {
			NewUpstream();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void NewUpstream() throws RemoteException {
		String blockName = null;
		RecConfigurator orc = null;

		IInputValidator validator = new IInputValidator() {
			public String isValid(String text) { //return an error message,
				if(text.length() < 1){ //or null for no error
					return "You must enter at least 1 characters";
				}
				else{
					return null;
				}
			}
		};
		//show dialog
		InputDialog inputDialog = new InputDialog( this.shell,
				"Please input a String", //dialog title
				"Enter a String:", //dialog prompt
				"", //default text
				validator ); //validator to use
		inputDialog.open();
		
		//new a block
		//check
		if((null ==inputDialog.getValue())||("" ==inputDialog.getValue().trim())){
			return;
		}
		orc = (RecConfigurator) this.element.getMiddlewareFactory().getConfigurator();
		Block newBlock = orc.newBlock();
		newBlock.setName("upstream "+inputDialog.getValue()+" ");
//		System.out.println("inputDialog:"+inputDialog.getValue());
		List<Block> list = orc.getBlocks("http", "");
		blockName = "http:0";
		if(list.size()>0){
			orc.append(newBlock, blockName);
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
		shell = targetPart.getSite().getShell();
		String ID = "org.cs2c.vcenter.views.MiddlewareView";
		MiddlewareView meviewer = (MiddlewareView) targetPart.getSite().getWorkbenchWindow().getActivePage().findView(ID);
		this.treeViewer = meviewer.getTreeViewer();
	}

}
