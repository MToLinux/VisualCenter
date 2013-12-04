package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecStringParameter;
import org.cs2c.nginlib.config.StringParameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class TagParamInput extends Composite implements ParamInput {

	String strParamName = "";
	
	String strValue = "";
	String tips = "";
	
	Button ctlCheckButton;
	
	ParameterMeta pMeta;
	
	StringParameter strParam = new RecStringParameter();

	
	public TagParamInput(Composite parent, int style, ParameterMeta meta) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(1,false));
		
		ctlCheckButton = new Button(this,SWT.CHECK);
		ctlCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				UpdateParam();
			}
		});
		ctlCheckButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		ctlCheckButton.setText(strParamName);
		
		
		this.pMeta = meta;
		
		strParamName = this.pMeta.getName();
		tips = this.pMeta.getTips();
		
		ctlCheckButton.setText(strParamName);
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		strParamName = this.pMeta.getName();
		tips = this.pMeta.getTips();
		
		ctlCheckButton.setText(strParamName);
	}
	
	public void UpdateParam()
	{
		if(ctlCheckButton.getSelection())
		{
			strParam.setValue(strParamName);
		}
		else
		{
			strParam.setValue("");
		}
	}

	@Override
	public Parameter getParameter() {
		
		return strParam;
	}

}
