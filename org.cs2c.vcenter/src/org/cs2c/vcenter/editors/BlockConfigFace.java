package org.cs2c.vcenter.editors;

import java.util.Hashtable;
import java.util.List;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Configurator;
import org.cs2c.nginlib.config.RecBlock;
import org.cs2c.vcenter.composites.BlockInput;
import org.cs2c.vcenter.dialog.BlockElementInfo;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.metadata.MetaManager;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.SWT;

public class BlockConfigFace extends EditorPart implements ISaveablePart2 {
	
	public static final String ID="org.cs2c.vcenter.editors.BlockConfigFace";
	
	private Block oldBlock = null;
	private Block newBlock = null;
	private String blockName = null;
	
	private TabFolder tabFolder = null;

	private Hashtable<String, TabItem> htGroupTItems = new Hashtable<String, TabItem>();
	private Hashtable<String, BlockInput> htGroupBInputs = new Hashtable<String, BlockInput>();
	private BlockInput bInput = null;
	
	private TreeElement input = null;
	private String blockType = null;
	
	private BlockMeta bMeta = null;
	List<String> blockGroups = null;
	
	
	private MiddlewareFactory middleware = null;
	private String blockOutNames = null;
	private String blockIndex = null;
	private Configurator orc = null;
	
	private String blockMetaType = null;
	
	
	private boolean dirty;
	String partName = "";
	
	
	public BlockConfigFace() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		
		if(MessageDialog.openConfirm(getEditorSite().getShell(),
				"Question", "Are you sure to save?"))
		{
		}
		else
		{
			return;
		}
		
		try {
			orc.replace(this.oldBlock, this.newBlock, this.blockOutNames);
		} catch (RemoteException e) {
			e.printStackTrace();
			MessageDialog.openError(new Shell(), "Error", e.getMessage());
			return;
		}
		
		if(bInput != null && bInput.isChanged())
		{
			bInput.benchmark();
		}
		else
		{
			if(blockGroups!=null && !blockGroups.isEmpty())
			{
				int count = 0;
				int i = 0;
				while(i < count)
				{
					BlockInput blkinput = htGroupBInputs.get(blockGroups.get(i));
					blkinput.benchmark();
					i++;
				}
			}
		}
		
		dirty = false;
		firePropertyChange(ISaveablePart2.PROP_DIRTY);
		
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
		
		this.middleware = this.input.getMiddlewareFactory();
		this.blockType = this.input.getBlocktype();
		this.blockOutNames = this.input.getOuterBlockNames();
		this.blockIndex = this.input.getBlockIndex();
		
		this.orc = this.middleware.getConfigurator();
		
		String sType[] = this.blockType.split(" ");
		this.blockMetaType = sType[0];
		
		try {
			if(this.blockType == "main")
			{
				this.oldBlock = this.orc.getRootBlock();
			}
			else
			{
				List<Block> tmBlocks = this.orc.getBlocks(this.blockType, this.blockOutNames);
				this.oldBlock = tmBlocks.get(Integer.parseInt(this.blockIndex));
			}
			this.blockName = this.oldBlock.getName();
			
			this.newBlock = this.orc.newBlock();
			this.newBlock.setName(this.blockName);
			((RecBlock)(this.newBlock)).SetBlockText(this.oldBlock.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
			MessageDialog.openError(new Shell(), "Error", e.getMessage());
			return;
		}
		
	}
	
	public void SetDirty(boolean dirty)
	{
		this.dirty = dirty;
		
		if(dirty)
		{
			if(partName.charAt(0)!='*')
			{
				setPartName("*"+partName);
			}
			else
			{
				setPartName(partName);
			}
		}
		else
		{
			if(partName.charAt(0)!='*')
			{
				setPartName(partName);
			}
			else
			{
				setPartName(partName.substring(1, partName.length()));
			}
		}
		
	}

	@Override
	public boolean isDirty() {
		
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {

		MetaManager mmanager = MetaManager.getInstance();
		bMeta = mmanager.getBlockMeta(this.blockMetaType);
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
		
		BlockElementInfo bcInfo = new BlockElementInfo();
		bcInfo.setBlock(this.newBlock);
		bcInfo.setBlockType(this.blockType);
		bcInfo.setBlockMeta(this.bMeta);
		bcInfo.setMiddleware(middleware);
		
		if(countGroups == 1)
		{
			String subGroupName = blockGroups.get(0);
			bInput = new BlockInput(parent, SWT.NONE, bcInfo, subGroupName, this);
		}
		else
		{
			this.tabFolder = new TabFolder(parent, SWT.NONE);
			
			int i = 0;
			BlockInput[] bInputs = new BlockInput[countGroups];
			while(i < countGroups)
			{
				String subGroupName = blockGroups.get(i);
				
				TabItem tbi = new TabItem(this.tabFolder, SWT.NONE);
				tbi.setText(subGroupName);
				
				bInputs[i] = new BlockInput(this.tabFolder, SWT.NONE, bcInfo, subGroupName, this);
				
				tbi.setControl(bInputs[i]);
				
				htGroupTItems.put(subGroupName, tbi);
				htGroupBInputs.put(subGroupName, bInputs[i]);
				
				i++;
			}
		}
	
	}

	@Override
	public void dispose(){
		super.dispose();
		
	}
	@Override
	public void setFocus() {

	}

	@Override
	public int promptToSaveOnClose() {
		if(dirty)
		{
			if(MessageDialog.openConfirm(getEditorSite().getShell(),
					"Warning", "Modification of '"+partName+"' is not saved, are you sure to close without saving?"))
			{
				return ISaveablePart2.NO;
			}
			else
			{
				return ISaveablePart2.CANCEL;
			}
		}
		
		return YES;
	}
	
	@Override
	protected void setPartName(String partName)
	{
		this.partName = partName;
		super.setPartName(partName);
	}
	
}
