package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;

public class OptionIntParamInput extends Composite implements ParamInput {

	//�ԡ�[name]=[value]����ʽ��ָ�����������[value]���ṩspinner��ȡ����ֵ�������е�λ����open_file_cache max=1000 inactive=20s
	
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
