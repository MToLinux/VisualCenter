package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;

public class IntParamInput extends Composite implements ParamInput {

	//以Spinner获取的整数作为指令参数，可有单位，单位提供Combo进行选择。
	
	public IntParamInput(Composite parent, int style) {
		super(parent, style);
	}

	public Parameter getParameter() {
		return null;
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		
	}

}
