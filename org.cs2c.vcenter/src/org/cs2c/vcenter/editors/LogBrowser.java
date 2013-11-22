/**
 * 
 */
package org.cs2c.vcenter.editors;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.log.Logger;
import org.cs2c.vcenter.views.models.LogInstanceElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;

/**
 * @author Administrator
 *
 */
public class LogBrowser extends EditorPart {
	private Text text;
	static public final String ID="org.cs2c.vcenter.editors.LogBrowser";
	private LogInstanceElement input;
	/**
	 * 
	 */
	public LogBrowser() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());
		this.input=(LogInstanceElement)input;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		this.text = new Text(parent, SWT.BORDER);
		this.text.setEditable(false);
		Logger logger=this.input.getMiddlewareFactory().getLogger();
		try {
			String content=logger.getLogContent(this.input.getLog().getName());
			this.text.setText(content);
		} catch (RemoteException e) {
			this.getEditorSite().getActionBars().getStatusLineManager().setMessage(e.getMessage());
			MessageDialog.openError(this.getSite().getShell(), "Remote Error", e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		this.text.setFocus();
	}

}
