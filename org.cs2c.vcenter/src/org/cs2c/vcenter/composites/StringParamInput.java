package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class StringParamInput extends Composite implements ParamInput {
	
	String strPara;
	Text ctlText;

	public StringParamInput(Composite parent, int style) {
		super(parent, style);

		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(1,false));
		
		ctlText = new Text(this,SWT.NONE);
		ctlText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
	
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		
	}

	@Override
	public Parameter getParameter() {
		return null;
	}

}
