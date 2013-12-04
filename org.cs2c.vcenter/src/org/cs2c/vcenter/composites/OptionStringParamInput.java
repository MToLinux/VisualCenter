package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Option;
import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecOption;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class OptionStringParamInput extends Composite implements ParamInput {

	//<param name="uri" class="OptionStringParamInput" tips="optional" />
	String strParamName = "";
	String strValue = "";
	String tips = "";
	
	Label ctlLabel;
	Text ctlText;
	
	ParameterMeta pMeta;
	Option opt = new RecOption();

	
	public OptionStringParamInput(Composite parent, int style, ParameterMeta meta) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2,false));	
		
		ctlLabel = new Label(this,SWT.NONE);
		ctlLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ctlLabel.setText(strParamName+" = ");

		ctlText = new Text(this,SWT.NONE);
		ctlText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
			}
		});
		ctlText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		
		this.pMeta = meta;
		
		strParamName = this.pMeta.getName();
		tips = this.pMeta.getTips();
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		strParamName = this.pMeta.getName();
		tips = this.pMeta.getTips();

	}

	public void UpdateParam()
	{
		opt.setName(strParamName);
		strValue = ctlText.getText();
		opt.setValue(strValue);
	}
	
	@Override
	public Parameter getParameter() {

		return opt;
	}
	
}
