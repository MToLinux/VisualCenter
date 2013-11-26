package org.cs2c.vcenter.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class Importmiddleware extends Dialog {
	Combo combo_host = null;
	
    List<String> listMiddleware = null;
	private String middlewarename = null;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public Importmiddleware(Shell parentShell) {
		super(parentShell);
	}
	
	public String getServername() {
		return this.middlewarename;
	}
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 * @return 
	 */
	public void init(List<String> list) {
		//check list
		listMiddleware = list;
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setEnabled(false);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 6;
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Host：");
		
		combo_host = new Combo(container, SWT.NONE);
		combo_host.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Setmiddlewarename();
			}
		});
		GridData gd_combo_host = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_combo_host.widthHint = 178;
		combo_host.setLayoutData(gd_combo_host);
		
		String Miditems[] = new String[listMiddleware.size()];
		for(int i=0;i<listMiddleware.size();i++){
			Miditems[i]=listMiddleware.get(i);
		}
		combo_host.setItems(Miditems);
		combo_host.select(0);
//		combo_host.i
		
		
		Button btn_ViewSelect = new Button(container, SWT.NONE);
		btn_ViewSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//调出Host Editor查看该Host的详细信息
//				HostsEditDialog hed = new HostsEditDialog(shell, middlewarename, null);
			}
		});
		btn_ViewSelect.setText("  ...  ");

		return container;
	}
	

	private void Setmiddlewarename() {
		this.middlewarename = combo_host.getText();
	}


	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button_ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button_ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				middlewarename=combo_host.getText();
			}
		});
		Button button_Cancel = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		button_Cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				middlewarename=null;
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
