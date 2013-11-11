/**
 * 
 */
package org.cs2c.vcenter.popup.actions;

import org.cs2c.nginlib.AuthInfo;
import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.ctl.Controller;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Administrator
 *
 */
public class StartAction implements IObjectActionDelegate {
	private Shell shell;
	/**
	 * 
	 */
	public StartAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		// Directly standard selection
		try {
			StartNginx();
		} catch (RemoteException e) {
			try{
				//The nginx is running already.
				if(e.getMessage().equals("The nginx is running already. ")){
					openMessageDialog(e.getMessage());
				}else{
					e.printStackTrace();
				}
			}catch (Exception ex){
				MessageDialog.openInformation(shell, "Exception", ex.getMessage());
				e.printStackTrace();
			}
		}
	}
	
//	private void openDirectoryDialog(){
//		DirectoryDialog dirDialog = new DirectoryDialog(shell);
//		dirDialog.setText("Select your home directory");
//		String selectedDir = dirDialog.open();
//	}
	
	private void openMessageDialog(String mess){
		MessageDialog.openInformation(shell, "Information", mess);
	}
	
	private void StartNginx() throws RemoteException{
		
		AuthInfo authInfo=MiddlewareFactory.newAuthInfo();

		authInfo.setHost("10.1.50.4");
		authInfo.setUsername("root");
		authInfo.setPassword("cs2csolutions");
		
		MiddlewareFactory instance1 = null;
		instance1 = MiddlewareFactory.getInstance(authInfo, "/usr/local/nginx/");

		Controller reccontro=instance1.getController();
		if(reccontro==null)
		{
			throw new RemoteException("reccontro=null");
		}
		else
		{
			reccontro.start();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

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
