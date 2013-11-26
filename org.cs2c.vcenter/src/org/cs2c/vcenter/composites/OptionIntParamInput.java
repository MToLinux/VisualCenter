package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class OptionIntParamInput extends Composite implements ParamInput {

	String strParamName = "max";
	int intValue;
	String strUnit;
	
	Label ctlLabel;
	Spinner ctlSpinner;
	Combo ctlCombo;
	
	public OptionIntParamInput(Composite parent, int style) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3,false));
		
		ctlLabel = new Label(this,SWT.NONE);
		ctlLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ctlLabel.setText(strParamName+" = ");
		
		ctlSpinner = new Spinner(this,SWT.NONE);
		ctlSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		ctlCombo = new Combo(this,SWT.NONE);
		ctlCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

	}
	
	@Override
	public void setMeta(ParameterMeta meta) {
		
	}
	
	@Override
	public Parameter getParameter() {
		return null;
	}

}
