package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

public class IntParamInput extends Composite implements ParamInput {

	//以Spinner获取的整数作为指令参数，可有单位，单位提供Combo进行选择。
	
	int intParam;
	String strUnit;
	
	Spinner ctlSpinner;
	Combo ctlCombo;
	
	public IntParamInput(Composite parent, int style) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2,false));
		
		ctlSpinner = new Spinner(this,SWT.NONE);
		GridData gd_ctlSpinner = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		ctlSpinner.setLayoutData(gd_ctlSpinner);
		
		ctlCombo = new Combo(this,SWT.NONE);
		GridData gd_ctlCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		ctlCombo.setLayoutData(gd_ctlCombo);
		
	}

	public Parameter getParameter() {
		return null;
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		
	}

}
