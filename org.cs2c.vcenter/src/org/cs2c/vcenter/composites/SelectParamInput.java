package org.cs2c.vcenter.composites;

import java.util.List;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecStringParameter;
import org.cs2c.nginlib.config.StringParameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class SelectParamInput extends Composite implements ParamInput {

	List<String> strOptions = null;
	String tips = "";
	
	String selValue = "";
	
	ParameterMeta pMeta;

	Combo ctlCombo;
	
	StringParameter strParam = new RecStringParameter();

	
	public SelectParamInput(Composite parent, int style, ParameterMeta meta) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2,false));
		
		ctlCombo = new Combo(this,SWT.NONE);
		ctlCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
			}
		});
		ctlCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		
		this.pMeta = meta;
		new Label(this, SWT.NONE);
		
		strOptions = this.pMeta.getItems();
		tips = this.pMeta.getTips();
		
		if(strOptions!=null && !strOptions.isEmpty())
		{
			for(String option : strOptions)
			{
				ctlCombo.add(option);
			}
		}
		else
		{
			ctlCombo.add("");
		}
		if(ctlCombo.getItemCount() > 0)
		{
			ctlCombo.select(0);
		}
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		strOptions = this.pMeta.getItems();
		tips = this.pMeta.getTips();
		
		if(strOptions!=null && !strOptions.isEmpty())
		{
			for(String option : strOptions)
			{
				ctlCombo.add(option);
			}
		}
		else
		{
			ctlCombo.add("");
		}
		if(ctlCombo.getItemCount() > 0)
		{
			ctlCombo.select(0);
		}
	}
	
	public void UpdateParam()
	{
		int comCount = ctlCombo.getItemCount();
		int index = ctlCombo.getSelectionIndex();
		if(comCount>0 && index!=-1)
		{
			selValue = ctlCombo.getItem(ctlCombo.getSelectionIndex());
		}
		strParam.setValue(selValue);
	}

	@Override
	public Parameter getParameter() {

		return strParam;
	}

}
