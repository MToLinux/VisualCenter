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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class TagParamInput extends BaseParamInput {

	private String strParamName = "";
	
	private String tips = "";
	
	private Button ctlCheckButton;
	private boolean isChecked = false;
	private Label ctlLabel;
	
	private ParameterMeta pMeta;
	
	private StringParameter strParam = new RecStringParameter();
	
	private DirectiveInput parentDialog = null;
	
	public TagParamInput(Composite parent, int style, ParameterMeta meta, DirectiveInput parentDlg) {
		super(parent, style);
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(10,true));
		
		ctlCheckButton = new Button(this,SWT.CHECK);
		ctlCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
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
		
		ctlLabel = new Label(this,SWT.CHECK);
		GridData gridDataListLB = new GridData(GridData.FILL_BOTH);
		gridDataListLB.horizontalSpan=9;
		ctlLabel.setLayoutData(gridDataListLB);
		ctlLabel.setText(strParamName);
		
		this.pMeta = meta;
		this.parentDialog = parentDlg;
		
		strParamName = this.pMeta.getName();
		tips = this.pMeta.getTips();
		
		ctlLabel.setText(strParamName);
		
		if(tips!=null && !tips.isEmpty())
		{
			ctlCheckButton.setToolTipText(tips);
			ctlLabel.setToolTipText(tips);
		}
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		strParamName = this.pMeta.getName();
		tips = this.pMeta.getTips();
		
		ctlLabel.setText(strParamName);
		
		if(tips!=null && !tips.isEmpty())
		{
			ctlCheckButton.setToolTipText(tips);
			ctlLabel.setToolTipText(tips);
		}
	}
	
	public void UpdateParam()
	{
		if(isChecked)
		{
			isChecked = false;
		}
		else
		{
			isChecked = true;
		}
	}
	
	@Override
	public Parameter getParameter() {
		
		if(isChecked)
		{
			strParam.setValue(strParamName);
		}
		else
		{
			strParam = null;
		}
		
		return strParam;
	}
	
	@Override
	public void setInputData(List<Parameter> params)
	{
		for(Parameter param : params)
		{
			String paramStr = param.toString();
			paramStr = paramStr.trim();
			if(paramStr.equals(strParamName))
			{
				isChecked = true;
				ctlCheckButton.setSelection(true);
				
				params.remove(param);
				break;
			}
		}
	}
	
}
