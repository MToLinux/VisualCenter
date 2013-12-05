package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecStringParameter;
import org.cs2c.nginlib.config.StringParameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class StringParamInput extends Composite implements ParamInput {
	
	String strValue = "";
	String tips = "";
	
	Text ctlText;
	
	ParameterMeta pMeta;
	
	StringParameter strParam = new RecStringParameter();


	public StringParamInput(Composite parent, int style, ParameterMeta meta) {
		super(parent, style);

		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(1,false));
		
		ctlText = new Text(this,SWT.NONE);
		ctlText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
			}
		});
		ctlText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
	
		
		this.pMeta = meta;
		
		tips = this.pMeta.getTips();
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		tips = this.pMeta.getTips();
	}
	
	public void UpdateParam()
	{
		strValue = ctlText.getText();
		strParam.setValue(strValue);
	}

	@Override
	public Parameter getParameter() {
		
		return strParam;
	}

}
