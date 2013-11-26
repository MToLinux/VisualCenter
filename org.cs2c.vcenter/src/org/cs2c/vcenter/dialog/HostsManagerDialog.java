package org.cs2c.vcenter.dialog;

import java.util.ArrayList;
import org.cs2c.vcenter.Application;
import org.cs2c.vcenter.metadata.DOMParser;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;

public class HostsManagerDialog extends Dialog {
	DOMParser hostXml = null;
	List list = null;
	GridData gd_list;
	Button btnNewButton = null;
	Button btnNewButton_1 = null;
	Button btnNewButton_2 = null;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public HostsManagerDialog(Shell parentShell) {
		super(parentShell);
		hostXml = DOMParser.getInstance();
		
		//hostXml=org.cs2c.vcenter.Application.domparser;

	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {

			}
		});
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 8;

		list = new List(container, SWT.BORDER);
		btnNewButton = new Button(container, SWT.NONE);
		btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_2 = new Button(container, SWT.NONE);
			
		getHostsInfoFromXml();

		gd_list = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 5);
		gd_list.heightHint = 229;
		gd_list.widthHint = 282;
		list.setLayoutData(gd_list);

		new Label(container, SWT.NONE);

	
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// list.getItems()
				HostsEditDialog addhostDialog = new HostsEditDialog(this
						.getShell(), "", hostXml);

				if (addhostDialog.open() == OK)	
				{
					getHostsInfoFromXml();
				}

			}

			private Shell getShell() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setText("Add");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				HostsEditDialog edithostDialog = new HostsEditDialog(this
						.getShell(), list.getSelection()[0], hostXml);

				if (edithostDialog.open() == OK)
				{
					getHostsInfoFromXml();
				}

			}

			private Shell getShell() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1));
		btnNewButton_1.setText("Edit");
		new Label(container, SWT.NONE);

		
		btnNewButton_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				hostXml.deleteHostInfo(list.getSelection()[0]);		
				hostXml.saveXml(hostXml.getDocument(), "conf/host.xml");
				
				getHostsInfoFromXml();

			}
		});
		btnNewButton_2.setText("Delete");

		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnNewButton_1.setEnabled(true);
				btnNewButton_2.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				btnNewButton_1.setEnabled(true);
				btnNewButton_2.setEnabled(true);
			}
		});
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	public void getHostsInfoFromXml() {
		list.removeAll();
		for (int i = 0; i < hostXml.getMainHostInfo().size(); i++) {
			list.add(hostXml.getMainHostInfo().get(i));
		}
		list.setFocus();
		if(list.getItemCount()!=0) 
		{
			list.setSelection(0);
			btnNewButton_1.setEnabled(true);
			btnNewButton_2.setEnabled(true);
		}
		else
		{
			btnNewButton_1.setEnabled(false);
			btnNewButton_2.setEnabled(false);
		}
		
	}
}