package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class StringParamInput extends Composite implements ParamInput {
	
	String strPara;
	Text ctlText;

	//以普通字符串作为指令参数
	
	public StringParamInput(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		
	}

	@Override
	public Parameter getParameter() {
		return null;
	}

}
