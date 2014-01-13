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
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
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
public class NewLocationAction implements IObjectActionDelegate {
	private TreeElement element;
	private Shell shell;
	private TreeViewer treeViewer=null;
	
	/**
	 * 
	 */
	public NewLocationAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		try {
			NewLocation();
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
	 *get a new location which its name is user input value
	 * @throws RemoteException
	 */
	private void NewLocation() throws RemoteException {
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
				"New Location Dialog", //dialog title
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
		newBlock.setName("location "+inputDialog.getValue()+" ");
//		System.out.println("inputDialog:"+inputDialog.getValue());
		//add the new block to conf,first do getBlocks and get datastamp
		String viewServer_name = this.element.getName();
//		System.out.println("viewServer_name:"+viewServer_name);

		int nserverindex = GetServerIndex(orc,viewServer_name);
		if(nserverindex > -1){
			blockName = "http:0|server:"+Integer.toString(nserverindex);
//			System.out.println("blockName:"+blockName);
			orc.append(newBlock, blockName);
			//aoto show in treeview,do refresh
			this.treeViewer.refresh(this.element);
		}
	}

	/**
	 * 
	 * @param orc
	 * @param viewServer_name
	 * @return
	 * @throws RemoteException
	 */
	private int GetServerIndex(RecConfigurator orc,String viewServer_name) throws RemoteException {
		String outerBlockNames = "http";
		List<Block> list = orc.getBlocks("server", outerBlockNames);
		for(int i = 0;i<list.size();i++){
//			System.out.println("list:"+list.size());
			List<Directive> listdire = new ArrayList<Directive>();
			listdire = list.get(i).getDirectives();
			for(int j = 0;j<listdire.size(); j++){
				if(listdire.get(j).getName().equals("server_name")){
					RecStringParameter rsp = (RecStringParameter)listdire.get(j).getParameters().get(0);
					if((null != rsp.getValue())&&(viewServer_name.equals(rsp.getValue())));
					return i;
//					System.out.println(i);
				}
			}
		}

		return -1;
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
