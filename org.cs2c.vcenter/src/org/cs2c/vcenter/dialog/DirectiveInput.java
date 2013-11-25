package org.cs2c.vcenter.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DirectiveInput extends Dialog {
	
	private String directiveString = null;
	
	private org.eclipse.swt.widgets.List ctlList;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	
	private Text textParam;

	/**
	 * @wbp.parser.constructor
	 */
	
	public DirectiveInput(Shell parent, String directiveString) {
		super(parent);
		this.directiveString = directiveString;

	}
	
	protected Control createDialogArea(Composite parent) {
		
		//Composite
		Composite composite = (Composite)super.createDialogArea(parent);

		//Layout
		composite.layout(true);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(3,false));
	    
		//1
		ctlList = new List(composite, SWT.BORDER | SWT.V_SCROLL);
		ctlList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridData gridDataList=new GridData(GridData.FILL_BOTH);
	    gridDataList.verticalSpan=6;
	    ctlList.setLayoutData(gridDataList);
		
	    //2
		new Label(composite, SWT.NONE);
		
		//3
		btnAdd = new Button(composite,SWT.NONE);
			
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAdd.setText("Add...");
		
		//4
		new Label(composite, SWT.NONE);
		
		//5
		new Label(composite, SWT.NONE);
		
		//6
		new Label(composite, SWT.NONE);
		
		//7
		this.btnEdit = new Button(composite,SWT.NONE);
		
		this.btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnEdit.setText("Edit...");
		
		//8
		new Label(composite, SWT.NONE);
		
		//9
		new Label(composite, SWT.NONE);
		
		//10
		new Label(composite, SWT.NONE);
		
		//11
		btnDelete = new Button(composite,SWT.NONE);
		
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelete.setText("   Delete   ");
		
		//12
		new Label(composite, SWT.NONE);
		
		//13
		new Label(composite, SWT.NONE);
		
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
		//DirectiveInput
		//         负责对某个指令进行输入，指令可能具有一个或者多个参数，而参数需要特定自定义控件获取用户的输入，具体的控件类型依据meta对象而定。
		//BlockInput
		//        负责输入一个Block在内的各种指令。包括一个List 框，添加按钮，编辑按钮，删除按钮。
		//ElementSelector
		//        类似于Eclipse中接口和类选择时的选择器，允许搜索。
		
		//1、StringParamInput
		textParam = new Text(parent, SWT.NONE);
		
	    return composite;
   }
	
	public String getEleValue()
	{
		return directiveString;
	}

}
