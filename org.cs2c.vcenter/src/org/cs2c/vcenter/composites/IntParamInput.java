package org.cs2c.vcenter.composites;

import java.util.ArrayList;
import java.util.List;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecStringParameter;
import org.cs2c.nginlib.config.StringParameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseAdapter;

public class IntParamInput extends Composite implements ParamInput {

	String strParamName = "";
	List<String> strUnit = new ArrayList<String>();
	int max = 999999999;
	int min = -999999999;
	String tips = "";
	
	int selValue = 0;
	String selUnit = "";
	
	ParameterMeta pMeta;
	
	Button ctlCheckButton;
	Spinner ctlSpinner;
	Combo ctlCombo;
	
	boolean isChecked = false;
	
	StringParameter strParam = new RecStringParameter();
	
	public IntParamInput(Composite parent, int style, ParameterMeta meta) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3,false));
		
		ctlCheckButton = new Button(this,SWT.CHECK);
		ctlCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(isChecked)
				{
					isChecked = false;
					ctlSpinner.setEnabled(false);
					ctlCombo.setEnabled(false);
				}
				else
				{
					isChecked = true;
					ctlSpinner.setEnabled(true);
					if(strUnit!=null && !strUnit.isEmpty())
					{
						ctlCombo.setEnabled(true);
					}
					else
					{
						ctlCombo.setEnabled(false);
					}
				}
			}
		});
		ctlCheckButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ctlCheckButton.setText("");
		
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
		ctlCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		
		this.pMeta = meta;
		
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
			int count = strUnit.size();
			int i = 0;
			while(i<count)
			{
				ctlCombo.add(strUnit.get(i));
				i++;
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
		
		isChecked = false;
		ctlSpinner.setEnabled(false);
		ctlCombo.setEnabled(false);
		
		if(tips!=null && !tips.isEmpty())
		{
			ctlSpinner.setToolTipText(tips);
			ctlCombo.setToolTipText(tips);
		}
	}

	public void UpdateParam()
	{
		selValue = ctlSpinner.getSelection();
		
		if(ctlCombo.getItemCount() > 0 && ctlCombo.getSelectionIndex()!=-1)
		{
			selUnit = ctlCombo.getItem(ctlCombo.getSelectionIndex());
			strParam.setValue(Integer.toString(selValue)+selUnit);
		}
		else
		{
			strParam.setValue(Integer.toString(selValue));
		}
	}
	
	public Parameter getParameter() {
		
		if(isChecked)
		{
			return strParam;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
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
			int count = strUnit.size();
			int i = 0;
			while(i<count)
			{
				ctlCombo.add(strUnit.get(i));
				i++;
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
		
		if(!isChecked)
		{
			isChecked = false;
			ctlSpinner.setEnabled(false);
			ctlCombo.setEnabled(false);
		}
		else
		{
			isChecked = true;
			ctlSpinner.setEnabled(true);
			if(strUnit!=null && !strUnit.isEmpty())
			{
				ctlCombo.setEnabled(true);
			}
			else
			{
				ctlCombo.setEnabled(false);
			}
		}
		
		if(tips!=null && !tips.isEmpty())
		{
			ctlSpinner.setToolTipText(tips);
			ctlCombo.setToolTipText(tips);
		}
	}

}
