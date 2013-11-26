package org.cs2c.vcenter.dialog;

import java.util.ArrayList;
import java.util.List;

import org.cs2c.vcenter.composites.IntParamInput;
import org.cs2c.vcenter.composites.OptionIntParamInput;
import org.cs2c.vcenter.composites.OptionStringParamInput;
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
	
	private DirectiveMeta dMeta = null;
	private String directiveString = null;
	
	private String directiveName = null;
	private String directiveValue = null;
	/**
	 * @wbp.parser.constructor
	 */
	
	public DirectiveInput(Shell parent, DirectiveMeta dMeta) {
		super(parent);
		
		this.dMeta = dMeta;
		this.directiveString = dMeta.getName();

	}
	
	protected Control createDialogArea(Composite parent) {
		
		//StringParamInput
		//        以普通字符串作为指令参数。
		//IntParamInput
		//        以Spinner获取的整数作为指令参数，可有单位，单位提供Combo进行选择。
		//SelectParamInput
		//        以Combo指定的可选值中选择作为指令的参数
		//OptionIntParamInput
		//        以“[name]=[value]”形式的指令参数，其中[value]需提供spinner获取整数值，可能有单位，如open_file_cache max=1000 inactive=20s
		//OptionStringParamInput
		//         以“[name]=[value]”形式的指令参数，其中[value]需提供Text获取普通字符串值。
		
		
		//Composite
		Composite composite = (Composite)super.createDialogArea(parent);

		//Layout
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
				if(strClassName == "OptionIntParamInput")
				{
					//OptionIntParamInput
					OptionIntParamInput oppInput = new OptionIntParamInput(composite, SWT.NONE);
					oppInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_opp=new GridData(GridData.FILL_BOTH);
					gridDataList_opp.verticalSpan=1;
				    oppInput.setLayoutData(gridDataList_opp);
				}
				else if(strClassName == "OptionIntParamInput")
				{
					//OptionStringParamInput
					OptionStringParamInput ospInput = new OptionStringParamInput(composite, SWT.NONE);
				    ospInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_osp=new GridData(GridData.FILL_BOTH);
					gridDataList_osp.verticalSpan=1;
				    ospInput.setLayoutData(gridDataList_osp);
				}
				else if(strClassName == "OptionIntParamInput")
				{
					//IntParamInput
					IntParamInput ipInput = new IntParamInput(composite, SWT.NONE);
				    ipInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_ip=new GridData(GridData.FILL_BOTH);
					gridDataList_ip.verticalSpan=1;
					ipInput.setLayoutData(gridDataList_ip);
				}
				else if(strClassName == "OptionIntParamInput")
				{
					//StringParamInput
					StringParamInput strpInput = new StringParamInput(composite, SWT.NONE);
					strpInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_strp=new GridData(GridData.FILL_BOTH);
					gridDataList_strp.verticalSpan=1;
					strpInput.setLayoutData(gridDataList_strp);
				}
				else if(strClassName == "OptionIntParamInput")
				{
					//SelectParamInput
					SelectParamInput slctpInput = new SelectParamInput(composite, SWT.NONE);
					slctpInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_slctp=new GridData(GridData.FILL_BOTH);
					gridDataList_slctp.verticalSpan=1;
					slctpInput.setLayoutData(gridDataList_slctp);
				}
				else if(strClassName == "OptionIntParamInput")
				{
					//TagParamInput
					TagParamInput tagpInput = new TagParamInput(composite, SWT.NONE);
					tagpInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					GridData gridDataList_tagp=new GridData(GridData.FILL_BOTH);
					gridDataList_tagp.verticalSpan=1;
					tagpInput.setLayoutData(gridDataList_tagp);
				}
				
				i++;
			}
		}
		
	    return composite;
   }
	
	public String getEleValue()
	{
		return directiveString;
	}

}
