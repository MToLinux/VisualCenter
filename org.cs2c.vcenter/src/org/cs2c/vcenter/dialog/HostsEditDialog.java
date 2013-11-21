package org.cs2c.vcenter.dialog;

import org.cs2c.vcenter.metadata.DOMParser;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

public class HostsEditDialog extends Dialog {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private int flag=0;
	private String[] selectItem=new String[]{};
	private DOMParser hostData=new DOMParser();
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public HostsEditDialog(Shell parentShell) {
		super(parentShell);
	}
	public HostsEditDialog(Shell parentShell,String[] para,DOMParser domPara) {
		super(parentShell);
		selectItem=para;
		hostData=domPara;
		
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		
		if(!selectItem.equals(""))
		{
			
		}
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;
		new Label(container, SWT.NONE);
		
		Group grpHostinfo = new Group(container, SWT.NONE);
		grpHostinfo.setText("HostInfo");
		GridData gd_grpHostinfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpHostinfo.heightHint = 107;
		gd_grpHostinfo.widthHint = 339;
		grpHostinfo.setLayoutData(gd_grpHostinfo);
		
		Label lblNewLabel = new Label(grpHostinfo, SWT.NONE);
		lblNewLabel.setBounds(10, 27, 61, 17);
		lblNewLabel.setText("*Host:");
		
		Label lblNewLabel_1 = new Label(grpHostinfo, SWT.NONE);
		lblNewLabel_1.setBounds(10, 56, 61, 17);
		lblNewLabel_1.setText("*Username:");
		
		Label lblNewLabel_2 = new Label(grpHostinfo, SWT.NONE);
		lblNewLabel_2.setBounds(10, 88, 61, 17);
		lblNewLabel_2.setText("*Password:");
		
		text = new Text(grpHostinfo, SWT.BORDER);
		text.setBounds(115, 27, 220, 23);
		
		if(flag==1)
			text.setText("111");
		else if(flag==2)
			text.setText("112");
		text_1 = new Text(grpHostinfo, SWT.BORDER);
		text_1.setBounds(115, 56, 220, 23);
		
		text_2 = new Text(grpHostinfo, SWT.BORDER);
		text_2.setBounds(115, 88, 220, 23);
		new Label(container, SWT.NONE);
		
		Group grpMiddlewareinfo = new Group(container, SWT.NONE);
		grpMiddlewareinfo.setText("Middleware Info");
		GridData gd_grpMiddlewareinfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpMiddlewareinfo.widthHint = 340;
		gd_grpMiddlewareinfo.heightHint = 91;
		grpMiddlewareinfo.setLayoutData(gd_grpMiddlewareinfo);
		
		Label lblmiddleware = new Label(grpMiddlewareinfo, SWT.NONE);
		lblmiddleware.setText("*Middleware:");
		lblmiddleware.setBounds(10, 31, 92, 17);
		
		Label lblhome = new Label(grpMiddlewareinfo, SWT.NONE);
		lblhome.setText("*Home:");
		lblhome.setBounds(10, 70, 92, 17);
		
		text_4 = new Text(grpMiddlewareinfo, SWT.BORDER);
		text_4.setBounds(108, 28, 220, 23);
		
		text_5 = new Text(grpMiddlewareinfo, SWT.BORDER);
		text_5.setBounds(108, 67, 220, 23);
		new Label(container, SWT.NONE);
		
		Group grpMiddlewareManagerInfo = new Group(container, SWT.NONE);
		GridData gd_grpMiddlewareManagerInfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpMiddlewareManagerInfo.widthHint = 348;
		grpMiddlewareManagerInfo.setLayoutData(gd_grpMiddlewareManagerInfo);
		grpMiddlewareManagerInfo.setText("Middleware Manager Info");
		
		Label lblStatusPath = new Label(grpMiddlewareManagerInfo, SWT.NONE);
		lblStatusPath.setText("Status Path:");
		lblStatusPath.setBounds(10, 27, 99, 17);
		
		Label lblUsername = new Label(grpMiddlewareManagerInfo, SWT.NONE);
		lblUsername.setText("Username:");
		lblUsername.setBounds(10, 56, 99, 17);
		
		Label lblPassword = new Label(grpMiddlewareManagerInfo, SWT.NONE);
		lblPassword.setText("Password:");
		lblPassword.setBounds(10, 88, 99, 17);
		
		text_6 = new Text(grpMiddlewareManagerInfo, SWT.BORDER);
		text_6.setBounds(115, 27, 220, 23);
		
		text_7 = new Text(grpMiddlewareManagerInfo, SWT.BORDER);
		text_7.setBounds(115, 56, 220, 23);
		
		text_8 = new Text(grpMiddlewareManagerInfo, SWT.BORDER);
		text_8.setBounds(115, 88, 220, 23);

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(515, 513);
	}
	
	
	
}
