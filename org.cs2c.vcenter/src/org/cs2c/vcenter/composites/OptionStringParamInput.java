package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;

public class OptionStringParamInput extends Composite implements ParamInput {

	//�ԡ�[name]=[value]����ʽ��ָ�����������[value]���ṩText��ȡ��ͨ�ַ���ֵ��
	
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
