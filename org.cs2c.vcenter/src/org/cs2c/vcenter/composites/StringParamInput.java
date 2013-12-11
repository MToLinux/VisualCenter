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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class StringParamInput extends BaseParamInput {
	
	private String strValue = "";
	private String tips = "";
	
	private Text ctlText;
	
	private Button ctlCheckButton;
	
	private boolean isChecked = false;
	
	private ParameterMeta pMeta;
	
	private StringParameter strParam = new RecStringParameter();

	private DirectiveInput parentDialog = null;

	public StringParamInput(Composite parent, int style, ParameterMeta meta, DirectiveInput parentDlg) {
		super(parent, style);

		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2,false));
		
		ctlCheckButton = new Button(this,SWT.CHECK);
		ctlCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(isChecked)
				{
					isChecked = false;
					ctlText.setEnabled(false);
				}
				else
				{
					isChecked = true;
					ctlText.setEnabled(true);
				}
				
				UpdateParam();
				parentDialog.updateDirctString();
			}
		});
		ctlCheckButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ctlCheckButton.setText("");
		
		ctlText = new Text(this,SWT.NONE);
		ctlText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				UpdateParam();
				parentDialog.updateDirctString();
			}
		});
		ctlText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
	
		
		this.pMeta = meta;
		this.parentDialog = parentDlg;
		
		isChecked = false;
		ctlText.setEnabled(false);
		
		tips = this.pMeta.getTips();
		if(tips!=null && !tips.isEmpty())
		{
			ctlText.setToolTipText(tips);
		}
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		if(!isChecked)
		{
			isChecked = false;
			ctlText.setEnabled(false);
		}
		else
		{
			isChecked = true;
			ctlText.setEnabled(true);
		}
		
		tips = this.pMeta.getTips();
		if(tips!=null && !tips.isEmpty())
		{
			ctlText.setToolTipText(tips);
		}
	}
	
	public void UpdateParam()
	{
		strValue = ctlText.getText();
		strParam.setValue(strValue);
	}

	@Override
	public Parameter getParameter() {
		
		if(isChecked && strValue!=null && !strValue.isEmpty())
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
			paramStr = paramStr.replaceAll(" ", "");
			if(paramStr.indexOf("=")==-1 /*&& isnotIntParam(paramStr)*/)
			{
				isChecked = true;
				ctlCheckButton.setSelection(true);
				ctlText.setEnabled(true);
				
				ctlText.setText(paramStr);
				
				params.remove(param);
				break;
			}
		}
	}
	
}
