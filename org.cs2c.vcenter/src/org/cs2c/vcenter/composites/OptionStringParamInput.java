package org.cs2c.vcenter.composites;

import java.util.List;

import org.cs2c.nginlib.config.Option;
import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecOption;
import org.cs2c.vcenter.dialog.DirectiveInput;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class OptionStringParamInput extends BaseParamInput {

	private String strParamName = "";
	private String strValue = "";
	private String tips = "";
	
	private Button ctlCheckButton;
	private Label ctlLabel;
	private Text ctlText;
	
	private boolean isChecked = false;
	
	private ParameterMeta pMeta;
	private Option opt = new RecOption();

	private DirectiveInput parentDialog = null;
	
	public OptionStringParamInput(Composite parent, int style, ParameterMeta meta, DirectiveInput parentDlg) {
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
	
		ctlLabel = new Label(this,SWT.NONE);
		ctlLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

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
		
		strParamName = this.pMeta.getName();
		ctlLabel.setText(strParamName+" = ");
		new Label(this, SWT.NONE);

		tips = this.pMeta.getTips();
		
		isChecked = false;
		ctlText.setEnabled(false);
		
		isChecked = false;
		ctlText.setEnabled(false);
			
		if(tips!=null && !tips.isEmpty())
		{
			ctlLabel.setToolTipText(tips);
			ctlCheckButton.setToolTipText(tips);
			ctlText.setToolTipText(tips);
		}
	}

	@Override
	public void setMeta(ParameterMeta meta) {
		this.pMeta = meta;
		
		strParamName = this.pMeta.getName();
		ctlLabel.setText(strParamName+" = ");
		
		tips = this.pMeta.getTips();
		
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
		
		if(tips!=null && !tips.isEmpty())
		{
			ctlLabel.setToolTipText(tips);
			ctlCheckButton.setToolTipText(tips);
			ctlText.setToolTipText(tips);
		}

	}

	public void UpdateParam()
	{
		opt.setName(strParamName);
		strValue = ctlText.getText();
		opt.setValue(strValue);
	}
	
	@Override
	public Parameter getParameter() {

		if(isChecked && strValue!=null && !strValue.isEmpty())
		{
			return opt;
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
			String paramStr = deleteExtraSpace(param.toString());
			paramStr = paramStr.replaceAll(" ", "");
			String[] paramStrSlip = paramStr.split("=");
			if(paramStrSlip[0].equals(strParamName))
			{
				isChecked = true;
				ctlCheckButton.setSelection(true);
				ctlText.setEnabled(true);
				
				ctlText.setText(paramStrSlip[1]);
				
				params.remove(param);
				break;
			}
		}
	}
	
}
