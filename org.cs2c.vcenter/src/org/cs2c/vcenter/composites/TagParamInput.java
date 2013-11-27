package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;

public class TagParamInput extends Composite implements ParamInput {

	String strParamName = "Check Button";
	Button ctlCheckButton;
	boolean isChecked;
	
	public TagParamInput(Composite parent, int style) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(1,false));
		
		ctlCheckButton = new Button(this,SWT.CHECK);
		ctlCheckButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		ctlCheckButton.setText(strParamName);

	}

	@Override
	public void setMeta(ParameterMeta meta) {
		
	}

	@Override
	public Parameter getParameter() {
		return null;
	}

}
