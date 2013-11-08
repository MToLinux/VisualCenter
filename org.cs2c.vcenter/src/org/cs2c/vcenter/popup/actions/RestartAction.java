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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Administrator
 *
 */
public class RestartAction implements IObjectActionDelegate {
	private Shell shell;
	/**
	 * 
	 */
	public RestartAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		try {
			RestartNginx();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void RestartNginx() throws RemoteException{
		
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
			reccontro.restart();
		}
	}
	private void openMessageDialog(String mess){
		MessageDialog.openInformation(shell, "Information", mess);
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
