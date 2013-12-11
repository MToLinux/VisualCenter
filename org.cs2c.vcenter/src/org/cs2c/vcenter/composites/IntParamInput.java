package org.cs2c.vcenter.composites;

import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseAdapter;

public class IntParamInput extends BaseParamInput {
	
	private List<String> strUnit = new ArrayList<String>();
	private int max = 999999999;
	private int min = -999999999;
	private String tips = "";
	
	private int selValue = 0;
	private String selUnit = "";
	
	private ParameterMeta pMeta;
	
	private Button ctlCheckButton;
	private Spinner ctlSpinner;
	private Combo ctlCombo;
	
	private boolean isChecked = false;
	
	private StringParameter strParam = new RecStringParameter();
	
	private DirectiveInput parentDialog = null;
	
	public IntParamInput(Composite parent, int style, ParameterMeta meta, DirectiveInput parentDlg) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(10,true));
		
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
				
				UpdateParam();
				parentDialog.updateDirctString();
			}
		});
		GridData gridDataListCB = new GridData(GridData.FILL_BOTH);
		gridDataListCB.verticalAlignment = SWT.CENTER;
		gridDataListCB.horizontalAlignment = SWT.CENTER;
		gridDataListCB.horizontalSpan=1;
		ctlCheckButton.setLayoutData(gridDataListCB);
		ctlCheckButton.setText("");
		
		ctlSpinner = new Spinner(this,SWT.NONE);
		ctlSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
				parentDialog.updateDirctString();
			}
		});
		GridData gridDataListSP = new GridData(GridData.FILL_BOTH);
		gridDataListSP.horizontalSpan=6;
		ctlSpinner.setLayoutData(gridDataListSP);
		
		ctlCombo = new Combo(this,SWT.NONE);
		ctlCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
				parentDialog.updateDirctString();
			}
		});
		GridData gridDataListCMB = new GridData(GridData.FILL_BOTH);
		gridDataListCMB.horizontalSpan=3;
		ctlCombo.setLayoutData(gridDataListCMB);
		
		this.pMeta = meta;
		this.parentDialog = parentDlg;
		
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
	
	@Override
	public void setInputData(List<Parameter> params)
	{
		for(Parameter param : params)
		{
			String paramStr = param.toString().trim();
			paramStr = paramStr.replaceAll(" ", "");
			
			int i;
			for (i = paramStr.length();--i>=0;)
			{
				if (Character.isDigit(paramStr.charAt(i)))
				{
					break;
				}
			}
			
			if(isNum(paramStr))
			{
				isChecked = true;
				ctlCheckButton.setSelection(true);
				ctlSpinner.setEnabled(true);
				
				ctlSpinner.setSelection(Integer.parseInt(paramStr));
				
				if(strUnit!=null && !strUnit.isEmpty())
				{
					ctlCombo.setEnabled(true);
					ctlCombo.setText("");
				}
				else
				{
					ctlCombo.setEnabled(false);
					ctlCombo.setText("");
				}
				
				params.remove(param);
				break;
			}
			else if(i>0 && isNum(paramStr.substring(0, i+1)))
			{
				if(strUnit!=null && !strUnit.isEmpty() && 
					strUnit.contains(paramStr.substring(i+1, paramStr.length())))
				{
					isChecked = true;
					ctlCheckButton.setSelection(true);
					ctlSpinner.setEnabled(true);
					
					ctlSpinner.setSelection(Integer.parseInt(paramStr.substring(0, i+1)));
					
					ctlCombo.setEnabled(true);
					ctlCombo.setText(paramStr.substring(i+1, paramStr.length()));
					
					params.remove(param);
					break;
				}
			}
		}
		
	}
	
}
