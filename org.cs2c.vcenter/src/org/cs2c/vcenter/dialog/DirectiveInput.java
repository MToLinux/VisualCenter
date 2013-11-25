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
		//        ����ͨ�ַ�����Ϊָ�������
		//IntParamInput
		//        ��Spinner��ȡ��������Ϊָ����������е�λ����λ�ṩCombo����ѡ��
		//SelectParamInput
		//        ��Comboָ���Ŀ�ѡֵ��ѡ����Ϊָ��Ĳ���
		//OptionIntParamInput
		//        �ԡ�[name]=[value]����ʽ��ָ�����������[value]���ṩspinner��ȡ����ֵ�������е�λ����open_file_cache max=1000 inactive=20s
		//OptionStringParamInput
		//         �ԡ�[name]=[value]����ʽ��ָ�����������[value]���ṩText��ȡ��ͨ�ַ���ֵ��
		//DirectiveInput
		//         �����ĳ��ָ��������룬ָ����ܾ���һ�����߶����������������Ҫ�ض��Զ���ؼ���ȡ�û������룬����Ŀؼ���������meta���������
		//BlockInput
		//        ��������һ��Block���ڵĸ���ָ�����һ��List ����Ӱ�ť���༭��ť��ɾ����ť��
		//ElementSelector
		//        ������Eclipse�нӿں���ѡ��ʱ��ѡ����������������
		
		//1��StringParamInput
		textParam = new Text(parent, SWT.NONE);
		
	    return composite;
   }
	
	public String getEleValue()
	{
		return directiveString;
	}

}
