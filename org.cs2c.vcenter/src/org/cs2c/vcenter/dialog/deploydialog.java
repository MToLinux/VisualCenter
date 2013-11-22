package org.cs2c.vcenter.dialog;

import java.io.File;
import java.io.FilenameFilter;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

public class deploydialog extends Dialog {
	private Text text_file;
	private Text text_root;
	private Button button_ok;
	private Button button_cancel;
	
	String LocalFilename = null;
	String RemotePath = null;
	private Button btn_selectpath;
//	private Shell shell;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public deploydialog(Shell parentShell) {
		super(parentShell);
	}
	
	public String getLocalFilename() {
		return LocalFilename;
	}
	public String getRemotePath() {
		return RemotePath;
	}

	public void setRemotePath(String strRemotePath) {
		RemotePath = strRemotePath;
		setControlEnable();
	}
	
	private void setControlEnable() {
		text_root.setEnabled(true);
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
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Deploy File:");
		
		text_file = new Text(container, SWT.BORDER);
		text_file.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				SetOkEnable();
			}
		});
		GridData gd_text_file = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_text_file.widthHint = 200;
		text_file.setLayoutData(gd_text_file);
		
		btn_selectpath = new Button(container, SWT.NONE);
		btn_selectpath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				FileDialog dialog = new FileDialog(shell);
				dialog.setText( "Upload Files" );
				dialog.setFilterExtensions(new String[] {"*.zip"});
				dialog.open();
				
				text_file.setText(dialog.getFilterPath()+File.separator+dialog.getFileName());
			}
		});
		btn_selectpath.setText("  ...  ");
		new Label(container, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("root:");
		
		text_root = new Text(container, SWT.BORDER);
		text_root.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				SetOkEnable();
			}
		});
		GridData gd_text_root = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_text_root.widthHint = 200;
		text_root.setLayoutData(gd_text_root);
		new Label(container, SWT.NONE);

		return container;
	}
	
	private void SetOkEnable() {
		LocalFilename = text_file.getText().trim();
		RemotePath = text_root.getText().trim();
		
		if((null != LocalFilename)&&(null != RemotePath)&&
			("" != LocalFilename)&&("" != RemotePath)){
			button_ok.setEnabled(true);
		}else{
			button_ok.setEnabled(false);
		}
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
				LocalFilename = text_file.getText();
				RemotePath = text_root.getText();
			}
		});

		button_cancel = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		button_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				LocalFilename = null;
				RemotePath = null;
			}
		});
	}

	@Override
	protected void setShellStyle(int newShellStyle) {
	    super.setShellStyle(newShellStyle ^ SWT.CLOSE);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

}
