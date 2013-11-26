package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class OptionStringParamInput extends Composite implements ParamInput {

	//以“[name]=[value]”形式的指令参数，其中[value]需提供Text获取普通字符串值。
	
	String strParamName = "option";
	int intParam;
	String strUnit;
	
	Label ctlLabel;
	Text ctlText;
	
	public OptionStringParamInput(Composite parent, int style) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2,false));	
		
		ctlLabel = new Label(this,SWT.NONE);
		ctlLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ctlLabel.setText(strParamName+" = ");

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
