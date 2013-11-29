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
public class DeleteLocationAction implements IObjectActionDelegate {
	private TreeElement element;
//	private Shell shell;
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
			DeleteLocation();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
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
			this.treeViewer.refresh();
		}

		
		
		
		
		
		
		
		
		
		
		//
//		String viewServer_name = this.element.getParent().getName();
//		int nserverindex = GetServerIndex(orc,viewServer_name);
//		if(nserverindex > -1){
//			outerBlockNames = "http:0|server:"+Integer.toString(nserverindex);
//			String locationName = "location "+this.element.getName();
////			System.out.println("locationName:"+locationName);
//			List<Block> listlo = orc.getBlocks(locationName , outerBlockNames);
//			if((listlo != null)&&(listlo.size()>0)){
////				System.out.println("listlo.size():"+listlo.size());
//				Block delBlock = listlo.get(0);
//				orc.delete(delBlock, outerBlockNames);
//				//aoto show in treeview,do refresh
//				this.treeViewer.refresh();
//			}
//		}
	}

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
//		shell = targetPart.getSite().getShell();
		MiddlewareView meviewer = (MiddlewareView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MiddlewareView.ID);
		this.treeViewer = meviewer.getTreeViewer();
	}

}
