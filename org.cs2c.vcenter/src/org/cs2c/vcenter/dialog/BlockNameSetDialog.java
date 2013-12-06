package org.cs2c.vcenter.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class BlockNameSetDialog extends Dialog {
	
	private Text ctlText;
	private Label labs;
	
	private String blockType = "";
	private String blockName = "";
	
	Button ctlCheckButton;
	
	boolean isChecked = false;

	/**
	 * @wbp.parser.constructor
	 */
	public BlockNameSetDialog(Shell parentShell, String blockType) {
		super(parentShell);
		this.blockType = blockType;
	}

	public BlockNameSetDialog(IShellProvider parentShell, String blockType) {
		super(parentShell);
		this.blockType = blockType;
	}
	
	protected Control createDialogArea(Composite parent) {
		
		getShell().setText(this.blockType);
		
		Composite cmpsite = (Composite)super.createDialogArea(parent);
		
		cmpsite.layout(true);
		cmpsite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		cmpsite.setLayout(new GridLayout(1,false));
		
		ctlCheckButton = new Button(cmpsite,SWT.CHECK);
		ctlCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(isChecked)
				{
					isChecked = false;
					ctlText.setEnabled(false);
				}
				else
				{
					isChecked = true;
					ctlText.setEnabled(true);
				}
			}
		});
		ctlCheckButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ctlCheckButton.setText("Set a Block Name:");

		this.ctlText = new Text(cmpsite, SWT.NONE);
		this.ctlText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
			}
		});
		this.ctlText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		this.labs = new Label(cmpsite, SWT.NONE);
		this.labs.setText("The Block name can be specified or not.");
		this.ctlText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		isChecked = false;
		ctlText.setEnabled(false);
		
		return cmpsite;
	}
	
	public void UpdateParam()
	{
		if(isChecked)
		{
			this.blockName = this.ctlText.getText();
			this.labs.setText(this.blockType + " " + this.blockName + " {...}");
		}
		else
		{
			this.labs.setText(this.blockType + " {...}");
		}
	}
	
	public String getBlockName()
	{
		if(!isChecked)
		{
			this.blockName = "";
			return this.blockName;
		}
		else
		{	this.blockName = this.ctlText.getText();
			return this.blockName;
		}
	}

}
