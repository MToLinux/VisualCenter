package org.cs2c.vcenter.dialog;

import java.util.ArrayList;

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

public class HostsManagerDialog extends Dialog {
	DOMParser hostData=null;
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public HostsManagerDialog(Shell parentShell) {
		super(parentShell);
		hostData=new DOMParser();
		
		
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 8;

		List list = new List(container, SWT.BORDER);


		for(int i=0;i<hostData.getMainHostInfo("d:/host.xml").size();i++)
		{	
				
			list.add(hostData.getMainHostInfo("d:/host.xml").get(i));
		}
		
		
		GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5,
				5);
		gd_list.heightHint = 229;
		gd_list.widthHint = 282;
		list.setLayoutData(gd_list);
		
		new Label(container, SWT.NONE);
		
				Button btnNewButton = new Button(container, SWT.NONE);
				btnNewButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						//list.getItems()
						HostsEditDialog addhostDialog=new HostsEditDialog(this.getShell(),1);

						addhostDialog.open();
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
		
				Button btnNewButton_1 = new Button(container, SWT.NONE);
				btnNewButton_1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						HostsEditDialog edithostDialog=new HostsEditDialog(this.getShell(),2,list.getItems());
						
						edithostDialog.open();//edithostDialog.initEditDialog();
						
						
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
		
				Button btnNewButton_2 = new Button(container, SWT.NONE);
				btnNewButton_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
				btnNewButton_2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						MessageBox mb = new MessageBox(getShell(), SWT.OK);
						mb.setMessage("Hello world!");
						mb.setText("Demo");
						mb.open();
					}
				});
				btnNewButton_2.setText("Delete");

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


}