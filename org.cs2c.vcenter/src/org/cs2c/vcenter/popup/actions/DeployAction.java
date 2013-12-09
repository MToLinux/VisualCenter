/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.RecBlock;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.nginlib.config.StringParameter;
import org.cs2c.nginlib.ctl.Controller;
import org.cs2c.vcenter.dialog.deploydialog;
//import org.cs2c.vcenter.views.MiddlewareView;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
//import org.eclipse.ui.PlatformUI;

/**
 * @author Administrator
 *
 */
public class DeployAction implements IObjectActionDelegate {
	private TreeElement element;
	private Shell shell;
//	private TreeViewer treeViewer=null;
//	private MiddlewareView meview = null;

	
	/**
	 * 
	 */
	public DeployAction() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		try {
			DeployToServer();
		} catch (RemoteException e) {
			//RemoteException message
			if(null != e.getMessage()){
				MessageDialog.openInformation(shell, "RemoteException", e.getMessage());
				e.printStackTrace();
			}else{
				e.printStackTrace();
			}
		}catch (Exception ex) {
			MessageDialog.openInformation(shell, "Exception", ex.getMessage());
			ex.printStackTrace();
		}
	}
//	private void showMessage(String message) {
//		this.meview.showMessage(message);
//	}

	private void DeployToServer() throws RemoteException, IOException{
		String rootvalue = null;
//		Directive oldDirective = null;
		//check server block weather has the root directive
		RecConfigurator orc = null;
		orc = (RecConfigurator) this.element.getMiddlewareFactory().getConfigurator();
		String outerBlockNames = this.element.getOuterBlockNames();
		List<Block> list = orc.getBlocks(this.element.getBlocktype(), outerBlockNames);
		int index = Integer.parseInt(this.element.getBlockIndex());
		
		Block serverBlock = list.get(index);
		List<Directive> listdire = serverBlock.getDirectives();
		for(int j = 0;j<listdire.size(); j++){
			if(listdire.get(j).getName().equals("root")){
//				oldDirective = listdire.get(j);
				rootvalue = listdire.get(j).getParameters().get(0).toString();
				break;
			}
		}
		
		//open dialog
		deploydialog dialog = new deploydialog(shell);
		dialog.init(rootvalue);
	    dialog.open();
	    String LocalFilename = dialog.getLocalFilename();
	    String RemotePath = dialog.getRemotePath();
		
	    if((null == LocalFilename)||
	    	(null == RemotePath)){
	    	return;
	    }
	    
	    WritetoRemote(LocalFilename,RemotePath);
	    
	    //update root info to Nginxconf's Server
	    if(rootvalue == null){
			RecBlock newElement = new RecBlock();
			newElement.setName(serverBlock.getName());
			newElement.SetBlockText(serverBlock.toString());
			Directive rdroot = orc.newDirective();
			rdroot.setName("root");
			StringParameter param1 = orc.newStringParameter();
			param1.setValue(RemotePath);
			rdroot.addParameter(param1);
			newElement.addDirective(rdroot);
			orc.replace(serverBlock, newElement, outerBlockNames);
	    }
//	    else{
//	    	outerBlockNames = this.element.getOuterBlockNames()+"|"+
//	    			this.element.getBlocktype()+":"+this.element.getBlockIndex();
//	    	
//	    	RecDirective newDirective = new RecDirective();
//	    	newDirective.setName(oldDirective.getName());
//	    	newDirective.SetDirectiveText(oldDirective.toString());
//	    	newDirective.deleteParameter(newDirective.getParameters().get(0));
//			StringParameter param1 = orc.newStringParameter();
//			param1.setValue(RemotePath);
//			newDirective.addParameter(param1);
//			
//	    	//replace Directives
//	    	orc.replace(oldDirective, newDirective, outerBlockNames);
//	    }
	}
	
	/**
	 * Write the Remote nginx.conf file which is select.
	 * @throws RemoteException 
	 * @throws IOException 
	 * */
	private void WritetoRemote(String LocalFilename,String RemotePath) throws IOException, RemoteException{        
//		System.out.println("LocalFilename:"+LocalFilename);

		Controller orc = null;
		orc = this.element.getMiddlewareFactory().getController();
		File zipfile = new File(LocalFilename);
		if (null != zipfile){
//			System.out.println("RemotePath:"+RemotePath);
			orc.deploy(zipfile, RemotePath);
			// show Information
			MessageDialog.openInformation(shell, "Information", "Deploy OK");
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
//		MiddlewareView meviewer = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
//		this.treeViewer = meviewer.getTreeViewer();
//		this.meview = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
	}

}
