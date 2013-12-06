package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecStringParameter;
import org.cs2c.nginlib.config.StringParameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class StringParamInput extends Composite implements ParamInput {
	
	String strValue = "";
	String tips = "";
	
	Text ctlText;
	
	Button ctlCheckButton;
	
	boolean isChecked = false;
	
	ParameterMeta pMeta;
	
	StringParameter strParam = new RecStringParameter();


	public StringParamInput(Composite parent, int style, ParameterMeta meta) {
		super(parent, style);

		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2,false));
		
		ctlCheckButton = new Button(this,SWT.CHECK);
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
		ctlCheckButton.setText("");
		
		ctlText = new Text(this,SWT.NONE);
		ctlText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
			}
		});
		ctlText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
	
		
		this.pMeta = meta;
		
		isChecked = false;
		ctlText.setEnabled(false);
		
		tips = this.pMeta.getTips();
		if(tips!=null && !tips.isEmpty())
		{
			ctlText.setToolTipText(tips);
		}
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		if(!isChecked)
		{
			isChecked = false;
			ctlText.setEnabled(false);
		}
		else
		{
			isChecked = true;
			ctlText.setEnabled(true);
		}
		
		tips = this.pMeta.getTips();
		if(tips!=null && !tips.isEmpty())
		{
			ctlText.setToolTipText(tips);
		}
	}
	
	public void UpdateParam()
	{
		strValue = ctlText.getText();
		strParam.setValue(strValue);
	}

	@Override
	public Parameter getParameter() {
		
		if(isChecked && strValue!=null && !strValue.isEmpty())
		{
			return strParam;
		}
		else
		{
			return null;
		}
	}

}
