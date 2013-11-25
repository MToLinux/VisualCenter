package org.cs2c.vcenter.composites;

import java.util.ArrayList;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Configurator;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.RecBlock;
import org.cs2c.nginlib.config.RecDirective;
import org.cs2c.vcenter.dialog.DirectiveInput;
import org.cs2c.vcenter.dialog.ElementSelector;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.metadata.DirectiveMeta;
import org.cs2c.vcenter.metadata.MetaManager;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

public class BlockInput extends Composite {
	
	//��������һ��Block���ڵĸ���ָ�����һ��List ����Ӱ�ť���༭��ť��ɾ����ť��
	
	private org.eclipse.swt.widgets.List ctlList;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	boolean flagChanged;
	
	private TreeElement input;
	
	private MiddlewareFactory middleware = null;
	private String blockName = null;
	private String blockType = null;
	private String blockGroup = null;
	private String blockSubGroup = null;
	private String blockOutNames = null;
	private String blockIndex = null;
	private Configurator orc = null;
	
	//Metadata
	private BlockMeta bMeta = new BlockMeta();
	
	//Server Configuration Information
	private Block block = new RecBlock();
	private java.util.List<Directive> directives = new ArrayList<Directive>();
	private java.util.List<Block> blocks = new ArrayList<Block>();

	
	public BlockInput(Composite parent, int style, TreeElement input, BlockMeta blkMeta, String blkSubGroup) {
		super(parent, style);
		
		//ִ�в���:
		//1����ʼ��input��middleware��Block Name��Block Type��Block OutNames��Block Index��Configurator(����)��Block Meta������(Block Sub Group)����Ϣ;
		//2������Block Name��Block Type��Block Index��ȡBlock;
		//3��ҳ�沼��,����Ӱ�ť��Ӧ(3-1,3-2,3-3);
		//4������List;
		
		//Step 1
		this.input = input;
		this.middleware = input.getMiddlewareFactory();
		this.blockName = input.getName();
		this.blockType = input.getBlocktype();
		this.blockOutNames = input.getOuterBlockNames();
		this.blockIndex = input.getBlockIndex();
		this.orc = this.middleware.getConfigurator();
		this.bMeta = blkMeta;
		this.blockSubGroup = blkSubGroup;
		
		//Step 2
		try {
			if(this.blockType == "main")
			{
				this.block = this.orc.getRootBlock();
			}
			else
			{
				this.block = this.orc.getBlocks(blockType, blockOutNames).get(Integer.parseInt(blockIndex));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		//Step 3
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3,false));
	    
		this.ctlList = new List(this, SWT.BORDER | SWT.V_SCROLL);
		this.ctlList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridData gridDataList=new GridData(GridData.FILL_BOTH);
	    gridDataList.verticalSpan=6;
	    this.ctlList.setLayoutData(gridDataList);
		
		new Label(this, SWT.NONE);
		this.btnAdd = new Button(this,SWT.NONE);
		
		//Step 3-1
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				//ִ�в���:
				//1����ȡ��Ӧ��Block��Ԫ������Ϣ(directiveMetas,blockMetas);
				//2������directiveMetas��blockMetas������Name List(��������directiveMetas��blockMetas�����ֵ�List);
				//3��������ӵ���Block��Directive��Block�����ִ�Name List��ɾ�����Ӷ����ɿ���ӵ�Name List;
				//4���Կ���ӵ�Name ListΪ����Դ������ElementSelector;
				//5����ElementSelector::open()����OK�������OK��ť����ElementSelector�˳����������ѡDirective��Block��Name���鴴���������öԻ���ElementSetDialog;
				//6����ElementSetDialog::open()����OK�������OK��ť����ElementSetDialog�˳�������������õ�Directive��Block����ֵ����Block,������List.
				//7�������Directive��Block�ɹ��������flagChangedΪTRUE��
				
				//Step 1 --- ����Step-3
				java.util.List<DirectiveMeta> directiveMetas = new ArrayList<DirectiveMeta>();
				java.util.List<BlockMeta> blockMetas = new ArrayList<BlockMeta>();
				directiveMetas = bMeta.getDirectiveMeta(blockSubGroup);
				blockMetas = bMeta.getBlockMeta(blockSubGroup);
				
				//Step 2
				java.util.List<String> elementNames = new ArrayList<String>();
				
				int count = 0;
				int i = 0;
				if(directiveMetas != null && !directiveMetas.isEmpty())
				{
					count = directiveMetas.size();
					i = 0;
					while(i<count)
					{
						elementNames.add(directiveMetas.get(i).getName());
						i++;
					}
				}
				count = 0;
				i = 0;
				if(blockMetas != null && !blockMetas.isEmpty())
				{
					count = blockMetas.size();
					i = 0;
					while(i<count)
					{
						elementNames.add(blockMetas.get(i).getName()+" {...}");
						i++;
					}
				}
				
				//Step 3
				count = 0;
				i = 0;
				if(directives != null && !directives.isEmpty())
				{
					count = directives.size();
					while(i<count)
					{
						//�ж��Ƿ���ظ�������
						//�ж��Ƿ���ظ�������
						elementNames.remove(directives.get(i).getName());
						i++;
					}
				}
				count = 0;
				i = 0;
				if(blocks != null && !blocks.isEmpty())
				{
					count = blocks.size();
					while(i<count)
					{
						//�ж��Ƿ���ظ�������
						//�ж��Ƿ���ظ�������
						elementNames.remove(blocks.get(i).getName()+" {...}");
						i++;
					}
				}
				
				//Step 4
				ElementSelector eleSelector = new ElementSelector(new Shell(), elementNames);
				
				//Step 5
				if(Window.OK == eleSelector.open())
				{
					//1����ȡѡ�е�Element��selEleName;
					//2���ж�selEleName���Ƿ����" {...}";������ΪBlock����û����ΪDirective;
					//3��
					//4��
					
					//Step 1
					String selEleName = eleSelector.getSelectedElementName();
					
					//Step 2
					int len = selEleName.length();
					if(len > 6 && selEleName.substring(selEleName.length()-6,selEleName.length()).equals(" {...}"))
					{
						BlockInput bInput = null;
					}
					else
					{
						DirectiveInput eldlg = new DirectiveInput(new Shell(), selEleName);
					
						//Step 6
						if(Window.OK == eldlg.open())
						{
							//��ȡ���õ�Directive��Ϣ
							//Stirng selEleValue = eldlg.getEleValue();
							String selEleValue = "DefaultValue";
							
							RecDirective dirct = new RecDirective();
							dirct.SetDirectiveText(selEleName+selEleValue);
							count = directives.size();
							directives.add(count, dirct);
							UpdateListCtl();
							
							//Just for test!!! begin
							//ctlList.add(selEleName+"DefaultValue");
							//Just for test!!! begin
							
							//Step 7
							flagChanged = true;
						}
					}//else
					
					
				}
			}
		});
		
		this.btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnAdd.setText("Add...");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		this.btnEdit = new Button(this,SWT.NONE);
		
		//Step 3-2
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {

				//ִ�в���:
				//1����ȡList�ؼ��б�ѡ�е����ֵ,��ѡ���Directive��Block��directiveString,blockString;
				//2����directiveString,blockStringΪ����������ElementSetDialog;
				//3����ElementSetDialog::open()����OK�������OK��ť����ElementSetDialog�˳�������������õ�Directive����ֵ����Block,������List.
				//4�������Directive�ɹ��������flagChangedΪTRUE��
				
				//Step 1
				if(ctlList.getSelectionCount() == 0)
				{
					MessageDialog.openWarning(getShell(),"Question","Please select an option!");
					return;
				}
				String directiveString = ctlList.getItem(ctlList.getSelectionIndex());//.getSelectedElementName();
				RecDirective dirct = new RecDirective();
				dirct.SetDirectiveText(directiveString);
				String directiveName = dirct.getName();
				
				//Step 2
				DirectiveInput eldlg = new DirectiveInput(new Shell(), directiveString);
				
				//Step 3
				if(Window.OK == eldlg.open())
				{
					//��ȡ���õ�Directive��Ϣ
					//Stirng selEleValue = eldlg.get...();
					String selEleValue = "DefaultValue";
					
					directives.remove(dirct);
					dirct.SetDirectiveText(directiveName+selEleValue);
					int count = directives.size();
					directives.add(count, dirct);
					UpdateListCtl();
					
					//Step 4
					flagChanged = true;
				}
			}
		});
		
		this.btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnEdit.setText("Edit...");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		this.btnDelete = new Button(this,SWT.NONE);
		
		//Step 3-3
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {

				//ִ�в���:
				//1����ȡList�ؼ��б�ѡ�е����ֵ,��ѡ���Directive��directiveString;
				//2����directiveStringΪ����������RecDirective;
				//3���ӱ�Block��ɾ��RecDirective,����List�ؼ���ɾ����Ӧ��Directive���Ը���List�ؼ�;
				//4�������Directive�ɹ��������flagChangedΪTRUE��
				
				//Step 1
				if(ctlList.getSelectionCount() == 0)
				{
					MessageDialog.openWarning(new Shell(),"Warning","Please select an option!");
					return;
				}
				if(false == MessageDialog.openQuestion(getShell(), "Question", "Are you sure to delete the option?"))
				{
					return;
				}
				String directiveString = ctlList.getItem(ctlList.getSelectionIndex());//.getSelectedElementName();
				
				//Step 2
				RecDirective dirct = new RecDirective();
				dirct.SetDirectiveText(directiveString);
				
				//Step 3
				directives.remove(dirct);
				UpdateListCtl();
				
				//Step 4
				flagChanged = true;
			}
		});
		
		this.btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnDelete.setText("   Delete   ");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		//Step 4
		UpdateListCtl();
		
	}
	
	private void UpdateListCtl()
	{
		//ִ�в���:
		//1�����¶�Ӧ��Block�ĳ�Ա����Directives��Blocks;
		//2�����List����;
		//3������Ա����Directives��Blocks�е����ݸ�����List�С�
		
		//Step 1
		try {
			this.directives = this.block.getDirectives();
			this.blocks = this.block.getBlocks();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		//Just for test!!! begin
		//this.directives = null;
		//this.blocks = null;
		//Just for test!!! end
		
		//Step 2
		ctlList.removeAll();
		
		//Step 3
		int i = 0;
		if(this.directives != null && !this.directives.isEmpty())
		{
			while(i<this.directives.size())
			{
				ctlList.add(directives.get(i).toString());
				i++;
			}
		}
		if(this.blocks != null && !this.blocks.isEmpty())
		{
			i = 0;
			while(i<this.blocks.size())
			{
				ctlList.add(blocks.get(i).toString());
				i++;
			}
		}
		
	}
	
	public List getList()
	{
		return ctlList;
	}
	public Button getAddButton()
	{
		return btnAdd;
	}
	public Button getEditButton()
	{
		return btnEdit;
	}
	public Button getDeleteButton()
	{
		return btnDelete;
	}
	
	public boolean isChanged()
	{
		return flagChanged;
	}
	public void setChangedFlag(boolean flag)
	{
		flagChanged = flag;
	}
	
	public Block getBlock()
	{
		return this.block;
	}
	
	public void benchmark()
	{
		
	}
	
	public void setMeta(BlockMeta meta)
	{
		bMeta = meta;
	}
	
}
