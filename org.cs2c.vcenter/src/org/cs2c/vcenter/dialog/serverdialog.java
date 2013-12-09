package org.cs2c.vcenter.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class serverdialog extends Dialog {
	private Text text_sername;
	private Text text_listen;
	private Button button_ok;
	private Button button_cancel;
	
	String Servername = null;
	String ListenParam = null;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public serverdialog(Shell parentShell) {
		super(parentShell);
	}
	
	public String getServername() {
		return Servername;
	}
	public String getListenParam() {
		return ListenParam;
	}
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Server Dialog");
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 4;
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("server_name:");
		
		text_sername = new Text(container, SWT.BORDER);
		text_sername.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				SetOkEnable();
			}
		});
		GridData gd_text_sername = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_sername.widthHint = 210;
		text_sername.setLayoutData(gd_text_sername);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("listen:");
		
		text_listen = new Text(container, SWT.BORDER);
		text_listen.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				SetOkEnable();
			}
		});
		GridData gd_text_listen = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_listen.widthHint = 211;
		text_listen.setLayoutData(gd_text_listen);

		return container;
	}
	
	private void SetOkEnable() {
		Servername = text_sername.getText().trim();
		ListenParam = text_listen.getText().trim();
		
		if((null != Servername)&&(null != ListenParam)&&
			("" != Servername)&&("" != ListenParam)){
			button_ok.setEnabled(true);
		}else{
			button_ok.setEnabled(false);
		}
	}
	
	@Override
	protected void setShellStyle(int newShellStyle) {
	    super.setShellStyle(newShellStyle ^ SWT.CLOSE);
	}
	
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		button_ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button_ok.setEnabled(false);
		button_ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Servername = text_sername.getText();
				ListenParam = text_listen.getText();
			}
		});

		button_cancel = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		button_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Servername = null;
				ListenParam = null;
			}
		});
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
