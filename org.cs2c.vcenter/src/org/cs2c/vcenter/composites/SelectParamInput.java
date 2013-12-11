package org.cs2c.vcenter.composites;

import java.util.List;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecStringParameter;
import org.cs2c.nginlib.config.StringParameter;
import org.cs2c.vcenter.dialog.DirectiveInput;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class SelectParamInput extends BaseParamInput {

	private List<String> strOptions = null;
	private String tips = "";
	
	private String selValue = "";
	
	private ParameterMeta pMeta;

	private Combo ctlCombo;
	
	private Button ctlCheckButton;
	
	private boolean isChecked = false;
	
	private StringParameter strParam = new RecStringParameter();

	private DirectiveInput parentDialog = null;
	
	public SelectParamInput(Composite parent, int style, ParameterMeta meta, DirectiveInput parentDlg) {
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
					ctlCombo.setEnabled(false);
				}
				else
				{
					isChecked = true;
					ctlCombo.setEnabled(true);
				}
				
				UpdateParam();
				parentDialog.updateDirctString();
			}
		});
		ctlCheckButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ctlCheckButton.setText("");
		
		ctlCombo = new Combo(this,SWT.NONE);
		ctlCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
				parentDialog.updateDirctString();
			}
		});
		ctlCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		
		this.pMeta = meta;
		this.parentDialog = parentDlg;
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
		
		isChecked = false;
		ctlCombo.setEnabled(false);
		
		if(tips!=null && !tips.isEmpty())
		{
			ctlCombo.setToolTipText(tips);
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
		
		if(!isChecked)
		{
			isChecked = false;
			ctlCombo.setEnabled(false);
		}
		else
		{
			isChecked = true;
			ctlCombo.setEnabled(true);
		}
		
		if(tips!=null && !tips.isEmpty())
		{
			ctlCombo.setToolTipText(tips);
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

		if(isChecked && selValue!=null && !selValue.isEmpty())
		{
			return strParam;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public void setInputData(List<Parameter> params)
	{
		for(Parameter param : params)
		{
			String paramStr = param.toString().trim();
			if(strOptions!=null && !strOptions.isEmpty() && strOptions.contains(paramStr))
			{
				isChecked = true;
				ctlCheckButton.setSelection(true);
				ctlCombo.setEnabled(true);
				
				ctlCombo.setText(paramStr);
				
				params.remove(param);
				break;
			}
		}
	}
	
}
