package org.cs2c.vcenter.dialog;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.composites.BaseParamInput;
import org.cs2c.vcenter.composites.IntParamInput;
import org.cs2c.vcenter.composites.OptionIntParamInput;
import org.cs2c.vcenter.composites.OptionStringParamInput;
import org.cs2c.vcenter.composites.ParamInput;
import org.cs2c.vcenter.composites.SelectParamInput;
import org.cs2c.vcenter.composites.StringParamInput;
import org.cs2c.vcenter.composites.TagParamInput;
import org.cs2c.vcenter.metadata.DirectiveMeta;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DirectiveInput extends Dialog {
	
	private List<ParamInput> listParamInput = new ArrayList<ParamInput>();
	private List<Parameter> valueParams = new ArrayList<Parameter>();
	private List<Parameter> initialParams = new ArrayList<Parameter>();
	
	private Directive dirct = null;
	private DirectiveMeta dMeta = null;
	
	private Label dirctString = null;
	private Label dirctTips = null;
	private String dirctTipsStr = "";
	
	Hashtable<String, String> htParams = new Hashtable<String, String>();
	
	/**
	 * @wbp.parser.constructor
	 */
	
	public DirectiveInput(Shell parent, Directive direct, DirectiveMeta dMeta) {
		super(parent);
		setShellStyle(SWT.BORDER | SWT.MIN | SWT.MAX | SWT.RESIZE);
		
		this.dirct = direct;
		
		this.dMeta = dMeta;
		if(dMeta == null)
		{
			MessageDialog.openError(parent, "Error", "Directive Meta is null!");
		}
		this.dirctTipsStr = this.dMeta.getTips();
		
		if(this.dirct != null)
		{
			List<Parameter> params = new ArrayList<Parameter>();
			params = this.dirct.getParameters();
			
			for(Parameter param : params)
			{
				initialParams.add(param);
			}
		}
		
	}
	
	private void AddParamInput(BaseParamInput input)
	{
		input.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridData gridDataList_opp=new GridData(GridData.FILL_BOTH);
		gridDataList_opp.verticalSpan=1;
		input.setLayoutData(gridDataList_opp);
	    
		input.setInputData(initialParams);
	    
	    listParamInput.add(input);
	}
	
	protected Control createDialogArea(Composite parent) {
		
		getShell().setText(this.dMeta.getName());
		
		Composite composite = (Composite)super.createDialogArea(parent);

		composite.layout(true);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(1,true));
		
		List<ParameterMeta> listParamMetas = new ArrayList<ParameterMeta>();
		listParamMetas = dMeta.getOptions();
		if(listParamMetas!=null && !listParamMetas.isEmpty())
		{
			int count = listParamMetas.size();
			int i = 0;
			while(i < count)
			{
				ParameterMeta pMeta = listParamMetas.get(i);
				String strClassName = pMeta.getClassName();
				
				BaseParamInput pInput = null;
				if(strClassName.equals("OptionIntParamInput"))
				{
					pInput = new OptionIntParamInput(composite, SWT.NONE, pMeta, this);
				}
				else if(strClassName.equals("OptionStringParamInput"))
				{
					pInput = new OptionStringParamInput(composite, SWT.NONE, pMeta, this);
				}
				else if(strClassName.equals("IntParamInput"))
				{
					pInput = new IntParamInput(composite, SWT.NONE, pMeta, this);
				}
				else if(strClassName.equals("StringParamInput"))
				{
					pInput = new StringParamInput(composite, SWT.NONE, pMeta, this);
				}
				else if(strClassName.equals("SelectParamInput"))
				{
					pInput = new SelectParamInput(composite, SWT.NONE, pMeta, this);
				}
				else if(strClassName.equals("TagParamInput"))
				{
					pInput = new TagParamInput(composite, SWT.NONE, pMeta, this);
				}
				
				if(pInput != null)
				{
					AddParamInput(pInput);
				}
				
				i++;
			}
			
		}
		
		Label tm = new Label(composite, SWT.NONE);
		tm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridData gridDataList_tm=new GridData(GridData.FILL_BOTH);
		gridDataList_tm.verticalSpan=1;
		tm.setLayoutData(gridDataList_tm);
		
		Label tmlDirctStr = new Label(composite, SWT.NONE);
		tmlDirctStr.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridData gridDataList_tml=new GridData(GridData.FILL_BOTH);
		gridDataList_tml.verticalAlignment = SWT.BOTTOM;
		gridDataList_tm.verticalSpan=1;
		tmlDirctStr.setLayoutData(gridDataList_tml);
		tmlDirctStr.setText("Directive:");
		dirctString = new Label(composite, SWT.BORDER);
		dirctString.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridData gridDataList_drctstr=new GridData(GridData.FILL_BOTH);
		gridDataList_drctstr.verticalSpan=1;
		dirctString.setLayoutData(gridDataList_drctstr);
		
		if(dirct != null)
		{
			dirctString.setText((dirct.toString()).trim());
		}
		else
		{
			dirctString.setText(dMeta.getName() + " ;");
		}
		
		Label tmlTips = new Label(composite, SWT.NONE);
		tmlTips.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		gridDataList_tm.verticalSpan=1;
		GridData gd_tmlTips = new GridData(GridData.FILL_BOTH);
		gd_tmlTips.verticalAlignment = SWT.BOTTOM;
		tmlTips.setLayoutData(gd_tmlTips);
		tmlTips.setText("Tips:");
		dirctTips = new Label(composite, SWT.BORDER);
		dirctTips.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		gridDataList_drctstr.verticalSpan=1;
		dirctTips.setLayoutData(new GridData(GridData.FILL_BOTH));
		dirctTips.setText(this.dirctTipsStr);
		
	    return composite;
   }
	
	protected void okPressed()
	{
		valueParams.clear();
		
		if(listParamInput==null || listParamInput.isEmpty())
		{
			return;
		}
		
		for(ParamInput pinpt : listParamInput)
		{
			valueParams.add(pinpt.getParameter());
		}
		
		super.okPressed();
	}
	
	public List<Parameter> getParams()
	{
		return valueParams;
	}
	
	public void updateDirctString()
	{
		valueParams.clear();
		if(listParamInput==null || listParamInput.isEmpty())
		{
			return;
		}
		for(ParamInput pinpt : listParamInput)
		{
			Parameter tmParam = pinpt.getParameter();
			if(tmParam != null)
			{
				valueParams.add(tmParam);
			}
		}
		
		String dirctStr = dMeta.getName();//dirct.getName();
		if(valueParams!=null && !valueParams.isEmpty())
		{
			Parameter param = null;
			int count = valueParams.size();
			int i = 0;
			while(i < count)
			{
				param = valueParams.get(i);
				dirctStr = dirctStr + " " + param.toString();
				i++;
			}
			dirctStr = dirctStr + ";";
		}
		dirctStr = dirctStr.trim();
		if(!dirctStr.endsWith(";"))
		{
			dirctStr = dirctStr + " ;";
		}
		if(dirctString!=null)
		{
			dirctString.setText(dirctStr);
		}
	}
	
}
