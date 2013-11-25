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
	
	//负责输入一个Block在内的各种指令。包括一个List 框，添加按钮，编辑按钮，删除按钮。
	
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
		
		//执行步骤:
		//1、初始化input、middleware，Block Name，Block Type，Block OutNames，Block Index，Configurator(连接)，Block Meta，子组(Block Sub Group)等信息;
		//2、根据Block Name、Block Type、Block Index获取Block;
		//3、页面布局,并添加按钮响应(3-1,3-2,3-3);
		//4、更新List;
		
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
				
				//执行步骤:
				//1、获取对应本Block的元数据信息(directiveMetas,blockMetas);
				//2、根据directiveMetas和blockMetas，创建Name List(包含所有directiveMetas和blockMetas的名字的List);
				//3、将已添加到本Block的Directive，Block的名字从Name List中删除，从而生成可添加的Name List;
				//4、以可添加的Name List为数据源，创建ElementSelector;
				//5、若ElementSelector::open()返回OK，即点击OK按钮导致ElementSelector退出，则根据所选Directive，Block的Name和组创建参数设置对话框ElementSetDialog;
				//6、若ElementSetDialog::open()返回OK，即点击OK按钮导致ElementSetDialog退出，则根据所设置的Directive，Block参数值更新Block,并更新List.
				//7、若添加Directive，Block成功，则更新flagChanged为TRUE。
				
				//Step 1 --- 上述Step-3
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
						//判断是否可重复！！！
						//判断是否可重复！！！
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
						//判断是否可重复！！！
						//判断是否可重复！！！
						elementNames.remove(blocks.get(i).getName()+" {...}");
						i++;
					}
				}
				
				//Step 4
				ElementSelector eleSelector = new ElementSelector(new Shell(), elementNames);
				
				//Step 5
				if(Window.OK == eleSelector.open())
				{
					//1、获取选中的Element名selEleName;
					//2、判断selEleName中是否包含" {...}";若有则为Block，若没有则为Directive;
					//3、
					//4、
					
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
							//获取设置的Directive信息
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

				//执行步骤:
				//1、获取List控件中被选中的项的值,即选择的Directive，Block的directiveString,blockString;
				//2、以directiveString,blockString为参数，创建ElementSetDialog;
				//3、若ElementSetDialog::open()返回OK，即点击OK按钮导致ElementSetDialog退出，则根据所设置的Directive参数值更新Block,并更新List.
				//4、若添加Directive成功，则更新flagChanged为TRUE。
				
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
					//获取设置的Directive信息
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

				//执行步骤:
				//1、获取List控件中被选中的项的值,即选择的Directive的directiveString;
				//2、以directiveString为参数，创建RecDirective;
				//3、从本Block中删除RecDirective,并从List控件中删除对应的Directive项以更新List控件;
				//4、若添加Directive成功，则更新flagChanged为TRUE。
				
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
		//执行步骤:
		//1、更新对应本Block的成员变量Directives，Blocks;
		//2、清空List数据;
		//3、将成员变量Directives，Blocks中的数据更新至List中。
		
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
