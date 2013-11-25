package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;

public class SelectParamInput extends Composite implements ParamInput {

	//以Combo指定的可选值中选择作为指令的参数
	
	public SelectParamInput(Composite parent, int style) {
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
