package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;

public class OptionIntParamInput extends Composite implements ParamInput {

	//以“[name]=[value]”形式的指令参数，其中[value]需提供spinner获取整数值，可能有单位，如open_file_cache max=1000 inactive=20s
	
	public OptionIntParamInput(Composite parent, int style) {
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
