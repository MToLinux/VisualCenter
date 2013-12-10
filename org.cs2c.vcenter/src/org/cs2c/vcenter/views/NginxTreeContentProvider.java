/**
 * 
 */
package org.cs2c.vcenter.views;

import java.util.*;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.vcenter.views.models.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author Administrator
 *
 */
public class NginxTreeContentProvider implements ITreeContentProvider {
	Shell shell = null;

	/**
	 * 
	 */
	public NginxTreeContentProvider() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		List<TreeElement> projectList=(List<TreeElement>)inputElement;
		return projectList.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		TreeElement pnode=(TreeElement)parentElement;
		List<TreeElement> children = null;
		try {
			children = pnode.getChildren();
		} catch (RemoteException e) {
			e.printStackTrace();
			shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openInformation(shell, "RemoteException", e.getMessage());
		}catch (Exception ex) {
			shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openInformation(shell, "Exception", ex.getMessage());
			ex.printStackTrace();
		}

		if(children==null){
			return null;
		}
		return children.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		TreeElement node=(TreeElement)element;
		return node.getParent();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		TreeElement node=(TreeElement)element;
		try {
			return node.hasChildren();
		} catch (RemoteException e) {
			e.printStackTrace();
			shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openInformation(shell, "RemoteException", e.getMessage());
			return false;
		}catch (Exception ex) {
			shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openInformation(shell, "Exception", ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

}
