package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class SelectParamInput extends Composite implements ParamInput {

	//以Combo指定的可选值中选择作为指令的参数
	
	String strParam;
	Combo ctlCombo;
	
	public SelectParamInput(Composite parent, int style) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2,false));
		
		ctlCombo = new Combo(this,SWT.NONE);
		GridData gd_ctlCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		ctlCombo.setLayoutData(gd_ctlCombo);
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		
	}

	@Override
	public Parameter getParameter() {
		return null;
	}

}
