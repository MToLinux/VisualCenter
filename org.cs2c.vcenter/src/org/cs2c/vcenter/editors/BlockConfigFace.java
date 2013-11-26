package org.cs2c.vcenter.editors;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.cs2c.vcenter.composites.BlockInput;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.metadata.MetaManager;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.SWT;

public class BlockConfigFace extends EditorPart {
	
	//根据树形节点的类型，将配置编辑模块的EditorPart分为：BasicConfigFace(Nginx主节点配置信息)，BlockConfigFace(块配置信息)
	
	public static final String ID="org.cs2c.vcenter.editors.BlockConfigFace";
	
	private TabFolder tabFolder = null;
	//if Groups
	//Hashtable<GroupName, TabItem>
	private Hashtable<String, TabItem> htGroupTItems = new Hashtable<String, TabItem>();
	//Hashtable<GroupName, BlockInput>
	private Hashtable<String, BlockInput> htGroupBInputs = new Hashtable<String, BlockInput>();
	//if only one group
	private BlockInput bInput = null;
	
	private TreeElement input;
	private String blockType = null;
	
	private BlockMeta bMeta = new BlockMeta();
	List<String> blockGroups = new ArrayList<String>();
	
	public BlockConfigFace() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if(bInput != null && bInput.isChanged())
		{
			//保存修改的内容
			//修改bInput的状态标识为FALSE
			bInput.setChangedFlag(false);
		}
		else
		{
			//对每个BlockInput执行如下操作:
			//保存修改的内容
			//修改bInput的状态标识为FALSE
			if(blockGroups!=null && !blockGroups.isEmpty())
			{
				int count = 0;
				int i = 0;
				while(i < count)
				{
					//根据子组名，获取BlockInput
					BlockInput blkinput = htGroupBInputs.get(blockGroups.get(i));
					//保存修改的内容
					
					//修改bInput的状态标识为FALSE
					blkinput.setChangedFlag(false);
				}
			}

		}
		
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {

		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());
		this.input=(TreeElement)input;

	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		
		//执行流程：
		//1、根据(TreeElement input)获取选中的Block类型(非Block名);
		//2、根据Block获取Block的元数据信息(子组名列表等);
		//3、判断子组列表是否为空。若为空,则返回;若非空,则获取子组的数量;
		//4、判断子组的数量：
		//	(1)若子组数量为1,则创建对应该子组的BlockInput,并将其设置在parent Composite中;
		//	(2)若子组数量大于1,则执行如下操作：
		//		1)、创建TabFolder;
		//		2)、为每个Block的子组创建TabItem和BlockInput,循环执行如下操作:
		//			a、从子组名列表中获取一个组名;
		//			b、创建对应该子组的TabItem,并调用TabItem::setText()设置TabItem的名为子组名;
		//			c、创建对应该子组的BlockInput,并将其设置在对应的TabItem中;
		//			d、将新建的子组名与TabItem和BlockInput保存至Hashtable中。

		//Step 1
		//this.blockType = input.getBlocktype();
		//Just for test!!! begin by yanbin.jia
		this.blockType = "main";
		//Just for test!!! end by yanbin.jia
		
		//Step 2
		MetaManager mmanager = new MetaManager();
		bMeta = mmanager.getBlockMeta(this.blockType);
		blockGroups = bMeta.getGroups();
		
		//Step 3
		int countGroups = 0;
		if(blockGroups == null && blockGroups.isEmpty())
		{
			return;
		}
		else
		{
			countGroups = blockGroups.size();
		}
		
		//Step 4
		if(countGroups == 1)
		{
			//Step 4-1
			String subGroupName = blockGroups.get(0);
			bInput = new BlockInput(parent, SWT.NONE, input, bMeta, subGroupName);//this.name, subGroupName, this.middleware);
		}
		else
		{
			//Step 4-2
			//Step 4-2-1
			this.tabFolder = new TabFolder(parent, SWT.NONE);
			
			//Step 4-2-2
			int i = 0;
			while(i < countGroups)
			{
				//Step 4-2-2-a
				String subGroupName = blockGroups.get(i);
				
				//Step 4-2-2-b
				TabItem tbi = new TabItem(this.tabFolder, SWT.NONE);
				tbi.setText(subGroupName);
				
				//Step 4-2-2-c
				BlockInput cpstBlockInput = new BlockInput(
						this.tabFolder, SWT.NONE, input, bMeta, subGroupName);//this.name, subGroupName, this.middleware);
				tbi.setControl(cpstBlockInput);
				
				//Step 4-2-2-d
				htGroupTItems.put(subGroupName, tbi);
				htGroupBInputs.put(subGroupName, cpstBlockInput);
				
				i++;
			}
		}

//		//Just for Test!!! begin
//		this.tabFolder = new TabFolder(parent, SWT.NONE);
//		//1
//		TabItem tbtmHttp = new TabItem(this.tabFolder, SWT.NONE);
//		tbtmHttp.setText("Http");
//		BlockInput cpstBlockInput = new BlockInput(this.tabFolder, SWT.NONE, input, "subGroupName");//"", "", middleware);
//		tbtmHttp.setControl(cpstBlockInput);
//		//2
//		//this.tabFolder = new TabFolder(parent, SWT.NONE);
//		TabItem tbtmHttp2 = new TabItem(this.tabFolder, SWT.NONE);
//		tbtmHttp2.setText("Http2");
//		BlockInput cpstBlockInput2 = new BlockInput(this.tabFolder, SWT.NONE, input, "subGroupName");
//		tbtmHttp2.setControl(cpstBlockInput2);
//		//Just for Test!!! end
		
	}

	@Override
	public void dispose(){
		super.dispose();
	}
	@Override
	public void setFocus() {

	}
	
}
