package org.cs2c.vcenter.composites;

import java.util.ArrayList;
import java.util.List;

import org.cs2c.nginlib.config.Option;
import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecOption;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class OptionIntParamInput extends Composite implements ParamInput {

	String strParamName = "";
	List<String> strUnit = new ArrayList<String>();
	int max = 999999999;
	int min = -999999999;
	String tips = "";
	
	int selValue = 0;
	String selUnit = "";
	
	ParameterMeta pMeta;
	Option opt = new RecOption();
	
	Label ctlLabel;
	Spinner ctlSpinner;
	Combo ctlCombo;
	
	public OptionIntParamInput(Composite parent, int style, ParameterMeta meta) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3,false));
		
		ctlLabel = new Label(this,SWT.NONE);
		ctlLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		ctlSpinner = new Spinner(this,SWT.NONE);
		ctlSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
			}
		});
		ctlSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		ctlCombo = new Combo(this,SWT.NONE);
		ctlCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
			}
		});
		ctlCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		
		this.pMeta = meta;
		
		strParamName = this.pMeta.getName();
		selValue = 0;
		max = (int) this.pMeta.getMax();
		min = (int) this.pMeta.getMin();
		strUnit = this.pMeta.getUnit();
		tips = this.pMeta.getTips();
		
		ctlSpinner.setMaximum(max);
		ctlSpinner.setMinimum(min);
		ctlSpinner.setSelection(0);
		
		if(strUnit!=null && !strUnit.isEmpty())
		{
			int i = strUnit.size();
			while(i>0)
			{
				ctlCombo.add(strUnit.get(i-1));
				i--;
			}
		}
		else
		{
			//ctlCombo.add("");
			ctlCombo.setEnabled(false);
		}
		if(ctlCombo.getItemCount() > 0)
		{
			ctlCombo.select(0);
		}
		
		ctlLabel.setText(strParamName+" = ");

	}
	
	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		strParamName = this.pMeta.getName();
		selValue = 0;
		max = (int) this.pMeta.getMax();
		min = (int) this.pMeta.getMin();
		strUnit = this.pMeta.getUnit();
		tips = this.pMeta.getTips();
		
		ctlSpinner.setMaximum(max);
		ctlSpinner.setMinimum(min);
		ctlSpinner.setSelection(0);
		
		if(strUnit!=null && !strUnit.isEmpty())
		{
			int i = strUnit.size();
			while(i>0)
			{
				ctlCombo.add(strUnit.get(i-1));
				i--;
			}
		}
		else
		{
			//ctlCombo.add("");
			ctlCombo.setEnabled(false);
		}
		if(ctlCombo.getItemCount() > 0)
		{
			ctlCombo.select(0);
		}
		
		ctlLabel.setText(strParamName+" = ");

	}
	
	public void UpdateParam()
	{
		selValue = ctlSpinner.getSelection();
		
		opt.setName(strParamName);
		if(ctlCombo.getItemCount() > 0 && ctlCombo.getSelectionIndex()!=-1)
		{
			selUnit = ctlCombo.getItem(ctlCombo.getSelectionIndex());
			opt.setValue(Integer.toString(selValue)+selUnit);
		}
		else
		{
			opt.setValue(Integer.toString(selValue));
		}
	}
	
	@Override
	public Parameter getParameter() {
		
		return opt;
	}

}
