package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;

public class IntParamInput extends Composite implements ParamInput {

	//��Spinner��ȡ��������Ϊָ����������е�λ����λ�ṩCombo����ѡ��
	
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
