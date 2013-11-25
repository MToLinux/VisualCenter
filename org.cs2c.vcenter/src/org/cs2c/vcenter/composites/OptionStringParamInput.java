package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;

public class OptionStringParamInput extends Composite implements ParamInput {

	//以“[name]=[value]”形式的指令参数，其中[value]需提供Text获取普通字符串值。
	
	public OptionStringParamInput(Composite parent, int style) {
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
