package org.cs2c.vcenter.dialog;

import java.util.ArrayList;
import java.util.List;

import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecDirective;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DirectiveInput extends Dialog {
	
	private List<ParamInput> listParamInput = new ArrayList<ParamInput>();
	private List<Parameter> valueParams = new ArrayList<Parameter>();
	
	private Directive dirct = new RecDirective();
	private DirectiveMeta dMeta = null;
	
	static Shell newShell = new Shell();

	
	/**
	 * @wbp.parser.constructor
	 */
	
	public DirectiveInput(Shell parent, Directive direct, DirectiveMeta dMeta) {
		super(parent);
		
		this.dirct = direct;
		this.dMeta = dMeta;
		if(this.dirct != null)
		{
			List<Parameter> params = new ArrayList<Parameter>();
			params = this.dirct.getParameters();
			for(Parameter param : params)
			{
				valueParams.add(param);
			}
			
			//Unknown the Parameter Type£¡£¡£¡
		}
		
	}
	
	protected Control createDialogArea(Composite parent) {
		
		getShell().setText(this.dMeta.getName());
		
		Composite composite = (Composite)super.createDialogArea(parent);

		composite.layout(true);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(1,false));
		
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
				if(strClassName.equals("OptionIntParamInput"))
				{
					//OptionIntParamInput
					OptionIntParamInput oppInput = new OptionIntParamInput(composite, SWT.NONE, pMeta);
					//oppInput.setMeta(pMeta);
					oppInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_opp=new GridData(GridData.FILL_BOTH);
					gridDataList_opp.verticalSpan=1;
				    oppInput.setLayoutData(gridDataList_opp);
				    
				    if(this.dirct != null)
					{
				    	this.dirct.getParameters();
					}
				    
				    listParamInput.add(oppInput);
				}
				else if(strClassName.equals("OptionStringParamInput"))
				{
					//OptionStringParamInput
					OptionStringParamInput ospInput = new OptionStringParamInput(composite, SWT.NONE, pMeta);
					//ospInput.setMeta(pMeta);
				    ospInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_osp=new GridData(GridData.FILL_BOTH);
					gridDataList_osp.verticalSpan=1;
				    ospInput.setLayoutData(gridDataList_osp);
				    
				    if(this.dirct != null)
					{
					}
				    
				    listParamInput.add(ospInput);
				}
				else if(strClassName.equals("IntParamInput"))
				{
					//IntParamInput
					IntParamInput ipInput = new IntParamInput(composite, SWT.NONE, pMeta);
					//ipInput.setMeta(pMeta);
				    ipInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_ip=new GridData(GridData.FILL_BOTH);
					gridDataList_ip.verticalSpan=1;
					ipInput.setLayoutData(gridDataList_ip);
					
					if(this.dirct != null)
					{
					}
					
					listParamInput.add(ipInput);
				}
				else if(strClassName.equals("StringParamInput"))
				{
					//StringParamInput
					StringParamInput strpInput = new StringParamInput(composite, SWT.NONE, pMeta);
					//strpInput.setMeta(pMeta);
					strpInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_strp=new GridData(GridData.FILL_BOTH);
					gridDataList_strp.verticalSpan=1;
					strpInput.setLayoutData(gridDataList_strp);
					
					if(this.dirct != null)
					{
					}
					
					listParamInput.add(strpInput);
				}
				else if(strClassName.equals("SelectParamInput"))
				{
					//SelectParamInput
					SelectParamInput slctpInput = new SelectParamInput(composite, SWT.NONE, pMeta);
					//slctpInput.setMeta(pMeta);
					slctpInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_slctp=new GridData(GridData.FILL_BOTH);
					gridDataList_slctp.verticalSpan=1;
					slctpInput.setLayoutData(gridDataList_slctp);
					
					if(this.dirct != null)
					{
					}
					
					listParamInput.add(slctpInput);
				}
				else if(strClassName.equals("TagParamInput"))
				{
					//TagParamInput
					TagParamInput tagpInput = new TagParamInput(composite, SWT.NONE, pMeta);
					//tagpInput.setMeta(pMeta);
					tagpInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_tagp=new GridData(GridData.FILL_BOTH);
					gridDataList_tagp.verticalSpan=1;
					tagpInput.setLayoutData(gridDataList_tagp);
					
					if(this.dirct != null)
					{
					}
					
					listParamInput.add(tagpInput);
				}
				
				i++;
			}
		}
		
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

}
