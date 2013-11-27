package org.cs2c.vcenter.dialog;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Configurator;
import org.cs2c.vcenter.composites.BlockInput;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.metadata.MetaManager;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class BlockConfigDialog extends Dialog {
	
	private Block oldBlock = null;
	private Block newBlock = null;
	
	private MiddlewareFactory middleware = null;
	private String blockOutNames = null;
	private String blockIndex = null;
	private Configurator orc = null;
	
	private String blockMetaType = null;
	
	private TabFolder tabFolder = null;

	private Hashtable<String, TabItem> htGroupTItems = new Hashtable<String, TabItem>();
	private Hashtable<String, BlockInput> htGroupBInputs = new Hashtable<String, BlockInput>();
	private BlockInput bInput = null;
	
	private TreeElement input;
	private String blockType = null;
	
	private BlockMeta bMeta = new BlockMeta();
	List<String> blockGroups = new ArrayList<String>();

	
	public BlockConfigDialog(Shell parentShell, BlockConfigInfo bcInfo) {
		super(parentShell);
		// TODO Auto-generated constructor stub
		
		this.middleware = this.input.getMiddlewareFactory();
		this.blockType = this.input.getBlocktype();
		this.blockOutNames = this.input.getOuterBlockNames();
		this.blockIndex = this.input.getBlockIndex();
		this.orc = this.middleware.getConfigurator();
		
		String sType[] = blockType.split(" ");
		this.blockMetaType = sType[0];
		
		try {
			if(this.blockType == "main")
			{
				this.oldBlock = this.orc.getRootBlock();
			}
			else
			{
				this.oldBlock = this.orc.getBlocks(blockType, blockOutNames).get(Integer.parseInt(blockIndex));
			}
			newBlock = oldBlock;
		} catch (RemoteException e) {
			e.printStackTrace();
			MessageDialog.openError(new Shell(), "Error", e.getMessage());
			return;
		}
		
		//Composite
		Composite cmpsite = (Composite)super.getDialogArea();
		
		cmpsite.layout(true);
		cmpsite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		cmpsite.setLayout(new GridLayout(1,false));

		
		this.blockType = input.getBlocktype();
		
		MetaManager mmanager = MetaManager.getInstance();
		bMeta = mmanager.getBlockMeta(this.blockType);
		blockGroups = bMeta.getGroups();
		
		int countGroups = 0;
		if(blockGroups == null && blockGroups.isEmpty())
		{
			return;
		}
		else
		{
			countGroups = blockGroups.size();
		}
		
		if(countGroups == 1)
		{
			String subGroupName = blockGroups.get(0);
			bInput = new BlockInput(cmpsite, SWT.NONE, input, this.oldBlock, bMeta, subGroupName);
		    
			bInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			GridData gridDataList=new GridData(GridData.FILL_BOTH);
			bInput.setLayoutData(gridDataList);
		}
		else
		{
			this.tabFolder = new TabFolder(cmpsite, SWT.NONE);
		    
			this.tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			GridData gridDataList=new GridData(GridData.FILL_BOTH);
			this.tabFolder.setLayoutData(gridDataList);
			
			int i = 0;
			while(i < countGroups)
			{
				String subGroupName = blockGroups.get(i);
				
				TabItem tbi = new TabItem(this.tabFolder, SWT.NONE);
				tbi.setText(subGroupName);
				
				BlockInput cpstBlockInput = new BlockInput(
						this.tabFolder, SWT.NONE, input, this.oldBlock, bMeta, subGroupName);//this.name, subGroupName, this.middleware);
				tbi.setControl(cpstBlockInput);
				
				htGroupTItems.put(subGroupName, tbi);
				htGroupBInputs.put(subGroupName, cpstBlockInput);
				
				i++;
			}
		}
		
	}

	public BlockConfigDialog(IShellProvider parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

}
